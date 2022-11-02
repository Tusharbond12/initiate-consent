package com.demo.controller;

import com.demo.feignClient.FeignService;
import com.demo.request.RequestObjectReport;
import com.demo.utilityFunctionsService.SendFileToBucket;
import com.demo.utilityFunctionsService.UnzipExtractXML;
import com.demo.utilityFunctionsService.XmlToJson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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

@Slf4j
@RestController
public class FetchReportController {
    @Autowired
    FeignService feignService;
    @Autowired
    SendFileToBucket sendFileToBucket;
    @Autowired
     XmlToJson xmlToJson;
    @Autowired
    UnzipExtractXML unzipExtractXML;
    @Value("gs://${gcs-resource-test-bucket}")
    private Resource gcsFile;

    @PostMapping("/writeFile")
    public String writeGcs(@RequestBody String data) throws IOException {

        try (OutputStream os = ((WritableResource) this.gcsFile).getOutputStream()) {
            os.write(data.getBytes());
        }
            return "file was updated\n";
    }
    @GetMapping("/send-data")
    public String sendData(@RequestBody RequestObjectReport requestObjectReport) throws IOException
    {
        String txnId=requestObjectReport.getTxnId();
        String bucket="my-deployment-bucket";
        return sendFileToBucket.sendFileToGoogleBucket(bucket,txnId);
    }
    @GetMapping("/getFile")
    public String readGcsFile() throws IOException {

        return StreamUtils.copyToString(this.gcsFile.getInputStream(), Charset.defaultCharset()) + "\n";
    }
    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hellooooo ";
    }
    @PostMapping("/fetchReport")
    public ResponseEntity<Object> getResponse(@RequestBody RequestObjectReport requestObject, @RequestHeader(name = "api_key") String api_key,
                                              @RequestHeader(name = "org_id") String org_id) {
        byte[] perfiosData = feignService.getResponse(requestObject, api_key, org_id);
        unzipExtractXML.unzipAndExtractXMLReports(perfiosData, requestObject.getTxnId());
        JSONObject obj=xmlToJson.xmlFileToJson(requestObject.getTxnId());
        JSONArray jsArr = new JSONArray();
        return new ResponseEntity<>(obj.toMap(), HttpStatus.OK);
    }}