with open("ml-pipeline/src/synthesis/tile_synthesizer.py", "r") as f:
    content = f.read()

content = content.replace("if new_w <= 0 or new_h <= 0: continue", "if new_w <= 0 or new_h <= 0:\n                continue")
content = content.replace("if max_x <= 0 or max_y <= 0: continue", "if max_x <= 0 or max_y <= 0:\n                continue")
content = content.replace("if not placed: continue", "if not placed:\n                continue")
content = content.replace("if not bboxes: continue", "if not bboxes:\n            continue")
content = content.replace("if not final_bboxes: continue", "if not final_bboxes:\n                continue")
content = content.replace("except Exception as e:\n            pass", "except Exception:\n            pass")

with open("ml-pipeline/src/synthesis/tile_synthesizer.py", "w") as f:
    f.write(content)
