package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

// (String ID, String USERNAME, String PASSWORD, String PERMISSION) 
//Inseriamo la validazione di Javax jsr-303
//Inseriamo la generazione in automatico di Lombok di getter e setter
//Inseriamo la generazione in automatico anche dei costruttori
//Importante sepre ricordarsi di generare i costruttori in testa alla 
//Classe!!!
//E' fondamentale NON aggiungere la notazione 
//ad i campi numerici @NotBlank @NotEmpty in quanto  
//i numeri non possono essere vuoti ma solo @NotNull

@Entity
@Table(name="USER")
@AllArgsConstructor @NoArgsConstructor
public class User {
		@Id
		@Column(name="ID")
	    @NotBlank @NotEmpty @NotNull
	    @Getter @Setter
		private String id;
		
		@Column(name="USERNAME")
		@NotBlank @NotEmpty @NotNull
		@Getter @Setter
		private String username;
		
		@Column(name="PASSWORD")
		@NotBlank @NotEmpty @NotNull
		@Getter @Setter
		private String password;
		
		@Column(name="PERMISSION")
		@NotBlank @NotEmpty @NotNull
		@Getter @Setter
		private String permission;
	
}
