package application.login_register;

import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import application.entities.Account;
import application.entities.UserSession;
import application.model.AccountModel;
import application.userVerify.UserVerify1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {


    @FXML
    private Button button_forgetPassword;
	
	@FXML
	private Button buttonEye;

    @FXML
    private ImageView eyeIcon;
    
    @FXML
    private Button button_singin;

    @FXML
    private PasswordField inputPassword;
    
    @FXML
    private TextField visiblePasswordField;

    @FXML
    private ImageView imageView_Logo;

    @FXML
    private TextField inputUsername;

    @FXML
    private AnchorPane pane1;
    
    private String username;
    private String password;
    
    private final Image eyeOpen = new Image(getClass().getResourceAsStream("/application/login_register/libs/eye-line.png"));
    private final Image eyeClosed = new Image(getClass().getResourceAsStream("/application/login_register/libs/eye-off-line.png"));


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		eyeIcon.setImage(eyeOpen);
		
	    
		
	}
	@FXML
    void forgetPassword(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetPasswordPage1.fxml"));
			AnchorPane defaultView = loader.load();
			
			pane1.getChildren().addAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@FXML
	void sginIn(ActionEvent event) {
	    String username = inputUsername.getText();
	    String password = inputPassword.getText();

	    if (inputPassword.getLength() < 8) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setContentText("Mật khẩu phải có ít nhất 8 ký tự!");
	        alert.show();
	        return;
	    }

	    AccountModel accountModel = new AccountModel();
	    Account account = accountModel.findByUsername(username);

	    if (account != null && BCrypt.checkpw(password, account.getPassword())) {
	        // Xác định nếu người dùng là admin
	        if (account.getRole().equals("admin")) {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/admin/adminBackground.fxml"));
	                Parent root = loader.load();

	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.setTitle("Admin");
	                stage.show();
	                stage.setResizable(false);

	                Stage currentStage = (Stage) button_singin.getScene().getWindow();
	                currentStage.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else if (account.getRole().equals("user")) { // Xử lý đăng nhập cho người dùng
	        	UserSession.getInstance(account.getId());
	        	
	        	System.out.println("Is First Login for user " + account.getUsername() + ": " + account.getIsFirstLogin());


	        	
	        	if(account.getIsFirstLogin()!=null && account.getIsFirstLogin() == true) {
	        		
	        		
	        		try {
	        			
	        			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/categoryLoan/categoryLoanPage.fxml"));
	            		Parent root = loader.load();
	            		
	            		 Scene scene = new Scene(root);
	                     scene.setFill(Color.TRANSPARENT); 

	                     Stage stage = new Stage();
	                     stage.initStyle(StageStyle.TRANSPARENT); 
	                     stage.setScene(scene);
	                     
	                     stage.initModality(Modality.APPLICATION_MODAL);
	                     stage.setResizable(false);
	                     stage.show();
	            		
	            		Stage currentStage = (Stage) button_singin.getScene().getWindow();
	            		currentStage.close();
	            		
	            		if (accountModel.updateFirstLogin(account.getId(), false)) {
	            		    System.out.println("Updated isFirstLogin to false successfully.");
	            		} else {
	            		    System.out.println("Failed to update isFirstLogin.");
	            		}
	         		            		
					} catch (Exception e) {
						e.printStackTrace();
					}
	        	}else {      		
	        		try {
	        			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard/dashboardPage.fxml"));
	            		Parent root = loader.load();
	            		          		
	                    Stage stage = new Stage();
	                    stage.setScene(new Scene(root));
	                    stage.setTitle("Dashboard");
	                    stage.show();
	                    stage.setResizable(false);
	                                      
	                    Stage currentStage = (Stage) button_singin.getScene().getWindow();
	                    currentStage.close();
					} catch (Exception e) {
						e.printStackTrace();
					}       		
	        	}
	        	
	        }
	    } else {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setContentText("Tên đăng nhập hoặc mật khẩu không đúng!");
	        alert.show();
	    }
	}

	  
    @FXML
    void togglePasswordVisibility(ActionEvent event) {
    	if(inputPassword.isVisible()) {
    		visiblePasswordField.setText(inputPassword.getText());
    		visiblePasswordField.setVisible(true);
    		inputPassword.setVisible(false);
    		eyeIcon.setImage(eyeOpen);
    	}else {
    		visiblePasswordField.setText(inputPassword.getText());
    		visiblePasswordField.setVisible(false);
    		inputPassword.setVisible(true);
    		eyeIcon.setImage(eyeClosed);
    	}
    }
    public void setUsername(String username) {
    	inputUsername.setText(username);
    }
    public void setPassword(String password) {
    	inputPassword.setText(password);
    }


}
