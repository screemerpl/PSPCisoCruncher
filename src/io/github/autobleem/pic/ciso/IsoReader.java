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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
