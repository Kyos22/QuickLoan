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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerify6Controller implements Initializable {

    @FXML
    private RadioButton buttonRadius_driverLicense;

    @FXML
    private RadioButton buttonRadius_driverLicense1;

    @FXML
    private Button button_back;

    @FXML
    private Button button_close;

    @FXML
    private Button button_continue;


    @FXML
    private ToggleGroup documentTypeToggleGroup1;

    @FXML
    private ImageView imageViewBack;

    @FXML
    private ImageView imageViewCamera;
    
    @FXML
    private RadioButton buttonRadius_TakePhoto;

    @FXML
    private RadioButton buttonRadius_UploadFile;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private ImageView imageViewUpload;

    private int idIdentity;
    private int phoneNumber;
    private LocalDate dateofbirth;
    private String country;
    private String fulladdress;
    private String city;
    
    @FXML
    void back(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage5.fxml"));
            Parent root = loader.load();
            UserVerify5Controller controller = loader.getController();
         
            controller.setIdentityNumber(idIdentity);
            controller.setPhoneNumber(phoneNumber);        
            controller.setDateOfBirth(dateofbirth);
            controller.setCountry(this.country);
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
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void continue_page6(ActionEvent event) {
    	 RadioButton selectedRadioButton = (RadioButton) documentTypeToggleGroup1.getSelectedToggle();
         if (selectedRadioButton == null) {
             showAlert("Error", "Please select a document type to continue.");
             return;
         }
         if (selectedRadioButton == buttonRadius_UploadFile) {
             switchScene(event);
        	 System.out.println("success");
         } else {         
             showAlert("Error", "The selected document type is not yet supported.");
         }
         if (selectedRadioButton == buttonRadius_TakePhoto) {
         	Alert alert = new Alert(AlertType.WARNING);
     	    alert.setTitle("Sorry");
     	    alert.setHeaderText(null);
     	    alert.setContentText("This function will update in the future");
     	    alert.showAndWait();
         }
         
    }
    private void switchScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage7.fxml"));
            Parent root = loader.load();
            UserVerify7Controller controller = loader.getController();
         
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
    void license(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 button_continue.setDisable(true);

	        documentTypeToggleGroup1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
	           
	            button_continue.setDisable(newValue == null);

	            if (newValue != null) {
	                
	                ((RadioButton) newValue).getStyleClass().add("radio-button-selected");
	            }       
	            if (oldValue != null) {
	                ((RadioButton) oldValue).getStyleClass().remove("radio-button-selected");
	            }
	        });

	        buttonRadius_TakePhoto.getStyleClass().add("radio-button");
	        buttonRadius_UploadFile.getStyleClass().add("radio-button");
	        
	        Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
	   		imageViewClose.setImage(imageClose);
	   		
	   		Image imageBack = new Image(getClass().getResourceAsStream("/application/userVerify/image/arrow-back-regular-48.png"));
	   		imageViewBack.setImage(imageBack);
	   		
	   		Image imageUp = new Image(getClass().getResourceAsStream("/application/userVerify/image/cloud-upload-solid-48.png"));
	   		imageViewUpload.setImage(imageUp);
	   		
	   		Image imageCamera = new Image(getClass().getResourceAsStream("/application/userVerify/image/camera-solid-48.png"));
	   		imageViewCamera.setImage(imageCamera);
	}
	public void showAlert(String title,String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
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
