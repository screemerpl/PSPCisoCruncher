/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.mt;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Worker;

/**
 *
 * @author artur.jakubowicz
 */
public class ProcessingJob implements Runnable {

    private enum State {
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
                return;
            }
            percentage = (worker.getProcessedItems() * 1.0f / worker.getTotalItems() * 1.0f) * 100.0f;
            long currentTime = System.currentTimeMillis();
            long milis = currentTime - startedTime;

            estimatedEnd = (milis * worker.getTotalItems()) / worker.getProcessedItems();
            estimatedEnd = estimatedEnd - milis;

            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println("Completed: " + df.format(percentage) + "%  Approx End In:" + getDurationBreakdown(estimatedEnd));
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
    }

    public void terminate() {
        try {
            if (state != State.FINISHED) {
                state = State.CANCELLED;
                th.join();
                worker.terminate();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessingJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
