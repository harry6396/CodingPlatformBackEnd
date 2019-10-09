package com.example.codingplatform.codingplatform;

import Controller.BuisnessLogic;
import Models.Register;
import Models.Team;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/codingPlatform")
@SpringBootApplication
public class CodingplatformApplication {
        
        private final String sharedKey = "SHARED_KEY";
        
        @CrossOrigin(origins = "http://localhost:3000")
        @RequestMapping(value = "/register", method = RequestMethod.POST)
        public Register token(@RequestParam(value = "key") String key, @RequestBody Register resource) {
            return BuisnessLogic.addUser(resource);
        }
        
        @CrossOrigin(origins = "http://localhost:3000")
        @RequestMapping(value = "/checkTeam", method = RequestMethod.POST)
        public Team token(@RequestParam(value = "key") String key, @RequestBody Team resource) {
            return BuisnessLogic.checkTeamAvailability(resource);
        }
        
	public static void main(String[] args) {
		SpringApplication.run(CodingplatformApplication.class, args);
	}

}
