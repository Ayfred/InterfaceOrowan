module com.example.interfaceorowan {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interfaceorowan to javafx.fxml;
    exports com.example.interfaceorowan;
}