import socket
import sys
import configparser
from pi_connection import pi_connection
from socket import socket
from os import path
import os.path
import yaml
import time

cfgfile = "config.yml"
dbfile = "../database/current.csv"

# How often to requery devices in seconds
#updateFrequency = int(sys.argv[1])
updateFrequency = 5 #Just for the sake of testing; change this back when done.

# This function connects to the given Pi Zero's IP
# and requests specificed data in list
# e.g.: data_types = ['temp', 'waterlevel']

def tab():
        return "    "

# Setup config file
def initialize_config():
        try:
                with open(cfgfile, "w") as config:
                        config.write('ExamplePi1:\n')
                        config.write(tab() + 'name: Example Pi 1\n')
                        config.write(tab() + 'ip: 12.34.56.78\n')
                        config.write(tab() + 'port: 8080\n')
                        config.write(tab() + 'location: Exhibit A\n')
                        config.write(tab() + 'sensors:\n')
                        config.write(tab() + tab() + 'temperature:\n')
                        config.write(tab() + tab() + tab() + 'normal_range: 18-22\n')
                        config.write(tab() + tab() + tab() + 'warning_range: 15-25\n')


        except:
                print("Failed to generate " + cfgfile)
                sys.exit(0)
        print("Please populate the " + cfgfile + " file\n")
        sys.exit(0)

# Read in device list
def read_config():

        #List of devices
        devices = {}

        with open(cfgfile, 'r') as f:
                config = yaml.load(f, Loader=yaml.FullLoader)


        return config



# Make a list of sensor data to retreive
def get_data_types(dict):
        data_types = []

        for dtype in dict['sensors']:
                data_types.append(dtype)

        return data_types

#
# Begin main script
#

if not path.exists(cfgfile):
        print("No config file found. Generating a new one\n")
        initialize_config()
entries = read_config()

#
# Open TCP connections to hosts
#


#connected_devices is a list of pairs [[ <connection object>, <dict of device info> ]]
connected_devices = []

for entry in entries.items():
 
        ip = entry[1]['ip']
        port = entry[1]['port']

        obj_line_pair = []
        connection = pi_connection(str(ip), str(port))
        if connection:
                obj_line_pair.append(connection)
                obj_line_pair.append(entry[1])
        connected_devices.append( obj_line_pair )

# Query the specified data
while True:
        with open(dbfile, 'w+') as f:
                for device in connected_devices:

                        connection = device[0]
                        data_types = get_data_types(device[1])
                        warnflag,dangerflag,status = 0,0,0

                        try:
                                result = connection.query(data_types)

                                # Check if data is in range for each data type
                                for data_type in data_types:

                                        normal_range = device[1]['sensors'].get(data_type)['normal_range']
                                        warning_range = device[1]['sensors'].get(data_type)['warning_range']
                                        data = normal_range.split("-")
                                        low = int(data[0])
                                        high = int(data[1])

                                        data = warning_range.split("-")
                                        low_warn = int(data[0])
                                        high_warn = int(data[1])
                                        
                                        if result[0] < low or result[0] > high:
                                                dangerflag = 1

                                        if result[1] < low_warn or result[1] > high_warn:
                                                warnflag = 1
                                        


                                #TODO:
                                # - Parse both above strings into integers
                                #     (e.g.: "18-22" -> low = 18, hi = 22)
                                # - Compare with received data (in 'result' variable)
                                #     & set warnflag/dangerflag if needed

                                if dangerflag == 1:
                                        status = 2
                                elif warnflag == 1 and dangerflag == 0:
                                        status = 1


                                f.write(device[1]['name'] + ',' + device[1]['ip'] + ',' + device[1]['location']
                                + ',' + result[0] + ',' + result[1] + ',' + str(status) + '\n')
                                print('update of ip ' + device[1]['ip'] + " successful")

                        except Exception as e:
                                print(e)
                                continue

        time.sleep(updateFrequency)
