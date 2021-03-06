/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.CheckProgress;
import Models.Code;
import Models.FinalSubmission;
import Models.Login;
import Models.Question;
import Models.Register;
import Models.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
                sqlQuery = "INSERT INTO TeamDetail (TeamName, PassCode, QuestionType, QuestionNumber, TeamScore, FinalTime, IsComplete, InitialTime) VALUES('"+registerUser[0].getTeamName().toLowerCase()
                        +"', 'qmpzdin2o0','P',1,0,NULL,0,'5400'"
                        +")";
                stmt.executeUpdate(sqlQuery);
            	register.setStatus("Success");
            }
            }else{
                register.setStatus("Fail");
            }
            con.close();
        }
        catch(Exception ex){
        	Logger.getLogger(BuisnessLogic.class.getName()).log(Level.SEVERE, null, ex);
        	register.setStatus(ex.getMessage());
        }
        return register;
    }
    
    public static Team checkTeamAvailability(Team teamDetails) throws SQLException{
        Team team = new Team();
        java.sql.Connection con = Connection.connectionEstablish();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT TeamName"
                    + " FROM RegisterUser WHERE TeamName='" + teamDetails.teamName.toLowerCase() + "';";
            ResultSet rst = stmt.executeQuery(sql);
            if(rst!=null){
                if (rst.next()) {
                    team.setStatus("Fail");
                } else {
                    team.setStatus("Success");
                }
            }else{
                team.setStatus("Success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BuisnessLogic.class.getName()).log(Level.SEVERE, null, ex);
            team.setStatus(ex.getMessage());
        }
        con.close();
        return team;
    }
    
    public static Question question(Question loginDetails) throws SQLException{
        Question question = new Question();
        String sql="";
        java.sql.Connection con = Connection.connectionEstablish();
        Statement stmt = con.createStatement();
        ResultSet rst = null;
        int numRowsChanged=0;
        try{
            if(loginDetails.getAnswer()!=null){
            if(loginDetails.getAnswer().equals("ac")){
                sql = "UPDATE TeamDetail"
                  +" SET QuestionNumber = QuestionNumber + 1"
                  +" ,QuestionType = 'P'"
                  +" ,TeamScore = TeamScore+100"
                  +" WHERE TeamName='" + loginDetails.getTeamName().toLowerCase() + "';";
            }else if(loginDetails.getQuestionType().trim().equals("P")){
                sql = "UPDATE TeamDetail"
                  +" INNER JOIN PuzzleProblemStatement ON"
                  +" PuzzleProblemStatement.ProblemId = TeamDetail.QuestionNumber"
                  +" AND PuzzleProblemStatement.Answer = '" + loginDetails.getAnswer().trim().toLowerCase() + "'"
                  +" SET QuestionType = 'C'"
                  +" ,TeamScore = TeamScore+50"
                  +" WHERE TeamName = '" + loginDetails.getTeamName().toLowerCase() + "';";
            }
            if(!sql.isEmpty()){
            numRowsChanged = stmt.executeUpdate(sql);
            }
            }
            if(numRowsChanged>0||loginDetails.getAnswer()==null){
            sql = "SELECT ProblemStatement, ProblemDescription, TestCase, MaxScore, PuzzleStatement,  PuzzleDescription, Answer, QuestionType, CodeInput, CodeOutput, Example, ExpectedOp"
                      +" FROM ProblemStatement PS"
                      +" INNER JOIN PuzzleProblemStatement PPS ON PPS.ProblemId = PS.ProblemID"
                      +" INNER JOIN TeamDetail TD ON TD.QuestionNumber = PS.ProblemID"
                      +" WHERE TeamName='"+loginDetails.getTeamName().toLowerCase()+"';";
                rst = stmt.executeQuery(sql);
                while(rst.next()){
                    question.setMaxScore(rst.getString("MaxScore"));
                    question.setProblemDescription(rst.getString("ProblemDescription"));
                    question.setProblemStatement(rst.getString("ProblemStatement"));
                    question.setTestCase(rst.getString("TestCase"));
                    question.setAnswer(rst.getString("Answer"));
                    question.setPuzzleDescription(rst.getString("PuzzleDescription"));
                    question.setPuzzleStatement(rst.getString("PuzzleStatement"));
                    question.setQuestionType(rst.getString("QuestionType"));
                    question.setQuestionInputFormat(rst.getString("CodeInput"));
                    question.setQuestionOutputFormat(rst.getString("CodeOutput"));
                    question.setExample(rst.getString("Example"));
                    question.setExpectedOp(rst.getString("ExpectedOp"));
                }
                question.setStatus("Success");
            }else{
                question.setStatus("Fail");
            }
            con.close();
        }
        catch(Exception ex){
            question.setStatus(ex.getMessage());
        }
        return question;
    }
    
    public static Login checkLogin(Login loginDetails) throws SQLException{
        Login login = new Login();
        String sql="";
        java.sql.Connection con = Connection.connectionEstablish();
        Statement stmt = con.createStatement();
        ResultSet rst = null;
        sql =   "SELECT TeamName"
                    + " ,InitialTime"
                    + " FROM TeamDetail WHERE TeamName='" 
                    + loginDetails.getTeamName().toLowerCase() 
                    + "' AND PassCode='"+loginDetails.getPasscode()+"'"
                    + " AND IsComplete = 0;";
        rst = stmt.executeQuery(sql);
        if(rst.next()){
            login.setInitialTime(rst.getString("InitialTime"));
            stmt.executeUpdate("UPDATE TeamDetail SET IsComplete = 1 WHERE TeamName='"+loginDetails.getTeamName()+"';");
            login.setStatus("Success");
            login.setTeamName(loginDetails.getTeamName());
        }else{
            login.setStatus("Fail");
        }
        con.close();
        return login;
    }
    
    public static FinalSubmission finalSubmit(FinalSubmission finalSubmission){
        FinalSubmission finalSubmit = new FinalSubmission();
        String sql="UPDATE TeamDetail SET FinalTime='"+finalSubmission.getCompletionTime()+"'"
        		+" WHERE TeamName='"+finalSubmission.getTeamName()+"';";
        java.sql.Connection con = Connection.connectionEstablish();
        try{
        	Statement stmt = con.createStatement();
        	int numRowsChanged = stmt.executeUpdate(sql);
        	if(numRowsChanged>0){
                    finalSubmit.setStatus("Success");
        	}else{
                    finalSubmit.setStatus("Fail");
        	}
        }
        catch(Exception ex){
            finalSubmit.setStatus("Fail");
        }
        return finalSubmit;
    }
    
    public static CheckProgress checkProgress(CheckProgress login) throws SQLException{
    	CheckProgress checkProgress = new CheckProgress();
    	java.sql.Connection con = Connection.connectionEstablish();
        String sql = "SELECT QuestionType, QuestionNumber FROM TeamDetail WHERE TeamName  ='"+login.getTeamName()+"';";
        try{
        	Statement stmt = con.createStatement();
        	ResultSet rst = stmt.executeQuery(sql);
        	if(rst.next()){
        		checkProgress.setQuestionType(rst.getString("QuestionType"));
                        checkProgress.setQuestionNumber(rst.getString("QuestionNumber"));
        		checkProgress.setStatus("Success");
        	}else{
        		checkProgress.setStatus("Fail");
        	}
        }
        catch(Exception ex){
        	checkProgress.setStatus(ex.getMessage());
        }
        con.close();
        return checkProgress;
    }
    
    public static Code compileCode(Code resource){
        Code code = new Code();
        MultiValueMap<String, String> jsonMap= new LinkedMultiValueMap<String, String>();
        String URL="";
        if(resource.getType().toLowerCase().equals("compile")){
            URL = "https://api.hackerearth.com/v3/code/compile/";
        }
        else if(resource.getType().toLowerCase().equals("run")){
            URL = "https://api.hackerearth.com/v3/code/run/";
            jsonMap.add("input", resource.getTestCase());
        }
	jsonMap.add("async", "0");
	jsonMap.add("time_limit","10");
	jsonMap.add("memory_limit","262144");
        jsonMap.add("client_secret", "c80a8e5ab76c54820f05971d7ed7b8286431087a");
	jsonMap.add("source",resource.getSourceCode());
        jsonMap.add("lang",resource.getLang());
        HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); 
        Map<String,Object>result= callWebService(jsonMap,headers,URL); 
        code.setResult(result);
        
        if(result!=null&&result.size()>0){
            code.setStatus("Success");
        }else{
            code.setStatus("Fail");
        }
        return code;
    }
    
    public static Map<String,Object> callWebService( MultiValueMap<String, String> jsonMap,HttpHeaders headers,String url){
	RestTemplate restTemplate = new RestTemplate();
	HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(jsonMap,headers);
	Map<String,Object> result=new HashMap<String, Object>();
	try{
            result = restTemplate.postForObject(url, entity, Map.class);
	}catch(HttpClientErrorException e){
		e.printStackTrace();
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
} 
}
