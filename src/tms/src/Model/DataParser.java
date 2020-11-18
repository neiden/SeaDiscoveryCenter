package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataParser  {

    private List<String> entries = new ArrayList<>();
    private Source source;
    private String temperatureDataFromPhp;

    public List<String> getEntries() {
        return entries;
    }

    public String getTemperatureDataFromPhp () {
        return temperatureDataFromPhp;
    }

    public Source getSource () {
        return source;
    }

    public void parseFromSqlDump(){
        source = new Source();

        try{
            // NOTE: Make sure this file is in your prototype directory
            File sqlFile = new File(this.source.getSourceFile());
            BufferedReader br = new BufferedReader(new FileReader(sqlFile));

            String line;
            while((line = br.readLine()) != null){// While we have input lines

                if(line.contains("INSERT INTO `temperatures` VALUES") == true){
                    // The SQL format is: (3346,'2018-02-01 19:19:53','pi_1',77.112)

                    StringTokenizer split = new StringTokenizer(line, "(");
                    while(split.hasMoreTokens()){// Display every single portion
                        entries.add(split.nextToken());

                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: File doesn't exist...");
            System.exit(0);
        }
    }

    public void parseFromOpenVpn(){
        //Todo: implement parse data through OpenVpn
    }

    public void parseFromPiTunnelling(){
        //Todo: implement parse data through Pi-tunnelling
    }

    public void parseFromPHPWebsite(){
        //Todo: implement parse through Pi-tunneling
        //System.out.println("Attempting to invoke the python script...");

        File f = new File("dataPullPython.py");
        String absolute = f.getAbsolutePath();
        String command = "";
       // String dataString= "";

        //command = "python dataPullPython.py";
        command = "python scraper.py";
        try {

            String line = "";

            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
          //  System.out.println("output = ");
            try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {

                while((line = br.readLine()) != null){
                    // System.out.println(line);
                    temperatureDataFromPhp = line;
                }
            }

        } catch (Exception e) {
            System.out.println("Problem executing python script!\n" + e );
        }

        return;
    }

}
