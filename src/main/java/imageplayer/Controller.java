package imageplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ImageView imageView;

    @FXML
    public ProgressBar progressBar;

    @FXML
    public Label statusLabel;

    @FXML
    public Button startButton;

    @FXML
    public Button loadButton;

    private ImageDiscoverer imageDiscoverer;
    private Thread discoverThread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusLabel.setText("application started ...");
    }

    @FXML
    public void onStartPressed(ActionEvent actionEvent) {
        statusLabel.setText("on start button pressed ...");
        // imageDiscoverer.discover();
        discoverThread = new Thread(imageDiscoverer);
        progressBar.setProgress(0);
        discoverThread.start();
    }

    @FXML
    public void onPausePressed(ActionEvent actionEvent) {
        // insert your code here
    }

    @FXML
    public void onLoadButtonPressed(ActionEvent actionEvent) {
        statusLabel.setText("on load button pressed ...");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("selectImage");
        fileChooser.setInitialDirectory(new File("."));
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("Image Files",
                        "*.jpg", "*.png", "*.JPG", "*.PNG", "*.JPEG", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File imageFile = fileChooser.showOpenDialog(null);

        if (imageFile != null) {
            statusLabel.setText(imageFile.getName());
            Image image = new Image(String.valueOf(imageFile.toURI()));
            setImage(image);
        }
    }

    private void setImage(Image image) {
        imageDiscoverer = new ImageDiscoverer(image, progressBar);
        imageView.setImage(imageDiscoverer.getDestinationImage());

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        imageView.setFitWidth(Math.min(width, 1000));
        imageView.setFitHeight(Math.min(height, 600));

        Scene scene = imageView.getScene();
        BorderPane borderPane = (BorderPane) imageView.getParent();
        borderPane.setPrefSize(Math.min(width, 1000), Math.min(height, 600));
        scene.getWindow().sizeToScene();
    }
}