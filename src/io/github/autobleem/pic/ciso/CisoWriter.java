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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;


public class CisoWriter {

    private CisoHeader header;
    private long isoSize;
    private int compressionLevel;
    private String filename;

    private URandomAccessFile file;
    private int blockNum = 0;

    public boolean isClosed() {
        return !file.getChannel().isOpen();
    }

    private void fillHeader() {
        header = new CisoHeader();
        header.setMagic("CISO");
        header.setHeaderSize(UInteger.valueOf(0));
        header.setTotalBytes(ULong.valueOf(isoSize));
        header.setBlockSize(UInteger.valueOf(2048));
        header.setVer(UByte.valueOf(1));
        header.setAlign(UByte.valueOf(0));
        header.setRes0(UByte.valueOf(0));
        header.setRes1(UByte.valueOf(0));

        header.setTotalBlocks(header.getTotalBytes().toBigInteger().divide(new BigInteger(header.getBlockSize().toString())).intValue());
        header.setIndexSize((header.getTotalBlocks() + 1) * 4);

        header.setIndex(new UInteger[header.getTotalBlocks() + 1]);
        header.setCompressed(new boolean[header.getTotalBlocks() + 1]);

    }

    private void writeHeader() {
        try {
            file.writeBytes(header.getMagic());
            file.writeUInt(header.getHeaderSize());
            file.writeULong(header.getTotalBytes());
            file.writeUInt(header.getBlockSize());
            file.writeUByte(header.getVer());
            file.writeUByte(header.getAlign());
            file.writeUByte(header.getRes0());
            file.writeUByte(header.getRes1());

            byte[] mockup = new byte[header.getIndexSize()];

            for (int i = 0; i < header.getIndexSize(); i++) {
                mockup[i] = 0;
            }
            file.write(mockup);

        } catch (IOException ex) {
            Logger.getLogger(CisoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean init(String filename, int compressionLevel, long isoSize) {
        try {
            File f = new File(filename);
            if (f.exists()) {
                f.delete();
            }
            f = null;
            this.filename = filename;
            this.compressionLevel = compressionLevel;
            this.isoSize = isoSize;
            fillHeader();
            file = new URandomAccessFile(filename, "rwd");
            writeHeader();

            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CisoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void writeIndex() {
        try {
            header.getIndex()[header.getTotalBlocks()] = UInteger.valueOf(file.getChannel().position());
            header.getCompressed()[header.getTotalBlocks()]=true;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (int i = 0; i < header.getTotalBlocks() + 1; i++) {
                int index = header.getIndex()[i].intValue();
                boolean compressed = header.getCompressed()[i];

                if (!compressed) {
                    index += 0x80000000;
                }
                byte[] val = ByteBuffer.allocate(4).putInt(index).array();
                // to little endian
                for (int x = 0; x < val.length / 2; x++) {
                    byte temp = val[x];
                    val[x] = val[val.length - x - 1];
                    val[val.length - x - 1] = temp;
                }

                bos.write(val);

            }
            file.seek(0x18);
            file.write(bos.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(CisoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeBlock(byte[] block) {

        try {
            long posStart = file.getChannel().position();
            header.getIndex()[blockNum] = UInteger.valueOf(posStart);
            byte[] outBuffer = new byte[4096];
            Deflater deflater = new Deflater(compressionLevel, true);
            deflater.setInput(block);
            deflater.finish();
            int newSize = deflater.deflate(outBuffer);
            deflater.end();

            if (newSize < 2048) {
                file.write(outBuffer, 0, newSize);
                header.getCompressed()[blockNum] = true;
            } else {
                file.write(block);
                header.getCompressed()[blockNum] = false;
            }
            blockNum++;
        } catch (IOException ex) {
            Logger.getLogger(CisoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remove() {
        try {
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(IsoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        File f = new File(filename);
        f.delete();
    }

    public void close() {
        try {
            writeIndex();
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(IsoWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
