import socket
import sys
import configparser
import os.path
from os import path
import configparser
import time
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



config = configparser.ConfigParser()


# Generate a new config file if none exists
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

# Read in config file values
config.read("server.config")
config.sections()

port = config['SETTINGS']['port']
print("Starting server on port " + port)

try:
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	server_address = ('', int(port))
	sock.bind(server_address)
except:
	print("Failed to start server on port " + str( port ) + ": " + str( sys.exc_info()[0] ) + "\n")
	sys.exit(1)
sock.listen(1)
print("Done - ready for requests")

# Remain waiting for request from client Pi (master)

while True:

	try:
		connection, client_address = sock.accept()

	except KeyboardInterrupt:
		sock.close()
		sys.exit()

	except:
		connection.close()
		continue

	while True:
		try:
			data = connection.recv(1024)

			if data:
				print("received: " + str(data.decode()))

			else:
				break

			# Pass the received data to getreply as string, encode() back
			# to bytes before sneding with sendall()
			connection.sendall( getreply( data.decode()).encode() )

		except KeyboardInterrupt as k:
			sock.close()
			sys.exit()
		#except Exception as e:
		#	print("Error: " + str(e))
		#	break

	time.sleep(0.25)
