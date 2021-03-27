import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Gui extends Application {


    private Parent createGameWindow() {
        Pane root = new Pane();
        root.setPrefSize(600,600);
        Tile[] board = new Gui.Tile[9];
        int temp = 0;

        // loop door rows
        for (int y = 0; y < 3; y++) {
            // loop door columns
            for (int x = 0; x < 3; x++) {
                Gui.Tile tile = new Gui.Tile();
                tile.setTranslateX(x * 200);
                tile.setTranslateY(y * 200);

                // voeg tiles toe aan root
                root.getChildren().add(tile);

                board[temp] = tile;
                temp++;
            }
        }

        return root;
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            boolean playable = true;
            boolean player1 = true;
            boolean player2 = true;
            setOnMouseClicked(event -> {
//              if (!playable)
//                    return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!player1)
                        return;
                    drawX();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (!player2)
                        return;
                    drawO();
                }
            });
        }
        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(createGameWindow()));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
