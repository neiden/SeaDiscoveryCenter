import waterlvl
import readtemp


def getreply(string):
    string = string.split(',')
    reply = ''

    if string[0] == 'query':
        for i in range( len(string) - 1 ):
                if string[i+1] == 'waterlevel':
                    reply = reply + str( waterlvl.read() ) + ","
                elif string[i+1] == 'temperature':
                    reply = reply + str( readtemp.read() ) + ","
                elif string[i+1] == ',' or not string[i+1]:
                    continue
                else:
                    reply = reply + "n/a,"
    return reply[:-1]

def test_getreply():
    string = 'query,waterlevel,temperature'
    assert getreply(string) == string


def generate_config():
    if not path.exists("./server.config"):
	print("Could not locate config file. generating a new one\n")
	try:
		with open("server.config", "w") as f:
			config['SETTINGS'] = {	'port': '8080', 'pubkey': '<insert here>'}
			config.write(f)
	except:
		print("Failed to create a config file. Do you have write permissions here?\n")
		exit(1)
	print("Please fill in the config.server file")

def test_generate_config():
    
