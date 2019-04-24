import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private void startNewGame(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/fxml/game.fxml"));
            Parent gameRoot = loader.load() ;
            GameController gameController = loader.getController();
            String gameMode = ((Button) event.getSource()).getId();

            if (gameMode.equals("twoPlayerGame")) {
                gameController.twoPlayerGame();
            } else {
                gameController.computerPlayerGame();
            }

            Scene gameScene = new Scene(gameRoot, 900, 600);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(gameScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
