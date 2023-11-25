package application.userVerify;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.UserSession;
import application.model.AccountModel;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DefaultStringConverter;

public class UserVerify2Controller implements Initializable {

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
    private TextField input_MM;

    @FXML
    private TextField input_YYYY;

    @FXML
    private TextField input_dd;

    @FXML
    private TextField input_fullname;

    @FXML
    private TextField input_id;

    @FXML
    private TextField input_number;
    
    private String country;
    
    private String dateString;
    
    private String fullname;
    private int idIdentity;
    private int phoneNumber;
    private LocalDate dateofbirth;
    private String address;
    private String city;
    

    @FXML
    void back(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage1.fxml"));
    		Parent root = loader.load();
    		UserVerify1Controller controller = loader.getController();
    		controller.setSelectedCountry(country);
    		
    		
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

        	
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleCountrySelection(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image imageBack = new Image(getClass().getResourceAsStream("/application/userVerify/image/arrow-back-regular-48.png"));
		imageViewBack.setImage(imageBack);
		
		Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
		imageViewClose.setImage(imageClose);
		
		
		int UserId = UserSession.getInstance().getUserId();
		AccountModel accountModel = new AccountModel();
		Account userAccount = accountModel.findById(UserId);
		if(userAccount!=null) {
			
			input_fullname.setText(userAccount.getFullname());
			fullname = input_fullname.getText();
			
		}
		
		 setupPhoneNumberTextField();
		 setupIdentityNumberTextField();
		 setupDateOfBirthTextFields();
	}
	
	private void setupPhoneNumberTextField() {
	    TextFormatter<String> phoneNumberFormatter = new TextFormatter<>(new DefaultStringConverter(), "", change -> {
	        if (!change.isContentChange()) {
	            return change;
	        }
	        String newText = change.getControlNewText();

	        if (newText.length() > 11) {
	           
	            showAlert("Error", "You can only enter up to 11 digits of phone numbers");
	            return null;
	        } else if (!newText.matches("\\d*")) {	           
	            showAlert("Error", "Only numeric characters are allowed.");
	            return null;
	        }
	        return change;
	    });
	    input_number.setTextFormatter(phoneNumberFormatter);
	}
	private void setupIdentityNumberTextField() {
		TextFormatter<String> IdentityNumberFormatter = new TextFormatter<>(new DefaultStringConverter(), "", change -> {
			if (!change.isContentChange()) {
				return change;
			}
			String newText = change.getControlNewText();
			
			if (newText.length() > 12) {
				
				showAlert("Error", "You can only enter up to 12 digits of identity card number.");
				return null;
			} else if (!newText.matches("\\d*")) {	           
				showAlert("Error", "Only numeric characters are allowed.");
				return null;
			}
			return change;
		});
		input_id.setTextFormatter(IdentityNumberFormatter);
	}

	private void showAlert(String title, String content) {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	
	}
	
	private void setupDateOfBirthTextFields() {

	    TextFormatter<String> dayFormatter = new TextFormatter<>(change -> {
	        if (!change.isContentChange()) {
	            return change;
	        }
	        String newText = change.getControlNewText();
	        if (!newText.matches("\\d{0,2}")) {
	            showAlert("Error", "Day must be a number with max 2 digits.");
	            return null;
	        } else if (!newText.isEmpty() && (Integer.parseInt(newText) > 31 || Integer.parseInt(newText) < 1)) {
	            showAlert("Error", "Day must be between 1 and 31.");
	            return null;
	        }
	        return change;
	    });

	    TextFormatter<String> monthFormatter = new TextFormatter<>(change -> {
	        if (!change.isContentChange()) {
	            return change;
	        }
	        String newText = change.getControlNewText();
	        if (!newText.matches("\\d{0,2}")) {
	            showAlert("Error", "Month must be a number with max 2 digits.");
	            return null;
	        } else if (!newText.isEmpty() && (Integer.parseInt(newText) > 12 || Integer.parseInt(newText) < 1)) {
	            showAlert("Error", "Month must be between 1 and 12.");
	            return null;
	        }
	        return change;
	    });

	    TextFormatter<String> yearFormatter = new TextFormatter<>(change -> {
	        if (!change.isContentChange()) {
	            return change;
	        }
	        String newText = change.getControlNewText();
	        if (!newText.matches("\\d{0,4}")) {
	            showAlert("Error", "Year must be a number with max 4 digits.");
	            return null;
	        } else if (!newText.isEmpty() && Integer.parseInt(newText) > LocalDate.now().getYear()) {
	            showAlert("Error", "Year must not be greater than the current year.");
	            return null;
	        }
	        return change;
	    });

	    input_dd.setTextFormatter(dayFormatter);
	    input_MM.setTextFormatter(monthFormatter);
	    input_YYYY.setTextFormatter(yearFormatter);
	}

	
	public void ConnectDate() {
		String day = input_dd.getText();
		String month = input_MM.getText();
		String year = input_YYYY.getText();
		
		if(day.isEmpty() || month.isEmpty() || year.isEmpty()) {
			showAlert("Warning", "you must to insert day, month, year");
			return;
		}
		dateString = year + "-"+month+"-"+day;
	}
	@FXML
	void continue1(ActionEvent event) {
	    try {
	        if (input_id.getText().trim().isEmpty() || input_number.getText().trim().isEmpty() ||
	            input_dd.getText().trim().isEmpty() || input_MM.getText().trim().isEmpty() ||
	            input_YYYY.getText().trim().isEmpty()) {
	            showAlert("Error", "All fields are required and must be filled out.");
	            return;
	        }
	      
	        int yearInput = Integer.parseInt(input_YYYY.getText().trim());
	        if (yearInput < 1900) {
	            showAlert("Error", "Year must be greater than 1900.");
	            return;
	        } else if (yearInput > LocalDate.now().getYear()) {
	            showAlert("Error", "Year must not be greater than the current year.");
	            return;
	        }
	        
	        idIdentity = Integer.parseInt(input_id.getText().trim());
	        phoneNumber = Integer.parseInt(input_number.getText().trim());

	        ConnectDate();

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate dateOfBirth = LocalDate.parse(dateString, formatter);

	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage3.fxml"));
	        Parent root = loader.load();

	        UserVerify3Controller controller = loader.getController();
	        controller.setIdentityNumber(idIdentity);
	        controller.setPhoneNumber(phoneNumber);
	        controller.setDateOfBirth(dateOfBirth);
	        controller.setCountry(country);
	        controller.setCity(city);
	        controller.setFullAdress(address);

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
	    } catch (NumberFormatException e) {
	        showAlert("Error", "ID and Phone Number fields must contain only numbers.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	public void setIdIdentity(int idIdentity) {
	    this.idIdentity = idIdentity;
	    input_id.setText(String.valueOf(idIdentity));
	}

	public void setPhoneNumber(int phoneNumber) {
	    this.phoneNumber = phoneNumber;
	    input_number.setText(String.valueOf(phoneNumber));
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
	    this.dateofbirth = dateOfBirth;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    dateString = dateOfBirth.format(formatter);
	    input_dd.setText(dateOfBirth.format(DateTimeFormatter.ofPattern("dd")));
	    input_MM.setText(dateOfBirth.format(DateTimeFormatter.ofPattern("MM")));
	    input_YYYY.setText(dateOfBirth.format(DateTimeFormatter.ofPattern("yyyy")));
	}


	public void setCountry(String country) {
	    this.country = country;
	}
	public void setHomeAdress(String address) {
		this.address = address;
	}
	public void setCity (String city) {
		this.city = city;
	}




}
