module runwayredeclaration {
  requires javafx.graphics;
  requires transitive javafx.controls;
  requires javafx.fxml;
  requires java.xml;
  opens org.comp2211.group6.view to javafx.fxml;
  exports org.comp2211.group6;
  exports org.comp2211.group6.Model;
  exports org.comp2211.group6.Controller;
  exports org.comp2211.group6.view;
}
