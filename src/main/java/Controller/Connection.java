/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.DriverManager;

/**
 *
 * @author Prabhat
 */
public class Connection {
     // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/CodingTestPlatform";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "harry";
   
   public static java.sql.Connection connectionEstablish(){
       java.sql.Connection conn = null;
       try {
           //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");
           //STEP 3: Open a connection
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           return conn;
       } catch (Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
       }
       return null;
   }
}
