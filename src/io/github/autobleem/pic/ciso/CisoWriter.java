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
public class CisoWriter {
    
    public boolean isClosed() {
       
        return false;
    }

    public boolean init(String filename) {
        File f = new File(filename);
        if (f.exists())
        {
            f.delete();
        }
        f = null;
        return true;
    }

    public void writeBlock(byte[] block) {
     

    }
    
    public void remove()
    {
       
    }

    public void close() {
      
      

    }
}


