package application.login_register;

import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import application.entities.Account;
import application.model.AccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ForgetPasswordPage3Controller implements Initializable {

    @FXML
    private Button buttonUpdate;

    @FXML
    private ImageView imageForget1;

    @FXML
    private ImageView imageLock;

    @FXML
    private TextField inputConfirmPassword;

    @FXML
    private TextField inputNewPassword;

    @FXML
    private AnchorPane pane1;
    
    private String password_new;
    private String password_confirm;
    private String email;
    private String username;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image = new Image(getClass().getResourceAsStream("/application/login_register/libs/lock-solid-48.png"));
	    imageLock.setImage(image);
	    Image image1 = new Image(getClass().getResourceAsStream("/application/login_register/libs/forgetpass3.png"));
	    imageForget1.setImage(image1);
		
	}

    @FXML
    void Send(ActionEvent event) {
    	password_new = inputNewPassword.getText();
    	password_confirm = inputConfirmPassword.getText();
    	
    	
    	if(password_new.length() <  8) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("ERROR");
    		alert.setContentText("Password at least 8 characters");
    		alert.showAndWait();
    		return;
    	}
    	
    	if(!password_new.equals(password_confirm)) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("ERROR");
    		alert.setContentText("Confirmation password is different");
    		alert.showAndWait();
    		return;
    	}
    	
    	AccountModel accountModel = new AccountModel();
    	Account account = accountModel.findByEmail(email);
    	
    	if (account == null) {
    		
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("Cannot find username associated with the given email.");
            alert.showAndWait();
            return;
        }
    	
    	username = account.getUsername();
    	
    	
    	String currentUsername = account.getUsername();
    	
    	String hashedPassword = BCrypt.hashpw(password_confirm, BCrypt.gensalt());
    	
    	boolean isUpdated = accountModel.updatePassword(currentUsername, hashedPassword);
    	if(isUpdated) {
    		System.out.println("success change");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Password has been updated successfully!");
            alert.showAndWait();
            
            
            try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetPasswordPage4.fxml"));
				AnchorPane defaultView = loader.load();
				
				ForgetPasswordPage4Controller controller = loader.getController();
				controller.setUsername(username);
				controller.setPassword(inputConfirmPassword.getText());
				
				
				pane1.getChildren().setAll(defaultView);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("Failed to update the password. Please try again.");
            alert.showAndWait();
            return;
        }
    	
    	
    	
    	
    	
    }
    public void setEmail(String email) {
		this.email = email;
	}

}
