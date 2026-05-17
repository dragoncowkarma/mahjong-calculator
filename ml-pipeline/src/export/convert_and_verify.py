import os
import shutil
from pathlib import Path
from ultralytics import YOLO
import numpy as np
import torch

def check_size(path):
    """Returns file size in MB."""
    size_mb = os.path.getsize(path) / (1024 * 1024)
    return size_mb

def convert_model(pt_path, imgsz=320):
    """
    Converts PT model to ONNX, TFLite (FP16), and CoreML.
    Returns a dictionary of paths to exported models.
    """
    print(f"--- Exporting {pt_path} ---")
    model = YOLO(pt_path)
    base_name = Path(pt_path).stem
    weights_dir = Path(pt_path).parent

    exports = {}

    # 1. Export to ONNX
    print("Exporting to ONNX...")
    onnx_path = model.export(format='onnx', imgsz=imgsz, dynamic=False, simplify=True)
    exports['onnx'] = onnx_path

    # 2. Export to TFLite (FP16)
    print("Exporting to TFLite (FP16)...")
    # half=True ensures FP16 quantization
    tflite_path = model.export(format='tflite', imgsz=imgsz, half=True)
    # Ultralytics might return a path to a folder or the file
    if Path(tflite_path).is_dir():
        # Look for the .tflite file inside
        tflite_files = list(Path(tflite_path).glob("*.tflite"))
        if tflite_files:
            exports['tflite'] = str(tflite_files[0])
    else:
        exports['tflite'] = tflite_path

    # 3. Export to CoreML
    print("Exporting to CoreML...")
    coreml_path = model.export(format='coreml', imgsz=imgsz)
    exports['coreml'] = coreml_path

    return exports

def verify_parity(pt_path, exports, val_data_dir, num_samples=50):
    """
    Verifies parity between PT and exported formats.
    Target: Difference <= 1%.
    """
    print("--- Running Parity Verification ---")
    pt_model = YOLO(pt_path)
    val_images = list(Path(val_data_dir).glob("*.jpg"))[:num_samples]
    
    if not val_images:
        print("Error: No validation images found.")
        return False

    # Use a low confidence threshold to get more detections for comparison
    CONF_THRESHOLD = 0.001
    
    parity_results = {}
    
    # Baseline: PT model
    print(f"Collecting baseline from {pt_path}...")
    pt_scores = []
    for img in val_images:
        res = pt_model(img, conf=CONF_THRESHOLD, verbose=False)[0]
        if len(res.boxes) > 0:
            pt_scores.append(res.boxes.conf.mean().item())
        else:
            pt_scores.append(0.0)
    avg_pt = np.mean(pt_scores)
    print(f"PT Baseline Average Confidence: {avg_pt:.4f}")

    all_passed = True
    for fmt, path in exports.items():
        if fmt == 'onnx': continue # Skip ONNX for parity check as it's intermediate
        
        print(f"Verifying {fmt} parity...")
        try:
            m = YOLO(path)
            fmt_scores = []
            for img in val_images:
                res = m(img, conf=CONF_THRESHOLD, verbose=False)[0]
                if len(res.boxes) > 0:
                    fmt_scores.append(res.boxes.conf.mean().item())
                else:
                    fmt_scores.append(0.0)
            
            avg_fmt = np.mean(fmt_scores)
            diff = abs(avg_pt - avg_fmt) * 100
            print(f"{fmt.upper()} Average Confidence: {avg_fmt:.4f} (Diff: {diff:.2f}%)")
            
            if diff > 1.0:
                print(f"WARNING: {fmt.upper()} parity check failed (> 1% diff)")
                all_passed = False
        except Exception as e:
            print(f"Error verifying {fmt}: {e}")
            all_passed = False
            
    return all_passed

def integrate_models(exports, target_dir, version="v1.0"):
    """
    Integrates exported models into the KMP resource directory.
    """
    print(f"--- Integrating Models to {target_dir} ---")
    target_path = Path(target_dir)
    target_path.mkdir(parents=True, exist_ok=True)
    
    for fmt, path in exports.items():
        ext = Path(path).suffix
        if not ext and fmt == 'coreml': ext = ".mlpackage"
        
        dest_name = f"mahjong_detector_{version}{ext}"
        dest_path = target_path / dest_name
        
        print(f"Copying {fmt} to {dest_path}...")
        if Path(path).is_dir():
            if dest_path.exists(): shutil.rmtree(dest_path)
            shutil.copytree(path, dest_path)
        else:
            shutil.copy2(path, dest_path)
            
    print("Integration complete.")

if __name__ == "__main__":
    base_dir = Path(__file__).parent.parent.parent
    pt_model = base_dir / "models" / "mahjong_yolo_nano" / "weights" / "best.pt"
    val_dir = base_dir / "data" / "processed" / "val" / "images"
    resource_dir = base_dir / "app" / "composeApp" / "src/commonMain/composeResources/models"
    
    if not pt_model.exists():
        print(f"Error: PT model not found at {pt_model}")
        exit(1)
        
    # 1. Convert
    exports = convert_model(str(pt_model))
    
    # 2. Size Gate
    print("--- Size Verification ---")
    size_passed = True
    for fmt, path in exports.items():
        size = check_size(path)
        print(f"{fmt.upper()}: {size:.2f} MB")
        if size > 10.0:
            print(f"CRITICAL: {fmt.upper()} exceeds 10MB limit!")
            size_passed = False
            
    # 3. Parity Check
    parity_passed = verify_parity(str(pt_model), exports, str(val_dir))
    
    # 4. Integration
    if size_passed and parity_passed:
        integrate_models(exports, str(resource_dir))
        print("\n🎉 ALL CHECKS PASSED. Models integrated.")
    else:
        print("\n❌ QUALITY GATE FAILED. See logs above.")
        exit(1)
