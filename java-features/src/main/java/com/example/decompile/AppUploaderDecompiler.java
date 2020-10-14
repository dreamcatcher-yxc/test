package com.example.decompile;

import cn.hutool.core.io.file.FileReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * ApplicationUploader jar 反编译
 */
public class AppUploaderDecompiler {


    public static void main(String[] args) throws IOException {
        List<File> allSubFile = FileUtil.getAllSubFile(new File("D:\\devInstall\\appuploader\\u\\net\\appuploader"));
        for (File file : allSubFile) {
            String name = file.getName();

            // 顾虑掉非 class 文件
            if(!name.endsWith(".class")) {
                return;
            }

            // 解密
            FileReader reader = new FileReader(file, "UTF-8");
            byte[] array2 = reader.readBytes();
            final String s2 = "jar:file://";
            final byte[] array3 = array2;
            array2 = EncryptionUtils.decrypt(s2, array3, array3.length);

            // 保存新生成的 class 文件
            String absolutePath = file.getAbsolutePath();
            int ti = absolutePath.lastIndexOf(".");
            String newFileName = absolutePath.substring(0, ti) + "2" + ".class";
            FileOutputStream out = new FileOutputStream(newFileName, false);
            out.write(array2);
            out.close();
        }
    }
}
