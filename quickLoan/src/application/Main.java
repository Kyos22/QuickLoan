package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/login_register/background.fxml"));
			Scene scene = new Scene(root,700,487);
			stage.setResizable(false);
			
//			Parent root = FXMLLoader.load(getClass().getResource("/application/admin/adminBackGround.fxml"));
//			Scene scene = new Scene(root,944,641);
//			stage.setResizable(false);
			
//			Parent root = FXMLLoader.load(getClass().getResource("/application/requestLoanHome/requestLoanHomePage.fxml"));
//			Scene scene = new Scene(root,923,435);
//			stage.setResizable(false);
			
//			Parent root = FXMLLoader.load(getClass().getResource("/application/dashboard/topUpPage.fxml"));
//			Scene scene = new Scene(root,923,435);
//			stage.setResizable(false);
			
//			Parent root = FXMLLoader.load(getClass().getResource("/application/userVerify/catureFrontPage2.fxml"));
//			Scene scene = new Scene(root,508,588);
//			stage.setResizable(false);

//			Parent root = FXMLLoader.load(getClass().getResource("/application/dashboard/dashboardPage.fxml"));
//			Scene scene = new Scene(root,1271,850);
//			stage.setResizable(false);
			
//			Parent root = FXMLLoader.load(getClass().getResource("/application/categoryLoan/categoryLoanPage.fxml"));
//			Scene scene = new Scene(root,700,487);
//			stage.setResizable(false);

//			Parent root = FXMLLoader.load(getClass().getResource("/application/dashboard/calculatorLoanPage.fxml"));
//			Scene scene = new Scene(root,901,314);
			
//			Parent root = FXMLLoader.load(getClass().getResource("/application/userVerify/userVerifyPage.fxml"));
//			Scene scene = new Scene(root,700,487);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
