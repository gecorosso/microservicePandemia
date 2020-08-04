package com.example.services;

import java.util.List;
import com.example.demo.entities.Account;
import com.example.demo.entities.Operation;

public interface OperationService {
	List<Operation> getAllOperationPerAccount(String accountId);
    List<Account> getAllAccountsPerUser(String userId);
    Operation saveOperation(Operation operation);
}
