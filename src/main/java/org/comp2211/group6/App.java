package org.comp2211.group6;

import org.comp2211.group6.view.MainView;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {

    /** Public Methods */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(STYLESHEET_MODENA);
        MainView root = new MainView();
        Window.getWindows().addListener(new ListChangeListener<Window>() {
            @Override
            public void onChanged(Change<? extends Window> change) {
                change.next();
                if (change.wasAdded() || change.wasUpdated()) {
                    change.getList().forEach(w -> w.getScene().getStylesheets()
                                    .add(getClass().getResource("/css/base.css").toExternalForm()));
                }
            }
        });

        primaryStage.setTitle("Runway Re-declaration Tool");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("cli")) {
            CLI CLI = new CLI();
            CLI.run();
        } else {
            launch(args);
        }
    }

}
