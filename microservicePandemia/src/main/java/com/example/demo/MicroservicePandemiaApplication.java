package com.example.demo;

import java.util.Date;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.contollers.RestController;
import com.example.demo.daos.AccountDao;
import com.example.demo.daos.OperationDao;
import com.example.demo.daos.UserDao;
import com.example.demo.entities.Account;
import com.example.demo.entities.Operation;
import com.example.demo.entities.User;
//import com.example.utils.UtilityPerCriptare;
import com.example.utils.UtilityPerCriptare;


@SpringBootApplication

@ComponentScan({"com.example"})
//@ComponentScan({"com.example.demo.controllers"})
@ComponentScan(basePackageClasses = RestController.class)


public class MicroservicePandemiaApplication implements CommandLineRunner {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	OperationDao operationDao;
	
	@Autowired
	UtilityPerCriptare utilityPerCriptare;
	
	
	
	public static final Logger log = LoggerFactory.getLogger(MicroservicePandemiaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MicroservicePandemiaApplication.class, args);
	}
	
	@Override
	public void run(String...strings ) throws Exception {
		log.info("Bacia la fessa a tua sorella quella bionda");		
		
		String primo = utilityPerCriptare.criPta("123");
		String secondo = utilityPerCriptare.criPta("CiccioPericiccio");
		
		userDao.save(new User("RGNLSN87H13D761R", "Alessandro Argentieri",primo,"user"));
		userDao.save(new User("LSAGZN68A29H501C", "Aloisi Graziano", secondo,"user"));
		
		log.info("Cripta Primo-123->"+primo);
		log.info("Cripta Secondo-CiccioPericiccio->"+secondo);
		
		accountDao.save(new Account("PIPPO4563", "LSAGZN68A29H501C", 1000.00));
		accountDao.save(new Account("cn4563df3", "RGNLSN87H13D761R", 3000.00));
		accountDao.save(new Account("cn4563df3", "RGNLSN87H13D761R", 3000.00));
		accountDao.save(new Account("cn7256su9", "RGNLSN87H13D761R", 4000.00));
		accountDao.save(new Account("cn6396dr7", "FRNFBA85M08D761M", 7000.00));
		accountDao.save(new Account("cn2759ds4", "DSTLCU89R52D761R", 2000.00));
		accountDao.save(new Account("cn2874da2", "DSTLCU89R52D761R", 8000.00));
				
		operationDao.save(new Operation("3452",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3453",new Date(),"Pagamento tasse", -100.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3454",new Date(),"Postagiro", 230.00, "cn4563df3","cn2759ds4"));
		operationDao.save(new Operation("3455",new Date(),"Vaglia postale", 172.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3456",new Date(),"Acquisto azioni", -3400.00, "cn2759ds4",""));
		operationDao.save(new Operation("3457",new Date(),"Vendita azione", 100.00, "cn4563df3",""));
		operationDao.save(new Operation("3458",new Date(),"Prelevamento", -100.00, "cn4563df3",""));
		operationDao.save(new Operation("3459",new Date(),"Deposito", 1100.00, "cn4563df3",""));
		operationDao.save(new Operation("3460",new Date(),"Bonifico bancario", 100.00, "cn2874da2","cn4563df3"));
		operationDao.save(new Operation("3461",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn2874da2"));
		operationDao.save(new Operation("3462",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3463",new Date(),"Postagiro", 230.00, "cn7256su9","cn2759ds4"));
		operationDao.save(new Operation("3464",new Date(),"Vaglia postale", 172.00, "cn4563df3","cn7256su9"));
		operationDao.save(new Operation("3465",new Date(),"Acquisto azioni", -3400.00, "cn7256su9","cn7256s50"));
		operationDao.save(new Operation("3466",new Date(),"Acquisto Cannabis", 1000.00, "Fn7556su7","cnXXXs56"));
	
	}
	
	@Bean
	public BasicTextEncryptor criptazioneTesto() {
		//ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
		//cpe.setAlgorithm("SHA-1");
		//cpe.setPlainDigest(true);		
		
		BasicTextEncryptor textEncriptor = new BasicTextEncryptor();
		textEncriptor.setPassword("PippoFaLapizza_conlecozzeOffregate1948");
		return textEncriptor;
	}
	
	//Anche se ha tutti metodi statici ho iniettato il component 
	//per puro scopo didattico
	/*
	@Bean
	public JwtUtils jSonUtils() {
		JwtUtils jsonUtils = new JwtUtils(); 
		return jsonUtils;
	}
	*/
	
	
	
	
}
