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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerify4Controller implements Initializable {

    @FXML
    private RadioButton buttonRadius_Passport;

    @FXML
    private RadioButton buttonRadius_driverLicense;

    @FXML
    private RadioButton buttonRadius_idcard;

    @FXML
    private Button button_close;

    @FXML
    private Button button_continue;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private ImageView imageView_card1;

    @FXML
    private ImageView imageView_card2;

    @FXML
    private ImageView imageView_card3;

    
    @FXML
    private TextField input_country;
    
    private int idIdentity;
    private int phoneNumber;
    private LocalDate dateofbirth;
    private String country;
    private String fulladdress;
    private String city;
    

    @FXML
    private ToggleGroup documentTypeToggleGroup; 

    
    @FXML
    void card(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        button_continue.setDisable(true);

        documentTypeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
           
            button_continue.setDisable(newValue == null);

            if (newValue != null) {
                
                ((RadioButton) newValue).getStyleClass().add("radio-button-selected");
            }       
            if (oldValue != null) {
                ((RadioButton) oldValue).getStyleClass().remove("radio-button-selected");
            }
        });

        buttonRadius_Passport.getStyleClass().add("radio-button");
        buttonRadius_driverLicense.getStyleClass().add("radio-button");
        buttonRadius_idcard.getStyleClass().add("radio-button");
        
        Image imageIdcard1 = new Image(getClass().getResourceAsStream("/application/userVerify/image/id-card-solid-48.png"));
     	imageView_card1.setImage(imageIdcard1);
     	
     	Image imageIdcard2 = new Image(getClass().getResourceAsStream("/application/userVerify/image/credit-card-front-solid-48.png"));
     	imageView_card2.setImage(imageIdcard2);
     	
     	Image imageIdcard3 = new Image(getClass().getResourceAsStream("/application/userVerify/image/credit-card-solid-48.png"));
     	imageView_card3.setImage(imageIdcard3);
     	
     	Image imageclose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
     	imageViewClose.setImage(imageclose);
        
    }



    
    @FXML
    void continue2(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) documentTypeToggleGroup.getSelectedToggle();
        if (selectedRadioButton == null) {
            showAlert("Error", "Please select a document type to continue.");
            return;
        }
        if (selectedRadioButton == buttonRadius_idcard) {
            switchScene(event);
        } else {         
            showAlert("Error", "The selected document type is not yet supported.");
        }
        if (selectedRadioButton == buttonRadius_driverLicense) {
        	Alert alert = new Alert(AlertType.WARNING);
    	    alert.setTitle("Sorry");
    	    alert.setHeaderText(null);
    	    alert.setContentText("This function will update in the future");
    	    alert.showAndWait();
        }
        if (selectedRadioButton == buttonRadius_Passport) {
        	Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("Sorry");
        	alert.setHeaderText(null);
        	alert.setContentText("This function will update in the future");
        	alert.showAndWait();
        }
    }

    private void switchScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage5.fxml"));
            Parent root = loader.load();
            UserVerify5Controller controller = loader.getController();
         
            controller.setIdentityNumber(idIdentity);
            controller.setPhoneNumber(phoneNumber);        
            controller.setDateOfBirth(dateofbirth);
            controller.setCountry(input_country.getText());
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


    private void showAlert(String title,String Content) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(Content);
    	alert.showAndWait();
    }


    @FXML
    void license(ActionEvent event) {

    }

    @FXML
    void passport(ActionEvent event) {

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
    	input_country.setText(country);
    }
    public void setFullAdress(String fulladdress) {
    	this.fulladdress = fulladdress;
    }
    public void setCity(String city) {
    	this.city = city;
    }


}
