package com.demo.utilityFunctionsService;

import org.springframework.stereotype.Service;

import java.io.File;

import static com.demo.constants.Constants.OUT_DIR;

@Service
public class PathCreator {

    public File createJSONPath(String txnId) {
        String basePath = OUT_DIR + File.separator + txnId + File.separator;
        File jsonPath = new File(basePath + "json");
        boolean dirExists = jsonPath.exists() ? true : jsonPath.mkdirs();
        return jsonPath;
    }

    public File xmlDir(String txnId) {
        String path = OUT_DIR + File.separator + txnId + File.separator;
        File xmlPath = new File(path + "xml");
        return xmlPath;
    }

}
