package com.demo.utilityFunctionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Service
public class UnzipExtractXML {

    @Autowired
    PathCreator pathCreator;
    public String unzipAndExtractXMLReports(byte[] data, String txnID) {
        ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = null;

        try {
            File f = pathCreator.xmlDir(txnID);
            if (f.mkdirs()) {
                while ((entry = zipStream.getNextEntry()) != null) {

                    String entryName = entry.getName();

                    System.out.println("Entry name" + entryName);

                    File f2 = new File(f, entryName);

                    FileOutputStream out = new FileOutputStream(f2);

                    byte[] byteBuff = new byte[4096];
                    int bytesRead = 0;
                    while ((bytesRead = zipStream.read(byteBuff)) != -1) {
                        out.write(byteBuff, 0, bytesRead);
                    }

                    out.close();
                }
                zipStream.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        try {
            zipStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Done";
    }

}
