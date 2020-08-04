package com.example.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.daos.UserDao;
import com.example.demo.entities.User;
import com.example.utils.JwtUtils;
import com.example.utils.UserNotLoggedException;
import com.example.utils.UtilityPerCriptare;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired 
	UserDao userDao;
	
	@Autowired
	UtilityPerCriptare utilityPerCriptare; 
	
	
    final Logger log = org.slf4j.LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Override
	public Optional<User> getUserFromDbAndVerifyPassword(String id, String password) throws UserNotLoggedException {
				
		Optional<User> userr = userDao.findById(id);
		if(userr.isPresent()) {
			//Vai a fare una specie di cast
			User myUser = userr.get();
						
			if(utilityPerCriptare.deCripta(myUser.getPassword()).equals(password)) {
				log.info("UTENTE Riconosciuto LOGGATO!!!! USERNAME E PASSWORD Corrispondono!!!");
			}else {
				log.info("LA PASSWORD NON Corrisponde");
				throw new UserNotLoggedException("PASSWORD NO BUONA");
			}
		}
		return userr;
		
	}
	

	

	@Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UserNotLoggedException, UnsupportedEncodingException{
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token NON TROVATA");
        }
        Map<String, Object> userData = JwtUtils.jwt2Map(jwt);
        return userData;
    }




	@Override
	public String createJwt(String subject, String name, String permission, Date date)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public String createJwt(String subject, String name, String permission, Date date)
			throws UnsupportedEncodingException {
		Date expDate = date;
	    expDate.setTime(date.getTime()+(300*1000));
	    log.info("Questa sessione scadrà nel: " + expDate.getTime());
	    String token = JwtUtils.generateJwt(subject, expDate, name, permission);		
	    return token;
	}
	*/
	
	
	
}
