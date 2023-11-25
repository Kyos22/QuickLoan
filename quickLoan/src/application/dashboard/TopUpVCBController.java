package application.dashboard;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TopUpVCBController implements Initializable {

	  @FXML
	    private Button button_con;

    @FXML
    private Button button_return;

    @FXML
    private ImageView imageView_logoVCB;

    @FXML
    private TextField input_Amount;

    @FXML
    private Label myLabel;

    @FXML
    private Slider mySlider;

    @FXML
    private AnchorPane paneVCB;

    private int myAmount;
    private boolean isUpdatingFromSlider = false;
    
//    @FXML
//    void continue_paymentt(ActionEvent event) {
//    	System.out.println("da nhan");
//    	try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpVCB1.fxml"));
//			AnchorPane defaultView = loader.load();
//			
//			TopUpVCB1Controller controller = loader.getController();
//			controller.setAmount(String.format("%.2f", (float)myAmount)); 
//			
//			paneVCB.getChildren().setAll(defaultView);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
    @FXML
    void pay(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpVCB1.fxml"));
			AnchorPane defaultView = loader.load();
			
			TopUpVCB1Controller controller = loader.getController();
			controller.setAmount(String.format("%.2f", (float)myAmount));
			
			paneVCB.getChildren().clear();
			paneVCB.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void return1(ActionEvent event) {
    	System.out.println("nhan return");
    	myLabel.setText("");
    	input_Amount.setText("");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        input_Amount.setOnMouseClicked(e -> {
            input_Amount.selectAll();
        });

        input_Amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdatingFromSlider) {
                String cleanedValue = newValue.replaceAll("[^0-9]", "");
                if (!cleanedValue.isEmpty()) {
                    try {
                        int inputAmount = Integer.parseInt(cleanedValue);
                        if (inputAmount > mySlider.getMax()) {
                            // Show an alert if the input amount is greater than the slider's max value
                            showAlert("Invalid Amount", "The amount cannot be greater than " + formatter.format(mySlider.getMax()));
                            input_Amount.setText(oldValue); // Revert to the old value
                            return;
                        }
                        

                        myAmount = inputAmount;
                        myLabel.setText("$" + formatter.format(myAmount));

                        // Prevent recursion
                        isUpdatingFromSlider = true;
                        mySlider.setValue(myAmount);
                        isUpdatingFromSlider = false;

                        // Format and set the textfield value
                        input_Amount.setText(formatter.format(myAmount));
                    } catch (NumberFormatException e) {
                        input_Amount.setText(oldValue);
                    }
                } else {
                    myAmount = 0;
                    myLabel.setText("$0");
                    isUpdatingFromSlider = true;
                    mySlider.setValue(0);
                    isUpdatingFromSlider = false;
                }
            }
        });

        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdatingFromSlider) {
                myAmount = newValue.intValue();
                myLabel.setText("$" + formatter.format(myAmount));
                input_Amount.setText(formatter.format(myAmount));
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


	  private String formatNumber(int number) {
	        NumberFormat numberFormat = NumberFormat.getInstance();
	        return numberFormat.format(number);
	    }

}
