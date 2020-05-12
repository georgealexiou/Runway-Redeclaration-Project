package org.comp2211.group6.view;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class Formatters {
    public static TextFormatter<Change> numberFormatter(Boolean positive) {
        Pattern decimalPattern;
        if (positive) {
            decimalPattern = Pattern.compile("\\d*(\\.\\d*)?");
        } else {
            decimalPattern = Pattern.compile("-?\\d*(\\.\\d*)?");
        }
        UnaryOperator<Change> filter = change -> {
            if (decimalPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        return new TextFormatter<>(filter);
    }

    public static TextFormatter<Change> positionFormatter() {
        Pattern posPattern = Pattern.compile("L|R|C");
        UnaryOperator<Change> filter = change -> {
            if (posPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        return new TextFormatter<>(filter);
    }

    public static TextFormatter<Change> headingFormatter() {
        Pattern posPattern = Pattern.compile("[1-9]|0[1-9]|[12][0-9]|3[0-6]");
        UnaryOperator<Change> filter = change -> {
            if (posPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        return new TextFormatter<>(filter);
    }
}
