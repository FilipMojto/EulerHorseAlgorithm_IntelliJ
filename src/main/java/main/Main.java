package main;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import environment.ChessBoardSquare;
import environment.EulerKnightAlgorithm;

/**
 * Hlavna trieda, v ktorej mozno testovat algoritmus.
 * @author Filip Mojto
 *
 */
public class Main extends Application {
    /**
     * Vytvori GUI reprezentaciu sachovnice potom, co algoritmus najde riesenie. Finalne postupnosti krokov su zobrazene na jednotlivych polickach.
     * @param chessBoardSize - Velkost danej sachovnice
     * @param chessBoardSquare - Policko, na ktorom kon skoncil. Predstavuje hlavu spajaneho zoznamu, ktory je postupne tak, ako su jednotlive
     * elementy pospajane, zobrazovany na sachovnicu.
     */
    private void makeGUIRepresentation(final int chessBoardSize, ChessBoardSquare chessBoardSquare){
        var stage = new Stage();
        var pane = new GridPane();
        pane.setMinSize(40 * chessBoardSize, 40 * chessBoardSize);
        pane.setVgap(0);
        pane.setHgap(0);
        pane.setStyle("-fx-background-color: brown");
        pane.setAlignment(Pos.CENTER);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke
        .DEFAULT_WIDTHS)));

        for(int c = 0; c < chessBoardSize; c++){
            for (int l = 0; l < chessBoardSize; l++){
                if((c + l) % 2 == 0){
                    pane.add(new Rectangle(40, 40, Color.WHITE), c, l);
                }
                else {
                    pane.add(new Rectangle(40, 40, Color.BLACK), c, l);
                }
            }
        }

        for (int i = (int) Math.pow(chessBoardSize, 2); i >= 1; i--) {
            var label = new Label(Integer.toString(i));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setTextFill(Color.RED);
            label.setFont(new Font(label.getFont().getName(), 15));
            label.setStyle("-fx-font-weight: bold");

            GridPane.setHalignment(label, HPos.CENTER);
            pane.add(label, chessBoardSquare.getDimensionX() - 1, chessBoardSize - chessBoardSquare.getDimensionY()
                    );

            chessBoardSquare = chessBoardSquare.getPreviousSquare();
        }

        var scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);

        stage.setTitle("Search results");
        stage.setResizable(false);
        stage.setWidth(330);
        stage.setHeight(330);
        stage.show();
        stage.getIcons().add(new Image("C:\\Users\\fmojt\\Desktop\\Admin\\programming\\JavaProgramming\\EulerHorseAlgorithm\\src\\main\\resources\\img\\knight.png"));
    }

    /**
     * Inicialna metoda, v ktorej mozno testovat algoritmus pre rozne rozmery sachovnice a suradnice startovacej pozicie.
     */
    @Override
    public void start(Stage stage){
        var eulerHorseAlgorithm = new EulerKnightAlgorithm(6);
        //Test 1
        if(eulerHorseAlgorithm.startTest(50000, 3, 3)){
            this.makeGUIRepresentation(eulerHorseAlgorithm.getChessBoardSize(), eulerHorseAlgorithm.getCurrentSquare());
        }

        System.out.println(eulerHorseAlgorithm.getTimeElapsed());
        //Test 2
        if(eulerHorseAlgorithm.startTest(50000, 3, 3)){
            this.makeGUIRepresentation(eulerHorseAlgorithm.getChessBoardSize(), eulerHorseAlgorithm.getCurrentSquare());
        }

        System.out.println(eulerHorseAlgorithm.getTimeElapsed());
    }

    public static void main(String[] args) {
        launch();
    }
}