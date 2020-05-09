package org.comp2211.group6.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.comp2211.group6.Model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.comp2211.group6.XMLHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AsyncBoxView;

public class AirportConfigView extends GridPane implements Initializable {

    @FXML
    private GridPane baseGrid;

    @FXML
    private Text airportConfigText;

    @FXML
    private ButtonBar optionsBar;

    @FXML
    private Button save;

    @FXML
    private Button export;

    @FXML
    private GridPane nameGrid;

    @FXML
    private VBox nameVBox;

    @FXML
    private Text airportNameText;

    @FXML
    private TextField airportName;

    @FXML
    private TextField runwayName;

    @FXML
    private ChoiceBox<?> selectLogicalRunway;

    @FXML
    private ChoiceBox<?> selectRunway;

    @FXML
    private TextField tora;

    @FXML
    private TextField toda;

    @FXML
    private TextField asda;

    @FXML
    private TextField lda;

    @FXML
    private TextField logicalRunwayName;

    @FXML
    private TextField displacedThreshold;

    @FXML
    private Button addRunway;

    @FXML
    private Button saveRunway;

    @FXML
    private Button deleteRunway;

    @FXML
    private Button addLogicalRunway;

    @FXML
    private Button saveLogicalRunway;

    @FXML
    private Button deleteLogicalRunway;

    @FXML
    void exportClicked(MouseEvent event) {

    }

    @FXML
    void saveClicked(MouseEvent event) {

    }


    /*
    Logical Runway Buttons
     */
    @FXML
    void addLogicalRunwayClicked(MouseEvent event) {

    }

    @FXML
    void saveLogicalRunwayClicked(MouseEvent event) {

    }

    @FXML
    void deleteLogicalRunwayClicked(MouseEvent event) {

    }

    /*
    Runway Buttons
     */
    @FXML
    void addRunwayClicked(MouseEvent event) {

    }

    @FXML
    void saveRunwayClicked(MouseEvent event) {

    }

    @FXML
    void deleteRunwayClicked(MouseEvent event) {

    }


    /*
    Other Parameters
     */
    private Airport airport;

    private Runway currentRunway;

    private ArrayList<LogicalRunway> logicalRunways;
    private LogicalRunway currentLogicalRunway;

    /*
    Method used to load an airport into the view
     */
    public void loadAirport(Airport airport){
        this.airport = airport;
        airportName.setText(airport.getName());

        ObservableList runwayObservable = FXCollections.observableArrayList();
        runwayObservable.addAll(airport.getRunwayNames());
        selectRunway.setItems(runwayObservable);
        selectRunway.valueProperty().addListener((e, oldVal, newVal) -> {
            setRunway((String) newVal);
        });
        //selectRunway.getSelectionModel().selectFirst();
    }

    /*
    Method used when changing runways
     */
    public void setRunway(String runwayID){
        currentRunway = airport.getRunwayFromName(runwayID);
        runwayName.setText(currentRunway.getName());

        ObservableList logicalRunwayObservable = FXCollections.observableArrayList();

        logicalRunwayObservable.addAll(currentRunway.getLogicalRunwayNames());
        selectLogicalRunway.setItems(logicalRunwayObservable);
        selectLogicalRunway.valueProperty().addListener((e, oldVal, newVal) -> {
            setLogicalRunway((String) newVal);
        });
        //selectLogicalRunway.getSelectionModel().selectFirst();
        if (currentRunway.getLogicalRunways().isEmpty()){
            logicalRunwayName.setText("");
            displacedThreshold.setText("");
            tora.setText("");
            toda.setText("");
            toda.setText("");
            toda.setText("");
        }

    }

    public void setLogicalRunway(String logicalRunwayID){
        currentLogicalRunway = currentRunway.getLogicalRunwayFromName(logicalRunwayID);
        logicalRunwayName.setText(currentLogicalRunway.getIdentifier());
        displacedThreshold.setText(currentLogicalRunway.getDisplacedThreshold() + "");
        tora.setText(currentLogicalRunway.getParameters().getTORA() + "");
        toda.setText(currentLogicalRunway.getParameters().getTODA() + "");
        toda.setText(currentLogicalRunway.getParameters().getASDA() + "");
        toda.setText(currentLogicalRunway.getParameters().getLDA() + "");

    }



    public AirportConfigView() {
        loadFxml(getClass().getResource("/airport_config.fxml"), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        save.setDisable(true);
        saveRunway.setDisable(true);
        saveLogicalRunway.setDisable(true);
        deleteLogicalRunway.setDisable(true);

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
