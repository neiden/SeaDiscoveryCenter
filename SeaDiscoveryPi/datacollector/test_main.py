import yaml

cfgfile = "config.yml"
dbfile = "../database/current.csv"



def func(x):
    return x

def test_func():
    assert func(5) == 4
    
def read_config():

        #List of devices
        devices = {}

        with open(cfgfile, 'r') as f:
                config = yaml.load(f, Loader=yaml.FullLoader)
                
        return config

def test_config():
    assert read_config()

