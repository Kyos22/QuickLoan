package application.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TopUpHistory {
	private int id;
	private BigDecimal amount;
	private LocalDate created;
	private BigDecimal oldBalance;
	private BigDecimal newBalance;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	public BigDecimal getOldBalance() {
		return oldBalance;
	}
	public void setOldBalance(BigDecimal oldBalance) {
		this.oldBalance = oldBalance;
	}
	public BigDecimal getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(BigDecimal newBalance) {
		this.newBalance = newBalance;
	}
	
	
}
