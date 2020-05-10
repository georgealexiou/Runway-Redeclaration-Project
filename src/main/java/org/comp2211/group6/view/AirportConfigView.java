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
    protected Button save;

    @FXML
    protected Button export;

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
        if(currentLogicalRunway == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No logical runway selected", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + currentRunway.getIdentifier() + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                airport.getRunway(currentRunway.getIdentifier()).getLogicalRunways().remove(currentLogicalRunway);
                isChanged = true;
                load();
            }
        }
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
        if(currentRunway == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No runway selected", ButtonType.OK);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " +currentRunway.getIdentifier() + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                airport.getRunways().remove(currentRunway);
                isChanged = true;
                load();
            }
        }

    }


    /*
    Other Parameters
     */
    public Airport airport;
    private boolean isChanged;

    private Runway currentRunway;

    private ArrayList<LogicalRunway> logicalRunways;
    private LogicalRunway currentLogicalRunway;

    /*
    Method used to load an airport into the view
     */
    public void loadAirport(Airport airport) {
        try {
            this.airport = airport;
            isChanged = false;
            load();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    Method used when changing runways
     */
    public void setRunway(String runwayID){
        deleteRunway.setDisable(true);

        if(airport.getRunwayFromName(runwayID) != null) {
            currentRunway = airport.getRunwayFromName(runwayID);
            runwayName.setText(currentRunway.getName());
            deleteRunway.setDisable(false);

            if (currentRunway.getLogicalRunways().size() < 3)
                addLogicalRunway.setDisable(false);

            ObservableList logicalRunwayObservable = FXCollections.observableArrayList();

            logicalRunwayObservable.addAll(currentRunway.getLogicalRunwayNames());
            selectLogicalRunway.setItems(logicalRunwayObservable);
            selectLogicalRunway.valueProperty().addListener((e, oldVal, newVal) -> {
                deleteLogicalRunway.setDisable(false);
                setLogicalRunway((String) newVal);
            });

            if (currentRunway.getLogicalRunways().isEmpty()){
                logicalRunwayName.clear();
                displacedThreshold.clear();
                tora.clear();
                toda.clear();
                asda.clear();
                lda.clear();
            }
        }




    }

    public void setLogicalRunway(String logicalRunwayID){
        currentLogicalRunway = currentRunway.getLogicalRunwayFromName(logicalRunwayID);
        logicalRunwayName.setText(currentLogicalRunway.getIdentifier());
        displacedThreshold.setText(currentLogicalRunway.getDisplacedThreshold() + "");
        tora.setText(currentLogicalRunway.getParameters().getTORA() + "");
        toda.setText(currentLogicalRunway.getParameters().getTODA() + "");
        asda.setText(currentLogicalRunway.getParameters().getASDA() + "");
        lda.setText(currentLogicalRunway.getParameters().getLDA() + "");

    }

    private void load(){
        currentRunway = null;
        currentLogicalRunway = null;

        airportName.setText(airport.getName());
        runwayName.setText("");

        ObservableList runwayObservable = FXCollections.observableArrayList();
        runwayObservable.addAll(airport.getRunwayNames());
        selectRunway.setItems(runwayObservable);

        selectLogicalRunway.getItems().clear();
        logicalRunwayName.clear();
        displacedThreshold.clear();
        tora.clear();
        toda.clear();
        asda.clear();
        lda.clear();

        selectRunway.valueProperty().addListener((e, oldVal, newVal) -> {
            setRunway((String) newVal);
        });

        deleteRunway.setDisable(true);
        if(airport.getRunways().size() < 3)
            addRunway.setDisable(false);

        if(isChanged){
            save.setDisable(false);
        }

    }

    public AirportConfigView() {
        loadFxml(getClass().getResource("/airport_config.fxml"), this);
    }

    public Airport getAirport(){
        return airport;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        save.setDisable(true);

        saveRunway.setDisable(true);
        addRunway.setDisable(true);
        deleteRunway.setDisable(true);

        saveLogicalRunway.setDisable(true);
        addLogicalRunway.setDisable(true);
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
