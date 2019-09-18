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
public class MockWorker implements ProcessingWorker{

    int processedItems;
    static final int TOTAL=1000;
    @Override
    public void init(String filename) {
      processedItems = 0;
    }

    @Override
    public int getTotalItems() {
        return TOTAL;
    }

    @Override
    public int getProcessedItems() {
       return processedItems;
    }

    @Override
    public boolean processNext() {
        processedItems++;
        try {
            System.out.println("Item: "+processedItems);
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(MockWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return processedItems<TOTAL;
    }

    @Override
    public void terminate() {
      
    }

    @Override
    public void finish() {
       
    }
    
}
