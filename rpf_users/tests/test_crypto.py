import pytest
import random
import string

from rpf_users.crypto import hash_password, verify_password

def random_string(size=20):
    return ''.join(random.choice(string.printable) for x in range(size))

@pytest.mark.repeat(20)
def test_hash_password():
    pwd = random_string(20)
    hash = hash_password(pwd)
    assert pwd != hash                 # Did not return input password.
    assert hash != hash_password(pwd)  # Used a random salt.

@pytest.mark.repeat(20)
def test_verify_password():
    pwd = random_string(20)
    hash = hash_password(pwd)
    assert verify_password(hash, pwd)
    assert not verify_password(hash, hash)
    assert not verify_password(hash, random_string(20))
    assert not verify_password(hash_password(random_string(20)), pwd)
