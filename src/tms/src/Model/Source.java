package Model;

public class Source {

    //There are 3 types sources: 1. SqlDump; 2. Pi tunnelling, 3. OpenVpn
    private String sourceFile;
    private String type;

    public Source() {
        //this.sourceFile ="tempSqlDump";
        //this.sourceFile="databaseDesign.txt";
       //this.sourceFile="testTempCritical.txt";
        //this.sourceFile= "testTempGood.txt";
       // this.type = "SqlDump";
        this.type = "PHPWebsite";
       // this.sourceFile =
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getType() {
        return type;
    }

    public void setSourceFile ( String sourceFile ) {
        this.sourceFile = sourceFile;
    }
}
