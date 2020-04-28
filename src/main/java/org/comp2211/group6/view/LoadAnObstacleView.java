package org.comp2211.group6.view;

import org.comp2211.group6.Model.Obstacle;

public class LoadAnObstacleView extends ObstacleView {

    public LoadAnObstacleView() {
        super();
        loadFxml(getClass().getResource("/obstacle_view.fxml"), this);
        this.obstacleViewTitle.setText("Load an Obstacle");
    }

    public void loadPredefinedObstacle(Obstacle obstacle) {
        this.obstacleName.setText(obstacle.getName());
        this.obstacleDescription.setText(obstacle.getDescription());
        this.obstacleLength.setText(String.valueOf(obstacle.getLength()));
        this.obstacleWidth.setText(String.valueOf(obstacle.getWidth()));
        this.obstacleHeight.setText(String.valueOf(obstacle.getHeight()));
        this.obstacleDistanceFromCentreLine.setText(String.valueOf(obstacle.distanceToCentreLine));
        this.obstacleDistanceFromLeft.setText(String.valueOf(obstacle.distanceFromLeftThreshold));
        this.obstacleDistanceFromRight.setText(String.valueOf(obstacle.distanceFromRightThreshold));
    }
}
