package View;

import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane{
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
    //              if (!isHetBordVol())
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

    public String getValue() {
        return text.getText();
    }

}
