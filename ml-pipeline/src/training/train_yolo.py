import os
from pathlib import Path
from ultralytics import YOLO

def train_model(data_yaml, epochs=50, imgsz=640, model_variant='yolov8n.pt'):
    """
    Train YOLO Nano model for Mahjong tile recognition.
    Targeting mAP >= 95%.
    """
    # Ensure models directory exists for logs/weights
    base_dir = Path(__file__).parent.parent.parent
    project_dir = base_dir.parent
    models_dir = base_dir / "models"
    models_dir.mkdir(exist_ok=True)

    # Load a pretrained Nano model (YOLOv8n or YOLOv11n)
    model = YOLO(model_variant)

    # Train the model
    # Use reasonable patience and save to models/
    results = model.train(
        data=data_yaml,
        epochs=epochs,
        imgsz=imgsz,
        patience=10,
        project=str(models_dir),
        name="mahjong_yolo_nano",
        exist_ok=True,
        save=True,
        device='cpu' # Default to CPU, will use GPU if available and CUDA/MPS configured
    )
    
    # Export the best model to a compact format (TFLite or CoreML)
    # This ensures we meet the mobile size constraint early on
    best_model_path = models_dir / "mahjong_yolo_nano" / "weights" / "best.pt"
    if best_model_path.exists():
        best_model = YOLO(str(best_model_path))
        best_model.export(format='tflite', imgsz=imgsz)
        best_model.export(format='coreml', imgsz=imgsz)

    return results

if __name__ == "__main__":
    # Path relative to script location
    script_dir = Path(__file__).parent
    config_path = script_dir / "yolo_config.yaml"
    
    # Verify config exists
    if not config_path.exists():
        print(f"Error: Configuration not found at {config_path}")
    else:
        train_model(str(config_path))
