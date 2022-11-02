package com.demo.utilityFunctionsService;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;


@Slf4j
@Service
public class XmlToJson {



    @Autowired
    PathCreator pathCreator;
    public JSONObject xmlFileToJson(String txnId) {
        File xmlPath = pathCreator.xmlDir(txnId);
        JSONObject obj=new JSONObject();
        if (xmlPath.exists()) {
            File jsonDir = pathCreator.createJSONPath(txnId);

            System.out.println("list of files " + Arrays.toString(xmlPath.list()));

            log.debug("list of files " + Arrays.toString(xmlPath.list()));

            String[] xmlFiles = xmlPath.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    // TODO Auto-generated method stub
                    return name.endsWith(".xml");
                }
            });


            for (String xmlFile : xmlFiles) {
                String line = "", str = "";
                try {

                    BufferedReader reader = new BufferedReader(
                            new FileReader(xmlPath.getAbsolutePath() + File.separator + xmlFile));
                    while ((line = reader.readLine()) != null) {
                        str += line;
                    }
                    JSONObject jsondata = XML.toJSONObject(str);
                    // obj.put( jsonDir.getAbsolutePath(),jsondata);

                    System.out.println("writing to " + jsonDir.getAbsolutePath() + File.separator
                            + xmlFile.replace(".xml", ".json"));

                    FileWriter file = new FileWriter(
                            jsonDir.getAbsolutePath() + File.separator + xmlFile.replace(".xml", ".json"));
                    file.write(jsondata.toString());
                    file.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Code reached this part");
        return obj;

    }
}
