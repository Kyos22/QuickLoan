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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerify3Controller implements Initializable {

    @FXML
    private TextField Input_Country;

    @FXML
    private Button button_back;

    @FXML
    private Button button_close;

    @FXML
    private ImageView imageViewBack;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private TextField input_City;

    @FXML
    private TextField input_fulladdress;

    @FXML
    private Button button_continue;

    
    @FXML
    private Label myLabel;
    
    
    private int idIdentity;
    private int phoneNumber;
    private LocalDate dateofbirth;
    private String country;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setupFullAddressTextField();
		
		   Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
			imageViewClose.setImage(imageClose);
		
			   Image imageArrow = new Image(getClass().getResourceAsStream("/application/userVerify/image/arrow-back-regular-48.png"));
				imageViewClose.setImage(imageArrow);
	}
    
    private void prepareReturnData(UserVerify2Controller controller) {
        controller.setIdIdentity(idIdentity);
        controller.setPhoneNumber(phoneNumber);
        controller.setDateOfBirth(dateofbirth);
        controller.setCountry(Input_Country.getText());
        controller.setHomeAdress(input_fulladdress.getText());
        controller.setCity(input_City.getText());
        
    }

    @FXML
    void back(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage2.fxml"));
    		Parent root = loader.load();
    		UserVerify2Controller controller = loader.getController();
    		prepareReturnData(controller);
    		
    		
    		Scene scene = new Scene(root);
    		scene.setFill(Color.TRANSPARENT); 

    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.TRANSPARENT); 
    		stage.setScene(scene);
    		
    		stage.initModality(Modality.APPLICATION_MODAL);
    		
    		
    		stage.show();
    		stage.setResizable(false);
    		
    		Stage currentStage = (Stage) button_back.getScene().getWindow();
    		currentStage.close();
    		System.out.println(country);

        	
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void setupFullAddressTextField() {
    	input_fulladdress.textProperty().addListener((observable, oldValue, newValue) -> {
    	    // Kiểm tra nếu newValue không phải là null và chứa cả chữ và số
    	    if (newValue != null && (!newValue.matches(".*\\d.*") || !newValue.matches(".*[a-zA-Z]+.*"))) {
    	        // Hiển thị lỗi nếu chuỗi nhập không hợp lệ
    	        myLabel.setText("Address must contain both letters and numbers.");
    	        myLabel.setStyle("-fx-background-color:#291D24;-fx-text-fill:#832F3D");
    	    } else {
    	        // Xóa thông báo lỗi nếu chuỗi nhập hợp lệ
    	        myLabel.setText("");
    	        myLabel.setStyle(null);
    	        }
    	});

    }
    
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void continue_page3(ActionEvent event) {
    	try {
			if(input_fulladdress.getText().isEmpty() || input_City.getText().isEmpty()) {
				showAlert("Error", "All fields are required and must be filled out.");
				return;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage4.fxml"));
	        Parent root = loader.load();

	        UserVerify4Controller controller = loader.getController();
	        controller.setIdentityNumber(idIdentity);
	        controller.setPhoneNumber(phoneNumber);        
	        controller.setDateOfBirth(dateofbirth);
	        controller.setCountry(Input_Country.getText());
	        controller.setCity(input_City.getText());
	        controller.setFullAdress(input_City.getText());

	        Scene scene = new Scene(root);
	        scene.setFill(Color.TRANSPARENT);

	        Stage stage = new Stage();
	        stage.initStyle(StageStyle.TRANSPARENT);
	        stage.setScene(scene);

	        stage.initModality(Modality.APPLICATION_MODAL);

	        stage.show();
	        stage.setResizable(false);

	        // Đóng cửa sổ hiện tại
	        Stage currentStage = (Stage) button_back.getScene().getWindow();
	        currentStage.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private void showAlert(String title, String content) {
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
    	Input_Country.setText(country);
    }
    public void setFullAdress(String fulladdress) {
    	input_fulladdress.setText(fulladdress);
    }
    public void setCity(String city) {
    	input_City.setText(city);
    }

	


}
