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
package io.github.autobleem.pic;

import io.github.autobleem.pic.mt.ProcessingJob;
import io.github.autobleem.pic.mt.ProcessingWorker;
import io.github.autobleem.pic.mt.WorkerFactory;
import io.github.autobleem.pic.ui.ProgressElement;
import java.text.DecimalFormat;

/**
 *
 * @author artur.jakubowicz
 */
public class Job {
    private ProcessingJob job;
    private ProcessingWorker worker;
    private ProgressElement pe;
    private String filename;

    public String getFilename() {
        return filename;
    }

    
    public void setPe(ProgressElement pe) {
        this.pe = pe;
    }
    
    public boolean schedule(String filename)
    {
        this.filename = filename;
        worker = WorkerFactory.getWorker(filename);
        if (worker == null)
        {
            return false;
        }
        job = new ProcessingJob(worker);
        return true;
    }
    public void start()
    {
        job.setPe(pe);
        job.start();
    }
    public void terminate()
    {
        job.terminate();
    }
    public ProcessingJob.State getState()
    {
        ProcessingJob.State state = job.getState();
        return state;
    }
    
    public String getProgressText()
    {
         DecimalFormat df = new DecimalFormat("0.00");
         return "Progress: " + df.format(job.getPercentage()) + "%   Remaining time:" + job.getDurationBreakdown(job.getEstimatedEnd());
    }
    public float getPercentage()
    {
        return job.getPercentage();
    }
}
