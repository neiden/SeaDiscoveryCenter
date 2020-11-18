import requests
import urllib.request
import time
from bs4 import BeautifulSoup
import re


url = 'http://64.146.143.236:8082/latest_temps.php'


response = requests.get(url)


soup = BeautifulSoup(response.text, "html.parser")


s = str(soup.findAll('td'))
result = re.findall(r"[-+]?\d*\.\d+|\d+", s)
print (result)
