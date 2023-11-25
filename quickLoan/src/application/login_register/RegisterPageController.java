package application.login_register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class RegisterPageController {

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
    private Button button_imalready;


    @FXML
    private TextField inputUsername;
    
    @FXML
    private AnchorPane pane1;
    
    @FXML
    private Button button_signIn;


    private String email;
    private String username;
    private String password;
    private String fullname;
    @FXML
    void gotoSignIn(ActionEvent event) {
    	try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	pane1.getChildren().addAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void goToVerifyPage1(ActionEvent event) {
    	try {
    		email = inputEmail.getText();
      	  	username = inputUsername.getText();
      	  	fullname = inputFullName.getText();
      	  	
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
      	  	
      	  	String password = inputPassword.getText();
      	  	if (password.length() < 8) {
              Alert alert = new Alert(AlertType.ERROR);
              alert.setTitle("Error");
              alert.setContentText("Mật khẩu phải có ít nhất 8 ký tự!");
              alert.show();
              return; 
          }
      	  	
      	  	
      	  	fullname = inputFullName.getText();
      	  	boolean term = isTermAccepted();
      	
      	
    		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("verifyPage1.fxml"));
			AnchorPane defaultView = loader.load();
			
			VerifyEmailController1 verifyEmailController1 = loader.getController();
			verifyEmailController1.setEmail(email);
			verifyEmailController1.setFullname(fullname);
			verifyEmailController1.setUsername(username);
			verifyEmailController1.setPassword(password);
			verifyEmailController1.setTerm(isTermAccepted());
			
			pane1.getChildren().addAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public class EmailValidator {
        
        
        private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        public static boolean isValid(String email) {
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    
    public boolean isTermAccepted() {
        return inputTerm.isSelected();
    }
    
    public void setEmail(String email) {
    	inputEmail.setText(email);
    }
    public void setUsername(String username) {
    	inputUsername.setText(username);
    }
    public void setPassword(String password) {
    	inputPassword.setText(password);
    }
    public void setFullname(String fullname) {
    	inputFullName.setText(fullname);
    }
    public void setTerm(Boolean term) {
    	inputTerm.setSelected(term);
    }

}
