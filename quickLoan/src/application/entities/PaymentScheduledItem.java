package application.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentScheduledItem {
	private int id;
	private Integer customer_id;
	
	private LocalDate paymentDate;
    private BigDecimal remainingPrincipal;
    private BigDecimal monthlyRepayment;
    private Boolean status;
    private int loanId;
    
    public int getCustomer_id() {
		return customer_id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
    
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	public BigDecimal getRemainingPrincipal() {
		return remainingPrincipal;
	}
	public void setRemainingPrincipal(BigDecimal remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}
	public BigDecimal getMonthlyRepayment() {
		return monthlyRepayment;
	}
	public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}
	public PaymentScheduledItem(LocalDate paymentDate, BigDecimal remainingPrincipal, BigDecimal monthlyRepayment) {
        this.paymentDate = paymentDate;
        this.remainingPrincipal = remainingPrincipal;
        this.monthlyRepayment = monthlyRepayment;
        this.customer_id = null; 
    }
	public PaymentScheduledItem(LocalDate paymentDate, BigDecimal remainingPrincipal, BigDecimal monthlyRepayment, int customer_id, int loanId) {
        this.paymentDate = paymentDate;
        this.remainingPrincipal = remainingPrincipal;
        this.monthlyRepayment = monthlyRepayment;
        this.customer_id = customer_id;
        this.loanId = loanId;
    }
	public PaymentScheduledItem() {
		super();
		
	}
	
    
}
