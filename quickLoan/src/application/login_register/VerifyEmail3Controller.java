package application.login_register;

import org.mindrot.jbcrypt.BCrypt;


import application.entities.Account;
import application.login_register.RegisterPageController.EmailValidator;
import application.model.AccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class VerifyEmail3Controller {
	
	@FXML
    private Button button_signUp;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputFullName;

    @FXML
    private TextField inputPassword;

    @FXML
    private CheckBox inputTerm;

    @FXML
    private TextField inputUsername;

    @FXML
    private AnchorPane pane4;

    private String username;
    private String fullname;
    private String password;
    private String email;
    private boolean term;
	
    @FXML
    void signUp(ActionEvent event) {
    	
    	Account account = new Account();
    	try {
    		
    		email = inputEmail.getText();
      	  	username = inputUsername.getText();
      	  	fullname = inputFullName.getText();
    		account.setUsername(username);
    		
        	
        	
        	if(fullname.isEmpty()) {
          		Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Vui lòng điền full name");
                alert.show();
                return;
          	  }
          	  if(username.isEmpty()) {
          		  Alert alert = new Alert(AlertType.ERROR);
          		  alert.setTitle("Error");
          		  alert.setContentText("Vui lòng điền username");
          		  alert.show();
          		  return;
          	  }
          	  	
          	  if (!EmailValidator.isValid(email) || email.isEmpty()) {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setContentText("Địa chỉ email không hợp lệ!");
                  alert.show();
                  return;
              }
          	  	
          	  	AccountModel accountModel = new AccountModel();
          	  	if(accountModel.findByEmail(email) != null ) {
          	  	Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("email đã tồn tại!");
                alert.show();
                return;
          	  	}
          	  	
          	  	if(accountModel.findByUsername(username)!= null) {
          	  	Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Username đã tồn tại!");
                alert.show();
                return;
          	  	}
          	  	
        	if(!email.isEmpty()) {
        		account.setEmail(email);       		
        	}
        	
        	String password = inputPassword.getText();
        	if (password.length() < 8) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Mật khẩu phải có ít nhất 8 ký tự!");
                alert.show();
                return; // Dừng lại ở đây và không thực hiện bất kỳ thao tác nào tiếp theo
            }
        	
        	account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        	
        	String fullname = inputFullName.getText();
        	account.setFullname(fullname);
        	
        	Boolean term = inputTerm.isSelected();
        	account.setTerm(term);
        	account.setRole("user");
        	
        	
        	
        	if(AccountModel.create(account)) {
        		System.out.println("da tao");
        		Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("verifyPage4.fxml"));
                AnchorPane View = loader.load();
                
                VerifyEmail4Controller controller = loader.getController();
                controller.setUsername(username);
                controller.setPassword(password);
                
                pane4.getChildren().setAll(View);
        		
        	}else {
        		System.out.println("chua tao");
        		Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("created fail");
                alert.show();
                return;
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	
	public void setUsername(String username) {
    	inputUsername.setText(username);
    }
    public void setFullname(String fullname) {
    	inputFullName.setText(fullname);
    }
    public void setPassword(String password) {
    	inputPassword.setText(password);
    }
    public void setEmail(String email) {
    	inputEmail.setText(email);
    }
    public void setTerm(boolean term) {
    	inputTerm.setSelected(term);

    }
}
