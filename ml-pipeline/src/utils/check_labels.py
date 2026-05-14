import os
import sys

def check_labels(labels_dir):
    """
    Checks YOLO format labels in the specified directory.
    Expected format: <class_id> <x_center> <y_center> <width> <height>
    All values should be normalized between 0 and 1 (except class_id).
    """
    if not os.path.exists(labels_dir):
        print(f"Error: Labels directory '{labels_dir}' does not exist.")
        return False

    success = True
    files_checked = 0
    
    for filename in os.listdir(labels_dir):
        if not filename.endswith(".txt"):
            continue
            
        files_checked += 1
        filepath = os.path.join(labels_dir, filename)
        
        with open(filepath, 'r') as f:
            lines = f.readlines()
            
        for i, line in enumerate(lines):
            parts = line.strip().split()
            if len(parts) != 5:
                print(f"Malformed line in {filename}:{i+1} -> {line.strip()}")
                success = False
                continue
                
            try:
                class_id = int(parts[0])
                coords = [float(x) for x in parts[1:]]
                
                # Check class_id range (assuming 0-33 for 34 tiles)
                if class_id < 0 or class_id > 33:
                    print(f"Invalid class_id in {filename}:{i+1} -> {class_id}")
                    success = False
                
                # Check normalized coordinates
                for j, coord in enumerate(coords):
                    if coord < 0.0 or coord > 1.0:
                        print(f"Coordinate out of range in {filename}:{i+1} -> {coord}")
                        success = False
                        
            except ValueError:
                print(f"Non-numeric value in {filename}:{i+1} -> {line.strip()}")
                success = False

    print(f"Validation complete. Checked {files_checked} files.")
    return success

if __name__ == "__main__":
    # Default path relative to project root
    target_dir = "ml-pipeline/data/synthetic/labels"
    if len(sys.argv) > 1:
        target_dir = sys.argv[1]
        
    if check_labels(target_dir):
        print("All labels are valid.")
        sys.exit(0)
    else:
        print("Label validation failed.")
        sys.exit(1)
