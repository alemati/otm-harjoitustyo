/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    
    static String vastaus;
    
    public static String display(String title, String messege) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(200);
        window.setMaxWidth(250);
        window.setMaxHeight(200);
        
        
        Label l1 = new Label();
        l1.setText(messege);
        
        Button but1 = new Button("Delete user");
        but1.setOnAction(e -> {
            vastaus = "Delete user";
            window.close();
        });
        
        Button but2 = new Button("Cancel");
        
        but2.setOnAction(e -> {
            vastaus = "Cancel";
            window.close();
        });
        
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(l1,but1,but2);
        HBox layout2 = new HBox(10);
        layout2.getChildren().addAll(but1,but2);
        layout1.getChildren().addAll(layout2);
        
        Scene scene1 = new Scene(layout1);
        window.setScene(scene1);
        window.showAndWait();
        
        return vastaus;
    }

}