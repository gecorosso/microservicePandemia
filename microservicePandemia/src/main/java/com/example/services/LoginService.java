package com.example.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.example.utils.*;
import com.example.demo.entities.*;

public interface LoginService {
	Optional<User> getUserFromDbAndVerifyPassword(String id, String password)throws UserNotLoggedException;
    String createJwt(String subject, String name, String permission, Date date) throws UnsupportedEncodingException;
    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UserNotLoggedException, UnsupportedEncodingException;
    
}
