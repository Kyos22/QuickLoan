package application.dashboard;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class TopUpPage1Controller implements Initializable {

    @FXML
    private Button button_back1;

    
    @FXML
    private Button button_next;

   
    @FXML
    private ImageView imageViewBack;

   
    @FXML
    private AnchorPane paneVCB;

    
    @FXML
    private ImageView imageViewNext;

    @FXML
    private ImageView imageViewVCB;

   
    @FXML
    private AnchorPane paneChange;

    @FXML
    void next(ActionEvent event) {
    	
    }
   
    
    @FXML
    void back1(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpPageCate.fxml"));
			AnchorPane defaultView = loader.load();
			
			paneChange.getChildren().setAll(defaultView);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

  
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
      showpaneVCB();
    }
    
    public void showpaneVCB() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpVCB.fxml"));
			AnchorPane defaultView = loader.load();
			
			paneVCB.getChildren().setAll(defaultView);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

  

	    

}
