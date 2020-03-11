package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class MainView extends GridPane implements Initializable {

    @FXML
    private TopDownView topDownView;

    public MainView() {
        super();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main_view.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Runway runway = new Runway("Heathrow");
        LogicalRunway runway1 = new LogicalRunway(9, 306, 'L',
                        new RunwayParameters(3902, 3902, 3902, 3595));
        LogicalRunway runway2 =
                        new LogicalRunway(27, 0, 'R', new RunwayParameters(3884, 3962, 3884, 3884));
        try {
            runway.addRunway(runway1);
            runway.addRunway(runway2);
            this.topDownView.setRunway(runway);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
