package com.example.demo.daos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, String> {	 
	 @Query(value = "SELECT * FROM accounts WHERE FK_USER=:user", nativeQuery = true)
	 List<Account> getAllAccountsPerUser(@Param("user") String user);	 
}
