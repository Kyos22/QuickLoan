package application.login_register;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class VerifyEmail4Controller implements Initializable {

    @FXML
    private Button buttonsetStarted;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane pane5;

    private String username;
    private String password;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Image image = new Image(getClass().getResourceAsStream("/application/login_register/libs/image2.png"));
        imageView.setImage(image);
		
	}
    
    @FXML
    void gotoLogin(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        	AnchorPane defaulView = loader.load(); 
        	
        	LoginController loginController = loader.getController();
        	loginController.setUsername(username);
        	loginController.setPassword(password);
        	
        	pane5.getChildren().addAll(defaulView);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void setUsername(String username) {
    	this.username = username;
    }
    public void setPassword(String password) {
    	this.password = password;
    }
	

}
