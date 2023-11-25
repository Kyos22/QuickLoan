package application.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.entities.Account;
import application.entities.Loan;
import application.entities.PaymentScheduledItem;
import application.entities.Loan.LoanStatus;
import application.entities.Account.AccountStatus;


public class LoanModel {
	public static boolean create(Loan loan) {
		boolean result = true;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("insert into loan(customer_id, loanType, amount, duration, startDate, requestCreated, code, monthly_Repayment,total_Interest_Paid,Total_repayment,status) values(?, ?, ?, ?, ?, ?, ?,?,?,?,?)");
			   preparedStatement.setInt(1, loan.getCustomer_id());
	            preparedStatement.setString(2, loan.getLoantype());
	            preparedStatement.setBigDecimal(3, loan.getAmount());
	            preparedStatement.setInt(4, loan.getDuration());
	            preparedStatement.setObject(5, loan.getStartDate());
	            preparedStatement.setObject(6, loan.getRequestCreated());
	            preparedStatement.setString(7, loan.getCode());
	            preparedStatement.setBigDecimal(8, loan.getMonthly_Repayment());
	            preparedStatement.setBigDecimal(9, loan.getTotal_Interest_Paid());
	            preparedStatement.setBigDecimal(10, loan.getTotal_repayment());
	            preparedStatement.setInt(11, LoanStatus.PENDING.getValue());
			
			result = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			ConnectDB.disconnect();
		}
		return result;
	}
	public Loan findByIdUser(int id) {
		Loan loan = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("SELECT * FROM loan WHERE customer_id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				loan = new Loan();
					loan.setIdLoanAccount(resultSet.getInt("idLoanAccount"));
		            loan.setCustomer_id(resultSet.getInt("customer_id"));
		            loan.setLoantype(resultSet.getString("loantype"));
		            loan.setAmount(resultSet.getBigDecimal("amount"));
		            loan.setDuration(resultSet.getInt("duration"));
		            loan.setStartDate(resultSet.getDate("startDate").toLocalDate());
		            loan.setRequestCreated(resultSet.getDate("requestCreated").toLocalDate());
		            loan.setCode(resultSet.getString("code"));
		            loan.setMonthly_Repayment(resultSet.getBigDecimal("monthly_Repayment"));
		            loan.setTotal_Interest_Paid(resultSet.getBigDecimal("total_Interest_Paid"));
		            loan.setTotal_repayment(resultSet.getBigDecimal("Total_repayment"));
		            
		            int statusValue = resultSet.getInt("status");
		            LoanStatus status = LoanStatus.fromValue(statusValue);
		            loan.setStatus(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loan;
	}
	public static int getCountActiveLoansForUser(int userId) {
	    int count = 0;
	    try {
	        PreparedStatement preparedStatement = ConnectDB.connection()
	                .prepareStatement("SELECT COUNT(*) FROM loan WHERE customer_id = ? AND status = ?");
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, LoanStatus.ACTIVE.getValue());
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return count;
	}
	
	 public static BigDecimal getTotalActiveLoanAmount(int userId) {
	        BigDecimal totalLoanAmount = BigDecimal.ZERO;

	        try {
	            PreparedStatement preparedStatement = ConnectDB.connection()
	                    .prepareStatement("SELECT SUM(amount) as amount FROM loan WHERE customer_id = ? AND status = ?");
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, LoanStatus.ACTIVE.getValue());
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                totalLoanAmount = resultSet.getBigDecimal("amount");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectDB.disconnect();
	        }

	        return totalLoanAmount;
	    }
	

	 public static BigDecimal getTotalActiveMonthlyRepayment(int userId) {
	        BigDecimal totalMonthlyRepayment = BigDecimal.ZERO;

	        try {
	            PreparedStatement preparedStatement = ConnectDB.connection()
	                    .prepareStatement("SELECT SUM(monthly_Repayment) as monthly_Repayment FROM loan WHERE customer_id = ? AND status = ?");
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, LoanStatus.ACTIVE.getValue());
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                totalMonthlyRepayment = resultSet.getBigDecimal("monthly_Repayment");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectDB.disconnect();
	        }

	        return totalMonthlyRepayment;
	    }

    public boolean hasActiveLoanOfType(int userId, String loanType) {
        try {
            PreparedStatement preparedStatement = ConnectDB.connection()
                    .prepareStatement("SELECT COUNT(*) FROM loan WHERE customer_id = ? AND loantype = ? AND status = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, loanType);
            preparedStatement.setInt(3, LoanStatus.ACTIVE.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Kiểm tra xem có bản ghi nào không
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.disconnect();
        }
        return false;
    }
    public boolean hasLoan(int userId) {
        try {
            PreparedStatement preparedStatement = ConnectDB.connection()
                    .prepareStatement("SELECT COUNT(*) FROM loan WHERE customer_id = ? AND status = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, LoanStatus.ACTIVE.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Kiểm tra xem có bản ghi nào không
            }
        } catch (SQLException e	) {
            e.printStackTrace();
        } finally {
            ConnectDB.disconnect();
        }
        return false;
    }
    public boolean hasLoan1(int userId,int loanId) {
    	try {
    		PreparedStatement preparedStatement = ConnectDB.connection()
    				.prepareStatement("SELECT COUNT(*) FROM loan WHERE customer_id = ? AND idLoanAccount = ? AND status = ?");
    		preparedStatement.setInt(1, userId);
    		preparedStatement.setInt(2, loanId);
    		preparedStatement.setInt(3, LoanStatus.ACTIVE.getValue());
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if (resultSet.next()) {
    			return resultSet.getInt(1) > 0; // Kiểm tra xem có bản ghi nào không
    		}
    	} catch (SQLException e	) {
    		e.printStackTrace();
    	} finally {
    		ConnectDB.disconnect();
    	}
    	return false;
    }
   



    

}






















