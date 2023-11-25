package application.paymentScheduled;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.admin.RowUserRequestLoanController;
import application.entities.Loan;
import application.entities.UserSession;
import application.entities.Loan.LoanStatus;
import application.model.ConnectDB;
import application.requestLoanHome.RowRequestHistoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentScheduledController implements Initializable {

    @FXML
    private VBox layout;

    @FXML
    private AnchorPane paneMain;

    @FXML
    private AnchorPane paneScheduled;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int currentUserId = getCurrentUserId();
		loadScheduled(currentUserId);
	}
	private int getCurrentUserId() {
	    
	    return UserSession.getInstance().getUserId();
	}
	public void loadScheduled(int accountId) {
        List<Loan> loanList = getRequestLoan(accountId);

        layout.getChildren().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Loan loan : loanList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("rowPaymentScheduled.fxml"));
                Parent row = loader.load();
                RowPaymentScheduledController rowController = loader.getController();

                rowController.setLoan(loan); 
                layout.getChildren().add(row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	public List<Loan> getRequestLoan(int accountId) {
        List<Loan> loanList = new ArrayList<>();
        String sql = "SELECT * FROM loan WHERE customer_id = ? ORDER BY startDate DESC";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan();
                    loan.setIdLoanAccount(rs.getInt("idLoanAccount"));
                    loan.setCustomer_id(rs.getInt("customer_id"));
                    loan.setLoantype(rs.getString("loantype"));
                    loan.setAmount(rs.getBigDecimal("amount"));
                    loan.setDuration(rs.getInt("duration"));
                    
                    int statusValue = rs.getInt("status");
                    LoanStatus status = LoanStatus.fromValue(statusValue);
                    loan.setStatus(status);
                    
                    java.sql.Date startDateSQL = rs.getDate("startDate");
                    if (startDateSQL != null) {
                        loan.setStartDate(startDateSQL.toLocalDate());
                    } else {
                        loan.setStartDate(null);  
                    }
                  
                    loan.setRequestCreated(rs.getDate("requestCreated").toLocalDate());
                    loan.setCode(rs.getString("code"));
                    loan.setMonthly_Repayment(rs.getBigDecimal("monthly_Repayment"));
                    loan.setTotal_repayment(rs.getBigDecimal("total_repayment"));
                    loan.setTotal_Interest_Paid(rs.getBigDecimal("total_Interest_Paid"));

                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }
    
}













