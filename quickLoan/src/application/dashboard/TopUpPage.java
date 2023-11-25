package application.dashboard;

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

public class TopUpPage implements Initializable {

    
    
    @FXML
    private AnchorPane paneCenter;

    @FXML
    private AnchorPane paneChange;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showCatePayment();
		
	}
	public void showCatePayment() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpPageCate.fxml"));
			AnchorPane defaultView = loader.load();
			
			paneChange.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   

}
