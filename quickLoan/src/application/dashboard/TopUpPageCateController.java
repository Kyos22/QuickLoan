package application.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TopUpPageCateController {

    @FXML
    private Button button_agribank;

    @FXML
    private Button button_momo;

    @FXML
    private Button button_paypal;

    @FXML
    private Button button_tpbank;

    @FXML
    private Button button_vcb;

    @FXML
    private ImageView imageViewVCB;

    @FXML
    private ImageView imageView_Agribank;

    @FXML
    private ImageView imageView_momo;

    @FXML
    private ImageView imageView_paypal;

    @FXML
    private ImageView imageView_tpbank;

    @FXML
    private AnchorPane paneChange;

    @FXML
    void agribank(ActionEvent event) {

    }

    @FXML
    void momo(ActionEvent event) {

    }

    @FXML
    void paypal(ActionEvent event) {

    }

    @FXML
    void tpbank(ActionEvent event) {

    }

    @FXML
    void vcb(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpPage1.fxml"));
			AnchorPane defaultView = loader.load();
			
			paneChange.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
