package application.admin;


import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import application.entities.Account;
import application.entities.Account.AccountStatus;
import application.login_register.VerifyEmail2Controller;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RowAccountPending {

	@FXML
    private Label label_email;

    @FXML
    private Label label_username;
	
    @FXML
    private Button button_accept;

    @FXML
    private Button button_detailpending;

    @FXML
    private Button button_reject;

    private Account account;
    
    @FXML
    void accept(ActionEvent event) {
        if (showConfirmation("Accept Account", "Are you sure you want to accept this account?")) {
            updateAccountStatus(account.getId(), AccountStatus.ACTIVE);
            
            String emailContent = "your request active account was accepted: ";
        	
        	boolean emailSent = application.login_register.helper.MailHelper.send("nguyenthanhcongvt234@gmail.com", account.getEmail(), "Request Account", emailContent);

        	if (!emailSent) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("failed");
        		alert.show();
            }else {
            	Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Succes");
        		alert.show();           	
            }
            
        }
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
    
    @FXML
    public void detailPending(ActionEvent event) {
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
            detailController.setData(account);
            
            
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reject(ActionEvent event) {
        if (showConfirmation("Reject Account", "Are you sure you want to reject this account?")) {
            updateAccountStatus(account.getId(), AccountStatus.REJECTED);
            
            String emailContent = "your request active account was rejected: ";
        	
        	boolean emailSent = application.login_register.helper.MailHelper.send("nguyenthanhcongvt234@gmail.com", account.getEmail(), "Request Account", emailContent);

        	if (!emailSent) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("failed");
        		alert.show();
            }else {
            	Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Succes");
        		alert.show();           	
            }
        }
    }
    public void setAccountData(Account account) {
        this.account = account;
        label_username.setText(account.getUsername());
        label_email.setText(account.getEmail());
       
    }
    
    private void updateAccountStatus(int accountId, AccountStatus newStatus) {
        String sql = "UPDATE account SET status = ? WHERE id = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newStatus.getValue()); 
            pstmt.setInt(2, accountId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   


}
