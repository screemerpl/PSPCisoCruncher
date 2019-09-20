package io.github.autobleem.pic.ciso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.joou.UInteger;
import org.joou.ULong;

public class CisoReader {

    private CisoHeader header;
    private URandomAccessFile file;

    private boolean readHeader() {
        try {
            file.seek(0);
            header.setMagic(file.readString(4));
            header.setHeaderSize(file.readUInt());
            header.setTotalBytes(file.readULong());
            header.setBlockSize(file.readUInt());
            header.setVer(file.readUByte());
            header.setAlign(file.readUByte());
            header.setRes0(file.readUByte());
            header.setRes1(file.readUByte());

            if (!header.getMagic().equals("CISO")) {
                return false;
            }
            if (header.getBlockSize() == UInteger.valueOf(0)) {
                return false;
            }
            if (header.getTotalBytes() == ULong.valueOf(0)) {
                return false;
            }
            header.setTotalBlocks(header.getTotalBytes().toBigInteger().divide(new BigInteger(header.getBlockSize().toString())).intValue());
            header.setIndexSize((header.getTotalBlocks()+1)*4);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private boolean readIndex()
    {
      
        int blocks = header.getTotalBlocks();
        header.setIndex(new UInteger[blocks+1]);
        header.setCompressed(new boolean[blocks+1]);
          try {
            file.seek(0x18);
            for (int i = 0; i < blocks+1; i++) {

                boolean plain = false;
                UInteger idx = file.readUInt();
                if ((idx.longValue() & 0x80000000) == 0) {
                    plain = false;

                } else {
                    plain = true;
                }
                idx = UInteger.valueOf(idx.longValue() & 0x7fffffff);

                header.getIndex()[i] = idx;
                header.getCompressed()[i] = !plain;
               

            }
        } catch (IOException ex) {
           
            return false;
        }
        return true;
    }
    public boolean isClosed() {
        return !file.getChannel().isOpen();
    }

    public boolean init(String filename) {
        try {
            header = new CisoHeader();
            file = new URandomAccessFile(filename, "r");
        } catch (FileNotFoundException ex) {
           
            return false;
        }
        
        if (!readHeader())
        {
            return false;
        }
        if (!readIndex())
        {
           return false; 
        }
        return true;
    }

    public int getTotalBlocks() {
        return header.getTotalBlocks();
    }

    public byte[] getBlock(int sector) {
        try {
            long pos = header.getIndex()[sector].longValue() << header.getAlign().intValue();
            int size = 0;
            if (header.getCompressed()[sector]) {
                size = (int) ((header.getIndex()[sector + 1].longValue() <<  header.getAlign().intValue()) - pos);
            } else {
                size = (int) header.getBlockSize().longValue();
            }
            byte[] compResult = new byte[size];
            file.seek(pos);
            file.readFully(compResult);
            
            if (header.getCompressed()[sector])
            {
                    Inflater inf = new Inflater(true);
                    inf.setInput(compResult);
                    byte[] result = new byte[header.getBlockSize().intValue()];
                    inf.inflate(result);
                    inf.end();
                    return result;
            } else
            {
                return compResult;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CisoReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataFormatException ex) {
            Logger.getLogger(CisoReader.class.getName()).log(Level.SEVERE, null, ex);
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
