package com.example.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.daos.AccountDao;
import com.example.demo.daos.OperationDao;
import com.example.demo.entities.Account;
import com.example.demo.entities.Operation;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	OperationDao operationDao;	
	
	final Logger log = LoggerFactory.getLogger(OperationServiceImpl.class);

	
	@Override
	public List<Operation> getAllOperationPerAccount(String accountId) {
		log.info("Estrae tutte le operations");		
		return operationDao.findAllOperationsByAccount(accountId);
	}

	@Override
	public List<Account> getAllAccountsPerUser(String userId) {
		log.info("Estrae Account per User");       
		return accountDao.getAllAccountsPerUser(userId);
	}

	@Override
	public Operation saveOperation(Operation operation) {
		
		if(operation.getDate()==null) {
			log.info("Data NON TROVATA METTE QUELLA ODIERNA");
			operation.setDate(new Date());
		}
			log.info("SALVA OPERATION");
			return operationDao.save(operation);
			
			
	}

}
