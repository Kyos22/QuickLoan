package application.paymentScheduled;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.entities.Loan;
import application.entities.PaymentScheduledItem;
import application.entities.UserSession;
import application.model.LoanModel;
import application.model.PaymentScheduled;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScheduledBackgroundController implements Initializable {

	 @FXML
	    private Button button_close;

	private Loan loan;
	
    @FXML
    private VBox layout;

    @FXML
    private AnchorPane paneMain;

    
    public void loadData(Loan loan) {
        this.loan = loan;
        System.out.println("Loan Type: " + loan.getLoantype());
        System.out.println("Start Date: " + loan.getStartDate());
        System.out.println("Duration: " + loan.getDuration());
        System.out.println("Monthly Repayment: " + loan.getMonthly_Repayment());
        System.out.println("Total Repayment: " + loan.getTotal_repayment());
        updateSchedule();
    }
    private void updateSchedule() {       
        if (loan == null) {
            System.out.println("chua nhan loan");
            return;
        }

        layout.getChildren().clear(); 
        LocalDate currentDate = LocalDate.now(); 
        LocalDate startDate = loan.getStartDate();
        int duration = loan.getDuration();
        BigDecimal monthlyRepayment = loan.getMonthly_Repayment();
        BigDecimal totalRepayment = loan.getTotal_repayment();

        List<PaymentScheduledItem> payments = calculateSchedule(startDate, duration, monthlyRepayment, totalRepayment);
        for (PaymentScheduledItem payment : payments) {
            if (!payment.getPaymentDate().isBefore(currentDate)) { // Check if the payment date is not in the past
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("rowScheduledBackground.fxml"));
                    Parent row = loader.load();

                    RowScheduledBackgroundController controller = loader.getController();
                    controller.updateData(payment);

                    layout.getChildren().add(row);
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


	@FXML
    void close(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
    }
	

	public static List<PaymentScheduledItem> calculateSchedule(LocalDate startDate, int duration, BigDecimal monthlyRepayment, BigDecimal totalRepayment) {
	    List<PaymentScheduledItem> schedule = new ArrayList<>();
	   
	    LocalDate paymentDate = startDate.plusMonths(1);
	    
	    BigDecimal remainingPrincipal = totalRepayment;

	    for (int i = 0; i < duration; i++) {
	        
	        remainingPrincipal = remainingPrincipal.subtract(monthlyRepayment);

	        
	        if (remainingPrincipal.compareTo(BigDecimal.ZERO) < 0) {
	            remainingPrincipal = BigDecimal.ZERO;
	        }
	        int userId = UserSession.getInstance().getUserId();
	        LoanModel loanModel = new LoanModel();
	        Loan loan = loanModel.findByIdUser(userId);
	        
	        PaymentScheduledItem item = new PaymentScheduledItem(paymentDate, remainingPrincipal, monthlyRepayment, userId, loan.getIdLoanAccount());
	        schedule.add(item);
	        PaymentScheduled.createNewPaymentScheduled(item);
	         
	        paymentDate = paymentDate.plusMonths(1);
	    }
	    return schedule;
	}

        @Override
    	public void initialize(URL arg0, ResourceBundle arg1) {
        	
            updateSchedule();
    	}

	
}
