package application.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.Account.AccountStatus;
import application.entities.Loan;
import application.model.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class UserRequestLoanController implements Initializable {

    @FXML
    private VBox layout1;

    @FXML
    private AnchorPane paneCenter;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadRequestLoan();
	}
    public void loadRequestLoan() {
        List<Loan> requestLoan = getLoanRequestFromDatabase(); 

        for (Loan loan : requestLoan) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("rowUserRequestLoan.fxml"));
                Parent row = loader.load();
                RowUserRequestLoanController rowController = loader.getController();
                
                rowController.setLoanData(loan);
                layout1.getChildren().add(row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Loan> getLoanRequestFromDatabase() {
        List<Loan> loanRequests = new ArrayList<>();
       
        String sql = "SELECT * FROM loan WHERE status = 0 ORDER BY idLoanAccount DESC";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan();
                    loan.setIdLoanAccount(rs.getInt("idLoanAccount"));
                    loan.setCustomer_id(rs.getInt("customer_id"));
                    Date dob = rs.getDate("requestCreated");
                    if (dob != null) {
                        loan.setRequestCreated(dob.toLocalDate());
                    }
                    loan.setDuration(rs.getInt("duration"));
                    loan.setAmount(rs.getBigDecimal("amount"));
                    loan.setLoantype(rs.getString("loanType"));
                    

                    loanRequests.add(loan);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanRequests;
    }



	
}
