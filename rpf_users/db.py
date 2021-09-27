import sqlite3
import os.path
from . import app

class Db:
    """Thin wrapper around SQLite3 to handle context management."""

    def __init__(self, path):
        self.path = path

    def __enter__(self):
        self.conn = sqlite3.connect(self.path)
        self.cur = self.conn.cursor()
        return self

    def __exit__(self, *_):
        self.conn.close()

def open_db(path=None):
    """Create a new DB context.  If no path is specified, fetch from config."""
    return Db(path or app.config["DATABASE"])

def init_db(path=None):
    script_file = os.path.join(app.root_path, 'schema.sql')
    with open(script_file) as f:
        script = ''.join(f.readlines())
        with open_db() as db:
            db.cur.executescript(script)
