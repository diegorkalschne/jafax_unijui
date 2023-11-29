module br.com.math.javafxunijui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires json.simple;

    opens br.com.math.javafxunijui to javafx.fxml;
    exports br.com.math.javafxunijui;
}
