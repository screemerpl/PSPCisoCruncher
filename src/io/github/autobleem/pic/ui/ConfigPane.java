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
import io.github.autobleem.pic.Config;
import io.github.autobleem.pic.Job;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ConfigPane extends VBox {

    private Slider compressionLevelSlider = new Slider();
    private Slider threadSlider = new Slider();

    public ConfigPane() {
        super();
        this.setSpacing(5);
        compressionLevelSlider.setMax(9);
        compressionLevelSlider.setMin(0);
        compressionLevelSlider.setValue(Config.compressionLevel);
        compressionLevelSlider.setMajorTickUnit(1);
        compressionLevelSlider.setMinorTickCount(0);

        compressionLevelSlider.setSnapToTicks(true);
        compressionLevelSlider.setShowTickMarks(true);
        compressionLevelSlider.setShowTickLabels(true);

        threadSlider.setMax(8);
        threadSlider.setMin(1);
        threadSlider.setValue(Config.threads);
        threadSlider.setMajorTickUnit(1);
        threadSlider.setMinorTickCount(0);

        threadSlider.setSnapToTicks(true);
        threadSlider.setShowTickMarks(true);
        threadSlider.setShowTickLabels(true);

        compressionLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                Config.compressionLevel = new_val.intValue();
                Config.save();
            }
        });
        threadSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                Config.threads = new_val.intValue();
                Config.save();
            }
        });
        Font font = new Font("Arial", 15);
        Font font2 = new Font("Arial", 10);

        Label clLabel = new Label("Compression Level");
        clLabel.setFont(font);
        Label clInfo = new Label("Sets compression complexity from 0-none to 10-max");
        clInfo.setFont(font2);
        Label thLabel = new Label("Max threads");
        thLabel.setFont(font);
        Label thInfo = new Label("Sets number of files to process at a time.");
        thInfo.setFont(font2);

        Label delLabel = new Label("Remove complete");
        delLabel.setFont(font);
        Label delInfo = new Label("Remove input file after completion");
        delInfo.setFont(font2);

        ToggleButton tgl = new ToggleButton("Switch");
        tgl.setPrefWidth(315);
        tgl.setSelected(Config.removeInput);
        if (Config.removeInput) {
            tgl.setText("ON");
        } else {
            tgl.setText("OFF");
        }
        tgl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Config.removeInput = tgl.isSelected();
                Config.save();
                if (Config.removeInput) {
                    tgl.setText("ON");
                } else {
                    tgl.setText("OFF");
                }

            }
        });
        this.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().addAll(clLabel, clInfo, compressionLevelSlider, new Separator());
        this.getChildren().addAll(thLabel, thInfo, threadSlider, new Separator());
        this.getChildren().addAll(delLabel, delInfo, tgl, new Separator());

    }

}
