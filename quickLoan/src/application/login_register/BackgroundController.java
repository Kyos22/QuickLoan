package application.login_register;

import java.io.IOException;
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
import javafx.scene.layout.Pane;

public class BackgroundController implements Initializable {

	

    @FXML
    private ImageView imageView_Logo;

    @FXML
    private Button button_SignIn;

    @FXML
    private Button button_SignUp;

    @FXML
    private AnchorPane centerPaneSignup_in;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;
    
    @Override
  	public void initialize(URL arg0, ResourceBundle arg1) {
    	showSignInPage();
  		setSlider();
  		Image imageLogo = new Image(getClass().getResourceAsStream("/application/login_register/libs/logoQuickLoan.png"));
		imageView_Logo.setImage(imageLogo);
  		
  	}

    public void setSlider() {
    	try {
  	        FXMLLoader loader = new FXMLLoader(getClass().getResource("sliderPage.fxml"));
  	        Pane sliderPane = loader.load();
  	        pane1.getChildren().add(sliderPane);
  	    } catch (IOException e) {
  	        e.printStackTrace();
  	    }
    }
    
    public void showSignInPage() {
        
        try {
            AnchorPane defaultView =  FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            centerPaneSignup_in.getChildren().clear();

            centerPaneSignup_in.getChildren().addAll(defaultView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void buttonSignIn(ActionEvent event) {
   
        button_SignIn.setStyle("-fx-background-color: #64D7C4; -fx-text-fill:#fff;");
        button_SignUp.setStyle("-fx-background-color: #4C5C72; -fx-text-fill:#c1c1c1;");
       
        centerPaneSignup_in.getChildren().clear();

        showSignInPage();
    }


  
    
    @FXML
    void buttonSignUp(ActionEvent event) {
    	button_SignUp.setStyle("-fx-background-color: #64D7C4; -fx-text-fill:#fff;");
    	button_SignIn.setStyle("-fx-background-color: #4C5C72; -fx-text-fill:#c1c1c1;");
    	centerPaneSignup_in.getChildren().clear();
 
    	try {
    		AnchorPane defaultView =  FXMLLoader.load(getClass().getResource("registerPage.fxml"));
        	centerPaneSignup_in.getChildren().addAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }

	

}
