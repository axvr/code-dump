def test_index(client):
    resp = client.get('/')
    assert resp.status_code == 200
    assert '<p>Hello, rpf_users!</p>' == resp.data.decode()
