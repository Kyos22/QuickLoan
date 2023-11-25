package application.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
	private int id;
	private String username;
	private String fullname;
	private String email;
	private String password;
	private Boolean term;
	private String role;
	private BigDecimal balance;
	private byte[] photo_after;
	private byte[] photo_before;
	private Boolean isFirstLogin;
	private String firstLoanType;
	private String country;
	private String city;
	private String address;
	private int IdentityNumber;
	private int phoneNumber;
	
	
	
	public enum AccountStatus {
	    PENDING(0),
	    ACTIVE(1),
	    REJECTED(2);

	    private final int value;

	    private AccountStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }

	    // Phương thức để chuyển đổi từ số sang enum
	    public static AccountStatus fromValue(int value) {
	        for (AccountStatus status : AccountStatus.values()) {
	            if (status.getValue() == value) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Invalid status value: " + value);
	    }
	}
	
	 private AccountStatus status;

	    // ... các phương thức khác...

	    public void setStatus(AccountStatus status) {
	        this.status = status;
	    }

	    public AccountStatus getStatus() {
	        return status;
	    }
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getIdentityNumber() {
		return IdentityNumber;
	}
	public void setIdentityNumber(int identityNumber) {
		IdentityNumber = identityNumber;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public LocalDate getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	private LocalDate DateOfBirth;
	
	
	public String getFirstLoanType() {
		return firstLoanType;
	}
	public void setFirstLoanType(String firstLoanType) {
		this.firstLoanType = firstLoanType;
	}
	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}
	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	
	
	public byte[] getPhoto_after() {
		return photo_after;
	}
	public void setPhoto_after(byte[] photo_after) {
		this.photo_after = photo_after;
	}
	public byte[] getPhoto_before() {
		return photo_before;
	}
	public void setPhoto_before(byte[] photo_before) {
		this.photo_before = photo_before;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getTerm() {
		return term;
	}
	public void setTerm(Boolean term) {
		this.term = term;
	}
	
	
}
