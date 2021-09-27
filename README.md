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
