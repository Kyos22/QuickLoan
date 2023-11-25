package application.paymentScheduled;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.entities.PaymentScheduledItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RowScheduledBackgroundController {

    @FXML
    private Label label_date;

    @FXML
    private Label label_original;

    @FXML
    private Label label_status;

    @FXML
    private Label label_total;

    public void updateData(PaymentScheduledItem payment) {
    	 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         DecimalFormat decimalFormat = new DecimalFormat("#,##0.0"); 

         label_date.setText(payment.getPaymentDate().format(dateFormatter));
         label_original.setText(decimalFormat.format(payment.getRemainingPrincipal()));
         label_total.setText(decimalFormat.format(payment.getMonthlyRepayment()));
         
         updateStatusLabel(payment.getPaymentDate());
    }
    
    
    
    
    
    
    private void updateStatusLabel(LocalDate paymentDate) {
        LocalDate currentDate = LocalDate.now();

        
        if (paymentDate.getMonth() == currentDate.getMonth() && paymentDate.getYear() == currentDate.getYear()) {
            label_status.setText("Open");
            label_status.setStyle("-fx-text-fill:green;");
        } else {
            label_status.setText("Close");
            label_status.setStyle("-fx-text-fill: grey;");
        }
    }
    
    
//    private void updateStatusLabel(LocalDate paymentDate) {
//        // LocalDate currentDate = LocalDate.now(); // Replace this line
//        LocalDate testDate = LocalDate.of(2023, 12, 19); // Use a fixed test date												
//
//        if (paymentDate.getMonth() == testDate.getMonth() && paymentDate.getYear() == testDate.getYear()) {
//            label_status.setText("Open");
//            label_status.setStyle("-fx-text-fill:green;");
//        } else {
//            label_status.setText("Close");
//            label_status.setStyle("-fx-text-fill: grey;");
//        }
//    }

    
}
