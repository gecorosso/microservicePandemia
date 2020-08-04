package com.example.demo.daos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Operation;

@Repository
public interface OperationDao extends JpaRepository<Operation,String> {
	/*
	Optional<Operation> findById(String id);
	*/
	@Query(value = "SELECT * FROM operations WHERE FK_ACCOUNT1=:account OR FK_ACCOUNT2=:account", nativeQuery = true)
    List<Operation> findAllOperationsByAccount(@Param("account")String account);	 
	
}
