package application.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import application.entities.Account;
import application.entities.Loan;
import application.entities.Loan.LoanStatus;
import application.entities.Account.AccountStatus;
import application.model.AccountModel;
import application.model.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RowUserRequestLoanController {

    @FXML
    private Button button_accept;

    @FXML
    private Button button_detail;

    @FXML
    private Button button_reject;

    @FXML
    private Label label_amount;

    @FXML
    private Label label_dateOfBirth;

    @FXML
    private Label label_term;

    @FXML
    private Label label_type;

    @FXML
    private Label label_username;

    private Loan loan;
    
    private Account account;
    private AccountModel accountModel; 
    

    public RowUserRequestLoanController() {
        accountModel = new AccountModel(); // Initialize AccountModel in the constructor
    }
    
    @FXML
    void accept(ActionEvent event) {
    	 if (showConfirmation("Accept Loan Request", "Are you sure you want to accept this loan request?")) {
    	        updateLoanStatus(loan.getIdLoanAccount(), LoanStatus.ACTIVE);
    	        showAlert("Success", "Loan request accepted.");
    	    }
    }

    @FXML
    void detail(ActionEvent event) {
    	
    	
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accountPendingDetail.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT); 
            stage.setScene(scene);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
            
           
            AccountPendingDetailController detailController = loader.getController();
            Account account = accountModel.findById(loan.getCustomer_id());
  
            detailController.setData(account);
            
            
            
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void reject(ActionEvent event) {
    	if (showConfirmation("Reject Loan Request", "Are you sure you want to reject this loan request?")) {
            updateLoanStatus(loan.getIdLoanAccount(), LoanStatus.REJECTED);
            showAlert("Success", "Loan request rejected.");
        }
    }
    public void setLoanData(Loan loan) {
        this.loan = loan;
        this.account = accountModel.findById(loan.getCustomer_id());
        
        
        
        label_username.setText(account.getUsername());
        label_dateOfBirth.setText(loan.getRequestCreated().toString());
        label_type.setText(loan.getLoantype());
        label_term.setText(String.valueOf(loan.getDuration()));
        label_amount.setText(String.format("%.2f", loan.getAmount()));
        
       
    }
    private boolean showConfirmation(String title, String content) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(title);
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(content);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        confirmAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = confirmAlert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
    private void updateLoanStatus(int loanID, LoanStatus newStatus) {
        // Check if the status is being set to ACTIVE and include startDate in the SQL query
        String sql = newStatus == LoanStatus.ACTIVE
                     ? "UPDATE loan SET status = ?, startDate = CURRENT_DATE WHERE idLoanAccount = ?"
                     : "UPDATE loan SET status = ? WHERE idLoanAccount = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newStatus.getValue());
            if (newStatus == LoanStatus.ACTIVE) {
                // If the status is ACTIVE, the second parameter will be the loanID
                pstmt.setInt(2, loanID);
            } else {
                // If the status is not ACTIVE, set the loanID here
                pstmt.setInt(2, loanID);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating loan status failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update loan status: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    

    }

