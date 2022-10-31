package com.demo.controller;

import com.demo.feignClient.FeignService;
import com.demo.request.RequestObject;
import com.demo.request.RequestObjectReport;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.demo.constants.Constants.OUT_DIR;

@Slf4j
@RestController
public class FetchReportController {



    @Autowired
    FeignService feignService;

    @Value("gs://${gcs-resource-test-bucket}/FirstProgram.java")
    private Resource gcsFile;


    @PostMapping("/writeFile")
    public String writeGcs(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) this.gcsFile).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "file was updated\n";
    }

    @GetMapping("/getFile")
    public String readGcsFile() throws IOException {
        return StreamUtils.copyToString(this.gcsFile.getInputStream(), Charset.defaultCharset()) + "\n";
    }

    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hellooooo ";
    }

    // cee922276e91f9109d8f01a1eff1370dd151e995a69090f266a43220773a8770
    // demo


    @PostMapping("/fetchReport")
    public ResponseEntity<Object> getResponse(@RequestBody RequestObjectReport requestObject, @RequestHeader(name = "api_key") String api_key,
                                              @RequestHeader(name = "org_id") String org_id) {
        byte[] perfiosData = feignService.getResponse(requestObject, api_key, org_id);
        // System.out.print(perfiosData);

        unzipAndExtractXMLReports(perfiosData, requestObject.getTxnId());
        JSONObject obj=xmlFileToJson(requestObject.getTxnId());
        JSONArray jsArr = new JSONArray();
        jsArr.put(obj.toMap());
        return new ResponseEntity<>(obj.toMap(), HttpStatus.OK);



    }

    private File xmlDir(String txnId) {
        String path = OUT_DIR + File.separator + txnId + File.separator;

        File xmlPath = new File(path + "xml");

        return xmlPath;


    }

    // Method to convert all XML files intoJson
    private JSONObject xmlFileToJson(String txnId) {

        File xmlPath = xmlDir(txnId);
        JSONObject obj=new JSONObject();
        if (xmlPath.exists()) {
            File jsonDir = createJSONPath(txnId);

            System.out.println("list of files " + Arrays.toString(xmlPath.list()));

            log.debug("list of files " + Arrays.toString(xmlPath.list()));

            String[] xmlFiles = xmlPath.list(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    // TODO Auto-generated method stub
                    return name.endsWith(".xml");
                }
            });
//        String jsonFileName = "/Users/demo/Downloads/fetch-report-service/json";
//        String fileName = "/Users/demo/Downloads/fetch-report-service/" + txnId + "_acme-fip_xxxxxxxx";

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

    private File createJSONPath(String txnId) {
        String basePath = OUT_DIR + File.separator + txnId + File.separator;
        File jsonPath = new File(basePath + "json");
        boolean dirExists = jsonPath.exists() ? true : jsonPath.mkdirs();
        return jsonPath;
    }

    private String unzipAndExtractXMLReports(byte[] data, String txnID) {

        ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = null;

        try {
            File f = xmlDir(txnID);
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