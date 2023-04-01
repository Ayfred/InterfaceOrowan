package com.example.interfaceorowan;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public abstract class BasicInterface {
    private Scene scene;

    public BasicInterface(GridPane grid) {
        this.scene = new Scene(grid);
    }
}
