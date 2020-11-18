package Model;

import java.util.*;

import java.sql.*;

public class ServerPull {

   public static void main(String [] args){
      
      pull(); 
   
   }


   // The pull method will be used to connect to the MySQL database server and attempt to retrieve the data from the maria DB ran on it
   public static void pull(){
   
      try{ 
      
         Class.forName(" * mysql server address goes here? * ").newInstance();
         
         Connection connect = DriverManager.getConnection(" URL ", "username", "password");// driver manager. get connection "attempts to establish a connection to the given database URL"
      
         Statement state = connect.createStatement();// this sets up the query we will perform on the connected DB. 
         
         String query = ("SELECT * FROM ____;");// this will change depending on whatever format the maria DB stores its info
         
         ResultSet info = state.executeQuery(query);// perform that ^ query on the DB and store the info in the result set object. 
         
         while( info.next()){// Keep looping while there is information from the DB
         
            int piID = info.getInt("column name corresponding to the pi ID");
            
            String piName = info.getString(" column name corresponding to pi Name");
            
            int temp = info.getInt("column name corresponding to the temperature");
            
            int date = info.getInt("column name for the date (date and time included I believe");
            
            // HERE: Insert all of that into our database / pass it off to a helper method to examine the validity of incoming data
         }
      
         connect.close();// when we are done close the connection, MAYBE? we might leave it open to constantly pull. not 100% sure what we wanna do here
         
      } catch(Exception e){
         System.out.println(e);
      }
      
   
   }




}
