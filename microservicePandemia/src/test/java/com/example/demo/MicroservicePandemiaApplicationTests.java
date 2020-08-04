package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.daos.UserDao;
import com.example.demo.entities.User;
import com.example.services.LoginService;
import com.example.utils.UserNotLoggedException;
import com.example.utils.UtilityPerCriptare;

@SpringBootTest
class MicroservicePandemiaApplicationTests {
	
	//LoginService loginService = new LoginServiceImpl();
	@Autowired
	LoginService loginService;
	
	@Autowired
	UtilityPerCriptare utilityPerCriptare; 
	
	@Autowired 
	UserDao userDao;
	
	
	@Test
	void loginMyUser() throws UserNotLoggedException{
		String id ="RGNLSN87H13D761R";
		//String psw="123";
		Optional<User> userr = userDao.findById(id);
		assertEquals(true,userr.isPresent());
	}
	
	@Test
	void criPta() {
		
		String id ="RGNLSN87H13D761R";
		String psw="123";
		Optional<User> userr = userDao.findById(id);
		User myUser = userr.get();
		
		assertEquals(psw, utilityPerCriptare.deCripta(myUser.getPassword()));
		//if(utilityPerCriptare.deCripta(myUser.getPassword()).equals(password)) {
		
		
	}

}
