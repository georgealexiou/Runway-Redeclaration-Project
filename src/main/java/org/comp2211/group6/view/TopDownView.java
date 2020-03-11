package org.comp2211.group6.view;

import java.net.URL;
import java.util.ResourceBundle;

public class TopDownView extends RunwayView {

    public TopDownView() {
        super();
        loadFxml(getClass().getResource("/runway_view.fxml"), this);
    }

    @Override
    protected void redrawRunway() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
