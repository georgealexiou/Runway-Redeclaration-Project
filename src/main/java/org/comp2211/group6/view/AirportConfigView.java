package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.RunwayParameters;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class AirportConfigView extends GridPane implements Initializable {

    /*
     * FXML Stuff
     */
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane baseGrid;

    @FXML
    private Text airportConfigText;

    @FXML
    private GridPane nameGrid;

    @FXML
    private VBox nameVBox;

    @FXML
    private Text airportNameText;

    @FXML
    private TextField airportName;

    @FXML
    private ButtonBar optionsBar;

    @FXML
    private Button save;

    @FXML
    private Button export;

    @FXML
    private Button delete;

    @FXML
    private Accordion runwayAccordion;


    /*
     * Other Parameters
     */
    private Airport airport;


    public AirportConfigView() {
        loadFxml(getClass().getResource("/airport_config.fxml"), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         * 
         * setupBreakdownPicker(FXCollections.observableArrayList()); this.availableBreakdowns = new
         * HashMap<LogicalRunway, String>();
         * this.breakdownDetails.setStyle("-fx-font-family: 'monospaced';");
         * this.currentLogicalRunway = null; parameterColumn.setCellValueFactory( new
         * PropertyValueFactory<BreakdownComparison, String>("property"));
         * originalValueColumn.setCellValueFactory( new PropertyValueFactory<BreakdownComparison,
         * Double>("original")); recalculatedValueColumn.setCellValueFactory( new
         * PropertyValueFactory<BreakdownComparison, Double>("recalculated"));
         * valuesTable.getColumns().get(0).prefWidthProperty()
         * .bind(valuesTable.widthProperty().multiply(0.33));
         * valuesTable.getColumns().get(1).prefWidthProperty()
         * .bind(valuesTable.widthProperty().multiply(0.33));
         * valuesTable.getColumns().get(2).prefWidthProperty()
         * .bind(valuesTable.widthProperty().multiply(0.33)); valuesTable.setFixedCellSize(25);
         * this.getRowConstraints().get(1).prefHeightProperty()
         * .bind(Bindings.size(valuesTable.getItems())
         * .multiply(valuesTable.getFixedCellSize()).add(30));
         */

    }

    private static void loadFxml(URL fxmlFile, Object rootController) {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        loader.setController(rootController);
        loader.setRoot(rootController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
