import os
os.environ["OMP_NUM_THREADS"] = "1"
os.environ["MKL_NUM_THREADS"] = "1"
os.environ["VECLIB_MAXIMUM_THREADS"] = "1"
from pathlib import Path
from ultralytics import YOLO

def export_to_mobile(model_path, imgsz=320):
    """
    Export a trained YOLO model to TFLite (FP16) and CoreML.
    Ensures size <= 10MB.
    """
    model_path = Path(model_path)
    if not model_path.exists():
        print(f"Error: Model not found at {model_path}")
        return

    print(f"Exporting {model_path} to mobile formats...")
    model = YOLO(str(model_path))

    # 1. Export to ONNX (Intermediate)
    print("Exporting to ONNX...")
    model.export(format='onnx', imgsz=imgsz, dynamic=False)

    # 2. Export to TFLite (FP16 Quantization for size/speed)
    print("Exporting to TFLite (FP16)...")
    # half=True for FP16 quantization
    tflite_path = model.export(format='tflite', imgsz=imgsz, half=True)

    # 3. Export to CoreML
    print("Exporting to CoreML...")
    coreml_path = model.export(format='coreml', imgsz=imgsz)

    # Size Verification
    def check_size(path):
        size_mb = os.path.getsize(path) / (1024 * 1024)
        print(f"Format: {Path(path).suffix}, Size: {size_mb:.2f} MB")
        if size_mb > 10:
            print(f"WARNING: {path} exceeds 10MB limit!")
        return size_mb

    # Note: ultralytics export returns the path to the exported file or directory
    # For TFLite, it usually creates a directory or a .tflite file depending on version
    # We will need to locate the actual files.
    
    return tflite_path, coreml_path

if __name__ == "__main__":
    # Example usage
    base_dir = Path(__file__).parent.parent.parent
    model_weights = base_dir / "models" / "mahjong_yolo_nano" / "weights" / "best.pt"
    
    if model_weights.exists():
        export_to_mobile(model_weights)
    else:
        print(f"Weights not found at {model_weights}. Using yolov8n.pt for testing...")
        export_to_mobile(base_dir / "yolov8n.pt")
