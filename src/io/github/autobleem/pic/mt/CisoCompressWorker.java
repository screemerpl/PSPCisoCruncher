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
    private String filename;

    @Override
    public void init(String filename) {
        this.filename = filename;
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
        return filename;
    }

}
