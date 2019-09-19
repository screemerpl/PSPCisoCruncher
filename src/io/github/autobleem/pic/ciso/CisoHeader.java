package io.github.autobleem.pic.ciso;

import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;

public class CisoHeader {
    // FileData
    private String magic = "";
    private UInteger headerSize=UInteger.valueOf(0);
    private ULong totalBytes=ULong.valueOf(0);
    private UInteger blockSize=UInteger.valueOf(0);
    private UByte ver=UByte.valueOf(0);
    private UByte align=UByte.valueOf(0);;
    private UByte res0=UByte.valueOf(0);;
    private UByte res1=UByte.valueOf(0);;

    // calculated
    private int totalBlocks=0;
    private int indexSize=0;

    // blockIndex
    private UInteger[] index;
    private boolean[] compressed;

    /**
     * @return the magic
     */
    public String getMagic() {
        return magic;
    }

    /**
     * @param magic the magic to set
     */
    public void setMagic(String magic) {
        this.magic = magic;
    }

    /**
     * @return the headerSize
     */
    public UInteger getHeaderSize() {
        return headerSize;
    }

    /**
     * @param headerSize the headerSize to set
     */
    public void setHeaderSize(UInteger headerSize) {
        this.headerSize = headerSize;
    }

    /**
     * @return the totalBytes
     */
    public ULong getTotalBytes() {
        return totalBytes;
    }

    /**
     * @param totalBytes the totalBytes to set
     */
    public void setTotalBytes(ULong totalBytes) {
        this.totalBytes = totalBytes;
    }

    /**
     * @return the blockSize
     */
    public UInteger getBlockSize() {
        return blockSize;
    }

    /**
     * @param blockSize the blockSize to set
     */
    public void setBlockSize(UInteger blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * @return the ver
     */
    public UByte getVer() {
        return ver;
    }

    /**
     * @param ver the ver to set
     */
    public void setVer(UByte ver) {
        this.ver = ver;
    }

    /**
     * @return the align
     */
    public UByte getAlign() {
        return align;
    }

    /**
     * @param align the align to set
     */
    public void setAlign(UByte align) {
        this.align = align;
    }

    /**
     * @return the res0
     */
    public UByte getRes0() {
        return res0;
    }

    /**
     * @param res0 the res0 to set
     */
    public void setRes0(UByte res0) {
        this.res0 = res0;
    }

    /**
     * @return the res1
     */
    public UByte getRes1() {
        return res1;
    }

    /**
     * @param res1 the res1 to set
     */
    public void setRes1(UByte res1) {
        this.res1 = res1;
    }

    /**
     * @return the totalBlocks
     */
    public int getTotalBlocks() {
        return totalBlocks;
    }

    /**
     * @param totalBlocks the totalBlocks to set
     */
    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    /**
     * @return the indexSize
     */
    public int getIndexSize() {
        return indexSize;
    }

    /**
     * @param indexSize the indexSize to set
     */
    public void setIndexSize(int indexSize) {
        this.indexSize = indexSize;
    }

    /**
     * @return the index
     */
    public UInteger[] getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(UInteger[] index) {
        this.index = index;
    }

    /**
     * @return the compressed
     */
    public boolean[] getCompressed() {
        return compressed;
    }

    /**
     * @param compressed the compressed to set
     */
    public void setCompressed(boolean[] compressed) {
        this.compressed = compressed;
    }

  


}
