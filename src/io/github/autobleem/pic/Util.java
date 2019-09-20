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
