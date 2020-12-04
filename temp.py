#!/usr/bin/python2.7

# Import Libraries
import os
import glob
import time
import configparser
import requests

# Finds the correct device file that holds the temperature data
base_dir = '/sys/bus/w1/devices/'
device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'
 
# A function that reads the sensors data
def read_temp_raw():
  f = open(device_file, 'r') # Opens the temperature device file
  lines = f.readlines() # Returns the text
  f.close()
  return lines
 
# Convert the value of the sensor into a temperature
def read_temp():
  lines = read_temp_raw() # Read the temperature 'device file'
 
  # While the first line does not contain 'YES', wait for 0.2s
  # and then read the device file again.
  while lines[0].strip()[-3:] != 'YES':
    time.sleep(0.2)
    lines = read_temp_raw()
 
  # Look for the position of the '=' in the second line of the
  # device file.
  equals_pos = lines[1].find('t=')
 
  # If the '=' is found, convert the rest of the line after the
  # '=' into degrees Celsius, then degrees Fahrenheit
  if equals_pos != -1:
    temp_string = lines[1][equals_pos+2:]
    temp_c = float(temp_string) / 1000.0
    temp_f = temp_c * 9.0 / 5.0 + 32.0
    return temp_c, temp_f
 
# Print out the temperature until the program is stopped.
while True:
  config = configparser.ConfigParser()
  config.read('temp.config')
  name = (config['DEFAULT']['name'])
  payload = {'temp' : read_temp()[1], 'device_id' : name}
  try:
    r = requests.get('http://64.146.143.236:8082/update_temp.php', params=payload)
  except:
    print('URL error')
  print(r.url)
  time.sleep(30)
