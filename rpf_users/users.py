from . import app

@app.route("/")
def index():
    return '<p>Hello, rpf_users!</p>'
