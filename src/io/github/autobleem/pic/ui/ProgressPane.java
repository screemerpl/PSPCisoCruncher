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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author artur.jakubowicz
 */
public class ProgressPane extends ScrollPane {

    boolean emptyQueue = true;
    VBox scrollContent;
    
    
    public ProgressPane() {
        super();
        scrollContent = new VBox();
        this.setContent(scrollContent);
    }
    public ProgressElement addElement(String filename, Job job)
    {
        ProgressElement pe = new ProgressElement(filename,job.getProgressText());
        pe.setJob(job);
        scrollContent.getChildren().add(pe);
        return pe;
    }

}
