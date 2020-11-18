package Model;

public class TMSDatabase extends Database{

    Source s;

    @Override
    public void setUpDB() {
      initializeSensors();
      parseData();
      loadTemperatureReading(super.getTemperatureDataFromPhp());
     // loadSensorData();
    }

    @Override
    public void parseData() {
        s = new Source();

        if(s.getType().equals("PHPWebsite")){
            super.parseFromPHPWebsite();
        }

       /* if( s.getType().equals("SqlDump")){
            super.parseFromSqlDump();
        }
        else if(super.getSource().getType().equals("OpenVpn")) {
            super.parseFromOpenVpn();
        }
        else if(super.getSource().getType().equals("PHPWebsite")){
            super.parseFromPHPWebsite();
        }
        */

    }

}
