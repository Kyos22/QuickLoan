package application.admin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import application.entities.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccountPendingDetailController {

    @FXML
    private ImageView imageView_IDAfter;

    @FXML
    private ImageView imageView_IDBefore;

    @FXML
    private TextField inputCity;

    @FXML
    private TextField input_Address;

    @FXML
    private TextField input_Country;

    @FXML
    private TextField input_DateOfBirth;

    @FXML
    private TextField input_email;

    @FXML
    private TextField input_fullname;

    @FXML
    private TextField input_idcard;

    @FXML
    private TextField input_phoneNumber;

    @FXML
    private TextField input_username;

    @FXML
    private AnchorPane paneCenter;

    @FXML
    private Button button_close;

    
    public void setData(Account account) {
        input_username.setText(account.getUsername());
        input_email.setText(account.getEmail());
        input_fullname.setText(account.getFullname());
        
        input_Address.setText(account.getAddress());
        input_Country.setText(account.getCountry());
        inputCity.setText(account.getCity());
        input_idcard.setText(String.valueOf(account.getIdentityNumber()));
        
        LocalDate dateOfBirth = account.getDateOfBirth();
        if (dateOfBirth != null) {
            input_DateOfBirth.setText(dateOfBirth.toString());
        } else {
            input_DateOfBirth.setText("N/A"); // Hoặc một giá trị mặc định nào đó
        }
        input_phoneNumber.setText(String.valueOf(account.getPhoneNumber()));
        imageView_IDBefore.setImage(convertToImage(account.getPhoto_before()));
        imageView_IDAfter.setImage(convertToImage(account.getPhoto_after()));
    }
    private Image convertToImage(byte[] imageData) {
        if (imageData == null || imageData.length == 0) return null;

        InputStream is = new ByteArrayInputStream(imageData);
        return new Image(is);
    }
    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
}
