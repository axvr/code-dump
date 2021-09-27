import pytest
import rpf_users
from rpf_users.db import init_db

@pytest.fixture
def app():
    # TODO: create new app for every test run.
    rpf_users.app.config.from_mapping({
        'TESTING': True,
        'DATABASE': ':memory:'
    })
    init_db()
    return rpf_users.app

@pytest.fixture
def client(app):
    return app.test_client()
