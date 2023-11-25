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
import application.model.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AllUserVerifiedController implements Initializable {

    @FXML
    private VBox layout;

    @FXML
    private AnchorPane paneCenter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadPendingAccounts();
		
	}
	
	public void loadPendingAccounts() {
        List<Account> pendingAccounts = getAccountActive(); 
        for (Account account : pendingAccounts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("rowAllUserVerifiedPage.fxml"));
                Parent row = loader.load();
                RowAllUserVerifiedController rowController = loader.getController();
                
                rowController.setAccountData(account);
                layout.getChildren().add(row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public List<Account> getAccountActive() {
	    List<Account> pendingAccounts = new ArrayList<>();
	    String sql = "SELECT * FROM account WHERE status = 1 ORDER BY id DESC"; 

	    try (Connection conn = ConnectDB.connection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Account account = new Account();
	                account.setId(rs.getInt("id"));
                    account.setUsername(rs.getString("username"));
                    account.setFullname(rs.getString("fullname"));
                    account.setEmail(rs.getString("email"));
                    account.setPhoneNumber(rs.getInt("phoneNumber"));
                    account.setAddress(rs.getString("address"));
                    account.setCountry(rs.getString("country"));
                    account.setCity(rs.getString("city"));
                    account.setIdentityNumber(rs.getInt("IdentityNumber"));
                    
                    int statusValue = rs.getInt("status");
                    AccountStatus status = AccountStatus.fromValue(statusValue);
                    account.setStatus(status);
                    Date dob = rs.getDate("DateOfBirth");
                    if (dob != null) {
                        account.setDateOfBirth(dob.toLocalDate());
                    }
                    account.setPhoto_before(rs.getBytes("photo_before"));
                    account.setPhoto_after(rs.getBytes("photo_after"));
                    
	                pendingAccounts.add(account);
	            }
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return pendingAccounts;
	}
    
    
    
}
