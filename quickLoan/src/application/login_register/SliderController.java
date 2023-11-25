package application.login_register;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SliderController implements Initializable {
	
	 @FXML
	 private ImageView imageView;

	 @FXML
	    private Circle dot1;

	    @FXML
	    private Circle dot2;

	    @FXML
	    private Circle dot3;

	    @FXML
	    private HBox dotsContainer;

	    private List<Circle> dotList = new ArrayList<>();

	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        // Thêm các dot vào danh sách
	    	dotList.addAll(Arrays.asList(dot1, dot2, dot3));

	        // Hiển thị hình ảnh đầu tiên
	        imageView.setImage(images.get(imageIndex));

	        // Đặt màu cho các dot ban đầu
	        updateDotsColor();

	        Timeline timeline = new Timeline(
	            new KeyFrame(
	                Duration.seconds(4), // Thời gian giữa mỗi hình ảnh
	                event -> {
	                    imageIndex = (imageIndex + 1) % images.size();
	                    imageView.setImage(images.get(imageIndex));
	                    updateDotsColor(); // Cập nhật màu cho các dot mỗi khi hình ảnh thay đổi
	                }
	            )
	        );
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.play();
	    }

	    private void updateDotsColor() {
	        for (int i = 0; i < dotList.size(); i++) {
	            Circle dot = dotList.get(i);

	            if (i == imageIndex) {
	                dot.setFill(Color.WHITE); // Màu cho dot của hình ảnh hiện tại
	            } else {
	                dot.setFill(Color.GRAY); // Màu cho các dot khác
	            }
	        }
	    }

	
	    


    
    private int imageIndex = 0;
    private List<Image> images = Arrays.asList(
        new Image("/application/login_register/libs/picture1.png"),
        new Image("/application/login_register/libs/picture2.png"),
        new Image("/application/login_register/libs/picture3.png")
        
    );

    
}
    

