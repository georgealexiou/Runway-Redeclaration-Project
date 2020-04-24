package org.comp2211.group6.view;

public class LoadAnObstacleView extends ObstacleView {

    public LoadAnObstacleView() {
        super();
        loadFxml(getClass().getResource("/obstacle_view.fxml"), this);
        this.obstacleViewTitle.setText("Load an Obstacle");
    }
    
    public void loadPredefinedObstacle(String name, String description, double length, double width, double height) {
        this.obstacleName.setText(name);
        this.obstacleDescription.setText(description);
        this.obstacleLength.setText(String.valueOf(length));
        this.obstacleWidth.setText(String.valueOf(width));
        this.obstacleHeight.setText(String.valueOf(height));
    }
}
