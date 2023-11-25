package application.userVerify;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class UserVerifyController implements Initializable  {

    @FXML
    private Button button_StartNow;

    @FXML
    private ImageView imageViewVerify;
    
    @FXML
    private ImageView imageViewClose;
    
    @FXML
    private Button button_close;
    
    private Stage previousStage;
    
    @FXML
    void button_startNow(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage1.fxml"));
    		Parent root = loader.load();

    		Scene scene = new Scene(root);
    		scene.setFill(Color.TRANSPARENT); // Thiết lập màu nền của scene thành trong suốt

    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.TRANSPARENT); // Thiết lập style của stage thành trong suốt
    		stage.setScene(scene);
    		
    		stage.initModality(Modality.APPLICATION_MODAL);
    		
    		
    		stage.show();
    		stage.setResizable(false);
    		
    		Stage currentStage = (Stage) button_StartNow.getScene().getWindow();
    		currentStage.close();
    		
    		if (previousStage != null) {
                previousStage.close();
            }

        	
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }
    
    @FXML
    void close(ActionEvent event) {
    	 
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image imageVerify = new Image(getClass().getResourceAsStream("/application/userVerify/image/verify.png"));
		imageViewVerify.setImage(imageVerify);
		Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
		imageViewClose.setImage(imageClose);
		
		
	}

	
	

}
