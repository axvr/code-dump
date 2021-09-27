from . import app, db

@app.cli.command('init-db')
def init_db_command():
    db.init_db()
