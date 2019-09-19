/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.ciso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

/**
 *
 * @author screemer
 */
public class URandomAccessFile extends RandomAccessFile {

    public URandomAccessFile(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

    public URandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }

    // 8 bit 
    public UByte readUByte() throws IOException {
        int byteVal = readUnsignedByte();
        return UByte.valueOf(byteVal);
    }
    public void writeUByte(UByte value) {
        try {
            byte[] input = new byte[1];
            input[0] = (byte) (value.intValue() & 0xFF);
            write(input);
        } catch (IOException ex) {
            Logger.getLogger(URandomAccessFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // 16 bit
    public UShort readUShort() throws IOException {
        int shortVal = readUnsignedShort();
        return UShort.valueOf(shortVal);
    }

    // 32 bit 
    public UInteger readUInt() throws IOException {
        byte[] input = new byte[4];
        input[0] = readByte();
        input[1] = readByte();
        input[2] = readByte();
        input[3] = readByte();
        long x = 0xFF & input[0];
        x |= (0xFF & input[1]) << 8L;
        x |= (0xFF & input[2]) << 16L;
        x |= ((long) 0xFF & input[3]) << 24L;

        UInteger result = UInteger.valueOf(x);
        return result;

    }

    public void writeUInt(UInteger value) {
        try {
            byte[] input = new byte[4];
            input[0] = (byte) (value.longValue() & 0xFF);
            input[1] = (byte) ((value.longValue() >> 8) & 0xFF);
            input[2] = (byte) ((value.longValue() >> 16) & 0xFF);
            input[3] = (byte) ((value.longValue() >> 24) & 0xFF);
            write(input);
        } catch (IOException ex) {
            Logger.getLogger(URandomAccessFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // 64 bit 
    public ULong readULong() throws IOException {
        byte[] input = new byte[8];
        input[7] = readByte();
        input[6] = readByte();
        input[5] = readByte();
        input[4] = readByte();
        input[3] = readByte();
        input[2] = readByte();
        input[1] = readByte();
        input[0] = readByte();
        ULong result = ULong.valueOf(new BigInteger(input));
        return result;
    }

    public void writeULong(ULong value) {
        try {
            byte[] input = new byte[8];
            input[0] = (byte) (value.longValue() & 0xFF);
            input[1] = (byte) ((value.longValue() >> 8) & 0xFF);
            input[2] = (byte) ((value.longValue() >> 16) & 0xFF);
            input[3] = (byte) ((value.longValue() >> 24) & 0xFF);
            input[4] = (byte) ((value.longValue() >> 32) & 0xFF);
            input[5] = (byte) ((value.longValue() >> 40) & 0xFF);
            input[6] = (byte) ((value.longValue() >> 48) & 0xFF);
            input[7] = (byte) ((value.longValue() >> 56) & 0xFF);

            write(input);
        } catch (IOException ex) {
            Logger.getLogger(URandomAccessFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // String
    public String readString(int chars) throws IOException {
        String s = "";
        for (int i = 0; i < chars; i++) {
            byte c = readByte();
            s = s + Character.toString((char) c);
        }
        return s;
    }
}
