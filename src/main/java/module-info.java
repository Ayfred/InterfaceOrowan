module com.example.interfaceorowan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.interfaceorowan to javafx.fxml;
    exports com.example.interfaceorowan;
}