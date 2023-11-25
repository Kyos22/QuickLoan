package application.login_register;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ForgetPasswordPage4Controller implements Initializable {

	@FXML
	private Button buttonEye;
	
    @FXML
    private Button buttonStart;

    @FXML
    private ImageView imageCongratulation;

    @FXML
    private ImageView imageEye;

    @FXML
    private ImageView imageLock;

    @FXML
    private ImageView imageUser;

    @FXML
    private PasswordField inputPassword;
    
    @FXML
    private TextField visiblePasswordField;

    @FXML
    private TextField inputUsername;
    
    

    @FXML
    private AnchorPane pane1;
    
    private final Image eyeOpen = new Image(getClass().getResourceAsStream("/application/login_register/libs/eye-line.png"));
    private final Image eyeClosed = new Image(getClass().getResourceAsStream("/application/login_register/libs/eye-off-line.png"));


    @FXML
    void start(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	
        	LoginController controller = loader.getController();
        	controller.setUsername(inputUsername.getText());
        	controller.setPassword(inputPassword.getText());
        	
        	
        	pane1.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imageEye.setImage(eyeOpen);
		
		Image imageFire = new Image(getClass().getResourceAsStream("/application/login_register/libs/congratulation.png"));
		imageCongratulation.setImage(imageFire);
		
		Image image1 = new Image(getClass().getResourceAsStream("/application/login_register/libs/user-solid-48.png"));
		imageUser.setImage(image1);
		Image image2 = new Image(getClass().getResourceAsStream("/application/login_register/libs/lock-solid-48.png"));
		imageLock.setImage(image2);
		
		
	}
	
	@FXML
	void togglePasswordVisibility(ActionEvent event) {
    	if(inputPassword.isVisible()) {
    		visiblePasswordField.setText(inputPassword.getText());
    		visiblePasswordField.setVisible(true);
    		inputPassword.setVisible(false);
    		imageEye.setImage(eyeOpen);
    	}else {
    		visiblePasswordField.setText(inputPassword.getText());
    		visiblePasswordField.setVisible(false);
    		inputPassword.setVisible(true);
    		imageEye.setImage(eyeClosed);
    	}
    }

	public void setUsername(String username) {
		inputUsername.setText(username);
	}
	public void setUsernameTextfield(String username) {
		visiblePasswordField.setText(username);
	}
	public void setPassword(String password) {
		inputPassword.setText(password);
	}

}
