package application.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminBackgroundController implements Initializable {

	@FXML
    private ImageView imageView_refresh;
	
	 @FXML
	    private Button button_refresh;

	
    @FXML
    private Button button_accountPending;

    @FXML
    private Button button_logout;

    
    @FXML
    private Button button_allUser;

    @FXML
    private Button button_requestPending;

    @FXML
    private AnchorPane paneCenter;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		allUser(null);
	}
    
    @FXML
    void accountPending(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("accountPending.fxml"));
        	AnchorPane defaultView = loader.load();
        	paneCenter.getChildren().clear();
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void allUser(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("allUserVerifiedPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	paneCenter.getChildren().clear();
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }

    @FXML
    void requestPending(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("userRequestLoan.fxml"));
        	AnchorPane defaultView = loader.load();
        	paneCenter.getChildren().clear();
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}  	
    }
    @FXML
    void refresh(ActionEvent event) {
    	accountPending(event);
    }
    @FXML
    void logout_admin(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login_register/background.fxml"));
             Parent root = loader.load();
        
             
             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.setTitle("Login/Register");
             stage.show();
             stage.setResizable(false);
             
             
             ((Stage) button_logout.getScene().getWindow()).close();         
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    

}
