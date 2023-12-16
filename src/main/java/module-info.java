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
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires java.desktop;
    requires org.bytedeco.opencv;
    requires javafx.swing;
    requires jnativehook;


    opens uneverov.evgeny.poe_card_thrower to javafx.fxml;
    exports uneverov.evgeny.poe_card_thrower;
    exports uneverov.evgeny.poe_card_thrower.utils;
    opens uneverov.evgeny.poe_card_thrower.utils to javafx.fxml;
    exports uneverov.evgeny.poe_card_thrower.managers;
    opens uneverov.evgeny.poe_card_thrower.managers to javafx.fxml;
}