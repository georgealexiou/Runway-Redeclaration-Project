package org.comp2211.group6.view;

import java.net.URL;
import java.util.ResourceBundle;

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
     * Cannot export because the name, description, length, width, height cannot be changed when
     * editing an existing obstacle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableBindings = obstacleName.textProperty().isEmpty().or(obstacleLength.textProperty()
                        .isEmpty()
                        .or(obstacleWidth.textProperty().isEmpty().or(obstacleHeight.textProperty()
                                        .isEmpty()
                                        .or(obstacleDistanceFromCentreLine.textProperty().isEmpty()
                                                        .or(obstacleDistanceFromLeft.textProperty()
                                                                        .isEmpty()
                                                                        .or(obstacleDistanceFromRight
                                                                                        .textProperty()
                                                                                        .isEmpty()))))));
        obstacleSaveButton.disableProperty().bind(disableBindings);

        currentObstacle.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                this.obstacleName.setText(newVal.getName());
                this.obstacleDescription.setText(newVal.getDescription());
                this.obstacleLength.setText(String.valueOf(newVal.getLength()));
                this.obstacleWidth.setText(String.valueOf(newVal.getWidth()));
                this.obstacleHeight.setText(String.valueOf(newVal.getHeight()));
                this.obstacleDistanceFromCentreLine
                                .setText(String.valueOf(newVal.getDistanceToCentreLine()));
                this.obstacleDistanceFromLeft.setText(String.valueOf(newVal.getDistanceFromLeft()));
                this.obstacleDistanceFromRight
                                .setText(String.valueOf(newVal.getDistanceFromRight()));
            }


        });
    }
}
