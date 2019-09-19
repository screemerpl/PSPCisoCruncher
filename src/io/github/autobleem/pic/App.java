/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic;

import io.github.autobleem.pic.mt.ProcessingJob;
import io.github.autobleem.pic.mt.ProcessingWorker;
import io.github.autobleem.pic.mt.WorkerFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    
      ProcessingJob job1;
    

    private void buildProgressPane()
    {
        VBox scrollContent=new VBox(10);
        
        progressPane = new ScrollPane(scrollContent);
         progressPane.setPrefSize(120, 120);
        for (int i=0;i<1000;i++)
        {
            Label l=new Label("AAAAAAAAA");
            scrollContent.getChildren().add(l);
        }
        
        
    }
    
    private void buildLayout() {
        buildProgressPane();
        BorderPane borderPane = new BorderPane();

      

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
    

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);

        borderPane.setTop(hbox);
        borderPane.setCenter(progressPane);
        mainBorder = borderPane;

    }

    @Override
    public void stop() throws Exception {
        super.stop(); 
        job1.terminate();
    }

    
    @Override
    public void start(Stage stage) {
        ProcessingWorker pw = WorkerFactory.getWorker("/Users/screemer/0471 - Tomb Raider - Legend (USA) (v1.02).iso");
        job1=new ProcessingJob(pw);
        job1.start();
        
        buildLayout();
        stage.setTitle("JavaFX Welcome");
        Scene scene = new Scene(mainBorder, 320, 500);
        mainBorder.setStyle("");
        stage.setScene(scene);
        stage.show();
        
       
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
