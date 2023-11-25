package application.paidMoney;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.Loan;
import application.entities.PaymentScheduledItem;
import application.entities.UserSession;
import application.model.AccountModel;
import application.model.LoanModel;
import application.model.PaymentScheduled;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class PaidMoney1Controller implements Initializable {

    @FXML
    private Button button_repayment;

    @FXML
    private AnchorPane paneMain;

    
    @FXML
    private Button button_viewDetails;

    @FXML
    private Label label_RepaymentDate;

    @FXML
    private Label label_Status;

    @FXML
    private Label label_curentBalance1;

    @FXML
    private Label label_currentBalance;

    @FXML
    private Label label_installment;

    @FXML
    private Label label_repaymentAmount;

    @FXML
    private AnchorPane paneCenter;

    private Loan loan;
    
    private LoanModel loanModel;
    private PaymentScheduled paymentScheduled;
    private AccountModel accountModel;

    private final int userId = UserSession.getInstance().getUserId();
    
 
    @FXML
    void repayment(ActionEvent event) {
        int userId = UserSession.getInstance().getUserId();
        System.out.println(userId);
        
        
        loan = loanModel.findByIdUser(userId);
        
        if (loan != null) {
        	AccountModel accountModel = new AccountModel();
            Account userAccount = accountModel.findById(userId);
            PaymentScheduledItem nextPayment = PaymentScheduled.getNextPayment(userId);
            
            if (nextPayment != null) {
//                LocalDate paymentDate = nextPayment.getPaymentDate();
                LocalDate paymentDate = LocalDate.of(2023, 12, 18);
                
                BigDecimal repaymentAmount = nextPayment.getMonthlyRepayment();
                
//                LocalDate today = LocalDate.now(); 
                LocalDate today = LocalDate.of(2023, 12, 18);
                
                PaymentScheduledItem existingPayment = PaymentScheduled.findPayment(userId, loan.getIdLoanAccount(), paymentDate);

                if (existingPayment != null) {
                	Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Notice");
                    alert.setContentText("Your payment was paid already");
                    alert.showAndWait();
                } else {
                	if (paymentDate.isEqual(today) || paymentDate.isBefore(today)) {
                    	
                    	BigDecimal accountBalance = userAccount.getBalance();

                        if (accountBalance.compareTo(repaymentAmount) >= 0) {
                            // Số dư đủ để thanh toán
                            int loanId = nextPayment.getLoanId();
                            boolean updateStatusSuccess = PaymentScheduled.updatePaymentStatus(loanId, paymentDate);

                            if (updateStatusSuccess) {
                                // Trừ tiền từ tài khoản
                                BigDecimal newBalance = accountBalance.subtract(repaymentAmount);
                                boolean updateBalanceSuccess = accountModel.updateAccountBalance(userId, newBalance);

                                if (updateBalanceSuccess) {
                                    // Cập nhật giao diện
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("paidMoneyPage2.fxml"));
                                        AnchorPane defaultView = loader.load();
                                        paneMain.getChildren().setAll(defaultView);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println("Failed to update account balance.");
                                }
                            } else {
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setHeaderText("Failed");
                                alert.setContentText("the repayment is not time to paid");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setHeaderText("Insufficient Balance");
                            alert.setContentText("Your account balance is not sufficient to make the payment.");
                            alert.showAndWait();
                        }

                    } else {
                        
                        showPaymentDateNotDueMessage(paymentDate);
                    }
                }
            } else {
               
                System.out.println("No next payment found.");
            }
        } else {
            
            System.out.println("No loan found for user.");
        }
        System.out.println("End of repayment method.");
    }


    private void showPaymentDateNotDueMessage(LocalDate paymentDate) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Payment Information");
        alert.setHeaderText("Payment Date Not Due Yet");
        alert.setContentText("You cannot make a repayment before the payment date (" + paymentDate.toString() + ").");
        alert.showAndWait();
    }


    @FXML
    void viewDetails(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dashboard/dashboardPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loanModel = new LoanModel();
        paymentScheduled = new PaymentScheduled();
        accountModel = new AccountModel();

        Account account = accountModel.findById(userId);
        Loan userLoan = loanModel.findByIdUser(userId);
        PaymentScheduledItem nextPayment = paymentScheduled.getNextPayment(userId);
        

        if (userLoan != null && nextPayment != null && account != null) {

            BigDecimal currentBalance = account.getBalance();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate repaymentDate = nextPayment.getPaymentDate();
            label_RepaymentDate.setText(repaymentDate.format(formatter));

            
            label_currentBalance.setText(currencyFormat.format(currentBalance));
            label_curentBalance1.setText(currencyFormat.format(currentBalance));

            int activeLoanCount = PaymentScheduled.countActiveLoansByLoanId(userLoan.getIdLoanAccount());
            
            int totalLoanCount = userLoan.getDuration();

            
            label_installment.setText(String.format("%02d/%02d", activeLoanCount, totalLoanCount));
            
//            LocalDate today = LocalDate.now();
            LocalDate today = LocalDate.of(2023, 12, 18);
            if (repaymentDate.isAfter(today)) {
                label_Status.setText("CLOSED");
                label_Status.setStyle("-fx-text-fill:grey");
            } else if (repaymentDate.isEqual(today) || repaymentDate.isBefore(today)) {
            	label_Status.setText("OPEN");
            	label_Status.setStyle("-fx-text-fill:green");
            }
            label_repaymentAmount.setText(currencyFormat.format(userLoan.getMonthly_Repayment()));
        }
    }

}
