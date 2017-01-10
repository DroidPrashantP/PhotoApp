package com.app.paddycameraeditior.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtil {
    private static final int BUFFER_SIZE = 4096;

    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        for (ZipEntry entry = zipIn.getNextEntry(); entry != null; entry = zipIn.getNextEntry()) {
            String filePath = new StringBuilder(String.valueOf(destDirectory)).append(File.separator).append(entry.getName()).toString();
            if (entry.isDirectory()) {
                new File(filePath).mkdir();
            } else {
                extractFile(zipIn, filePath);
            }
            zipIn.closeEntry();
        }
        zipIn.close();
    }

    public static void extractFile(String zipFilePath, String inZipFilePath, String destDirectory) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            if (entry.isDirectory() || !inZipFilePath.equals(entry.getName())) {
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            } else {
                String filePath = entry.getName();
                int separatorIndex = filePath.lastIndexOf(File.separator);
                if (separatorIndex > -1) {
                    filePath = filePath.substring(separatorIndex + 1, filePath.length());
                }
                extractFile(zipIn, new StringBuilder(String.valueOf(destDirectory)).append(File.separator).append(filePath).toString());
                zipIn.close();
            }
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        while (true) {
            int read = zipIn.read(bytesIn);
            if (read == -1) {
                bos.close();
                return;
            }
            bos.write(bytesIn, 0, read);
        }
    }
}
