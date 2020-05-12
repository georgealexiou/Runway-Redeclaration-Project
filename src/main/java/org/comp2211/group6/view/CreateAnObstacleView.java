package org.comp2211.group6.view;

public class CreateAnObstacleView extends ObstacleView {

    public CreateAnObstacleView() {
        super();
        loadFxml(getClass().getResource("/obstacle_view.fxml"), this);
        this.obstacleViewTitle.setText("Create an Obstacle");
    }

    public void clearFields() {
        this.obstacleDescription.clear();
        this.obstacleName.clear();
        this.obstacleDistanceFromCentreLine.clear();
        this.obstacleDistanceFromRight.clear();
        this.obstacleDistanceFromLeft.clear();
        this.obstacleHeight.clear();
    }
}
