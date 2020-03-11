package org.comp2211.group6.view;

import java.net.URL;
import java.util.ResourceBundle;

public class SideOnView extends RunwayView {

    public SideOnView() {
        super();
        loadFxml(getClass().getResource("/runway_view.fxml"), this);
        this.viewTitle.setText("Side on View");
    }

    protected void redraw() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
