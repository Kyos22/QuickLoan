package application.categoryLoan;


import java.net.URL;
import java.util.ResourceBundle;

import application.entities.UserSession;
import application.model.AccountModel;
import application.userVerify.UserVerifyController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CategoryLoanController implements Initializable {

    @FXML
    private Button button_continue;

    @FXML
    private Button button_select_education;

    @FXML
    private Button button_select_home;

    @FXML
    private Button button_select_personal;

    @FXML
    private Button button_select_vehicle;
    
    
    
    @FXML
    private ImageView imageHome;

    @FXML
    private ImageView imagePersonal;

    @FXML
    private ImageView imageViewArrowRight;
    
    @FXML
    private ImageView imageViewClose;
    
    @FXML
    private ImageView imageViewEducation;

    @FXML
    private ImageView imageViewVehicle;
    
    @FXML
    private Button button_close;

    
    private String selectedLoanType;
    
    private Button selectedButton;
    
    private final String selectedStyle = "-fx-background-color: #5FCDBB; -fx-text-fill: #fff;-fx-border-radius: 15px;-fx-background-radius: 15px;-fx-font-weight: bold;";
    private final String defaultStyle = "-fx-background-color: transparent; -fx-text-fill: #ddddd; -fx-border-radius: 15px;-fx-border-width: 1.5px;-fx-border-color: #DADADA;";
    
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void continue_button(ActionEvent event) {
        
        if (selectedLoanType != null && !selectedLoanType.isEmpty()) {
//        	int userId = UserSession.getInstance().getUserId();
//        	
//        	AccountModel.updateFirstLoanType(userId, selectedLoanType);
        	
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userVerify/userVerifyPage.fxml"));
        		Parent root = loader.load();
        		UserVerifyController userVerifyController = loader.getController();

        	    // Gửi tham chiếu của cửa sổ hiện tại (categoryLoan) đến userVerifyController
        	    userVerifyController.setPreviousStage((Stage) button_continue.getScene().getWindow());

        		
        		
        		Scene scene = new Scene(root);
        		scene.setFill(Color.TRANSPARENT); // Thiết lập màu nền của scene thành trong suốt

        		Stage stage = new Stage();
        		stage.initStyle(StageStyle.TRANSPARENT); // Thiết lập style của stage thành trong suốt
        		stage.setScene(scene);
        		
        		stage.initModality(Modality.APPLICATION_MODAL);
        		
        		stage.setTitle("User Verify");
        		stage.show();
        		stage.setResizable(false);
        		
        		

        		
            	
            	
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        } else {
        	Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("Warning");
        	alert.setContentText("select the loan type");
        	alert.showAndWait();
        }
    }

    @FXML
    void education(ActionEvent event) {
    	updateSelectedButton(button_select_education);
    	selectedLoanType = "education";
    }

    @FXML
    void home(ActionEvent event) {
    	updateSelectedButton(button_select_home);
    	selectedLoanType = "home";
    }

    @FXML
    void personal(ActionEvent event) {
    	updateSelectedButton(button_select_personal);
    	selectedLoanType = "personal";
    }

    @FXML
    void vehicle(ActionEvent event) {
    	updateSelectedButton(button_select_vehicle);
    	selectedLoanType = "vehicle";
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    Image imageHome1 = new Image(getClass().getResourceAsStream("/application/categoryLoan/images/home.png"));
	    imageHome.setImage(imageHome1);
	    Image imagePersonal1 = new Image(getClass().getResourceAsStream("/application/categoryLoan/images/personal1.png"));
	    imagePersonal.setImage(imagePersonal1);
	    Image imageEducation1 = new Image(getClass().getResourceAsStream("/application/categoryLoan/images/education.png"));
	    imageViewEducation.setImage(imageEducation1);
	    Image imageVehicle = new Image(getClass().getResourceAsStream("/application/categoryLoan/images/vehicle.png"));
	    imageViewVehicle.setImage(imageVehicle);
	    
	    Image imageArrow1 = new Image(getClass().getResourceAsStream("/application/categoryLoan/images/right-arrow-alt-regular-48.png"));
	    imageViewArrowRight.setImage(imageArrow1);
	    
	    Image imageClose = new Image(getClass().getResourceAsStream("/application/categoryLoan/images/x-black.png"));
	    imageViewClose.setImage(imageClose);
	    

	    button_select_education.setStyle(defaultStyle);
        button_select_home.setStyle(defaultStyle);
        button_select_personal.setStyle(defaultStyle);
        button_select_vehicle.setStyle(defaultStyle);

		
	}
	private void updateSelectedButton(Button newSelectedButton) {
        
        if (selectedButton != null) {
            selectedButton.setStyle(defaultStyle);
        }

        selectedButton = newSelectedButton;
        if (selectedButton != null) {
            selectedButton.setStyle(selectedStyle);
        }
    }
}


