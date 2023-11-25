package application.login_register;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ForgetPasswordPage2Controller implements Initializable {

    @FXML
    private Button buttonVerify;

    @FXML
    private Button button_back2;

    @FXML
    private ImageView imageArrow;

    @FXML
    private ImageView imageForget1;

    @FXML
    private TextField otpField1;

    @FXML
    private TextField otpField2;

    @FXML
    private TextField otpField3;

    @FXML
    private TextField otpField4;

    @FXML
    private AnchorPane pane1;
    
    private String email;
    private String verificationCode;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setOTPFieldListener(otpField1, otpField2, null); // Không có ô trước đó cho ô đầu tiên
	    setOTPFieldListener(otpField2, otpField3, otpField1);
	    setOTPFieldListener(otpField3, otpField4, otpField2);
	    setOTPFieldListener(otpField4, null, otpField3); // Không có ô phía sau cho ô cuối cùng
	    
	    Image image = new Image(getClass().getResourceAsStream("/application/login_register/libs/white_arrow.png"));
	    imageArrow.setImage(image);
	    Image image1 = new Image(getClass().getResourceAsStream("/application/login_register/libs/forgetpass2.png"));
	    imageForget1.setImage(image1);
		
	}
    private void setOTPFieldListener(TextField currentField, TextField nextField, TextField previousField) {
        currentField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.matches("\\d*")) {
                currentField.setText(oldValue); // Nếu không phải ký tự Latin không dấu, quay trở lại giá trị trước đó
            }else if (newValue.length() > 1) {
                currentField.setText(newValue.substring(0, 1)); // Chỉ cho phép 1 ký tự
            } else if (newValue.length() == 1 && nextField != null) {
                nextField.requestFocus(); // Di chuyển con trỏ sang ô tiếp theo
            }
        });
        currentField.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                    if (currentField.getText().isEmpty() && previousField != null) {
                        previousField.requestFocus(); // Di chuyển con trỏ đến ô phía trước
                    }
                    break;
                case ENTER:
                    if (nextField != null) {
                        nextField.requestFocus(); // Di chuyển con trỏ sang ô tiếp theo
                    }
                    break;
                default:
                    break;
            }
        });
    }
    private String concatenateOTP() {
		return otpField1.getText() + otpField2.getText() + otpField3.getText() + otpField4.getText();
	}
    
    @FXML
    void back(ActionEvent event) {
    	System.out.println("back");
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetPasswordPage1.fxml"));
        	AnchorPane view = loader.load();
        ForgetPasswordPage1Controller controller = loader.getController();
        controller.setEmail(email);
        	
        	pane1.getChildren().setAll(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void verify(ActionEvent event) {
    	String enteredOTP = concatenateOTP();
    	if(enteredOTP.equals(verificationCode)) {
    		
    		System.out.println("true");
            try {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetPasswordPage3.fxml"));
                AnchorPane verifyView = loader.load();
                
                ForgetPasswordPage3Controller controller = loader.getController();
                controller.setEmail(email);
                
                pane1.getChildren().setAll(verifyView);
			} catch (Exception e) {
				
			}
        } else {
        	loadCurrentPage();
        	Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("failed");
    		loadCurrentPage();
    		
            System.out.println("failed");
        }
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    public void setVerificationCode (String verificationCode) {
    	this.verificationCode = verificationCode;
    }
    private void loadCurrentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("forgetPasswordPage2.fxml"));
            AnchorPane currentPage = loader.load();
            ForgetPasswordPage2Controller currentController = loader.getController();

            currentController.setEmail(email);
            currentController.setVerificationCode(verificationCode);

            pane1.getChildren().addAll(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	

}
