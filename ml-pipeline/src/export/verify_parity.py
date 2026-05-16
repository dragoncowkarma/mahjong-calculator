import numpy as np
from pathlib import Path
from ultralytics import YOLO

def verify_parity(pt_model_path, tflite_model_path, val_data_dir, num_samples=100):
    """
    Verify accuracy parity between PT and TFLite models.
    Target: Difference <= 1%.
    """
    print(f"Starting parity check for {num_samples} samples...")
    
    pt_model = YOLO(pt_model_path)
    tflite_model = YOLO(tflite_model_path)
    
    val_images = list(Path(val_data_dir).glob("*.jpg"))[:num_samples]
    
    pt_results = []
    tflite_results = []
    
    for img_path in val_images:
        # Run inference
        res_pt = pt_model(img_path, verbose=False)[0]
        res_tf = tflite_model(img_path, verbose=False)[0]
        
        # Simple metric: mean confidence of top detection
        if len(res_pt.boxes) > 0:
            pt_results.append(res_pt.boxes.conf[0].item())
        else:
            pt_results.append(0.0)
            
        if len(res_tf.boxes) > 0:
            tflite_results.append(res_tf.boxes.conf[0].item())
        else:
            tflite_results.append(0.0)

    avg_pt = np.mean(pt_results)
    avg_tf = np.mean(tflite_results)
    diff = abs(avg_pt - avg_tf) * 100
    
    print(f"Average Confidence (PT): {avg_pt:.4f}")
    print(f"Average Confidence (TFLite): {avg_tf:.4f}")
    print(f"Accuracy Parity Difference: {diff:.2f}%")
    
    if diff <= 1.0:
        print("SUCCESS: Parity check passed (<= 1%)")
        return True
    else:
        print("FAILURE: Parity check failed (> 1%)")
        return False

if __name__ == "__main__":
    base_dir = Path(__file__).parent.parent.parent
    weights_dir = base_dir / "models" / "mahjong_yolo_nano" / "weights"
    
    pt_path = weights_dir / "best.pt"
    # Note: Adjust path based on where ultralytics actually saves the exported model
    tf_path = weights_dir / "best_saved_model" / "best_float16.tflite" 
    val_dir = base_dir / "data" / "processed" / "val" / "images"
    
    if pt_path.exists() and tf_path.exists():
        verify_parity(str(pt_path), str(tf_path), str(val_dir))
    else:
        print("Model paths not found for verification.")
