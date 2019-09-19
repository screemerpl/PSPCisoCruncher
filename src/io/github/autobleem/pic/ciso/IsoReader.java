/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.ciso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author screemer
 */
public class IsoReader {
    private URandomAccessFile file;

    public boolean isClosed() {
        return !file.getChannel().isOpen();
    }

    public boolean init(String filename) {
        try {
            file = new URandomAccessFile(filename, "r");
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IsoReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getTotalBlocks() {
        try {
            long size = file.getChannel().size();
            int blocks = (int) (size / 2048);
            return blocks;
        } catch (IOException ex) {
            Logger.getLogger(IsoReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public byte[] getBlock(int sector) {
        try {
            long pos = sector*2048L;
            file.seek(pos);
            byte[] buffer = new byte[2048];
            file.readFully(buffer);
            return buffer;
        } catch (IOException ex) {
            Logger.getLogger(IsoReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void close() {

        try {
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(CisoReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
