package org.comp2211.group6.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Inet4Address;
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
     * Logical Runway Buttons
     */
    @FXML
    void addLogicalRunwayClicked(MouseEvent event) {
        logicalRunwayName.clear();
        displacedThreshold.clear();
        tora.clear();
        toda.clear();
        asda.clear();
        lda.clear();
        newLogical = true;
        saveLogicalRunway.setDisable(false);
        selectLogicalRunway.getSelectionModel().clearSelection();
        setEditableLogicalRunway(false);

    }

    @FXML
    void saveLogicalRunwayClicked(MouseEvent event) {
        try {
            saveLogicalRunway.setDisable(true);

            if (!newLogical) {

                if (logicalRunwayName.getText().length() != 3)
                    throw new Exception(
                                    "Invalid name for logical runway. Must be of the form <0-36><L/R/C>");

                airport.getRunwayFromName(currentRunway.getName()).getLogicalRunways()
                                .remove(currentLogicalRunway);

                StringBuilder sb = new StringBuilder();
                sb.append(logicalRunwayName.getText().charAt(0));
                sb.append(logicalRunwayName.getText().charAt(1));


                int heading = Integer.parseInt(sb.toString());
                double displacedThreshold1 = Double.parseDouble(displacedThreshold.getText());
                char position = logicalRunwayName.getText().charAt(2);

                RunwayParameters runwayParameters = new RunwayParameters();
                runwayParameters.setTORA(Double.parseDouble(tora.getText()));
                runwayParameters.setTODA(Double.parseDouble(toda.getText()));
                runwayParameters.setASDA(Double.parseDouble(asda.getText()));
                runwayParameters.setLDA(Double.parseDouble(lda.getText()));

                LogicalRunway logicalRunway = new LogicalRunway(heading, displacedThreshold1,
                                position, runwayParameters);
                airport.getRunwayFromName(currentRunway.getName()).addRunway(logicalRunway);
                isChanged = true;
                load();
            }

            if (newLogical) {
                if (logicalRunwayName.getText().length() != 3)
                    throw new Exception(
                                    "Invalid name for logical runway. Must be of the form <0-36><L/R/C>");

                StringBuilder sb = new StringBuilder();
                sb.append(logicalRunwayName.getText().charAt(0));
                sb.append(logicalRunwayName.getText().charAt(1));


                int heading = Integer.parseInt(sb.toString());
                double displacedThreshold1 = Double.parseDouble(displacedThreshold.getText());
                char position = logicalRunwayName.getText().charAt(2);

                RunwayParameters runwayParameters = new RunwayParameters();
                runwayParameters.setTORA(Double.parseDouble(tora.getText()));
                runwayParameters.setTODA(Double.parseDouble(toda.getText()));
                runwayParameters.setASDA(Double.parseDouble(asda.getText()));
                runwayParameters.setLDA(Double.parseDouble(lda.getText()));

                LogicalRunway logicalRunway = new LogicalRunway(heading, displacedThreshold1,
                                position, runwayParameters);
                airport.getRunwayFromName(currentRunway.getName()).addRunway(logicalRunway);
                isChanged = true;
                newLogical = false;
                load();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(),
                            ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    void deleteLogicalRunwayClicked(MouseEvent event) {
        if (currentLogicalRunway == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No logical runway selected",
                            ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Delete " + currentRunway.getIdentifier() + " ?", ButtonType.YES,
                            ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                airport.getRunway(currentRunway.getIdentifier()).getLogicalRunways()
                                .remove(currentLogicalRunway);
                isChanged = true;
                load();
            }
        }
    }

    /*
     * Runway Buttons
     */
    @FXML
    void addRunwayClicked(MouseEvent event) {
        System.out.println("DSDS");
        runwayName.clear();
        logicalRunwayName.clear();
        displacedThreshold.clear();
        tora.clear();
        toda.clear();
        asda.clear();
        lda.clear();
        newRunway = true;
        selectRunway.getSelectionModel().clearSelection();
        setEditableRunway(true);
        selectLogicalRunway.getSelectionModel().clearSelection();
        setEditableLogicalRunway(false);
        saveRunway.setDisable(false);
    }

    @FXML
    void saveRunwayClicked(MouseEvent event) {
        setEditableRunway(false);
        try {
            if (!newRunway) {

                airport.getRunways().remove(currentRunway);
                if (runwayName.getText().isEmpty()) {
                    throw new Exception("Please input a runway name");
                }

                currentRunway.setName(runwayName.getText());
                airport.getRunways().add(currentRunway);
                load();
            }

            if (newRunway) {
                if (runwayName.getText().isEmpty()) {
                    throw new Exception("Please input a runway name");
                }

                Runway runway = new Runway(runwayName.getText());
                airport.getRunways().add(runway);
                newRunway = false;
                load();
            }



        } catch (Exception e) {
            setEditableRunway(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(),
                            ButtonType.OK);
            alert.showAndWait();
        }

        save.setDisable(false);
        load();
    }


    @FXML
    void deleteRunwayClicked(MouseEvent event) {
        if (currentRunway == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No runway selected", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Delete " + currentRunway.getIdentifier() + " ?", ButtonType.YES,
                            ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                airport.getRunways().remove(currentRunway);
                isChanged = true;
                load();
            }
        }

    }


    /*
     * Other Parameters
     */
    public Airport airport;
    private boolean isChanged;

    private Runway currentRunway;

    private ArrayList<LogicalRunway> logicalRunways;
    private LogicalRunway currentLogicalRunway;
    private boolean newLogical;
    private boolean newRunway;
    public String newName;
    public boolean newAirport = false;

    /*
     * Method used to load an airport into the view
     */
    public void loadAirport(Airport airport) {
        try {
            this.airport = airport;
            isChanged = false;
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newAirport() {
        try {
            this.airport = new Airport("");
            isChanged = false;
            newAirport = true;
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        setEditableRunway(false);
        setEditableLogicalRunway(false);
        currentRunway = null;
        currentLogicalRunway = null;

        if(newName != null)
            airportName.setText(newName);
        else if (newAirport)
            airportName.clear();
        else
            airportName.setText(airport.getName());

        airportName.textProperty().addListener((e, oldVal, newVal) -> {
            newName = airportName.getText();
            save.setDisable(false);
            System.out.println(newName);
            isChanged = true;
        });
        runwayName.clear();

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
            saveRunway.setDisable(true);
            saveLogicalRunway.setDisable(true);
            setEditableRunway(true);

            newLogical = false;
            setRunway((String) newVal);
        });

        deleteRunway.setDisable(true);
        if (airport.getRunways().size() < 3)
            addRunway.setDisable(false);

        if (isChanged) {
            save.setDisable(false);
        }

        saveRunway.setDisable(true);
        saveLogicalRunway.setDisable(true);

    }

    /*
     * Method used when changing runways
     */
    public void setRunway(String runwayID) {
        deleteRunway.setDisable(true);

        if (airport.getRunwayFromName(runwayID) != null) {
            currentRunway = airport.getRunwayFromName(runwayID);
            runwayName.setText(currentRunway.getName());
            runwayName.textProperty().addListener((e, oldVal, newVal) -> {
                saveRunway.setDisable(false);
            });


            if (currentRunway.getLogicalRunways().size() < 3)
                addLogicalRunway.setDisable(false);

            deleteRunway.setDisable(false);

            ObservableList logicalRunwayObservable = FXCollections.observableArrayList();

            logicalRunwayObservable.addAll(currentRunway.getLogicalRunwayNames());
            selectLogicalRunway.setItems(logicalRunwayObservable);
            selectLogicalRunway.valueProperty().addListener((e, oldVal, newVal) -> {
                deleteLogicalRunway.setDisable(false);
                saveLogicalRunway.setDisable(true);
                saveRunway.setDisable(true);
                setEditableLogicalRunway(true);
                newLogical = false;
                setLogicalRunway((String) newVal);
            });

            if (currentRunway.getLogicalRunways().isEmpty()) {
                logicalRunwayName.clear();
                displacedThreshold.clear();
                tora.clear();
                toda.clear();
                asda.clear();
                lda.clear();
            }
        }
    }

    public void setLogicalRunway(String logicalRunwayID) {
        currentLogicalRunway = currentRunway.getLogicalRunwayFromName(logicalRunwayID);
        logicalRunwayName.setText(currentLogicalRunway.getIdentifier());
        displacedThreshold.setText(currentLogicalRunway.getDisplacedThreshold() + "");
        tora.setText(currentLogicalRunway.getParameters().getTORA() + "");
        toda.setText(currentLogicalRunway.getParameters().getTODA() + "");
        asda.setText(currentLogicalRunway.getParameters().getASDA() + "");
        lda.setText(currentLogicalRunway.getParameters().getLDA() + "");
        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            saveLogicalRunway.setDisable(false);
        });

        logicalRunwayName.textProperty().addListener(listener);
        displacedThreshold.textProperty().addListener(listener);
        tora.textProperty().addListener(listener);
        toda.textProperty().addListener(listener);
        asda.textProperty().addListener(listener);
        lda.textProperty().addListener(listener);
    }

    private void setEditableRunway(boolean editable){
        runwayName.setEditable(editable);
    }

    private void setEditableLogicalRunway(boolean editable){
        logicalRunwayName.setEditable(editable);
        displacedThreshold.setEditable(editable);
        tora.setEditable(editable);
        toda.setEditable(editable);
        asda.setEditable(editable);
        lda.setEditable(editable);
    }

    public AirportConfigView() {
        loadFxml(getClass().getResource("/airport_config.fxml"), this);
    }

    public Airport getAirport() {
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

        setEditableRunway(false);
        setEditableLogicalRunway(false);
    }
    public void reset(){
        airport = null;
        isChanged = false;
        newAirport = false;
        logicalRunways = null;
        currentLogicalRunway = null;
        newLogical = false;
        newRunway = false;
        newName = null;
        newAirport = false;

        save.setDisable(true);

        saveRunway.setDisable(true);
        addRunway.setDisable(true);
        deleteRunway.setDisable(true);

        saveLogicalRunway.setDisable(true);
        addLogicalRunway.setDisable(true);
        deleteLogicalRunway.setDisable(true);

        setEditableRunway(false);
        setEditableLogicalRunway(false);
        load();
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
