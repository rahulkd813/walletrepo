package com.transaction.service.dto;

import jakarta.validation.constraints.NotBlank;
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
public class BankAccountDTO {

 

    @NotBlank(message = "Account Holder's Name is required")
    private String accountHolderName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10}", message = "Please provide a valid 10-digit mobile number")
    private String mobileNumber;

    @NotBlank(message = "Account Type is required")
    private String accountType;

    @NotBlank(message = "Account Number is required")
    private String accountNumber;

    @NotBlank(message = "IFSC Code is required")
    private String ifsccode;
    
    @NotBlank(message = "ATM card number is required")
    private String atmCardNumber;

    @NotBlank(message = "BankAccount Name is required")
    private String bankName;

    @NotBlank(message = "ATM PIN is required")
    @Size(min = 4, max = 4, message = "ATM PIN must be 4 digits")
    private int atmPin;

}
