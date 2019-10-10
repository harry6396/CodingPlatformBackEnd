/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Register;
import Models.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prabhat
 */
public class BuisnessLogic {
    public static Register addUser(Register []registerUser){
        Register register = new Register();
        java.sql.Connection con = Connection.connectionEstablish();
        try{
        	Statement stmt = con.createStatement();
            String sql = "SELECT TeamName"
                    + " FROM RegisterUser WHERE TeamName='" + registerUser[0].getTeamName().toLowerCase() + "';";
            ResultSet rst = stmt.executeQuery(sql);
            if(rst != null){
                rst.beforeFirst();  
                rst.last();  
                int size = rst.getRow(); 
            if (size!=0) {
            	register.setStatus("Fail");
            } else {
            	String sqlQuery = "INSERT INTO RegisterUser (EmailID, Name, PhoneNumber, TeamName) VALUES('"
            			+	registerUser[0].getEmailID()+"','"
            			+	registerUser[0].getName()+"','"
            			+	registerUser[0].getPhoneNumber()+"','"
            			+	registerUser[0].getTeamName().toLowerCase()
            			+	"'),"
            			+ 	"('"
            			+	registerUser[1].getEmailID() + "','"
            			+	registerUser[1].getName() + "','"
            			+	registerUser[1].getPhoneNumber() + "','"
            			+	registerUser[1].getTeamName().toLowerCase()	
            			+ 	"')";
            	stmt.executeUpdate(sqlQuery);
            	con.close();
            	register.setStatus("Success");
            }
            }else{
                register.setStatus("Fail");
            }
        }
        catch(Exception ex){
        	Logger.getLogger(BuisnessLogic.class.getName()).log(Level.SEVERE, null, ex);
        	register.setStatus(ex.getMessage());
        }
        return register;
    }
    
    public static Team checkTeamAvailability(Team teamDetails){
        Team team = new Team();
        java.sql.Connection con = Connection.connectionEstablish();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT TeamName"
                    + " FROM RegisterUser WHERE TeamName='" + teamDetails.teamName.toLowerCase() + "';";
            ResultSet rst = stmt.executeQuery(sql);
            if(rst!=null){
                rst.beforeFirst();  
                rst.last();  
                int size = rst.getRow();  
                if (size==0) {
                    team.setStatus("Success");
                } else {
                team.setStatus("Fail");
                }
            }else{
                team.setStatus("Fail");
            }
            con.close();
            return team;
        } catch (SQLException ex) {
            Logger.getLogger(BuisnessLogic.class.getName()).log(Level.SEVERE, null, ex);
            team.setStatus(ex.getMessage());
            return team;
        }
    }
}
