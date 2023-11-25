package application.admin;

import java.io.IOException;

import application.entities.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RowAllUserVerifiedController {

    @FXML
    private Button button_detail;

    @FXML
    private ImageView imageView_detail;

    @FXML
    private Label label_Username;

    @FXML
    private Label label_email;

    private Account account;
    
    
    @FXML
    void detail(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accountPendingDetail.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT); 
            stage.setScene(scene);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
            
            AccountPendingDetailController detailController = loader.getController();
            detailController.setData(account);
                 
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    public void setAccountData(Account account) {
        this.account = account;
        label_Username.setText(account.getUsername());
        label_email.setText(account.getEmail());
       
    }

}
