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

import io.github.autobleem.pic.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class AboutPane extends VBox {
    private ImageView image;
    private Label programName;
    private Label version;
    private Label spacer;
    private Label copyright;
    public AboutPane() {
        super();
        Image img = new Image(getClass().getResource("/psp.png").toExternalForm());
        image = new ImageView();
        image.setImage(img);
        programName = new Label("PSPCisoCruncher");
        programName.setFont(new Font("Arial", 25));
        version = new Label(Config.VERSION);
        version.setFont(new Font("Arial", 12));
        copyright = new Label("(C)2019 Screemer/AutoBleem Team");
        copyright.setFont(new Font("Arial", 12));
        spacer = new Label("");
        spacer.setPrefHeight(40.0);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(image, programName, version, spacer,copyright);
       
    }
    
}
