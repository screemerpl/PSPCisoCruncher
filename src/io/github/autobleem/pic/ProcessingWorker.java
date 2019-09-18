/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic;

/**
 *
 * @author artur.jakubowicz
 */
public interface ProcessingWorker {
    public void init(String filename);
    public int getTotalItems();
    public int getProcessedItems();
    public boolean processNext();
    public void finish();
    public void terminate();
}
