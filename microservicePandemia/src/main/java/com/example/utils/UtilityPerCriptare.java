package com.example.utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UtilityPerCriptare {
	//Attento trattasi di Autowired del bean del Bean che vive nella classe madre.
	
	@Autowired 
	BasicTextEncryptor criptazioneTesto;
	
	public String criPta(String cript) {
		return criptazioneTesto.encrypt(cript);
	}
	
	public String deCripta(String decripta) {
		return criptazioneTesto.decrypt(decripta);
	}

}
