/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.mt;

import io.github.autobleem.pic.Util;
import io.github.autobleem.pic.ciso.CisoReader;
import io.github.autobleem.pic.ciso.IsoWriter;

/**
 *
 * @author screemer
 */
public class CisoExpandWorker implements ProcessingWorker {
     public String filename;
    private CisoReader reader;
    private IsoWriter writer;
    private int currentBlock=0;
    @Override
    public void init(String filename) {
        this.filename = filename;
        reader = new CisoReader();
        reader.init(filename);
        String outputName = Util.getFilenameWithoutExtension(filename)+".iso";
        System.out.println(outputName);
        writer = new IsoWriter();
        writer.init(outputName);
    }

    @Override
    public int getTotalItems() {
        return reader.getTotalBlocks();
    }

    @Override
    public int getProcessedItems() {
        return currentBlock;
    }

    @Override
    public boolean processNext() {
        if (currentBlock<getTotalItems())
        {
            byte[] data =reader.getBlock(currentBlock);
            writer.writeBlock(data);
            currentBlock++;
            return true;
        }
        return false;
        
    }

    @Override
    public void finish() {
        reader.close();
        writer.close();
    }

    @Override
    public void terminate() {
        reader.close();
        writer.remove();
    }

    @Override
    public String getFileName() {
       return filename;
    }
    
}