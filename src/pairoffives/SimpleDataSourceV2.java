/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pairoffives;

    
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Tony
 */
public class SimpleDataSourceV2 {


   private static String dbserver;
   private static String database;
   private static String username;
   private static String password;

   private static Connection activeConn;

   /**
      Initializes the data source.
      Checks if MySQL Driver is found
      contains the database driver,
      Fill variabels dbserver, database, username, and password
    *
    * TODO get variabels from a configuration file!!!
    * Hardcoded is a bad code
   */
   private static void init()
         
   {
      try {
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
      }
      catch (ClassNotFoundException e) {
          System.out.println(e);
      }


      dbserver="per.nl:3307";
      database="school";
      username = "school";
      password = "school123";
      
      
   }

   /**
      Gets a connection to the database.
      @return the database connection
   */
   public static Connection getConnection() throws SQLException
   {
       if (activeConn==null) {
           init();
           activeConn=createConnection();
       }

       System.out.println("Connectie database gelukt");
       
       return activeConn;

   }

   private static Connection createConnection() throws SQLException
   {

        String connectionString = "jdbc:mysql://" + dbserver + "/" + database + "?" +
                "user=" + username + "&password=" + password;

       return DriverManager.getConnection(connectionString);
       
       
   }
   
   
}
    

