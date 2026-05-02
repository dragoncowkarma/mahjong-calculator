from ultralytics import YOLO

def export_to_mobile(model_path):
    """
    학습된 .pt 모델을 TFLite 및 CoreML 포맷으로 변환합니다.
    """
    model = YOLO(model_path)
    
    # Export to TFLite (Android)
    model.export(format='tflite')
    
    # Export to CoreML (iOS)
    model.export(format='coreml')

if __name__ == "__main__":
    export_to_mobile("runs/detect/train/weights/best.pt")
