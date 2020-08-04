package com.example.demo.entities;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

//accounts   (String ID, String FK_USER, Double TOTAL)
//inseriamo la validazione JSR303
//E' fondamentale NON aggiungere la notazione 
//ad i campi numerici @NotBlank @NotEmpty in quanto  
//i numeri non possono essere vuoti ma solo @NotNull

@Entity
@Table(name="ACCOUNTS")

@AllArgsConstructor @NoArgsConstructor
public class Account {
	@Id
	@Column(name="ID")
	@NotBlank @NotEmpty @NotNull
	@Getter @Setter
	private String id;
	
	@Column(name="FK_USER")
	//@Size(max=6, min=3, message="{fk_user NO BUONO}")
	@NotBlank @NotEmpty @NotNull
	@Getter @Setter
	private String fk_user;
		
	@Column(name="TOTAL")
	@NotNull
	@Getter @Setter
	private Double total;
	
}
