import socket
import sys
import time

class pi_connection:

	def close(self):
		self.sock.close()

	def restart(self):
		self.sock.sendall( "restart".encode() )

	def query(self, data_types):
		try:
			send_string=""
			data_types = ["query", *data_types]

			# Build a comma seperated string of desired
			# query data
			for i in data_types:
				send_string = send_string + i + ","

			# Remove trailing comma
			send_string = send_string[:-1]

			self.sock.sendall( send_string.encode() )

			# Get response
			# TODO: set a timeout
			data = self.sock.recv(1024)
			data = data.decode().split(',')
			return data

		except Exception as e:
			print("Pi_connection error: " + e)
			self.sock.close()
			self.error = e
			return 0

	def __init__(self, ip, port):
		self.ip = ip
		self.port = port
		self.error = "none"

		try:
			self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

			# Set a timeout for the server to respond
			deadline = time.time() + 20.0
			server_address = ( self.ip, int(self.port) )
			self.sock.connect(server_address)
			self.status = 'connected'
		except Exception as e:
			self.status = 'failed'
			print(e)
