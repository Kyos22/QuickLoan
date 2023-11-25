package application.dashboard;

import java.math.BigDecimal;

import application.entities.Account;
import application.entities.UserSession;
import application.model.AccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TopUpVCB1Controller {

    @FXML
    private Button button_back;

    @FXML
    private Button button_continue;

    @FXML
    private ImageView imageView_Back;

    @FXML
    private ImageView imageView_checkLine;

    @FXML
    private Label myLabel;

    @FXML
    private AnchorPane paneVCB1;

    private BigDecimal amountDecimal;
    
    @FXML
    void back(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpVCB.fxml"));
			AnchorPane defaultView = loader.load();
			
			paneVCB1.getChildren().clear();
			
			paneVCB1.getChildren().setAll(defaultView);
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void continue_payment1(ActionEvent event) {
        AccountModel accountModel = new AccountModel();
        int userId = UserSession.getInstance().getUserId(); 
        BigDecimal topUpAmount = new BigDecimal(myLabel.getText().replace("$", "")); 
        
        
        if (accountModel.topUpAccount(userId, topUpAmount)) {
            System.out.println("Nạp tiền thành công");
          
            try {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("topUpComplete.fxml"));
    			AnchorPane defaultView = loader.load();
    			
    			paneVCB1.getChildren().clear();
    			
    			paneVCB1.getChildren().setAll(defaultView);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
            System.out.println("Nạp tiền thất bại");
        }
    }
    public void setAmount(String amount) {
        this.amountDecimal = new BigDecimal(amount); 
        myLabel.setText("$" + amount);
    }

    
}
