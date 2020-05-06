package org.comp2211.group6;

import org.comp2211.group6.Model.ColourScheme;
import org.comp2211.group6.view.MainView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {

    /** Public Methods */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(STYLESHEET_MODENA);
        MainView root = new MainView();
        primaryStage.setTitle("Runway Re-declaration Tool");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
        ColourScheme.getInstance().generateRootStyleString(Window.getWindows());
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
