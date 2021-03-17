from pi_connection import pi_connection
ip = '10.0.0.19'
port = '8080'
connection_obj = None
connection = pi_connection(str(ip), str(port))
    
def test_connection():
    if connection:
        connection_obj = connection
    assert connection_obj != None

#Needs to have server.py running to pass
def test_query():
    result = connection.query(['temperature', 'waterlevel'])

