package application.requestLoanHome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RequestLoanHome2Controller {

    @FXML
    private Button button_close;

    @FXML
    private Button button_gotoHome;

    @FXML
    private ImageView imageView_Underreview;

    @FXML
    private ImageView imageView_close;

    @FXML
    void close(ActionEvent event) {
    	//lúc nhấn close thì nhấn vào mé mé ngoài góc do nó bị css đè lên không nhận action, nhấn mé mé thì được
    	try {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    void goToHome(ActionEvent event) {
    	
    	
    	try {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

}
