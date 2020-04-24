package org.comp2211.group6.view;

import java.net.URL;
import java.util.ResourceBundle;
import org.comp2211.group6.Model.Obstacle;

public class EditAnObstacleView extends ObstacleView {
    
    public EditAnObstacleView() {
        super();
        loadFxml(getClass().getResource("/obstacle_view.fxml"), this);
        this.obstacleViewTitle.setText("Edit an Obstacle");
        this.obstacleName.setEditable(false);
        this.obstacleDescription.setEditable(false);
        this.obstacleLength.setEditable(false);
        this.obstacleWidth.setEditable(false);
        this.obstacleHeight.setEditable(false);
    }
    
    /*
     * Cannot export because the name, description, length, width, height cannot be changed when editing an existing obstacle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableBindings = 
                obstacleName.textProperty().isEmpty().or(
                        obstacleLength.textProperty().isEmpty().or(
                                obstacleWidth.textProperty().isEmpty().or(
                                        obstacleHeight.textProperty().isEmpty().or(
                                                obstacleDistanceFromCentreLine.textProperty().isEmpty().or(
                                                        obstacleDistanceFromLeft.textProperty().isEmpty().or(
                                                                obstacleDistanceFromRight.textProperty().isEmpty()))))));
        obstacleSaveButton.disableProperty().bind(disableBindings);
    }
    
    /*
     * Fetch data of the current obstacle(selected item of Obstacle ComboBox) and fill in TextFields
     */
    public void loadCurrentObstacle(Obstacle currentObstacle) {
        this.obstacleName.setText(currentObstacle.getName());
        this.obstacleDescription.setText(currentObstacle.getDescription());
        this.obstacleLength.setText(String.valueOf(currentObstacle.getLength()));
        this.obstacleWidth.setText(String.valueOf(currentObstacle.getWidth()));
        this.obstacleHeight.setText(String.valueOf(currentObstacle.getHeight()));
        this.obstacleDistanceFromCentreLine.setText(String.valueOf(currentObstacle.getDistanceToCentreLine()));
        this.obstacleDistanceFromLeft.setText(String.valueOf(currentObstacle.getDistanceFromLeft())); 
        this.obstacleDistanceFromRight.setText(String.valueOf(currentObstacle.getDistanceFromRight()));
    }
}
