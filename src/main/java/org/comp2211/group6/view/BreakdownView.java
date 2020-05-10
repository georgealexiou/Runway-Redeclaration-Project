
package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.comp2211.group6.Controller.Calculator;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class BreakdownView extends GridPane implements Initializable {

    /*
     * View Components
     */
    @FXML
    private ComboBox<LogicalRunway> breakdownPicker;
    @FXML
    private Label viewTitle;

    @FXML
    private TableView<BreakdownComparison> valuesTable;
    @FXML
    private TableColumn<BreakdownComparison, String> parameterColumn;
    @FXML
    private TableColumn<BreakdownComparison, Double> originalValueColumn;
    @FXML
    private TableColumn<BreakdownComparison, Double> recalculatedValueColumn;

    @FXML
    private TextArea breakdownDetails;


    private Map<LogicalRunway, String> availableBreakdowns;
    public SimpleObjectProperty<Runway> currentRunway = new SimpleObjectProperty<Runway>();
    public SimpleObjectProperty<LogicalRunway> currentLogicalRunway =
                    new SimpleObjectProperty<LogicalRunway>();
    public SimpleObjectProperty<Calculator> calculator = new SimpleObjectProperty<Calculator>();

    public BreakdownView() {
        loadFxml(getClass().getResource("/breakdown_view.fxml"), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupBreakdownPicker(FXCollections.observableArrayList());
        this.availableBreakdowns = new HashMap<LogicalRunway, String>();
        this.breakdownDetails.setStyle("-fx-font-family: 'monospaced';");
        parameterColumn.setCellValueFactory(
                        new PropertyValueFactory<BreakdownComparison, String>("property"));
        originalValueColumn.setCellValueFactory(
                        new PropertyValueFactory<BreakdownComparison, Double>("original"));
        recalculatedValueColumn.setCellValueFactory(
                        new PropertyValueFactory<BreakdownComparison, Double>("recalculated"));
        valuesTable.getColumns().get(0).prefWidthProperty()
                        .bind(valuesTable.widthProperty().multiply(0.33));
        valuesTable.getColumns().get(1).prefWidthProperty()
                        .bind(valuesTable.widthProperty().multiply(0.33));
        valuesTable.getColumns().get(2).prefWidthProperty()
                        .bind(valuesTable.widthProperty().multiply(0.33));
        valuesTable.setFixedCellSize(25);
        this.getRowConstraints().get(1).prefHeightProperty()
                        .bind(Bindings.size(valuesTable.getItems())
                                        .multiply(valuesTable.getFixedCellSize()).add(30));
        this.calculator.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                this.availableBreakdowns.clear();
                this.availableBreakdowns.putAll(newVal.getAllBreakdowns());
                this.breakdownDetails
                                .setText(this.availableBreakdowns.get(currentLogicalRunway.get()));
            }
        });
        this.currentLogicalRunway.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                List<BreakdownComparison> comparisons = new ArrayList<BreakdownComparison>();
                RunwayParameters original = newVal.getParameters();
                RunwayParameters recalc = newVal.getRecalculatedParameters();
                comparisons.add(new BreakdownComparison("TORA", original.getTORA(),
                                recalc.getTORA()));
                comparisons.add(new BreakdownComparison("TODA", original.getTODA(),
                                recalc.getTODA()));
                comparisons.add(new BreakdownComparison("ASDA", original.getASDA(),
                                recalc.getASDA()));
                comparisons.add(new BreakdownComparison("LDA", original.getLDA(), recalc.getLDA()));
                this.breakdownDetails.setText(this.availableBreakdowns.get(newVal));
                valuesTable.getItems().setAll(comparisons);
            }
        });
        this.currentRunway.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                setupBreakdownPicker(FXCollections.observableArrayList(newVal.getLogicalRunways()));
            }
        });
    }

    private void setupBreakdownPicker(ObservableList<LogicalRunway> data) {
        // Update the values
        breakdownPicker.setItems(data);
        breakdownPicker.getSelectionModel().selectFirst();
        // Re-create the event listener and string coverter
        breakdownPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.currentLogicalRunway.set(newVal);
        });
        breakdownPicker.setConverter(new StringConverter<LogicalRunway>() {
            @Override
            public String toString(LogicalRunway object) {
                if (object == null)
                    return null;
                return object.getIdentifier();
            }

            @Override
            public LogicalRunway fromString(String string) {
                return breakdownPicker.getItems().stream()
                                .filter(ap -> ap.getIdentifier().equals(string)).findFirst()
                                .orElse(null);
            }
        });
        // Update the current logical runway
        currentLogicalRunway.set(breakdownPicker.getValue());
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


    public class BreakdownComparison {

        public String property;
        public Double original;
        public Double recalculated;

        public BreakdownComparison(String property, Double original, Double recalculated) {
            this.property = property;
            this.original = original;
            this.recalculated = recalculated;
        }

        public String getProperty() {
            return this.property;
        }

        public Double getOriginal() {
            return this.original;
        }

        public Double getRecalculated() {
            return this.recalculated;
        }
    }
}
