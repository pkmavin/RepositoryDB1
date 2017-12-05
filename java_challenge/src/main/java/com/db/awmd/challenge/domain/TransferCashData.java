package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class TransferCashData {

  @NotNull
  @NotEmpty
  private final String fromAccountId;
  
  @NotNull
  @NotEmpty
  private final String toAccountId;

  @NotNull
  @Min(value = 0, message = "Amount should be positive.")
  private BigDecimal amount;

  

  @JsonCreator
  public TransferCashData(
		  @JsonProperty("fromAccountId") String fromAccountId,
		  @JsonProperty("toAccountId") String toAccountId,
		  @JsonProperty("amount") BigDecimal amount) 
  {
    this.fromAccountId = fromAccountId;
    this.toAccountId = toAccountId;
    this.amount = amount;
    
    
  }
}
