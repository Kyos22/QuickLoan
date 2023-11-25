package application.requestLoanHome;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import application.entities.Loan;
import application.entities.Loan.LoanStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RowRequestHistoryController {

    @FXML
    private Label amount;

    @FXML
    private Label label_type;

    @FXML
    private Label status;

    @FXML
    private Button button_detail;

    @FXML
    private ImageView imageViewHouse;

    @FXML
    private ImageView imageView_detail;

    @FXML
    public Label requestDate;

    @FXML
    private Label type;

    private String loanType;
   
    private Loan loan;
    @FXML
    void details(ActionEvent event) {
        
        if (loan.getStatus() == LoanStatus.ACTIVE) {
            if ("home".equals(loanType)) {
            	System.out.println("house");
            	try {
        
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("detailLoanHome.fxml"));
                	Parent root = loader.load();
                	
                	 DetailLoanController detailController = loader.getController();
                     detailController.setLoanData(loan);
                	
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
            } else if ("vehicle".equals(loanType)) {
            	System.out.println("vehicle");
            	try {
        
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("detailLoanHome.fxml"));
                	Parent root = loader.load();
                	
                	 DetailLoanController detailController = loader.getController();
                     detailController.setLoanData(loan);
                	
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
        } else {
            
            showAlert("Cannot View Details", "You can only view details of active loans.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    
    public void setLoan(Loan loan) {
    	this.loan = loan;
        setType(loan.getLoantype());
        setRequestDate(loan.getRequestCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        setAmount(loan.getAmount());
        setStatus(loan.getStatus());	
    }
    public void setType(String type) {
        this.loanType = type;
        
        if ("home".equals(type)) {
        	label_type.setText(type);       	
            imageViewHouse.setImage(new Image("/application/requestLoanHome/image/houseIcon.png"));
        } else if ("vehicle".equals(type)) {
        	label_type.setText(type);   
            imageViewHouse.setImage(new Image("/application/requestLoanHome/image/car.png"));
        }
    }
    public void setStatus(LoanStatus loanStatus) {
        switch (loanStatus) {
            case PENDING:
                status.setText("Pending");
        
                break;
            case ACTIVE:
                status.setText("Active");
                status.setStyle("-fx-text-fill:green");
                break;
            case REJECTED:
                status.setText("Rejected");
                status.setStyle("-fx-text-fill:red");
                break;
            default:
                status.setText("Unknown");
                break;
        }
    }
    public void setRequestDate(String date) {
        requestDate.setText(date);
    }
    public void setAmount(BigDecimal amountValue) {
    	 DecimalFormat decimalFormat = new DecimalFormat("#,##0.0");
    	    String formattedAmount = decimalFormat.format(amountValue);
    	    this.amount.setText(formattedAmount);
    }

}
