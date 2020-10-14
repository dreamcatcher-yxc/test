package com.example.decompile;

public final class EncryptionUtils
{
    public static byte[] decrypt(final String s, final byte[] array, final int n) {
        final byte[] bytes = s.getBytes();
        final byte[] array2 = new byte[256];
        int n2 = 0;
        for (int i = 0; i < 256; ++i) {
            array2[i] = (byte)i;
        }
        for (int j = 0; j < 256; ++j) {
            n2 = (n2 + array2[j] + bytes[j % bytes.length] & 0xFF);
            final byte b = array2[j];
            array2[j] = array2[n2];
            array2[n2] = b;
        }
        int n3 = 0;
        int n4 = 0;
        byte b3;
        int n5;
        for (int k = 0; k < n; n5 = k++, array[n5] ^= b3) {
            n3 = (n3 + 1 & 0xFF);
            n4 = (n4 + array2[n3] & 0xFF);
            final byte b2 = array2[n3];
            array2[n3] = array2[n4];
            array2[n4] = b2;
            b3 = array2[b2 + array2[n3] & 0xFF];
        }
        return array;
    }
}
