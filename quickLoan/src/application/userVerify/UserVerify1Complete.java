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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerify1Complete implements Initializable {

    @FXML
    private Button button_ccontinue;

    @FXML
    private Button button_close;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private ImageView imageViewComplete;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
   		imageViewClose.setImage(imageClose);
   		Image imageUnderreview = new Image(getClass().getResourceAsStream("/application/userVerify/image/underreview.png"));
   		imageViewComplete.setImage(imageUnderreview);
   		
   		
	}
    
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void continue_gotohome(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard/dashboardPage.fxml"));
             Parent root = loader.load();
             
             
             Scene scene = new Scene(root);
             
             Stage stage = new Stage();
            
             stage.setScene(scene);          
             stage.show();
             stage.setResizable(false);
             ((Stage) button_ccontinue.getScene().getWindow()).close();         
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

	

}
