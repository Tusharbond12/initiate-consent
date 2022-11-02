package com.demo.utilityFunctionsService;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.demo.constants.Constants.OUT_DIR;

@Service
public class SendFileToBucket
{
    @Autowired
    private Storage storage;

    public String sendFileToGoogleBucket( String bucket , String txnId  ) throws IOException
    {

        String path = OUT_DIR + File.separator + txnId + File.separator;
        File jsonPath = new File(path + "json");
        System.out.println(jsonPath);
        String[] jsonFiles=jsonPath.list();
        if(jsonFiles == null )
        {
            return "Code Failed";
        }
        for(String jsonFile:jsonFiles )
        {
            BlobId blobId=BlobId.of(bucket,path+"json"+File.separator+jsonFile);
            BlobInfo info=BlobInfo.newBuilder(blobId).build();
            File file=new File(path+"json"+File.separator+jsonFile);
            byte[] arr= Files.readAllBytes(Paths.get(file.toURI()));
            storage.create(info,arr);

        }
        return "File Uploaded Successfully ";
    }


}
