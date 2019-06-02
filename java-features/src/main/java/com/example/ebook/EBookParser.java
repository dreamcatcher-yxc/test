package com.example.ebook;

import java.io.File;
import java.io.IOException;

public abstract class EBookParser {

    private StringBuffer content = new StringBuffer();

    public EBookParser(File file) throws IOException {

    }

    public static void main(String[] args) {
        byte[] buffs, buffs2;
        buffs = new byte[30900 * 1024];
        buffs2 = new byte[30900*1024];
    }

}
