package com.example.codingplatform.codingplatform;

import Controller.BuisnessLogic;
import Models.CheckProgress;
import Models.Code;
import Models.FinalSubmission;
import Models.Login;
import Models.Question;
import Models.Register;
import Models.Team;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://codingtestplatform.herokuapp.com")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/codingPlatform")
@SpringBootApplication
public class CodingplatformApplication {
        //final private String url="http://localhost:3000";
        final private String url="https://codingtestplatform.herokuapp.com";
        private final String sharedKey = "SHARED_KEY";
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/register", method = RequestMethod.POST)
        public Register token(@RequestParam(value = "key") String key, @RequestBody Register []resource) {
            return BuisnessLogic.addUser(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/checkTeam", method = RequestMethod.POST)
        public Team token(@RequestParam(value = "key") String key, @RequestBody Team resource) throws SQLException {
            return BuisnessLogic.checkTeamAvailability(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/login", method = RequestMethod.POST)
        public Login token(@RequestParam(value = "key") String key, @RequestBody Login resource) throws SQLException {
            return BuisnessLogic.checkLogin(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/finalSubmission", method = RequestMethod.POST)
        public FinalSubmission token(@RequestParam(value = "key") String key, @RequestBody FinalSubmission resource) throws SQLException {
            return BuisnessLogic.finalSubmit(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/fetchQuestion", method = RequestMethod.POST)
        public Question token(@RequestParam(value = "key") String key, @RequestBody Question resource) throws SQLException {
            return BuisnessLogic.question(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/fetchProgress", method = RequestMethod.POST)
        public CheckProgress token(@RequestParam(value = "key") String key, @RequestBody CheckProgress resource) throws SQLException {
            return BuisnessLogic.checkProgress(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/compileAPI", method = RequestMethod.POST)
        public Code token(@RequestParam(value = "key") String key, @RequestBody Code resource) throws SQLException {
            return BuisnessLogic.compileCode(resource);
        }
        
        @CrossOrigin(origins = url)
        @RequestMapping(value = "/runAPI", method = RequestMethod.POST)
        public Code token1(@RequestParam(value = "key") String key, @RequestBody Code resource) throws SQLException {
            return BuisnessLogic.compileCode(resource);
        }
        
	public static void main(String[] args) {
		SpringApplication.run(CodingplatformApplication.class, args);
	}

}
