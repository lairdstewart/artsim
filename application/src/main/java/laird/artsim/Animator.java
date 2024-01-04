package laird.artsim;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class Animator {

    private final List<Node> nodes;
    Pane pane;
    private int currentIndex = 0;

    public Animator(List<Node> nodes) {
        this.nodes = nodes;
        this.pane = new Pane();
    }
    private double framesPerSecond = 1.0;
    private double secondsPerFrame = 1.0 / framesPerSecond;

    public void play() {
        KeyFrame keyframe = new KeyFrame(Duration.seconds(secondsPerFrame), e -> progress());

        Timeline timeline = new Timeline(keyframe);

        timeline.setCycleCount(Animation.INDEFINITE); // loop forever

        timeline.play();
    }

    public void progress(){
        currentIndex++;
        if (currentIndex == nodes.size())
        {
            currentIndex = 0;
        }

        this.pane.getChildren().clear();
        this.pane.getChildren().add(nodes.get(currentIndex));

    }

    public void setFramesPerSecond(double framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
        this.secondsPerFrame = 1.0 / framesPerSecond;
    }

    public Pane getPane() {
        return pane;
    }
}
