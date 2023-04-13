package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.modele.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministratorVue {
    private GridPane grid;
    private Scene scene;
    public PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public AdministratorVue() {
        this.grid = new GridPane();
        this.scene = new Scene(grid, 1500, 1000);

        adjustGrid(grid);
        createInterface(grid);
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void createInterface(GridPane gp){

        final Text actiontarget = new Text();
        Text scenetitle = new Text("Administrator");
        TextField userTextField = new TextField();
        Rectangle r1 =new Rectangle();
        StackPane sp = new StackPane();
        GridPane grid = new GridPane();
        adjustGrid(grid);

        r1.setWidth(310);
        r1.setHeight(145);
        r1.setFill(Color.WHITE);
        r1.opacityProperty().set(0.5);
        StackPane.setMargin(grid, new Insets(20, 20, 20, 20));
        sp.setAlignment(grid, Pos.CENTER);

        sp.getChildren().addAll(r1, grid);
        gp.add(sp,0, 0);
        // on recuprere tous les users les afficher dans la grid pane
        Modele m = Modele.getModeleinstance();
        ArrayList<String>[] result = m.getUsers();
        Label labelname = new Label("User");
        grid.add(labelname, 0, 0);
        Label labelrole = new Label("Role");
        grid.add(labelrole, 1, 0);
        Label labelAdmin = new Label("Give Admin Right");
        grid.add(labelAdmin, 2, 0);
        Label labelEngineer = new Label("Give Engineer Right");
        grid.add(labelEngineer, 3, 0);
        Label labelOperator = new Label("Give Operator Right");
        grid.add(labelOperator, 4, 0);
        Label labelDeleteUser = new Label("Delete User");
        grid.add(labelDeleteUser, 5, 0);





        // Ajout des données et du bouton dans la grille
        for (int row = 0; row < result[0].size(); row++) {
            for (int col = 0; col < result.length; col++) {
                String cellData = result[col].get(row);
                Label label = new Label(cellData);
                grid.add(label, col, row + 1);
            }
            System.out.println(result[0].get(row));
            if (!result[0].get(row).equals(m.getUser().getName())) {
                Button buttonAdmin = new Button("ADMIN");
                grid.add(buttonAdmin, result.length, row + 1);
                Button buttonEngineer = new Button("ENGINEER");
                grid.add(buttonEngineer, result.length + 1, row + 1);
                Button buttonOperator = new Button("OPERATOR");
                grid.add(buttonOperator, result.length + 2, row + 1);
                Button buttonDelete = new Button("DELETE");
                grid.add(buttonDelete, result.length + 3, row + 1);
            }
        }




    }

    private void adjustGrid(GridPane grid){
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

    }




}

