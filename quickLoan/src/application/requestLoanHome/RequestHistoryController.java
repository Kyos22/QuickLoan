package application.requestLoanHome;

import java.net.URL;
import java.util.ResourceBundle;

import application.entities.UserSession;
import application.model.LoanModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class RequestHistoryController implements Initializable {

	 @FXML
	    private Button button_loanAccount;

	    @FXML
	    private Button button_loanScheduled;
	
    @FXML
    private AnchorPane paneCenter;

    @FXML
    private AnchorPane paneMain;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showTableHistoryRequest();
	}
	
	public void showTableHistoryRequest() {
		 
		try {
            AnchorPane defaultView =  FXMLLoader.load(getClass().getResource("requestHistoryPage1.fxml"));
            paneMain.getChildren().clear();
            
            paneMain.getChildren().addAll(defaultView);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	 @FXML
	    void loanAccount(ActionEvent event) {
		 try {
	            AnchorPane defaultView =  FXMLLoader.load(getClass().getResource("requestHistoryPage1.fxml"));
	            paneMain.getChildren().clear();

	            paneMain.getChildren().addAll(defaultView);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	 @FXML
	 void loanScheduled(ActionEvent event) {
	     int userId = UserSession.getInstance().getUserId(); // Thay thế bằng mã để lấy userId của người dùng hiện tại (hoặc bạn có thể lấy từ đâu đó khác)
	     LoanModel loanModel = new LoanModel();

	     if (!loanModel.hasLoan(userId)) {
	         showAlert("Notice", "You don't have any active loans", "Please create a new loan before viewing loan details.");
	         return;
	     }

	     try {
	         FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/paymentScheduled/paymentScheduled.fxml"));
	         AnchorPane defaultView = loader.load();
	         paneMain.getChildren().clear();
	         paneMain.getChildren().addAll(defaultView);
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }

	    private void showAlert(String title, String header, String content) {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(header);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
	
	
	

}
