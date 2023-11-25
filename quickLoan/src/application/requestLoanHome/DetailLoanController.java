package application.requestLoanHome;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import application.entities.Loan;
import application.entities.PaymentScheduledItem;
import application.model.PaymentScheduled;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DetailLoanController{

    @FXML
    private Button button_close;

    @FXML
    private Label label_allAmountPaid;

    @FXML
    private Label label_duration;

    @FXML
    private Label label_interestRate;

    @FXML
    private Label label_loanAmount;
    
    @FXML
    private Label label_monthly_repayment;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Label label_nextRepayment;
    
    @FXML
    private Label label_nextRepayment1;
    
    @FXML
    private Label label_startDate;
    
    @FXML
    private Label label_term;

    private Loan loan;
    private String type;
    
    private PaymentScheduledItem paymentScheduledItem;
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    public void setLoanData(Loan loan) {
        this.loan = loan;
        
        int customer_id = loan.getCustomer_id();
        //-------------------------------------------------------------------------------------
        BigDecimal currentBalance = loan.getAmount();
        BigDecimal moneyRepayment = loan.getMonthly_Repayment();
       
        if (currentBalance != null && moneyRepayment != null) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            label_loanAmount.setText(currencyFormat.format(currentBalance));
            label_monthly_repayment.setText(currencyFormat.format(moneyRepayment));
        } else {
            
            label_loanAmount.setText("N/A");
            label_monthly_repayment.setText("N/A");
        }
        //-------------------------------------------------------------------------------------

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d'th',yyyy", Locale.ENGLISH); 
        LocalDate startDate = loan.getStartDate();
        label_startDate.setText(startDate.format(formatter));
        //-------------------------------------------------------------------------------------

        setType(loan.getLoantype());
        //-------------------------------------------------------------------------------------

        PaymentScheduled paymentScheduled = new PaymentScheduled();
         paymentScheduledItem = paymentScheduled.getNextPayment(customer_id);
       
        int activeLoanCount = PaymentScheduled.countActiveLoansByLoanId(loan.getIdLoanAccount());
        
        int totalLoanCount = loan.getDuration();

        
        label_duration.setText(String.format("%02d/%02d", activeLoanCount, totalLoanCount));
        
        LocalDate paymentDate = paymentScheduledItem.getPaymentDate();
        label_nextRepayment.setText(paymentDate.format(formatter));
        label_nextRepayment1.setText(paymentDate.format(dateFormatter));
        //-------------------------------------------------------------------------------------

        label_term.setText(String.valueOf(loan.getDuration()) + " month");
        //-------------------------------------------------------------------------------------

        
        BigDecimal totalLoanAmount = loan.getTotal_repayment();
  
        int paymentPaid = paymentScheduled.countActiveLoansByLoanId(loan.getIdLoanAccount());
        
        BigDecimal amountPaidSoFar = moneyRepayment.multiply(new BigDecimal(paymentPaid));

        
        BigDecimal percentagePaid = amountPaidSoFar.multiply(new BigDecimal("100")).divide(totalLoanAmount, 2, RoundingMode.HALF_UP);

        
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2); 
        String formattedPercentage = percentFormat.format(percentagePaid.doubleValue() / 100.0);

       
        label_allAmountPaid.setText(formattedPercentage);
        double progressValue = percentagePaid.doubleValue() / 100.0;
        progressBar.setProgress(progressValue);
        
    //------------------------------------------------------------------------------------------

    }
    public void setType(String type) {
        this.type = type;
        
        if ("home".equals(type)) {
        	label_interestRate.setText("10%");
        } else if ("vehicle".equals(type)) {
        	label_interestRate.setText("13%");
        }
    }

}
