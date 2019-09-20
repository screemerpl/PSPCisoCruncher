/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.mt;

import io.github.autobleem.pic.Config;
import io.github.autobleem.pic.Util;
import io.github.autobleem.pic.ciso.CisoWriter;
import io.github.autobleem.pic.ciso.IsoReader;
/**
 *
 * @author screemer
 */
public class CisoCompressWorker implements ProcessingWorker {

    private IsoReader reader;
    private CisoWriter writer;
    private int currentBlock = 0;

    @Override
    public void init(String filename) {
        reader = new IsoReader();
        reader.init(filename);
        String outputName = Util.getFilenameWithoutExtension(filename) + ".cso";
        System.out.println(outputName);
        writer = new CisoWriter();
        writer.init(outputName,Config.compressionLevel,reader.getTotalBlocks()*2048);
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
        if (currentBlock < getTotalItems()) {
            byte[] data = reader.getBlock(currentBlock);
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
        return "";
    }

}
