package application.requestLoanHome;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import application.userVerify.UserVerify1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class RequestLoanHomeController implements Initializable {

    @FXML
    private Button button_Back;

    @FXML
    private Button button_Getstarted;

    @FXML
    private ImageView imageView_House;

    @FXML
    private TextField input_amount;

    @FXML
    private Label label_TotalInterestPaid;

    @FXML
    private Label label_amount;

    @FXML
    private Label label_monthlyRepayment;

    @FXML
    private Label label_term;

    @FXML
    private Label label_term2;

    @FXML
    private Label label_totalRepayment;

    @FXML
    private AnchorPane paneCenter;

    @FXML
    private Slider sliderTerm;

    private int myTermNumber;
    
    private double principal;
    @FXML
    void back(ActionEvent event) {
    	
	
    	String amountText = input_amount.getText();
    	NumberFormat format = NumberFormat.getInstance();
    	try {
    	    principal = format.parse(amountText).doubleValue();
    	    // Tiếp tục tính toán với principal
    	} catch (ParseException e) {
    	    showAlert("Số không hợp lệ");
    	}


    double rate = 0.1;  
    int term = myTermNumber;
   
    double monthlyPrincipal = principal / term;
    double monthlyInterest = principal * rate;
    double emi = monthlyPrincipal + monthlyInterest;
    
    
 
    double monthlyInterest1 = principal * rate;
    double totalInterest1 = monthlyInterest1 * term;
    
   
    double totalRepayment = totalInterest1 + principal;
    

    label_monthlyRepayment.setText("$"+String.format("%.2f", emi));
    label_TotalInterestPaid.setText("$"+String.format("%.2f", totalInterest1));
    label_totalRepayment.setText("$"+String.format("%.2f", totalRepayment));
    }

    @FXML
    void getStarted(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/requestLoanHome/requestLoanHomePage1.fxml"));
    		Parent root = loader.load();
    		
    		// Get the Controller from loader
            RequestLoanHome1Controller controller = loader.getController();

            String amountText = label_amount.getText();
            amountText = amountText.replaceAll("[^\\d.]", ""); // This regex removes everything but digits and the decimal point
            controller.setAmount(new BigDecimal(amountText));
            controller.setTerm(myTermNumber);
            // Assume you have created setter methods for the other values in the RequestLoanHome1Controller
            controller.setMonthlyRepayment(new BigDecimal(label_monthlyRepayment.getText().replaceAll("[^\\d.]", "")));
            controller.setTotalInterest(new BigDecimal(label_TotalInterestPaid.getText().replaceAll("[^\\d.]", "")));
            controller.setTotalRepayment(new BigDecimal(label_totalRepayment.getText().replaceAll("[^\\d.]", "")));

    		
    		Scene scene = new Scene(root);
    		scene.setFill(Color.TRANSPARENT); 

    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.TRANSPARENT); 
    		stage.setScene(scene);
    		
    		stage.initModality(Modality.APPLICATION_MODAL);
    		
    		
    		stage.show();
    		stage.setResizable(false);
    		
    		

        	
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		input_amount.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.isEmpty()) {
		       
		        String plainNumber = newValue.replaceAll("[^\\d]", "");
		        if (!plainNumber.isEmpty()) {		          
		            try {
//		                int value = Integer.parseInt(plainNumber);
		            	double value = Double.parseDouble(plainNumber);
		                if (value > 500000) {
		                    showAlert("Invalid Input");
		                    input_amount.setText(oldValue);
		                } else {
		                    
		                    if (value > 9999) {
		                       
		                        input_amount.setText(formatNumber1(value));
		                        label_amount.setText("$"+formatNumber1(value));
		                    } else {
		                        
		                        input_amount.setText(plainNumber);
		                        label_amount.setText("$"+plainNumber);
		                    }
		                    
		                }
		            } catch (NumberFormatException e) {
		                showAlert("Invalid Input");
		                input_amount.setText(oldValue);
		            }
		        }
		    }
		});

		  sliderTerm.valueProperty().addListener((observable, oldValue, newValue) -> {
		        myTermNumber = newValue.intValue();
		        label_term.setText(formatNumber(myTermNumber));
		        label_term2.setText(formatNumber(myTermNumber));
		    });
	}
	private String formatNumber(int number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }
	private String formatNumber1(double number) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		return numberFormat.format(number);
	}
	private void showAlert(String message) {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("Cảnh báo");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}
