package application.requestLoanHome;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Random;

import application.entities.Loan;
import application.entities.UserSession;
import application.model.LoanModel;
import application.userVerify.UserVerify1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RequestLoanHome1Controller {

    @FXML
    private Button button_close;

    @FXML
    private Button button_confirm;

    @FXML
    private Label label_term;
    
    @FXML
    private ImageView imageView_close;

    @FXML
    private Label label_amount;

    private BigDecimal amount;
    private int term;
    private BigDecimal monthlyRepayment;
    private BigDecimal totalInterest;
    private BigDecimal totalRepayment;
    
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
    void confirm(ActionEvent event) {
    	 Loan loan = new Loan();
    	 
    	    int userId = UserSession.getInstance().getUserId();
    	    String userDbId = String.valueOf(userId);
            String randomCode = "st" + userDbId + generateRandomDigits(8); 

    	    loan.setCustomer_id(userId);
    	    loan.setLoantype("home");
    	    loan.setAmount(amount);
    	    loan.setDuration(term);
    	    loan.setStartDate(null);
    	    loan.setRequestCreated(LocalDate.now());
    	    loan.setCode(randomCode);
    	    loan.setMonthly_Repayment(monthlyRepayment);
    	    loan.setTotal_Interest_Paid(totalInterest);
    	    loan.setTotal_repayment(totalRepayment);

    	   
    	    
    	    if (LoanModel.create(loan)) {
    	    	try {
    	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/requestLoanHome/requestLoanHomePage2.fxml"));
    	    		Parent root = loader.load();
    	    		
    	    		Scene scene = new Scene(root);
    	    		scene.setFill(Color.TRANSPARENT); 

    	    		Stage stage = new Stage();
    	    		stage.initStyle(StageStyle.TRANSPARENT); 
    	    		stage.setScene(scene);
    	    		
    	    		stage.initModality(Modality.APPLICATION_MODAL);
    	    		
    	    		
    	    		stage.show();
    	    		stage.setResizable(false);
    	    		
    	    		Stage currentStage = (Stage) button_confirm.getScene().getWindow();
    	    		currentStage.close();


    	        	
    	        	
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    	    } else {
    	       
    	        showAlert("Error Creating Loan", AlertType.ERROR);
    	    }
    }
    private String generateRandomDigits(int length) {
        if (length < 1) throw new IllegalArgumentException();

        return new Random().ints('0', '9' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == AlertType.INFORMATION ? "Success" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        label_amount.setText(NumberFormat.getCurrencyInstance().format(amount));
    }

    public void setTerm(int term) {
        this.term = term;
        if(label_term != null) { 
            label_term.setText(String.valueOf(term)+"month");
        }
    }
    public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
    	this.monthlyRepayment = monthlyRepayment;
    }
    public void setTotalInterest(BigDecimal totalInterest) {
    	this.totalInterest = totalInterest;
    }
    public void setTotalRepayment(BigDecimal totalRepayment) {
    	this.totalRepayment = totalRepayment;
    }
    
}
