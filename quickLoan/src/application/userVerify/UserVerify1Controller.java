package application.userVerify;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerify1Controller implements Initializable {

    @FXML
    private TextField Input_SelectCountry;

    @FXML
    private Button button_continue;
    
    @FXML
    private Button button_close;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private ImageView imageViewDoc;

    @FXML
    private ImageView imageViewIdentity;

    @FXML
    private ImageView imageViewPlay;

    @FXML
    private ImageView imageViewUser;
    
    private String country;
    
    @FXML
    void continue1(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("userVerifyPage2.fxml"));
    		Parent root = loader.load();
    		
    		UserVerify2Controller controller = loader.getController();
    		controller.setCountry(country);
    		
    		Scene scene = new Scene(root);
    		scene.setFill(Color.TRANSPARENT); 

    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.TRANSPARENT); 
    		stage.setScene(scene);
    		
    		stage.initModality(Modality.APPLICATION_MODAL);
    		
    		
    		stage.show();
    		stage.setResizable(false);
    		
    		Stage currentStage = (Stage) button_continue.getScene().getWindow();
    		currentStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Input_SelectCountry.setOnMouseClicked(this::handleCountrySelection); 
		
		Image imageIdentity = new Image(getClass().getResourceAsStream("/application/userVerify/image/id-card-solid-24.png"));
		imageViewIdentity.setImage(imageIdentity);
		
		Image imageDoc = new Image(getClass().getResourceAsStream("/application/userVerify/image/file-doc-solid-24.png"));
		imageViewDoc.setImage(imageDoc);
		
		Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
		imageViewClose.setImage(imageClose);
		
		Image imageUser = new Image(getClass().getResourceAsStream("/application/userVerify/image/user-solid-24.png"));
		imageViewUser.setImage(imageUser);
		
		Image imagePlay = new Image(getClass().getResourceAsStream("/application/userVerify/image/play-circle-regular-24.png"));
		imageViewPlay.setImage(imagePlay);
		
		
		
	}
    
    @FXML
    void handleCountrySelection(MouseEvent event) {
    	System.out.println("được nhấn");
        if (event.getClickCount() == 1) { 

        	
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage1_1.fxml"));
        		Parent root = loader.load();
        		
        		UserVerify1_1Controller userVerify1_1Controller = loader.getController();
                userVerify1_1Controller.setUserVerify1Controller(this);
                
        		Scene scene = new Scene(root);
        		scene.setFill(Color.TRANSPARENT);
        		
        		Stage stage = new Stage();
        		stage.initStyle(StageStyle.TRANSPARENT); 
        		stage.setScene(scene);
        		
        		stage.initModality(Modality.APPLICATION_MODAL);
        		
        		stage.setTitle("User Verify");
        		stage.show();
        		stage.setResizable(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    @FXML
    void close(ActionEvent event) {
    	 
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
    }
    public void setSelectedCountry(String countryName) {
    	this.country = countryName;
    	Input_SelectCountry.setText(countryName);
    }
    

 
	
}
