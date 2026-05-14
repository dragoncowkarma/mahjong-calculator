import sys
import os

def check_eof_newline(filepath):
    """Checks if a file ends with exactly one blank line."""
    if not os.path.exists(filepath):
        print(f"Error: File '{filepath}' not found.")
        return False
        
    with open(filepath, 'rb') as f:
        f.seek(0, os.SEEK_END)
        size = f.tell()
        if size == 0:
            return True # Empty file is okay? Or should it have a newline?
            
        f.seek(max(0, size - 2))
        last_chars = f.read(2)
        
        # Exactly one newline at the end means the last char is \n and the one before is NOT \n
        if len(last_chars) == 1:
            return last_chars == b'\n'
        
        return last_chars[1:] == b'\n' and last_chars[:1] != b'\n'

def main():
    if len(sys.argv) < 2:
        print("Usage: python3 agent_md_linter.py <file1> <file2> ...")
        sys.exit(1)
        
    success = True
    for filepath in sys.argv[1:]:
        if not check_eof_newline(filepath):
            print(f"Linter Failure: '{filepath}' must end with exactly one newline.")
            success = False
        else:
            print(f"Linter Success: '{filepath}' protocol check passed.")
            
    if not success:
        sys.exit(1)
    sys.exit(0)

if __name__ == "__main__":
    main()
