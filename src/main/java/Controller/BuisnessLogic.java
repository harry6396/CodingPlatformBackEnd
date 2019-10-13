/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.FinalSubmission;
import Models.Login;
import Models.Question;
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
				String sqlQuery = "INSERT INTO RegisterUser (EmailID, Name, PhoneNumber, TeamName) VALUES('";
				String sqlQueryBody="";
				for(int iCounter=0;iCounter<registerUser.length;iCounter++){
					if(iCounter<registerUser.length-1){
				sqlQueryBody += registerUser[iCounter].getEmailID()+"','"
            			+	registerUser[iCounter].getName()+"','"
            			+	registerUser[iCounter].getPhoneNumber()+"','"
            			+	registerUser[iCounter].getTeamName().toLowerCase()
            			+	"'),('";
					}
					else{
						sqlQueryBody += registerUser[iCounter].getEmailID()+"','"
            			+	registerUser[iCounter].getName()+"','"
            			+	registerUser[iCounter].getPhoneNumber()+"','"
            			+	registerUser[iCounter].getTeamName().toLowerCase()
            			+	"');";
					}
				}
            	stmt.executeUpdate(sqlQuery+sqlQueryBody);
                sqlQuery = "INSERT INTO TeamDetail (TeamName, PassCode) VALUES('"+registerUser[0].getTeamName().toLowerCase()
                        +"', null"
                        +")";
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
    
    public static Login checkLogin(Login loginDetails) throws SQLException{
        Login login = new Login();
        String sql="";
        java.sql.Connection con = Connection.connectionEstablish();
        Statement stmt = con.createStatement();
        ResultSet rst = null;
        try{
            sql =   "SELECT TeamName"
                    + " FROM TeamDetail WHERE TeamName='" 
                    + loginDetails.getTeamName().toLowerCase() 
                    + "' AND PassCode='"+loginDetails.getPasscode()+"';";
            rst = stmt.executeQuery(sql);
            if(rst!=null){
                sql = "SELECT ProblemStatement, ProblemDescription, TestCase, MaxScore, PuzzleStatement,  PuzzleDescription, Answer"
                      +" FROM ProblemStatement PS"
                      +" INNER JOIN PuzzleProblemStatement PPS ON PPS.ProblemId = PS.ProblemID;";
                rst = stmt.executeQuery(sql);
                while(rst.next()){
                    Question question = new Question();
                    question.setMaxScore(rst.getString("MaxScore"));
                    question.setProblemDescription(rst.getString("ProblemDescription"));
                    question.setProblemStatement(rst.getString("ProblemStatement"));
                    question.setTestCase(rst.getString("TestCase"));
                    question.setAnswer(rst.getString("Answer"));
                    question.setPuzzleDescription("PuzzleDescription");
                    question.setPuzzleStatement(rst.getString("PuzzleStatement"));
                    login.question.add(question);
                }
                login.setStatus("Success");
                }
            else{
                login.setStatus("Fail");
            }
            con.close();
        }
        catch(Exception ex){
            login.setStatus(ex.getMessage());
        }
        return login;
    }
    
    public static FinalSubmission finalSubmit(FinalSubmission finalSubmission){
        FinalSubmission finalSubmit = new FinalSubmission();
        return finalSubmit;
    }
}
