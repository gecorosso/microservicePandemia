package com.example.demo.daos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	//Regola dei nomi
	Optional<User> findById(String id);
}
