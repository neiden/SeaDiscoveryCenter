import xml.etree.ElementTree as ET

data_top = ET.Element('tbody')
tree = ET.ElementTree(data_top)
#device_list = open("device_db.txt", 'r')

# Accepts list of lists in format [ id, ip, location, temperature, water lvl, status code ]
def generate_web_data(all_data_sets):
	for device_data in all_data_sets:
		id, ip, loc, tmp, lvl, status = "Missing","Missing","Missing","Missing","Missing","Missing"
		# Define which data is in which index of the list
		i = 0
		for data in device_data:
			if i == 0:
				id = data
			elif i == 1:
				ip = data
			elif i == 2:
				loc = data
			elif i == 3:
				tmp = data
			elif i == 4:
				lvl = data
			elif i == 5:
				status = data
			else:
				raise Exception("Error - too many items in this list: " + str(device_data) + "\n")
			i += 1

		tr = ET.SubElement(data_top, 'tr')
		th = ET.SubElement(tr, 'th')
		th.set('scope', 'row')
		th.text = str(id)

		td = ET.SubElement(tr, 'td')
		td.text = str(ip)
		td = ET.SubElement(tr, 'td')
		td.text = str(loc)

		td = ET.SubElement(tr, 'td')
		td.text = str(tmp) + str('\u00B0') + 'C'

		td = ET.SubElement(tr, 'td')
		td.text = str(lvl) + '"'

		# This element needed for table formatting - do not remove
		td = ET.SubElement(tr, 'td')
		a = ET.SubElement(td, 'a')
		a.set('href', '#')

		# Add logic to decide status
		status = int(status)
		if status == 0:
			a.set('class', 'btn btn-success')
			a.text = 'NOMINAL'
		elif status == 1:
			a.set('class', 'btn btn-warning')
			a.text = 'OUT OF RANGE'
		elif status == 2:
			a.set('class', 'btn btn-danger')
			a.text = 'FAR OUT OF RANGE'
		else:
			a.set('class', 'btn btn-basic')
			a.text = 'NO DATA'

	return ET.tostring(data_top, encoding='unicode')

data_list = []
for line in open('../database/current.csv', 'r'):
	data_list.append( line.split(',') )
print ( generate_web_data( data_list) )
