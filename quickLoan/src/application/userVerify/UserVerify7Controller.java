package application.userVerify;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.entities.Account;
import application.entities.UserSession;
import application.model.AccountModel;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class UserVerify7Controller implements Initializable {

    @FXML
    private Button button_close;

    @FXML
    private Button button_continue;

    @FXML
    private Button button_up1;

    @FXML
    private Button button_up2;

    @FXML
    private ImageView imageViewClose;

    @FXML
    private ImageView imageViewUpload;

    @FXML
    private ImageView imageView_Circle1;

    @FXML
    private ImageView imageView_Circle2;

    @FXML
    private ImageView myImageView1;

    @FXML
    private ImageView myImageView2;
    
    private int idIdentity;
    private int phoneNumber;
    private LocalDate dateofbirth;
    private String country;
    private String fulladdress;
    private String city;
    
    private File  selectedImageFile1;
    private File  selectedImageFile2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
   		imageViewClose.setImage(imageClose);
   		
   		Image imageUp1 = new Image(getClass().getResourceAsStream("/application/userVerify/image/cloud-upload-solid-108.png"));
   		imageViewUpload.setImage(imageUp1);
   		  		
   		Image image1 = new Image(getClass().getResourceAsStream("/application/userVerify/image/circle_1.png"));
   		imageView_Circle1.setImage(image1);
   		
   		Image image2 = new Image(getClass().getResourceAsStream("/application/userVerify/image/circle_2.png"));
   		imageView_Circle2.setImage(image2);
		
	}

    
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
   
   

    @FXML
    void continue_page7(ActionEvent event) {
        Account account = new Account();
        // Set all the values from the form to the account object
        int id = UserSession.getInstance().getUserId();
        
        account.setId(id); // assuming this is the account ID
        account.setIdentityNumber(this.idIdentity);
        account.setPhoneNumber(this.phoneNumber);
        account.setCountry(this.country);
        account.setCity(this.city);
        account.setAddress(this.fulladdress);
        account.setDateOfBirth(this.dateofbirth);
        
        if (selectedImageFile1 != null) {
	        try {
	            byte[] imageBytes = Files.readAllBytes(selectedImageFile1.toPath());
	            account.setPhoto_before(imageBytes);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
        if (selectedImageFile2 != null) {
        	try {
        		byte[] imageBytes = Files.readAllBytes(selectedImageFile2.toPath());
        		account.setPhoto_after(imageBytes);;
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }

        AccountModel accountModel = new AccountModel();
        boolean result = accountModel.updateAccountInfo(account);
        
        
        if (result) {
        	 try {
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyComplete.fxml"));
                 Parent root = loader.load();
            
                 
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
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("ERROR");
        	alert.setHeaderText(null);
        	alert.setContentText("Upload document failed");
        	alert.showAndWait();
        }
    }

    
    
  


    @FXML
    void upload1(ActionEvent event) {
    	uploadAndDisplayImage1();
    }

    @FXML
    void upload2(ActionEvent event) {
    	uploadAndDisplayImage2();
    }
    @FXML
    public void uploadAndDisplayImage1() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null); // null nếu bạn không có tham chiếu Stage tại thời điểm này
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            myImageView1.setImage(image);
            selectedImageFile1 = file;
        }
    }
    public void uploadAndDisplayImage2() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Image File");
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
    			);
    	File file = fileChooser.showOpenDialog(null); // null nếu bạn không có tham chiếu Stage tại thời điểm này
    	if (file != null) {
    		Image image = new Image(file.toURI().toString());
    		
    		myImageView2.setImage(image);
    		 selectedImageFile2 = file;
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
