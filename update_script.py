import re

with open("ml-pipeline/src/synthesis/tile_synthesizer.py", "r") as f:
    content = f.read()

content = content.replace("count=100", "count=100000")

with open("ml-pipeline/src/synthesis/tile_synthesizer.py", "w") as f:
    f.write(content)
