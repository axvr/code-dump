import sqlite3
import os.path
from . import app

class Db:
    """Thin wrapper around SQLite3 to handle context management."""

    def __init__(self, path):
        self.path = path

    def __enter__(self):
        self.conn = sqlite3.connect(
            self.path,
            # Autoconvert DB values back to Python types.
            detect_types=sqlite3.PARSE_DECLTYPES)

        # Return rows as dictionary-like objects.
        self.conn.row_factory = sqlite3.Row

        self.cur = self.conn.cursor()

        return self

    def __exit__(self, *_):
        self.conn.commit()
        self.cur.close()
        self.conn.close()

    def exec(self, query, params={}):
        return self.cur.execute(query, params)

def open_db(path=None):
    """Create a new DB context.  If no path is specified, fetch from config."""
    return Db(path or app.config["DATABASE"])

def init_db(path=None):
    """
    Initialise the DB with the basic schema.

    This should use a proper DB migration system, but this is just a demo.
    """
    script_file = os.path.join(app.root_path, 'schema.sql')
    with open(script_file) as f:
        script = ''.join(f.readlines())
        with open_db() as db:
            db.cur.executescript(script)
        return script
