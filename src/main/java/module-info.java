module com.example.interfaceorowan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.h2database;
    requires java.naming;


    opens com.example.interfaceorowan to javafx.fxml;
    exports com.example.interfaceorowan;
    exports com.example.interfaceorowan.vue;
    opens com.example.interfaceorowan.vue to javafx.fxml;
}