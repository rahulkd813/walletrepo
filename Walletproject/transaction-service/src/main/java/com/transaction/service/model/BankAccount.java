package com.transaction.service.model;

import org.springframework.stereotype.Controller;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Controller
public class BankAccount {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 @NotNull(message = "Account Holder's Name is required")
	    private String accountHolderName;

	    @NotNull(message = "Mobile number is required")
	    @Pattern(regexp = "\\d{10}", message = "Please provide a valid 10-digit mobile number")
	    private String mobileNumber;

	    @NotNull(message = "Account Type is required")
	    private String accountType;

	    @NotNull(message = "Account Number is required")
	    private String accountNumber;

	    @NotNull(message = "IFSC Code is required")
	    private String ifsccode;
	    
	    @NotNull(message = "ATM card number is required")
	    private String atmCardNumber;

	    @NotNull(message = "BankAccount Name is required")
	    private String bankName;

	    @Digits(integer = 4, fraction = 0, message = "ATM pin must be a 4-digit number")
	    @Column(name = "atm_pin")
	    private Integer atmPin;


	    @Column(name = "balance")
	    private Double balance;

}