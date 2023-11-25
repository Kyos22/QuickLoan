package application.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import application.entities.PaymentScheduledItem;

public class PaymentScheduled {
	public static boolean createNewPaymentScheduled(PaymentScheduledItem item) {
        boolean result = true;
        try {
            PreparedStatement preparedStatement = ConnectDB.connection()
                    .prepareStatement("INSERT INTO payment_scheduled (customer_id,payment_date, remainingPrincipal, monthlyRepayment,status,loanId) VALUES (?,?,?, ?, ?,?)");
            
            preparedStatement.setInt(1, item.getCustomer_id());
            preparedStatement.setDate(2, java.sql.Date.valueOf(item.getPaymentDate())); 
            preparedStatement.setBigDecimal(3, item.getRemainingPrincipal()); 
            preparedStatement.setBigDecimal(4, item.getMonthlyRepayment()); 
            preparedStatement.setBoolean(5, false);
            preparedStatement.setInt(6, item.getLoanId()); 

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            ConnectDB.disconnect(); 
        }
        return result;
    }
	public static PaymentScheduledItem getNextPayment(int userId) {
        PaymentScheduledItem nextPayment = null;
        LocalDate today = LocalDate.now();

        try {
            PreparedStatement preparedStatement = ConnectDB.connection()
                .prepareStatement("SELECT * FROM payment_scheduled WHERE customer_id = ? AND payment_date > ? ORDER BY payment_date ASC LIMIT 1");

            preparedStatement.setInt(1, userId);
            preparedStatement.setObject(2, today);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate paymentDate = resultSet.getDate("payment_date").toLocalDate();
                BigDecimal monthlyRepayment = resultSet.getBigDecimal("monthlyRepayment");
                BigDecimal remainingPrincipal = resultSet.getBigDecimal("remainingPrincipal");
                Boolean status = resultSet.getBoolean("status");
                int loanId = resultSet.getInt("loanId");

                nextPayment = new PaymentScheduledItem();
                nextPayment.setId(id);
                nextPayment.setPaymentDate(paymentDate);
                nextPayment.setMonthlyRepayment(monthlyRepayment);
                nextPayment.setRemainingPrincipal(remainingPrincipal);
                nextPayment.setCustomer_id(userId);
                nextPayment.setStatus(status);
                nextPayment.setLoanId(loanId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.disconnect(); 
        }
        return nextPayment;
    }
	public static boolean updatePaymentStatus(int paymentId, LocalDate paymentDate) {
	    boolean result = false;
	    try {
	        PreparedStatement preparedStatement = ConnectDB.connection()
	                .prepareStatement("UPDATE payment_scheduled SET status = ? WHERE loanId = ? AND payment_date = ?");
	        
	        preparedStatement.setBoolean(1, true);
	        preparedStatement.setInt(2, paymentId);
	        preparedStatement.setDate(3, java.sql.Date.valueOf(paymentDate)); 

	        result = preparedStatement.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        result = false;
	    } finally {
	        ConnectDB.disconnect();
	    }
	    return result;
	}
	public static int countActiveLoansByLoanId(int loanId) {
	    int count = 0;
	    try {
	        PreparedStatement preparedStatement = ConnectDB.connection()
	                .prepareStatement("SELECT COUNT(*) FROM payment_scheduled WHERE loanId = ? AND status = true");

	        preparedStatement.setInt(1, loanId);

	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        ConnectDB.disconnect();
	    }
	    return count;
	}
	public static PaymentScheduledItem findPayment(int userId, int loanId, LocalDate paymentDate) {
	    PaymentScheduledItem payment = null;

	    try {
	        PreparedStatement preparedStatement = ConnectDB.connection()
	            .prepareStatement("SELECT * FROM payment_scheduled WHERE customer_id = ? AND loanId = ? AND payment_date = ? AND status = true");

	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, loanId);
	        preparedStatement.setDate(3, java.sql.Date.valueOf(paymentDate));

	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            LocalDate paymentDate1 = resultSet.getDate("payment_date").toLocalDate();
	            BigDecimal monthlyRepayment = resultSet.getBigDecimal("monthlyRepayment");
	            BigDecimal remainingPrincipal = resultSet.getBigDecimal("remainingPrincipal");
	            Boolean status = resultSet.getBoolean("status");

	            payment = new PaymentScheduledItem();
	            payment.setId(id);
	            payment.setPaymentDate(paymentDate1);
	            payment.setMonthlyRepayment(monthlyRepayment);
	            payment.setRemainingPrincipal(remainingPrincipal);
	            payment.setStatus(status);
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        ConnectDB.disconnect(); 
	    }
	    return payment;
	}

	




}
