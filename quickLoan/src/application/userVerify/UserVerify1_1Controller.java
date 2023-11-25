package application.userVerify;

import application.entities.Country;
import application.model.CountryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserVerify1_1Controller implements Initializable {


    @FXML
    private Button button_close;

	@FXML
	private ImageView imageViewClose;
    
    @FXML
    private ListView<Country> myListView;
    
    private UserVerify1Controller userVerify1Controller;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        CountryModel countryModel = new CountryModel();
        List<Country> countries = countryModel.getCountries();

        myListView.getItems().addAll(countries);
        myListView.setCellFactory(listView -> new CountryListCell());
        
        Image imageClose = new Image(getClass().getResourceAsStream("/application/userVerify/image/x-regular-48.png"));
		imageViewClose.setImage(imageClose);
		
    }
    
    public void setUserVerify1Controller(UserVerify1Controller controller) {
        this.userVerify1Controller = controller;
    }
    @FXML
    private void handleCountrySelection(MouseEvent event) {
        if (event.getClickCount() == 2) { // Bạn có thể chọn chỉ định số lượng click
        	System.out.println("nhan row");
            Country selectedCountry = myListView.getSelectionModel().getSelectedItem();
            if (selectedCountry != null && userVerify1Controller != null) {
                userVerify1Controller.setSelectedCountry(selectedCountry.getName_country());
                close(null); 
            }
        }
    }

    static class CountryListCell extends ListCell<Country> {
    	@Override
        protected void updateItem(Country country, boolean empty) {
            super.updateItem(country, empty);
            if (country == null || empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(country.getName_country());
                setStyle("-fx-font-size: 14px;-fx-bacground-color: #1E2329"); // bạn có thể thêm nhiều styles khác tại đây
                
                
            }
        }
    }
    
    @FXML
    void close(ActionEvent event) {
    	Stage currentStage = (Stage) button_close.getScene().getWindow();
    	currentStage.close();
    }
}
