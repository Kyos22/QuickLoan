package application.dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.entities.TopUpHistory;
import application.entities.UserSession;
import application.model.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TopUpHistoryController implements Initializable {

    @FXML
    private HBox Layout1;

    @FXML
    private VBox TopUpLayout;

    @FXML
    private Label amount;

    @FXML
    private Label created;

    @FXML
    private Label currentBalance;

    @FXML
    private Label orignalBalance;

    @FXML
    private AnchorPane paneCenter;
    
    public void loadTopUpHistory(int accountId) {
        List<TopUpHistory> historyList = getTopUpHistoryData(accountId);

        TopUpLayout.getChildren().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        for (int i = 0; i < historyList.size(); i++) { // Bắt đầu từ i = 1 để bỏ qua dòng tiêu đề (index 0)
            TopUpHistory history = historyList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("rowTopUpHistoryPage.fxml"));
                Parent row = loader.load();
                RowTopUpHistoryController rowController = loader.getController();
                
                String formattedDate = history.getCreated().format(formatter);
                rowController.created.setText(formattedDate);
                
                rowController.amount.setText(String.format("%.2f", history.getAmount().doubleValue()));
                
                if (history.getOldBalance() != null) {
                    rowController.originalBalance.setText(String.format("%.2f", history.getOldBalance().doubleValue()));
                } else {
                    rowController.originalBalance.setText("N/A");
                }

                if (history.getNewBalance() != null) {
                    rowController.currentBalance.setText(String.format("%.2f", history.getNewBalance().doubleValue()));
                } else {
                    rowController.currentBalance.setText("N/A");
                }


               
                TopUpLayout.getChildren().add(row);
            } catch (IOException e) {
                e.printStackTrace();
                
            }
        }
    }
    

    public List<TopUpHistory> getTopUpHistoryData(int accountId) {
        List<TopUpHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM topup_history WHERE id = ? ORDER BY created DESC";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TopUpHistory history = new TopUpHistory();
                    history.setId(rs.getInt("id"));
                    history.setAmount(rs.getBigDecimal("amount"));
                    history.setCreated(rs.getDate("created").toLocalDate());
                    history.setOldBalance(rs.getBigDecimal("oldBalance"));
                    history.setNewBalance(rs.getBigDecimal("newBalance"));

                    historyList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return historyList;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int currentUserId = getCurrentUserId();
	    loadTopUpHistory(currentUserId);
	}
	
	private int getCurrentUserId() {
	    
	    return UserSession.getInstance().getUserId();
	}
}
