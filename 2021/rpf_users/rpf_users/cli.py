"""Module containing custom Flask CLI commands."""

from . import app, db

@app.cli.command('init-db')
def init_db_command():
    """
    Command to initialise the DB.

    Were this not a demo app, this would be a proper DB migration command.
    """
    print(f'Initialising DB: {app.config["DATABASE"]}')
    print('Executing SQL:\n')
    script = db.init_db()
    print(script)
