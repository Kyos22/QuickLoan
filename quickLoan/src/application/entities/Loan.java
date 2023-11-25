package application.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import application.entities.Account.AccountStatus;

public class Loan {
	private int idLoanAccount;
	private int customer_id;
	private String loantype;
	private BigDecimal amount;
	private int duration;
	
	
	private LocalDate startDate;
	private LocalDate requestCreated;
	private String code;
	private BigDecimal monthly_Repayment;
	private BigDecimal total_Interest_Paid;
	private BigDecimal Total_repayment;
	
	public enum LoanStatus {
	    PENDING(0),
	    ACTIVE(1),
	    REJECTED(2);

		
	    private final int value;

	    private LoanStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }

	    
	    
	    public static LoanStatus fromValue(int value) {
	        for (LoanStatus status : LoanStatus.values()) {
	            if (status.getValue() == value) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Invalid status value: " + value);
	    }
	}
	 private LoanStatus status;

	    

	    public void setStatus(LoanStatus status) {
	        this.status = status;
	    }

	    public LoanStatus getStatus() {
	        return status;
	    }
	
	
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}
	public BigDecimal getMonthly_Repayment() {
		return monthly_Repayment;
	}
	public void setMonthly_Repayment(BigDecimal monthly_Repayment) {
		this.monthly_Repayment = monthly_Repayment;
	}
	public BigDecimal getTotal_Interest_Paid() {
		return total_Interest_Paid;
	}
	public void setTotal_Interest_Paid(BigDecimal total_Interest_Paid) {
		this.total_Interest_Paid = total_Interest_Paid;
	}
	public BigDecimal getTotal_repayment() {
		return Total_repayment;
	}
	public void setTotal_repayment(BigDecimal total_repayment) {
		Total_repayment = total_repayment;
	}
	public int getIdLoanAccount() {
		return idLoanAccount;
	}
	public void setIdLoanAccount(int idLoanAccount) {
		this.idLoanAccount = idLoanAccount;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getRequestCreated() {
		return requestCreated;
	}
	public void setRequestCreated(LocalDate requestCreated) {
		this.requestCreated = requestCreated;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
	
}
