package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//operations (String ID, Date DATE, Double VALUE, String DESCRIPTION, String FK_ACCOUNT1, String FK_ACCOUNT2)
//Aggiungiamo la validazione Jsr-300
//Getter e setter di lombok
//Aggiungiamo le notazioni di lombok anche per generare costruttori
//In Automatico ricordiamoci di inserire la rigenerazione dei costruttori 
//sopra la classe

//E' fondamentale NON aggiungere la notazione 
//ad i campi numerici @NotBlank @NotEmpty in quanto  
//i numeri non possono essere vuoti ma solo @NotNull

@Entity
@Table(name="OPERATIONS")
@AllArgsConstructor @NoArgsConstructor
public class Operation {
	@Id
    @Column(name="ID")
	@NotBlank @NotEmpty @NotNull
	@Getter @Setter
	private String id; 
	
	@Column(name="DATE")
	@NotNull
	@Getter @Setter
	private Date date;
    
	@Column(name="DESCRIPTIONS")
	@Getter @Setter
	private String description;
	
	@Column(name="VALUE")	 
	@Getter @Setter
	@NotNull
	private Double value;
	
	@Column(name="FK_ACCOUNT1")
	@NotBlank @NotEmpty @NotNull
	@Getter @Setter
	private String fk_account1;
	
	@Column(name="FK_ACCOUNT2")
	@Getter @Setter
	private String fk_account2;	
	
	@PrePersist
	public void getOperation() {
		this.date = new Date();
	}

}
