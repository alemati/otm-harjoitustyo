/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otm.kirjasto;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String messege) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(200);
        window.setMaxWidth(200);
        window.setMaxHeight(200);

        Label l1 = new Label();
        l1.setText(messege);

        Button closeButton = new Button("OK!");

        closeButton.setOnAction(e -> window.close());

        VBox layout1 = new VBox(10);
        layout1.getChildren().add(l1);
        layout1.getChildren().add(closeButton);
        
        layout1.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout1);
        window.setScene(scene1);
        window.showAndWait();

    }

}