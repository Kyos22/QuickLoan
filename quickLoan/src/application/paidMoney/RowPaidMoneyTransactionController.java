package application.paidMoney;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

import application.entities.Account;
import application.entities.PaymentScheduledItem;
import application.model.PaymentScheduled;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RowPaidMoneyTransactionController {

    @FXML
    private ImageView imageView_imageViewType;

    @FXML
    private Label label_StartDate;

    @FXML
    private Label label_amountRepayment;

    @FXML
    private Label label_repayment;

    @FXML
    private Label label_type;
    
    private PaymentScheduledItem payment;
    
    public void setPaymentData(PaymentScheduledItem payment) {
        this.payment = payment;
        
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedAmount = currencyFormat.format(payment.getMonthlyRepayment());
        label_amountRepayment.setText("-"+formattedAmount);
        
        if (payment.getPaymentDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            label_StartDate.setText(payment.getPaymentDate().format(formatter));
        } else {
            label_StartDate.setText("empty");
        }
        
        if (isHouseLoan(payment.getLoanId())) {
            Image houseImage = new Image(getClass().getResourceAsStream("/application/dashboard/images/houseIcon.png"));
            imageView_imageViewType.setImage(houseImage);
            label_type.setText("House Loan");
        }
        
       
        
    }
    public void setPaymentNumber(int paymentNumber) {
   	 label_repayment.setText(getOrdinal(paymentNumber));
   }
    public static String getOrdinal(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th payment";
        }
        switch (number % 10) {
            case 1:
                return number + "st payment";
            case 2:
                return number + "nd payment";
            case 3:
                return number + "rd payment";
            default:
                return number + "th payment";
        }
    }

    private boolean isHouseLoan(int loanId) {
        
        return false;
    }
}
