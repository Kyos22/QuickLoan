package application.login_register;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

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

public class ForgetPasswordPage1Controller implements Initializable {

    @FXML
    private Button buttonSend;

    @FXML
    private Button button_back1;

    @FXML
    private ImageView imageArrow;

    @FXML
    private ImageView imageForget1;
    
    @FXML
    private ImageView imageUser;

    @FXML
    private TextField inputEmail;
    
    @FXML
    private AnchorPane pane1;
    
    private String verificationCode;

    @FXML
    void Send(ActionEvent event) {
    	
    	AccountModel accountModel = new AccountModel();
    	if(accountModel.findByEmail(inputEmail.getText()) == null) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("ERROR");
    		alert.setContentText("account does not exist");
    		alert.showAndWait();
    		return;
    		
    	}
    	
    	try {
    		verificationCode = generateVerificationCode();
        	
        	String emailContent = "Your verification code is: " + verificationCode;
        	
        	boolean emailSent = application.login_register.helper.MailHelper.send("nguyenthanhcongvt234@gmail.com", inputEmail.getText(), "Mã xác thức", emailContent);

        	if (!emailSent) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("failed");
        		alert.show();
            }else {
            	
            	Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucess");
                alert.setContentText("OTP code has been sent in your email account");
                alert.show();
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetPasswordPage2.fxml"));
                    AnchorPane verifyView = loader.load();
                    ForgetPasswordPage2Controller controller = loader.getController();
                    controller.setEmail(inputEmail.getText());
                    controller.setVerificationCode(verificationCode);
                           
                    pane1.getChildren().setAll(verifyView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               
                System.out.println(verificationCode);
            }
		} catch (Exception e) {
			e.printStackTrace();
			
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid email");
            alert.show();
		}
    }

    @FXML
    void back(ActionEvent event) {
    	System.out.println("print");
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
			AnchorPane defaultView = loader.load();
			
			pane1.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image = new Image(getClass().getResourceAsStream("/application/login_register/libs/white_arrow.png"));
        imageArrow.setImage(image);
        
        Image imageForget = new Image(getClass().getResourceAsStream("/application/login_register/libs/forgetpass1.png"));
        imageForget1.setImage(imageForget);
        
        Image imageUser1 = new Image(getClass().getResourceAsStream("/application/login_register/libs/user-solid-48.png"));
        imageUser.setImage(imageUser1);
        
        
		
	}
	//hàm random nè mấy bác
		public String generateVerificationCode() {
		    Random random = new Random();
		    return String.format("%04d", random.nextInt(10000)); // tạo mã 4 chữ số
		}
		public void setEmail (String email) {
			inputEmail.setText(email);
		}
    
    

}
