package application.userVerify;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CaptureFrontPage implements Initializable {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private VideoCapture capture;
    private boolean cameraActive = false;
    private boolean pictureTaken = false;

    @FXML
    private ImageView imageView;

    @FXML
    private Button button_start;

    @FXML
    private Button button_capture;

    @FXML
    private Button button_retake;

    @FXML
    private Button button_accept;

    @FXML
    private Button button_continue;

    @FXML
    private Button button_close;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.capture = new VideoCapture();
    }

    @FXML
    protected void startCamera(ActionEvent event) {
        if (!this.cameraActive) {
            // Start the video capture
            this.capture.open(0);

            if (this.capture.isOpened()) {
                this.cameraActive = true;

                // Grab a frame periodically
                Runnable frameGrabber = () -> {
                    Mat frame = grabFrame();
                    Image imageToShow = mat2Image(frame);
                    updateImageView(imageView, imageToShow);
                };

                Thread thread = new Thread(frameGrabber);
                thread.setDaemon(true);
                thread.start();

                // Update the button content
                button_start.setText("Stop Camera");
            } else {
                // Log the error
                System.err.println("Failed to open the camera connection...");
            }
        } else {
            // The camera is not active at this point
            this.cameraActive = false;
            button_start.setText("Start Camera");

            // Stop the camera
            if (this.capture.isOpened()) {
                this.capture.release();
            }
        }
    }

    @FXML
    protected void capture(ActionEvent event) {
        if (this.cameraActive) {
            // If the camera is active, capture the current frame
            pictureTaken = true;
            Mat frame = grabFrame();
            Image imageToShow = mat2Image(frame);
            updateImageView(imageView, imageToShow);

            // Stop the camera
            this.cameraActive = false;
            this.capture.release();
            button_start.setText("Start Camera");
        }
    }

    @FXML
    protected void retake(ActionEvent event) {
        if (!this.cameraActive) {
            // Start the camera
            startCamera(event);
        }
    }

    @FXML
    protected void accept(ActionEvent event) {
        if (pictureTaken) {
            // Save the picture
            Mat frame = grabFrame();
            Imgcodecs.imwrite("user_photo.jpg", frame);
            // Picture is accepted, you can add more logic here
            pictureTaken = false;
        }
    }

    @FXML
    protected void continue_page6(ActionEvent event) {
        // Here you would add the logic to continue to the next page or scene
    }

    @FXML
    protected void close(ActionEvent event) {
        Stage stage = (Stage) button_close.getScene().getWindow();
        stage.close();

        // Make sure to release the camera before closing the window
        if (this.capture.isOpened()) {
            this.capture.release();
        }
    }

    private Mat grabFrame() {
        Mat frame = new Mat();
        
        if (this.capture.isOpened()) {
            try {
                this.capture.read(frame);
            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        
        return frame;
    }

    private Image mat2Image(Mat frame) {
        try {
            MatOfByte buffer = new MatOfByte();
            Imgcodecs.imencode(".png", frame, buffer);
            return new Image(new ByteArrayInputStream(buffer.toArray()));
        } catch (Exception e) {
            System.err.println("Cannot convert the Mat object: " + e);
            return null;
        }
    }

    private void updateImageView(ImageView view, Image image) {
        Platform.runLater(() -> view.setImage(image));
    }
}
