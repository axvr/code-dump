"""Cryptographic primitives for rpf_users."""

import nacl.pwhash

def __coerce_to_bytes(txt):
    if isinstance(txt, str):
        return txt.encode()
    elif isinstance(txt, bytes):
        return txt
    else:
        raise TypeError('Value must be a string or byte array.')

def hash_password(pwd):
    pwd = __coerce_to_bytes(pwd)
    return nacl.pwhash.argon2id.str(pwd)

def verify_password(hash, pwd):
    pwd = __coerce_to_bytes(pwd)
    try:
        return nacl.pwhash.verify(hash, pwd)
    except nacl.exceptions.InvalidkeyError:
        return False
