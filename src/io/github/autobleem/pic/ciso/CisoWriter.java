/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.ciso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;

/**
 *
 * @author screemer
 */
public class CisoWriter {
    private CisoHeader header;
    private long isoSize;
    private int compressionLevel;
    private URandomAccessFile file;
    private int blockNum=0;
    
    public boolean isClosed() {
           return !file.getChannel().isOpen();
    }
    
    public void fillHeader()
    {
        header = new CisoHeader();
        header.setMagic("CISO");
        header.setHeaderSize(UInteger.valueOf(0x18));
        header.setTotalBytes(ULong.valueOf(isoSize));
        header.setBlockSize(UInteger.valueOf(2048));
        header.setVer(UByte.valueOf(1));
        header.setAlign(UByte.valueOf(0));
        header.setRes0(UByte.valueOf(0));
        header.setRes1(UByte.valueOf(0));
        
        header.setTotalBlocks(header.getTotalBytes().toBigInteger().divide(new BigInteger(header.getBlockSize().toString())).intValue());
        header.setIndexSize((header.getTotalBlocks()+1)*4);
        
        header.setIndex(new UInteger[header.getTotalBlocks()+1]);
        header.setCompressed(new boolean[header.getTotalBlocks()+1]);
        
        
        
    }

    public boolean init(String filename, int compressionLevel, long isoSize) {
        File f = new File(filename);
        if (f.exists())
        {
            f.delete();
        }
        f = null;
        this.compressionLevel = compressionLevel;
        this.isoSize = isoSize;
        fillHeader();
        
        
        
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


