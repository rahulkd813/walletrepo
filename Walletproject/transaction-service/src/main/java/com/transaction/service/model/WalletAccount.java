package com.transaction.service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class WalletAccount {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull(message = "Full name is required")
	    private String fullName;

	    @NotNull(message = "Email address is required")
	    @Email(message = "Please provide a valid email address")
	    private String emailAddress;

	    @NotNull(message = "Password is required")
	    @Size(min = 6, message = "Password must be at least 6 characters")
	    private String password;

	    @Digits(integer = 4, message = "Wallet PIN must be a positive integer with at most 4 digits", fraction = 0)
	    private int walletPin;

	    @NotNull(message = "Phone number is required")
	    @Pattern(regexp = "\\d{10}", message = "Please provide a valid 10-digit phone number")
	    private String phoneNumber;

	    @JsonFormat(pattern = "yyyy-MM-dd")
	    @NotNull(message = "Date of birth is required")
	    private Date dateOfBirth;

	    @NotNull(message = "Address is required")
	    private String address;
	    
	  
	    private Double balance;


	    @OneToMany(mappedBy = "walletAccount")
	    @JsonIgnore
	    private List<Transaction> transactions;

}