from ultralytics import YOLO

def train_model(data_yaml, epochs=100, imgsz=640):
    """
    YOLOv8/v11 Nano 모델을 마작 패 데이터셋으로 학습시킵니다.
    """
    model = YOLO('yolov8n.pt')  # Load a pretrained model
    results = model.train(data=data_yaml, epochs=epochs, imgsz=imgsz)
    return results

if __name__ == "__main__":
    train_model("data/processed/mahjong.yaml")
