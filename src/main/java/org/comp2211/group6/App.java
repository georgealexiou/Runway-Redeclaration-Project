package org.comp2211.group6;

import org.comp2211.group6.view.MainView;
import org.comp2211.group6.Model.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    /** Public Methods */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainView root = new MainView();

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
