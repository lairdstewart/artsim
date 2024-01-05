package laird.artsim.cellularautomaton;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import laird.artsim.Animator;

import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int width = 100;
        int numSteps = 100;

        int[] initialArray = new int[width];
        initialArray[10] = 1;
        initialArray[width/2] = 1;
        initialArray[width/2 + 1] = 1;
        initialArray[width/2 - 1] = 1;
        State initialState = new State(initialArray);

        ArrayList<Node> nodes = Simulator.runSimulation(initialState, numSteps, 26);

        Animator animator = new Animator(nodes);
        animator.setFramesPerSecond(10);
        animator.play();

        primaryStage.setScene(new Scene(animator.getPane(), 600, 1000));
        primaryStage.show();
    }
}
