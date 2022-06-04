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

# Initialise DB.
flask init-db

# Start server.
export FLASK_ENV=development
flask run

# Run tests.
pytest
```


### Windows (PowerShell)

```powershell
# Set up virtual environment.
py -3 -m venv venv
Set-ExecutionPolicy -ExecutionPolicy Bypass  # Allow executing ps1 scripts.
.\venv\Scripts\Activate.ps1

# Install dependencies.
pip install -r requirements.txt

# Initialise DB.
flask init-db

# Start server.
$env:FLASK_ENV = "development"
flask run

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

# Get details on specific user.
curl http://127.0.0.1:5000/users/1

# Delete a user.
curl http://127.0.0.1:5000/users/1 \
  -H 'X-API-KEY: very secret key' \
  -X DELETE

# Mark a user as active.
curl http://127.0.0.1:5000/users/1/active -X POST

# Mark a user as inactive.
curl http://127.0.0.1:5000/users/1/inactive -X POST
```
