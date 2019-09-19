/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic;

import io.github.autobleem.pic.ciso.CisoReader;
import io.github.autobleem.pic.ciso.URandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author screemer
 */
public class Util {
    public static String getFilenameWithoutExtension(String input)
    {
        int pos = input.lastIndexOf(".");
        return input.substring(0, pos);
    }
    public static boolean isCISO(String file)
    {
        try {
            URandomAccessFile raf = new URandomAccessFile(file, "r");
            String ciso = raf.readString(4);
            return ciso.equals("CISO");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean isISO(String file)
    {
        try {
            final int sectorSize = 2048;
            
            URandomAccessFile raf = new URandomAccessFile(file, "r");
            raf.seek(sectorSize*16+1);
            String id = raf.readString(5);
            if (id.equals("CD001"))
            {
            return true;
            }
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
            
        }
        return false;
    }
}
