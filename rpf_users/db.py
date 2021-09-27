import sqlite3
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
    if path:
        return Db(path)
    return Db(app.config["DATABASE"])
