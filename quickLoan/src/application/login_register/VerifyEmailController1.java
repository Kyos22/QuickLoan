package application.login_register;

import java.net.URL;
import java.util.Random;
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
import javafx.scene.paint.Color;

public class VerifyEmailController1 implements Initializable {

	

	@FXML
	private AnchorPane pane2;
	
    @FXML
    private Button button_Back1;

    @FXML
    private Button buttonconfirmEmail1;

    @FXML
    private TextField input_Email1;
    
    private String username;
    private String password;
    private String fullname;
    private Boolean term;
    
    private String verificationCode;
    
    @FXML
    private ImageView imageView1;
    
    
    @FXML
    private ImageView imageView;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Image image = new Image(getClass().getResourceAsStream("/application/login_register/libs/image1.png"));
        imageView.setImage(image);
        Image image1 = new Image(getClass().getResourceAsStream("/application/login_register/libs/arrow_left.png"));
        imageView1.setImage(image1);
        
		
	}

    @FXML
    void back1(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("registerPage.fxml"));
        	AnchorPane defaultView = loader.load();
        	
        	RegisterPageController registerPageController = loader.getController();
        	registerPageController.setEmail(input_Email1.getText());
        	registerPageController.setFullname(fullname);
        	registerPageController.setUsername(username);
        	registerPageController.setPassword(password);
        	registerPageController.setTerm(term);
        	
        	
        	pane2.getChildren().addAll(defaultView);
        	
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void confirmEmail(ActionEvent event) {
    	
    	try {
    		verificationCode = generateVerificationCode();
        	
        	String emailContent = "ma xac thuc cua ban la: " + verificationCode;
        	
        	boolean emailSent = application.login_register.helper.MailHelper.send("nguyenthanhcongvt234@gmail.com", input_Email1.getText(), "Mã xác thức", emailContent);

        	if (!emailSent) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("failed");
        		alert.show();
            }else {
            	
            	Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucess");
                alert.setContentText("Vao email de lay ma code xac thuc");
                alert.show();
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("verifyPage2.fxml"));
                    AnchorPane verifyView = loader.load();
                    
                    VerifyEmail2Controller controller = loader.getController();
                    controller.setVerificationCode(verificationCode);
                    controller.setEmail(input_Email1.getText());
                    controller.setUsername(username);
                    controller.setPassword(password);
                    controller.setFullname(fullname);
                    controller.setTerm(term);
            
                    pane2.getChildren().setAll(verifyView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
                System.out.println(verificationCode);
            }
		} catch (Exception e) {
			e.printStackTrace();
			
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("email khong hop le");
            alert.show();
		}
    }

    @FXML
    void inputEmail1(ActionEvent event) {

    }

    public void setEmail(String  email) {
        input_Email1.setText(email);
    }
	public void setUsername(String username) {
	    this.username = username;
	}
	public void setPassword(String password) {
	    this.password = password;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setTerm(Boolean term) {
		this.term = term;
	}
	
	//hàm random nè mấy bác
	public String generateVerificationCode() {
	    Random random = new Random();
	    return String.format("%04d", random.nextInt(10000)); // tạo mã 4 chữ số
	}
	//các hàm nhận về
	public void setinputEmail(String email) {
		input_Email1.setText(email);
	}
	public void setInputFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setInputPassword(String password) {
		this.password = password;
	}
	public void setInputUsername(String username) {
		this.username = username;
	}
	public void setInputTerm(Boolean term) {
		this.term = term;
	}

	
}
