
import os
import time
import busio
import digitalio
import board
import adafruit_mcp3xxx.mcp3008 as MCP
import sys
from adafruit_mcp3xxx.analog_in import AnalogIn

# create the spi bus
spi = busio.SPI(clock=board.SCK, MISO=board.MISO, MOSI=board.MOSI)

# create the cs (chip select)
cs = digitalio.DigitalInOut(board.D22)

# create the mcp object
mcp = MCP.MCP3008(spi, cs)

# create an analog input channel on pin 0
chan0 = AnalogIn(mcp, MCP.P0)

toolbar_width = 40
"""
def update_bar(val):
	# setup toolbar
	sys.stdout.write("[%s]" % (" " * toolbar_width))
	sys.stdout.flush()
	sys.stdout.write(" \r[") # return to start of line, after '['
	for i in range(val):
		# update the bar
		sys.stdout.write("#")
		sys.stdout.flush()
	sys.stdout.write("\r")
"""
def read():
	return chan0.value
if __name__ == "__main__":
	print(chan0.value)

