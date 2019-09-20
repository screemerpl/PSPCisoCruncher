/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.autobleem.pic.mt.ProcessingJob;
import io.github.autobleem.pic.ui.AboutPane;
import io.github.autobleem.pic.ui.ConfigPane;
import io.github.autobleem.pic.ui.ProgressElement;
import io.github.autobleem.pic.ui.ProgressPane;
import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author artur.jakubowicz
 */
public class App extends Application {

    public static final JobQueue queue = new JobQueue();

    private BorderPane mainBorder = new BorderPane();
    private ProgressPane progressPane;
    private Node configPane;
    private Node aboutPane;

    private Node buildToolBar() {

        final int BWidth = 80;
        final int BHeight = 70;
        VBox topBar = new VBox();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(20);

        Button buttonCurrent = new Button("Progress");
        buttonCurrent.setPrefSize(BWidth, BHeight);
    

        Button buttonConfig = new Button("Properties");
        buttonConfig.setPrefSize(BWidth, BHeight);

        Button buttonAbout = new Button("About");
        buttonAbout.setPrefSize(BWidth, BHeight);
        hbox.getChildren().addAll(buttonCurrent, buttonConfig, buttonAbout);

        GlyphIcon currentIcon = GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.TASKS)
                .size("5em")
                .build();
        GlyphIcon configIcon = GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.GEAR)
                .size("5em")
                .build();
        GlyphIcon aboutIcon = GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.QUESTION_CIRCLE)
                .size("5em")
                .build();

        buttonCurrent.setGraphic(currentIcon);
        buttonCurrent.setContentDisplay(ContentDisplay.TOP);
        buttonConfig.setGraphic(configIcon);
        buttonConfig.setContentDisplay(ContentDisplay.TOP);
        buttonAbout.setGraphic(aboutIcon);
        buttonAbout.setContentDisplay(ContentDisplay.TOP);

        buttonCurrent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainBorder.setCenter(progressPane);
            }
        });

        buttonConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainBorder.setCenter(configPane);
            }
        });
        buttonAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainBorder.setCenter(aboutPane);
            }
        });
        Separator sep = new Separator();
        topBar.getChildren().addAll(hbox, sep);
        return topBar;
    }

    private void buildLayout() {
        progressPane = new ProgressPane();
        aboutPane = new AboutPane();
        configPane = new ConfigPane();

        mainBorder.setTop(buildToolBar());
        mainBorder.setCenter(progressPane);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        for (Job job : App.queue) {
            job.terminate();
        }
        Config.save();
    }

    @Override
    public void start(Stage stage) {
        Config.load();
        setUserAgentStylesheet(STYLESHEET_MODENA);

   
        buildLayout();
        stage.setTitle("PSPCisoCruncher");

        mainBorder.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != mainBorder
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });

        mainBorder.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    List<File> fileList = db.getFiles();
                    for (File f:fileList)
                    {
                        Job job =  App.queue.schedule(f.getAbsolutePath());
                        System.out.println(f.getAbsolutePath());
                        if (job!=null)
                        {
                            ProgressElement pe = progressPane.addElement(f.getName(), job);
                            job.setPe(pe);
                            job.start();
                        }
                    }
                    
                    success = true;
                }
                mainBorder.setCenter(progressPane);
                event.setDropCompleted(success);
                event.consume();
                
            }
        });

        Scene scene = new Scene(mainBorder, 320, 500);
        scene.getStylesheets().add(getClass().getResource("/dark.css").toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMaxWidth(320);
        stage.getIcons().add(new Image(getClass().getResource("/umd.png").toExternalForm()));
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
