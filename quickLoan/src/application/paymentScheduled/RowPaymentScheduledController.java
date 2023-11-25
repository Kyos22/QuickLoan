package application.paymentScheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.entities.Loan;
import application.entities.UserSession;
import application.model.LoanModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class RowPaymentScheduledController {

    @FXML
    private Button button_viewing;

    @FXML
    private Label label_date;

    @FXML
    private Label label_home;

    private Loan loan;
    
    @FXML
    void viewing(ActionEvent event) {
    	
    	int userId = UserSession.getInstance().getUserId();
    	LoanModel loanModel = new LoanModel();
    	int loanId = loan.getIdLoanAccount();
    	
    	if (!loanModel.hasLoan1(userId,loanId)) {
	         Alert alert = new Alert(AlertType.WARNING);
	         alert.setHeaderText("Warning");
	         alert.setContentText("Your request loan was rejected");
	         alert.showAndWait();
	     }
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scheduledBackground.fxml"));
            Parent root = loader.load();
            
            ScheduledBackgroundController controller = loader.getController();
            if (loan != null) {
                controller.loadData(loan);
            } else {
                return;
            }
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT); 
            stage.setScene(scene);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void setLoan(Loan loan) {
        if (loan != null) {
            this.loan = loan;
            label_home.setText(loan.getLoantype());

            LocalDate startDate = loan.getStartDate();
            if (startDate != null) {
                
                label_date.setText(startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                
                label_date.setText("No Start Date");
            }
        } else {
            label_home.setText("");
            label_date.setText("");
        }
    }

    
}
