package application.dashboard;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.admin.RowAllUserVerifiedController;
import application.entities.Account;
import application.entities.PaymentScheduledItem;
import application.entities.Account.AccountStatus;
import application.entities.UserSession;
import application.model.AccountModel;
import application.model.ConnectDB;
import application.model.LoanModel;
import application.model.PaymentScheduled;
import application.paidMoney.RowPaidMoneyTransactionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable  {

	
	@FXML
    private Label label_totalAllLoan;

    @FXML
    private Label label_totalMonthlyRepayment;
    
    @FXML
    private Label label_allLoanAmount;
	
    @FXML
    private Button button_ApplyNewLoan;

    @FXML
    private Button button_education;

    @FXML
    private Button button_home;

    @FXML
    private Button button_payment;

    @FXML
    private Button button_topUp;

    @FXML
    private Button button_logout;

    
    @FXML
    private Button button_vehicle;

    @FXML
    private Button button_withdraw;

    @FXML
    private Button cate1;

    @FXML
    private Button button_HistoryTopUp;

    @FXML
    private Button button_logout1;

    
    @FXML
    private ImageView imageView_check;

    @FXML
    private ImageView imageView_dollars;


    @FXML
    private VBox layout_transaction;

    
    @FXML
    private ImageView imageView_education;

    @FXML
    private ImageView imageView_home;

    @FXML
    private ImageView imageView_payment;

    @FXML
    private ImageView imageView_personal;

    @FXML
    private ImageView ImageView_Arrow;
    
    @FXML
    private ImageView imageView_ringbell;

    @FXML
    private ImageView imageView_setting;

    @FXML
    private ImageView imageView_sim1;

    @FXML
    private ImageView imageView_sim2;

    @FXML
    private ImageView imageView_sim3;

    @FXML
    private ImageView imageView_topUp;

    @FXML
    private ImageView imageView_User;


    @FXML
    private AnchorPane paneCenter;

    
    @FXML
    private ImageView imageView_vehicle;

    @FXML
    private ImageView imageView_withdraw;

    @FXML
    private Button button_refresh;

    @FXML
    private Button button_loanDetail;
 
    @FXML
    private Label label_fullname;

    @FXML
    private Label label_currentBalance;
    
    @FXML
    private Label label_nextPayment;

    @FXML
    private Button button_dashboard;
 
    @FXML
    private Label label_scheduled;
    
    @FXML
    private AnchorPane paneMainCalculator;

    @FXML
    private Button button_showStatus;

    
    @FXML
    private ImageView imageView_refresh;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image imageArrowright = new Image(getClass().getResourceAsStream("/application/dashboard/images/right-arrow-alt-regular-48.png"));
	    ImageView_Arrow.setImage(imageArrowright);
	    Image imageSetting = new Image(getClass().getResourceAsStream("/application/dashboard/images/cog-regular-48.png"));
	    imageView_setting.setImage(imageSetting);
	    
	    Image imageUser = new Image(getClass().getResourceAsStream("/application/dashboard/images/user-solid-black.png"));
	    imageView_User.setImage(imageUser);	    
	    Image imageRingbell = new Image(getClass().getResourceAsStream("/application/dashboard/images/log-out-regular-108.png"));
	    imageView_ringbell.setImage(imageRingbell);
	    Image imageDollar = new Image(getClass().getResourceAsStream("/application/dashboard/images/dollar-circle-solid-48.png"));
	    imageView_dollars.setImage(imageDollar);
	    Image imageCheck = new Image(getClass().getResourceAsStream("/application/dashboard/images/check-circle-solid-48.png"));
	    imageView_check.setImage(imageCheck);
	    Image imagePayment = new Image(getClass().getResourceAsStream("/application/dashboard/images/payment.png"));
	    imageView_payment.setImage(imagePayment);
	    Image imageTopup = new Image(getClass().getResourceAsStream("/application/dashboard/images/top_up.png"));
	    imageView_topUp.setImage(imageTopup);
	    Image imageWithdraw = new Image(getClass().getResourceAsStream("/application/dashboard/images/withdraw.png"));
	    imageView_withdraw.setImage(imageWithdraw);
	    
	    Image imageHome = new Image(getClass().getResourceAsStream("/application/dashboard/images/home.png"));
	    imageView_home.setImage(imageHome);
	    Image imagePersonal = new Image(getClass().getResourceAsStream("/application/dashboard/images/personal.png"));
	    imageView_personal.setImage(imagePersonal);
	    Image imageEducation = new Image(getClass().getResourceAsStream("/application/dashboard/images/education.png"));
	    imageView_education.setImage(imageEducation);
	    Image imageVehicle = new Image(getClass().getResourceAsStream("/application/dashboard/images/vehicle.png"));
	    imageView_vehicle.setImage(imageVehicle);
	    
	    Image imageSim1 = new Image(getClass().getResourceAsStream("/application/dashboard/images/simwhite.png"));
	    imageView_sim1.setImage(imageSim1);
	    Image imageSim2 = new Image(getClass().getResourceAsStream("/application/dashboard/images/simpurple.png"));
	    imageView_sim2.setImage(imageSim2);
	    Image imageSim3 = new Image(getClass().getResourceAsStream("/application/dashboard/images/simblack.png"));
	    imageView_sim3.setImage(imageSim3);
	    Image imageRefresh = new Image(getClass().getResourceAsStream("/application/dashboard/images/refresh-regular-108.png"));
	    imageView_refresh.setImage(imageRefresh);
	    
	    showCalculatorPane();
		showFullname();
		showCurrentBalance();
		showNextPaymentDetails();
		button_refresh.setOnAction(this::handleRefreshButtonAction);
		loadTransactionPayment();
	
	}
    
public void showCalculatorPane() {
        
        try {
            AnchorPane defaultView =  FXMLLoader.load(getClass().getResource("calculatorLoanPage.fxml"));
            paneCenter.getChildren().clear();

            paneCenter.getChildren().addAll(defaultView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
@FXML
void dashboard(ActionEvent event) {
	 try {
         AnchorPane defaultView =  FXMLLoader.load(getClass().getResource("calculatorLoanPage.fxml"));
         paneCenter.getChildren().clear();

         paneCenter.getChildren().addAll(defaultView);
     } catch (Exception e) {
         e.printStackTrace();
     }
}

    @FXML
    void applyNewLoans(ActionEvent event) {

    }

    @FXML
    void education(ActionEvent event) {

    }

    @FXML
    void home(ActionEvent event) {
    	if (!isAccountActive()) {
            showAlert("Access Denied", "This action is not available because your account status is not active.");
            return;
        }
    	int userId = UserSession.getInstance().getUserId();
        LoanModel loanModel = new LoanModel();
        if (loanModel.hasActiveLoanOfType(userId, "home")) {
            showAlert("Loan application denied", "You already have an active 'home' loan account.");
            return;
        }
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/requestLoanHome/requestLoanHomePage.fxml"));
    		AnchorPane defaultView = loader.load();
    		
    		paneCenter.getChildren().clear();
    		paneCenter.getChildren().addAll(defaultView);
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void payment(ActionEvent event) {
    	
    	if (!isAccountActive()) {
            showAlert("Access Denied", "This action is not available because your account status is not active.");
            return;
        }
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/paidMoney/paidMoneyPage1.fxml"));
			AnchorPane defaultView = loader.load();
			
			paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void personal(ActionEvent event) {

    }
    @FXML
    void topUp(ActionEvent event) {
    	if (!isAccountActive()) {
            showAlert("Access Denied", "This action is not available because your account status is not active.");
            return;
        }
    	try {
    		FXMLLoader loader =  new FXMLLoader(getClass().getResource("topUpPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	    
    }

    @FXML
    void vehicle(ActionEvent event) {
    	System.out.println("da  nhan vehicle");
    	if (!isAccountActive()) {
            showAlert("Access Denied", "This action is not available because your account status is not active.");
            return;
        }
    	int userId = UserSession.getInstance().getUserId();
        LoanModel loanModel = new LoanModel();
        if (loanModel.hasActiveLoanOfType(userId, "vehicle")) {
            showAlert("Loan application denied", "You already have an active 'vehicle' loan account.");
            return;
        }
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/requestLoanHome/requestLoanVehiclePage.fxml"));
    		AnchorPane defaultView = loader.load();
    		
    		paneCenter.getChildren().clear();
    		paneCenter.getChildren().addAll(defaultView);
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void withdraw(ActionEvent event) {

    }
    public void showFullname() {
    	int id = UserSession.getInstance().getUserId();
    	AccountModel acocunModel = new AccountModel();
    	Account account = acocunModel.findById(id);
    	if(account!= null) {
    		label_fullname.setText(account.getFullname());
    	}
    }
    public void showCurrentBalance() {
    	int id = UserSession.getInstance().getUserId();
    	AccountModel accountModel = new AccountModel();
    	Account account = accountModel.findById(id);
    	if (account.getBalance() != null) {
    	    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    	    String balanceString = currencyFormat.format(account.getBalance());
    	    label_currentBalance.setText(balanceString);
    	} else {
    	    label_currentBalance.setText("$0.00"); 
    	}

    }
    @FXML
    void handleRefreshButtonAction(ActionEvent event) {
    	refreshData();
    }
    private void refreshData() {
        showFullname();
 
        showCurrentBalance();

		showNextPaymentDetails();
		
		reloadTransactionPayment();
		
		
    }
    private void reloadTransactionPayment() {
        layout_transaction.getChildren().clear(); // Xóa danh sách giao dịch hiện tại
        loadTransactionPayment(); // Tải lại danh sách giao dịch
    }
    @FXML
    void topUpHistory(ActionEvent event) {
    	try {
    		FXMLLoader loader =  new FXMLLoader(getClass().getResource("historyTopUpPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void loanDetail(ActionEvent event) {
    	try {
    		FXMLLoader loader =  new FXMLLoader(getClass().getResource("/application/requestLoanHome/requestHistoryPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	
        	paneCenter.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void status(ActionEvent event) {
        int userId = UserSession.getInstance().getUserId();
        AccountModel accountModel = new AccountModel();
        Account account = accountModel.findById(userId); 

        AccountStatus status = account.getStatus();
        String message = "";

        switch (status) {
            case PENDING:
                message = "Status: Pending";
                break;
            case ACTIVE:
                message = "Status: Active";
                break;
            case REJECTED:
                message = "Status: Rejected";
                break;
            default:
                message = "Unknown Status";
        }

        showAlert("Account Status", message);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void logout(ActionEvent event) {
    	UserSession.endSession();
    	closeCurrentStage(event);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login_register/background.fxml"));
            Parent root = loader.load();
       
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login/Register");
            stage.show();
            stage.setResizable(false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }@FXML
    void logout1(ActionEvent event) {
    	logout(event);
    }
    private void closeCurrentStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }
    private boolean isAccountActive() {
        int userId = UserSession.getInstance().getUserId();
        AccountModel accountModel = new AccountModel();
        Account account = accountModel.findById(userId);
        return account.getStatus() == AccountStatus.ACTIVE;
    }
    public void showNextPaymentDetails() {
        int userId = UserSession.getInstance().getUserId();
        
        PaymentScheduledItem nextPayment = PaymentScheduled.getNextPayment(userId);
       
        if (nextPayment != null) {
        	System.out.println(nextPayment.getCustomer_id());
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            String formattedAmount = currencyFormat.format(nextPayment.getMonthlyRepayment());
            String formattedDate = nextPayment.getPaymentDate().format(DateTimeFormatter.ofPattern("MMM d, yyyy"));

            label_nextPayment.setText(formattedAmount);
            label_scheduled.setText(formattedDate);
        } else {
            label_nextPayment.setText("$0");
            label_scheduled.setText("N/A");
        }
    }
    
    public void loadTransactionPayment() {
    	int userId = UserSession.getInstance().getUserId();
        List<PaymentScheduledItem> paymentActive = getPaymentActive(userId); 
        int paymentNumber = 1;
        for (PaymentScheduledItem payment : paymentActive) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/paidMoney/rowPaidMoneyTransaction.fxml"));
                Parent row = loader.load();
                RowPaidMoneyTransactionController rowController = loader.getController();
                
                rowController.setPaymentData(payment);
                rowController.setPaymentNumber(paymentNumber); // Truyền số thứ tự vào controller
                
                layout_transaction.getChildren().add(row);
                
                paymentNumber++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public List<PaymentScheduledItem> getPaymentActive(int userId) {
        List<PaymentScheduledItem> paymentActive = new ArrayList<>();
        String sql = "SELECT * FROM payment_scheduled WHERE status = 1 and customer_id =? ORDER BY payment_date ASC";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the userId parameter in the PreparedStatement
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PaymentScheduledItem payment = new PaymentScheduledItem();
                    payment.setLoanId(rs.getInt("loanId"));
                    payment.setMonthlyRepayment(rs.getBigDecimal("monthlyRepayment"));
                    
                    Date sqlDate = rs.getDate("payment_date");
                    LocalDate localDate = sqlDate != null ? sqlDate.toLocalDate() : null;
                    payment.setPaymentDate(localDate);
                    paymentActive.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider more robust error handling
        }

        return paymentActive;
    }




}
	

