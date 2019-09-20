/*
 * Copyright (C) 2019 artur.jakubowicz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.autobleem.pic.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.autobleem.pic.App;
import io.github.autobleem.pic.Job;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ProgressElement extends VBox {

    private String fullName;
    private Label filename;
    private Label progressText;
    private double percentage;
    private Button stopButton;
    private ProgressBar pb = new ProgressBar(0);
    private Job job;

    public Label getProgressText() {
        return progressText;
    }

    public ProgressBar getPb() {
        return pb;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void enableStop() {
        stopButton.setDisable(false);
    }

    public void disableStop() {
        stopButton.setDisable(true);
    }

    public ProgressElement(String name, String text, String fullName) {
        
        super(10.0);
        this.fullName = fullName;
        Image img = new Image(getClass().getResource("/remove.png").toExternalForm());
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setBorder(new Border(new BorderStroke(new Color(0.10, 0.10, 0.10, 0.100),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        filename = new Label(name);
        filename.setFont(new Font("Arial", 15));
        progressText = new Label(text);
        progressText.setFont(new Font("Arial", 10));
        percentage = 0.0D;
        pb.setProgress(0);
        pb.setMinWidth(200);
        VBox box = new VBox();
        box.setSpacing(10.0);
        box.setMaxWidth(220.0);
        box.setMinWidth(220.0);

        box.getChildren().addAll(filename, progressText, pb);
        hbox.getChildren().add(box);

        stopButton = new Button("Stop");
        stopButton.setDisable(true);
        stopButton.setGraphic(new ImageView(img));
        stopButton.setPadding(new Insets(5, 5, 5, 5));
        stopButton.setContentDisplay(ContentDisplay.TOP);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                job.terminate();

                for (Job job:App.queue)
                {
                    if (job.getFilename().equals(fullName))
                    {
                        App.queue.remove(job);
                        break;
                    }
                }

            }
        });
        StackPane pane = new StackPane();
        pane.setPrefSize(50, 50); //set a default size for your stackpane
        pane.setPadding(new Insets( 0, 0, 0, 20));

        pane.getChildren().add(stopButton);
        hbox.getChildren().add(pane);
        this.getChildren().addAll(hbox);
        this.getChildren().add(new Separator());
    }

}
