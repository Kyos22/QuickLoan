package application.userVerify;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerify5Controller implements Initializable {

    @FXML
    private Button button_back;

    @FXML
    private Button button_close;

    @FXML
    private Button button_continue;

    @FXML
    private ImageView imageViewBack;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private ImageView imageViewIdentity;

    @FXML
    private Label myLabel;
    
    private int idIdentity;
    private int phoneNumber;
    private LocalDate dateofbirth;
    private String country;
    private String fulladdress;
    private String city;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
   		imageViewClose.setImage(imageClose);
   		
   	   Image imageIdentity = new Image(getClass().getResourceAsStream("/application/userVerify/image/identityCard.png"));
   			imageViewIdentity.setImage(imageIdentity);
   			
   			Image imageBack = new Image(getClass().getResourceAsStream("/application/userVerify/image/arrow-back-regular-48.png"));
   	   		imageViewBack.setImage(imageBack);
   			
	}
    
    @FXML
    void back(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage4.fxml"));
            Parent root = loader.load();
            UserVerify4Controller controller = loader.getController();
         
            controller.setIdentityNumber(idIdentity);
            controller.setPhoneNumber(phoneNumber);        
            controller.setDateOfBirth(dateofbirth);
            controller.setCountry(country);
            controller.setCity(city);
            controller.setFullAdress(fulladdress);
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT); 
            stage.setScene(scene);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.show();
            
            ((Stage) button_continue.getScene().getWindow()).close();         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close(ActionEvent event) {
    	Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void continue_page5(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage6.fxml"));
            Parent root = loader.load();
            UserVerify6Controller controller = loader.getController();
         
            controller.setIdentityNumber(idIdentity);
            controller.setPhoneNumber(phoneNumber);        
            controller.setDateOfBirth(dateofbirth);
            controller.setCountry(country);
            controller.setCity(city);
            controller.setFullAdress(fulladdress);
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT); 

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT); 
            stage.setScene(scene);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.show();
            
            ((Stage) button_continue.getScene().getWindow()).close();         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setIdentityNumber(int idIdentity) {
        this.idIdentity = idIdentity;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setCountry(String country) {
    	this.country = country;
    }
    public void setFullAdress(String fulladdress) {
    	this.fulladdress = fulladdress;
    }
    public void setCity(String city) {
    	this.city = city;
    }

	

}
