/*
 * Copyright (C) 2019 Screemer/AutoBleem Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.autobleem.pic.ciso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
