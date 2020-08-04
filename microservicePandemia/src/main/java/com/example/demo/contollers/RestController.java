package com.example.demo.contollers;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entities.Operation;
import com.example.demo.entities.User;
import com.example.services.LoginService;
import com.example.services.OperationService;
import com.example.utils.UserNotLoggedException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Simple Logging Facade(Facciata) for Java (SLF4J)
//https://medium.com/@fabiogolfarelli/jwt-incrementiamo-la-sicurezza-con-i-json-web-tokens-cd0f2f9880da


@Controller
@ResponseBody
//@org.springframework.web.bind.annotation.RestController
public class RestController {
	final Logger log = LoggerFactory.getLogger(RestController.class);
	final private String sexScad ="Sessione Scaduta";
	
	//Facciamo @Autowired dell interfaccie
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	OperationService operationService;
	
	
	@RequestMapping("/index")
	public String saLuta() {
		return "<b><h1>Saluta Tua cugina quella bona!!</h1></b>";
	}
	
	@RequestMapping("/newUser1")
	public String userZerouno(@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "INPUT NON CONFORME--->" +  result.toString();
		}
		return "OKKEI--->"+user.getUsername();
	}
	
	@RequestMapping("newUser2")
	public String userZeroDue(User user, BindingResult result)  {
		Validazione validator = new Validazione();
		validator.validate(user, result);

		if(result.hasErrors()) {
			return user.toString();
		}

		return "OKKEI Password-->" + user.getPassword() + "Okkei User:-->" + user.getPermission();

	}
	
	//---InnerClass----
	public class Validazione implements Validator{
		@Override
		public boolean supports(Class<?> clazz) {
			return User.class.equals(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			User user = (User) target;
			if(user.getPassword().length()<8) {
				errors.rejectValue("ERRORE PASSWORD ALMENO 8 CARATTERI ", "PassLow");
			}
			if(user.getPermission().length()<2) {
				errors.rejectValue("PERMESSO ALMENO 2 CARATTERI", "PermessoLow");
			}
		}

	}
	
	//--InnerClass
	/*---------------------------------------------------------*/

    /**
     * inner class used as the Object tied into the Body of the ResponseEntity.
     * It's important to have this Object because it is composed of server response code and response object.
     * Then, JACKSON LIBRARY automatically convert this JsonResponseBody Object into a JSON response.
     */
	//Con questa Inner class dobbiamo considerare che dobbiamo 
	//costruire una risposta la quale è costituita da un codice e da una 
	//Stringa che sarebbe un oggetto .... quindi la seguente classe:
	
    @AllArgsConstructor
    public class JsonResponseBody{
        @Getter @Setter
        private int server;
        @Getter @Setter
        private Object response;
    }

    /*---------------------------------------------------------*/
	
    @RequestMapping(value = "/login")
    public ResponseEntity<JsonResponseBody> loginUser(@Valid @RequestParam(value ="id") String id, @RequestParam(value="password") String pwd){
    	log.info("ID-->"+id);
    	log.info("Password-->"+pwd);
    	
    	
    	try {
			Optional<User> userr = loginService.getUserFromDbAndVerifyPassword(id, pwd);			
			if(userr.isPresent()) {
				User user = userr.get();
				String subject = user.getId();
				String name = user.getUsername();
				String permission = user.getPermission();
				
				//Andiamo a prendere il jwt
				String jwt = loginService.createJwt(subject, name, permission, new Date());
				log.info("Jwt Creata-->" + jwt);
				
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.header("jwt", jwt)
						.body(new JsonResponseBody(HttpStatus.OK.value(),"jwt Creato con successo"));
				
			}			
    	} catch (UserNotLoggedException e1) {
    		log.info("Login Fallita");
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(),"Login Fallita"));
					
		}catch (UnsupportedEncodingException e) {
			log.info("Token In errore!!!");
			ResponseEntity
			.status(HttpStatus.FORBIDDEN)
			.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(),"Token In errore!!!"));
		}
    	
    	log.info("ATTENZIONE NESSUNA CORRISPONDENZA TROVATA NEL DB");
    	return ResponseEntity
    			.status(HttpStatus.FORBIDDEN)
    			.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(),"Nessuna corrispondenza trovata nel DB"));
    }
    
    
    @RequestMapping(value="/operations/accounts/{account}", method = RequestMethod.GET )
    public ResponseEntity<JsonResponseBody> fetchAllOperationsPerAccount(HttpServletRequest request, @PathVariable(name = "account") String account){
    	try {
			//Lo user è verificato
    		//Mettiamo il try catch per verificare se 
    		//ci sono delle eccezioni
    		//se ci sono allora lo user non è verificato e solleva l' eccezione
    		loginService.verifyJwtAndGetData(request);
			log.info("User correttamente Verificato");
    		return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(),operationService.getAllOperationPerAccount(account)));
    	} catch (UnsupportedEncodingException | UserNotLoggedException e) {
			log.info("Utente non Riconosciuto");
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Utente non riconosciuto: "+e.toString()));
		}catch(ExpiredJwtException expired) {
			log.info(sexScad + expired.toString());
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				   .body(new JsonResponseBody(HttpStatus.BAD_GATEWAY.value(),"Sessione Scaduta: "+expired.toString()));
		}
    }
    
    
    @RequestMapping(value = "/accounts/user", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> fetchAllAccountsPerUser(HttpServletRequest request){
        //request -> fetch JWT -> recover User Data -> Get user accounts from DB
        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), operationService.getAllAccountsPerUser((String) userData.get("subject"))));
        }catch(UnsupportedEncodingException e1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e1.toString()));
        }catch(UserNotLoggedException e2){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "User not logged! Login first : " + e2.toString()));
        }catch(ExpiredJwtException e3){
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired!: " + e3.toString()));
        }
    }

    
    
    @RequestMapping(value = "/operations/add", method=RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> addOperation(HttpServletRequest request, @Valid Operation operation, BindingResult bindingResult){
    	log.info("Description===>"+operation.getDescription());
    	
    	
    	if(bindingResult.hasErrors()) {
    		log.info("Errore nel formato della comunicazione!!"+HttpStatus.FORBIDDEN.value());
    		return ResponseEntity.status(HttpStatus.FORBIDDEN)
    				.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Errore nel formato della comunicazione!!"));
    	}
    	
    	try {
    		loginService.verifyJwtAndGetData(request);
			Operation myOperation = operationService.saveOperation(operation);
			log.info("SALVA OPERATION OKKEI");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new JsonResponseBody(HttpStatus.OK.value(),myOperation));
			
		} catch (UnsupportedEncodingException | UserNotLoggedException e) {
			log.info("Eccezione Per il login: " + e.toString());
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(),"Eccezione Per il login:"+e.toString()));
			
		} catch (ExpiredJwtException expired) {
			log.info(sexScad + expired.toString());
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(),sexScad));
		}
    	
    }
    
    
	
}
