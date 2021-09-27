"""Cryptographic primitives for rpf_users."""

import nacl.pwhash

def hash_password(pwd):
    if isinstance(pwd, str):
        pwd = pwd.encode()
    return nacl.pwhash.argon2id.str(pwd)

def verify_password(hash, pwd):
    if isinstance(pwd, str):
        pwd = pwd.encode()
    try:
        return nacl.pwhash.verify(hash, pwd)
    except nacl.exceptions.InvalidkeyError:
        return False
