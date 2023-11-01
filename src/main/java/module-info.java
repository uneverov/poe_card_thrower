module uneverov.evgeny.poe_card_thrower {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens uneverov.evgeny.poe_card_thrower to javafx.fxml;
    exports uneverov.evgeny.poe_card_thrower;
}