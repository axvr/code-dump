import os
from flask import Flask

def create_app(config=None):
    """Create a new instance of the app."""

    app = Flask(__name__, instance_relative_config=True)

    app.config.from_pyfile('config.py', silent=True)

    if config:
        app.config.from_mapping(config)

    # Ensure instance directory exists to store SQLite DB in.
    try:
        os.makedirs(app.instance_path)
    except FileExistsError: pass

    return app

app = create_app()
