/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.ciso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author screemer
 */
public class IsoWriter {

    private File f;
    private FileOutputStream fw;

    public boolean isClosed() {
        try {
            fw.flush();
        } catch (IOException ex) {
            return true;

        }
        return false;
    }

    public boolean init(String filename) {
        try {
            f = new File(filename);
            if (f.exists())
            {
               f.delete();
            }
            f = null;
            f = new File(filename);
            fw = new FileOutputStream(f);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(IsoWriter.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    public void writeBlock(byte[] block) {
        try {
            fw.write(block);
        } catch (IOException ex) {
            Logger.getLogger(CisoReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void remove()
    {
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(IsoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.delete();
    }

    public void close() {
        try {
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(IsoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
      

    }
}
