import yaml

cfgfile = "config.yml"
dbfile = "../database/current.csv"


def read_config():

        #List of devices
        devices = {}

        with open(cfgfile, 'r') as f:
                config = yaml.load(f, Loader=yaml.FullLoader)
                
        return config

def test_config():
    assert read_config()!= None

def get_data_types(dict):
        data_types = []

        for dtype in dict['sensors']:
                data_types.append(dtype)

        return data_types

def test_get_data_types():
    device_sensor_test = {'name': 'Example Pi 1', 'ip': '10.0.0.19', 'port': '8080', 'location': 'Exhibit A', 'sensors': ['temperature', 'waterlevel']}
    assert get_data_types(device_sensor_test) == ['temperature', 'waterlevel']

    
