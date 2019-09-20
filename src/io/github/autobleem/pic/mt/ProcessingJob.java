/*
 * Copyright (C) 2019 Screemer/AutoBleem Team
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
package io.github.autobleem.pic.mt;

import io.github.autobleem.pic.ui.ProgressElement;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author artur.jakubowicz
 */
public class ProcessingJob implements Runnable {

    public enum State {
        NEW,
        RUNNING,
        FINISHED,
        CANCELLED
    }

    private String filename;
    private float percentage;
    private long startedTime;
    private long estimatedEnd;
    private State state;
    private Thread th;
    private ProcessingWorker worker;
    private ProgressElement pe;

    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            return "??:??:??";
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        DecimalFormat df = new DecimalFormat("00");
        sb.append(df.format(hours));
        sb.append(":");
        sb.append(df.format(minutes));
        sb.append(":");
        sb.append(df.format(seconds));

        return (sb.toString());
    }

    @Override
    public void run() {
        while (state == State.RUNNING) {
            boolean workerState = worker.processNext();
            if (!workerState) {
                state = State.FINISHED;
                percentage = 100.0f;
                estimatedEnd = 0;
                worker.finish();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pe.getProgressText().setText("Done");
                        pe.getPb().setProgress(getPercentage() / 100.0);
                        pe.disableStop();
                    }

                });

                return;
            }
            percentage = (worker.getProcessedItems() * 1.0f / worker.getTotalItems() * 1.0f) * 100.0f;
            long currentTime = System.currentTimeMillis();
            long milis = currentTime - startedTime;

            estimatedEnd = (milis * worker.getTotalItems()) / worker.getProcessedItems();
            estimatedEnd = estimatedEnd - milis;

            DecimalFormat df = new DecimalFormat("0.00");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pe.getProgressText().setText("Progress: " + df.format(getPercentage()) + "%   Remaining time:" + getDurationBreakdown(getEstimatedEnd()));
                    pe.getPb().setProgress(getPercentage() / 100.0);
                }
            });

        }
        if (state == State.CANCELLED)
        {
             Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  pe.getProgressText().setText("Terminated");
                }
            });
             
            
        }
    }

    public synchronized float getPercentage() {
        return percentage;
    }

    public synchronized String getFilename() {
        return filename;
    }

    public synchronized long getEstimatedEnd() {
        return estimatedEnd;
    }

    public synchronized State getState() {
        return state;
    }

    public void setPe(ProgressElement pe) {
        this.pe = pe;
    }

    public ProcessingJob(ProcessingWorker pw) {
        this.filename = pw.getFileName();
        this.percentage = 0.0f;
        this.startedTime = 0;
        this.estimatedEnd = 0;
        this.state = State.NEW;
        th = new Thread(this);
        worker = pw;
    }

    public void start() {
        percentage = 0.0f;
        startedTime = System.currentTimeMillis();
        estimatedEnd = startedTime;
        state = State.RUNNING;
        th.start();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pe.enableStop();
            }
        });
    }

    public void terminate() {
        try {
            if (state != State.FINISHED) {
                state = State.CANCELLED;
                th.join();
                worker.terminate();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pe.disableStop();
                       
                    }
                });
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessingJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
