/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    @Override
    public void run() {
        while (state == State.RUNNING) {
            boolean workerState = worker.processNext();
            if (!workerState)
            {
                state = State.FINISHED;
                percentage = 100.0f;
                estimatedEnd = 0;
                worker.finish();
                return;
            }
            percentage = (worker.getProcessedItems() * 1.0f / worker.getTotalItems() * 1.0f) * 100.0f;
            long currentTime = System.currentTimeMillis();
            long milis = currentTime - startedTime;

            estimatedEnd = (milis * worker.getTotalItems()) /  worker.getProcessedItems();
            estimatedEnd = estimatedEnd - milis;

            long minutes = (estimatedEnd / 1000) / 60;
            long seconds = (estimatedEnd / 1000) % 60;
        
            System.out.println("Percentage: " + percentage + "  Approx End:" + minutes +":"+seconds);
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

    public ProcessingJob(String filename, ProcessingWorker pw) {
        this.filename = filename;
        this.percentage = 0.0f;
        this.startedTime = 0;
        this.estimatedEnd = 0;
        this.state = State.NEW;
        th = new Thread(this);
        worker = pw;
    }

    public void start() {
        worker.init(filename);
        percentage = 0.0f;
        startedTime = System.currentTimeMillis();
        estimatedEnd = startedTime;
        state = State.RUNNING;
        th.start();
    }

    public void terminate() {
        try {
            state = State.CANCELLED;
            th.join();
            worker.terminate();
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessingJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
