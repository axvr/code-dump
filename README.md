# rpf\_users

## Usage

### Unix-like OSs (Bash)

```sh
# Set up vitual environment.
python3 -m venv venv
. venv/bin/activate

# Install dependencies.
pip install --upgrade pip
pip install -r requirements.txt

# Start server.
export FLASK_ENV=development
flask run

# Initialise DB.
flask init-db

# Run tests.
pytest
```


### Windows (PowerShell)

```powershell
# Set up virtual environment.
py -3 -m venv venv
.\venv\Scripts\activate.ps1

# Install dependencies.
pip install --upgrade pip
pip install -r requirements.txt

# Start server.
$env:FLASK_ENV = "development"
flask run

# Initialise DB.
flask init-db

# Run tests.
pytest
```


## Test with Curl

```sh
# Get list of users.
curl http://127.0.0.1:5000/users

# Create a new user.
curl http://127.0.0.1:5000/users \
  -H 'Content-Type: application/json' \
  -d '{"username": "foo", "password": "very secure password", "email_address": "foo@example.com"}'
```
