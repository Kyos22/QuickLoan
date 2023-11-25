package application.dashboard;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import application.entities.UserSession;
import application.model.LoanModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class CalculatorLoanController implements Initializable {

    @FXML
    private Button buttonEducation, buttonHouse, buttonPersonal, buttonVehicle, button_Calculator;

   

    @FXML
    private Label interestRate, myLabel2;

    @FXML
    private TextField myLabel;
    
    @FXML
    private Slider sliderLoan, sliderTerm;
    
    @FXML
    private Label labelMonthlyRepayment;

    @FXML
    private Label labelTimeToComplete;

    @FXML
    private Label labelTotalInterest;

    @FXML
    private Label labelTotalRepayment;
    
    @FXML
    private AnchorPane pane_calculator;
    
    @FXML
    private AnchorPane paneCenter;

    @FXML
    private ImageView imageViewCalculator;

    @FXML
    private ImageView imageViewFlag1;

    @FXML
    private ImageView imageViewFlag2;

    @FXML
    private ImageView imageViewFlag3;

    @FXML
    private ImageView imageViewFlag4;


    @FXML
    private ImageView imageView_check;

    @FXML
    private ImageView imageView_dollars;

    @FXML
    private Label label_allLoanAmount;

    @FXML
    private Label label_totalAllLoan;

    @FXML
    private Label label_totalMonthlyRepayment;

    
    private int myLoanNumber;
    private int myTermNumber;
    
    private String selectedCalculator;

    private boolean isUpdatingFromSlider = false;
    
    @FXML
    private PieChart loanPieChart;



    @FXML
    void education(ActionEvent event) {
    	selectedCalculator = "5";
    	interestRate.setText(selectedCalculator);
    }

    @FXML
    void house(ActionEvent event) {
    	selectedCalculator = "10";
    	interestRate.setText(selectedCalculator);
    }

    @FXML
    void personal(ActionEvent event) {
    	selectedCalculator = "18";
    	interestRate.setText(selectedCalculator);
    }

    @FXML
    void vehicle(ActionEvent event) {
    	selectedCalculator = "13";
    	interestRate.setText(selectedCalculator);
    }

    private DecimalFormat formatter = new DecimalFormat("#,###");
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	updateDefaultPieChart();
    	
    	int userId = UserSession.getInstance().getUserId();
		
    	BigDecimal totalActiveLoanAmount = LoanModel.getTotalActiveLoanAmount(userId);
        BigDecimal totalActiveMonthlyRepayment = LoanModel.getTotalActiveMonthlyRepayment(userId);
        int totalActiveLoanCount = LoanModel.getCountActiveLoansForUser(userId);

        if (totalActiveLoanAmount != null && totalActiveLoanAmount.compareTo(BigDecimal.ZERO) > 0) {
            label_allLoanAmount.setText(formatCurrency(totalActiveLoanAmount));
        } else {
            label_allLoanAmount.setText("$0");
        }

        if (totalActiveMonthlyRepayment != null && totalActiveMonthlyRepayment.compareTo(BigDecimal.ZERO) > 0) {
            label_totalMonthlyRepayment.setText(formatCurrency(totalActiveMonthlyRepayment));
        } else {
            label_totalMonthlyRepayment.setText("$0");
        }

        label_totalAllLoan.setText(String.valueOf(totalActiveLoanCount));
    	
		
    	Image imageCalculator = new Image(getClass().getResourceAsStream("/application/dashboard/images/calculator-regular-48.png"));
	    imageViewCalculator.setImage(imageCalculator);
	    
	    Image imageFlag1 = new Image(getClass().getResourceAsStream("/application/dashboard/images/flag.png"));
	    imageViewFlag1.setImage(imageFlag1);
	    
	    Image imageFlag2 = new Image(getClass().getResourceAsStream("/application/dashboard/images/flag.png"));
	    imageViewFlag2.setImage(imageFlag2);
	    
	    Image imageFlag3 = new Image(getClass().getResourceAsStream("/application/dashboard/images/flag.png"));
	    imageViewFlag3.setImage(imageFlag3);
	    
	    Image imageFlag4 = new Image(getClass().getResourceAsStream("/application/dashboard/images/flag.png"));
	    imageViewFlag4.setImage(imageFlag4);
	    
	    Image imageDollar = new Image(getClass().getResourceAsStream("/application/dashboard/images/dollar-circle-solid-48.png"));
	    imageView_dollars.setImage(imageDollar);
	    
	    Image imageCheck = new Image(getClass().getResourceAsStream("/application/dashboard/images/check-circle-solid-48.png"));
	    imageView_check.setImage(imageCheck);
	    
	    
    	
    	myLabel.setOnMouseClicked(e -> {
    	    myLabel.selectAll();
    	});
    	myLabel.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	    @Override
    	    public void handle(KeyEvent event) {
    	        if (event.getCode() == KeyCode.BACK_SPACE) {
//    	            if (myLabel.getText().isEmpty()) {
//    	                myLabel.selectAll();
//    	            }
    	            // Nếu không, cho phép phím Backspace xóa từng ký tự như bình thường
    	        }
    	    }
    	});


    	

    	
    	myLabel.textProperty().addListener(new ChangeListener<String>() {
    		@Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	        if (!isUpdatingFromSlider) {
    	            if (newValue.trim().isEmpty()) {  
    	                myLoanNumber = 0; 
    	                sliderLoan.setValue(myLoanNumber);  
    	                return;  
    	            }

    	            try {
    	                String cleanedValue = newValue.replaceAll("[^0-9]", ""); 
    	                long longValue = Long.parseLong(cleanedValue);
    	                myLoanNumber = (int) longValue;

    	                String formattedValue;
    	                if (longValue < 10000) {
    	                    formattedValue = String.valueOf(longValue);
    	                } else {
    	                    formattedValue = formatter.format(longValue);
    	                }

    	                isUpdatingFromSlider = true;
    	                myLabel.setText(formattedValue);
    	                isUpdatingFromSlider = false;

    	                sliderLoan.setValue(myLoanNumber);
    	            } catch (NumberFormatException e) {
    	                myLabel.setText(oldValue);
    	            }
    	        }
    	    }

			
        });
    	
    	sliderLoan.valueProperty().addListener((observable, oldValue, newValue) -> {
    	    isUpdatingFromSlider = true;
    	    myLoanNumber = newValue.intValue();
    	    myLabel.setText(formatNumber(myLoanNumber));
    	    isUpdatingFromSlider = false;
    	});


        sliderTerm.valueProperty().addListener((observable, oldValue, newValue) -> {
            myTermNumber = newValue.intValue();
            myLabel2.setText(formatNumber(myTermNumber));
        });
      
    }
    
    private String formatCurrency(BigDecimal value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(value);
    }
    

    private String formatNumber(int number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }
    
     

    @FXML
    void calculator(ActionEvent event) {
    	 if (selectedCalculator == null || selectedCalculator.isEmpty()) {
    	        showErrorAlert("Select Type of Loan");
    	        return;
    	    }

    	    if (myLoanNumber <= 0) {
    	        showErrorAlert("Select Loan Amount");
    	        return;
    	    }

    	    if (myTermNumber <= 0) {
    	        showErrorAlert("Select Loan Term");
    	        return;
    	    }
    	
        double principal = myLoanNumber;  
        double rate = Double.parseDouble(selectedCalculator) / 100;  
        int term = myTermNumber;
       
        double monthlyPrincipal = principal / term;
        double monthlyInterest = principal * rate;
        double emi = monthlyPrincipal + monthlyInterest;
        
        
     
        double monthlyInterest1 = principal * rate;
        double totalInterest1 = monthlyInterest1 * term;
        
       
        double totalRepayment = totalInterest1 + principal;
        

        labelMonthlyRepayment.setText(String.format("%.2f", emi));
        labelTotalInterest.setText(String.format("%.2f", totalInterest1));
        labelTotalRepayment.setText(String.format("%.2f", totalRepayment));
        labelTimeToComplete.setText(String.format("%d", myTermNumber));
        
     
        updateDefaultPieChart(principal, totalInterest1, totalRepayment);
        
        
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateDefaultPieChart() {
        PieChart.Data slice1 = new PieChart.Data("Principal", 1);
        PieChart.Data slice2 = new PieChart.Data("Total Interest", 1);
        PieChart.Data slice3 = new PieChart.Data("Total Repayment", 1);
        
        loanPieChart.getData().clear();
        loanPieChart.getData().addAll(slice1, slice2, slice3);
        applyPieChartColors();
    }

   
    private void updateDefaultPieChart(double principal, double totalInterest, double totalRepayment) {
        PieChart.Data slice1 = new PieChart.Data("Principal", principal);
        PieChart.Data slice2 = new PieChart.Data("Total Interest", totalInterest);
        PieChart.Data slice3 = new PieChart.Data("Total Repayment", totalRepayment);
        
        loanPieChart.getData().clear();
        loanPieChart.getData().addAll(slice1, slice2, slice3);
        applyPieChartColors();
    }

    private void applyPieChartColors() {
        String[] pieColors = new String[]{"#9143BB", "#49A6C1", "#4559BE"};
        for (int i = 0; i < loanPieChart.getData().size(); i++) {
            loanPieChart.getData().get(i).getNode().setStyle("-fx-pie-color: " + pieColors[i]);
        }
    }





}
