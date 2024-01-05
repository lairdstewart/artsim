package laird.artsim.HolmbergLights;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laird.artsim.Animator;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // create initial state
        List<Bulb> lightBulbs = generateStartingState(100);

        // simulate sequence of nodes
        Simulator simulator = new Simulator(0.1, 1000, 5);
        List<Node> nodes = simulator.runSimulation(lightBulbs);

        // animate nodes
        Animator animator = new Animator(nodes);
        animator.setFramesPerSecond(120);
        animator.play();

        // begin application
        primaryStage.setScene(new Scene(animator.getPane(), 500, 500));
        primaryStage.show();
    }

    public static List<Bulb> generateStartingState(int numBulbs) {
        List<Bulb> result = new ArrayList<>();
        for (int i = 0; i < numBulbs; i++) {
            double xPos = Math.random() * 500;
            double yPos = Math.random() * 500;
            result.add(new Bulb(new Point2D.Double(xPos, yPos), new Point2D.Double(0, 0), 1, 5));
        }
        return result;
    }
}
