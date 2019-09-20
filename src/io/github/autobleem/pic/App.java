/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic;

import io.github.autobleem.pic.mt.ProcessingJob;
import io.github.autobleem.pic.mt.ProcessingWorker;
import io.github.autobleem.pic.mt.WorkerFactory;
import io.github.autobleem.pic.ui.AboutPane;
import io.github.autobleem.pic.ui.ConfigPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author artur.jakubowicz
 */
public class App extends Application {

    private BorderPane mainBorder;
    private ScrollPane progressPane;
    private Node configPane;
    private Node aboutPane;

    ProcessingJob job1;

    private void buildProgressPane() {
        VBox scrollContent = new VBox(10);

        progressPane = new ScrollPane(scrollContent);
        progressPane.setPrefSize(120, 120);
        for (int i = 0; i < 100; i++) {
            Label l = new Label("AXAXAXA");
            scrollContent.getChildren().add(l);
        }

    }

    private void buildLayout() {
        buildProgressPane();
        aboutPane = new AboutPane();
        configPane = new ConfigPane();

        BorderPane borderPane = new BorderPane();

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);

        Button buttonCurrent = new Button("Progress");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonConfig = new Button("Properties");
        buttonConfig.setPrefSize(100, 20);

        Button buttonAbout = new Button("About");
        buttonAbout.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonConfig, buttonAbout);

        buttonCurrent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               borderPane.setCenter(progressPane);
            }
        });

        buttonConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                  borderPane.setCenter(configPane);
            }
        });
        buttonAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
              borderPane.setCenter(aboutPane);
            }
        });

        borderPane.setTop(hbox);
        borderPane.setCenter(progressPane);
        mainBorder = borderPane;

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        //  job1.terminate();
    }

    @Override
    public void start(Stage stage) {
        setUserAgentStylesheet(STYLESHEET_MODENA);
      
        /*
        ProcessingWorker pw = WorkerFactory.getWorker("/Users/screemer/0369 - OutRun 2006 - Coast 2 Coast (Europe) (En,Fr,De,Es,It) (v1.01).iso");
        job1=new ProcessingJob(pw);
        job1.start();
         */
        buildLayout();
        stage.setTitle("PSPCisoCruncher");
        Scene scene = new Scene(mainBorder, 320, 500);
        scene.getStylesheets().add(getClass().getResource("/dark.css").toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMaxWidth(320);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
