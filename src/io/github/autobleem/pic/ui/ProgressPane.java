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

import io.github.autobleem.pic.App;
import io.github.autobleem.pic.Job;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author artur.jakubowicz
 */
public class ProgressPane extends ScrollPane {

    boolean emptyQueue = true;
    VBox scrollContent;
    VBox initialContent = new VBox();
    
    
    public ProgressPane() {
        super();
        scrollContent = new VBox();
        Image img = new Image(getClass().getResource("/umd.png").toExternalForm());
        ImageView image = new ImageView();
        image.setImage(img);
       
        initialContent.setPadding(new Insets(20));
   
        initialContent.getChildren().add(image);
        initialContent.setAlignment(Pos.CENTER);
        initialContent.getChildren().add(new Label("Drop files to this window to start processing"));
        this.setContent(initialContent);
    }
    public ProgressElement addElement(String filename, Job job)
    {
         this.setContent(scrollContent);
        ProgressElement pe = new ProgressElement(filename,job.getProgressText());
        pe.setJob(job);
        scrollContent.getChildren().add(pe);
        return pe;
    }

}
