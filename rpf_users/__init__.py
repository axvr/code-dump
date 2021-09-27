import os
from flask import Flask

def create_app(config=None):
    """Create a new instance of the app."""

    app = Flask(__name__, instance_relative_config=True)

    # Source global configuration file.
    app.config.from_pyfile('config.py', silent=True)

    # Source per-environment configuration file.
    if "ENV" in app.config:
        app.config.from_pyfile(f'config_{app.config["ENV"]}.py', silent=True)

    # Load override configuration options (useful for tests).
    if config:
        app.config.from_mapping(config)

    # Ensure instance directory exists to store SQLite DB in.
    try:
        os.makedirs(app.instance_path)
    except FileExistsError: pass

    return app

app = create_app()

# Import modules containing Flask CLI commands.
from . import cli
