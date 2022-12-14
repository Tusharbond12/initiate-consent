package com.demo.feignClient;


import com.demo.request.RequestObject;
import com.demo.request.RequestObjectReport;
import com.demo.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="initiate-consent-service", url = "https://fiu.perfios.com")
public interface FeignService {

    @PostMapping(value="/fiu/process/initiateConsent")
    ResponseObject getConsent(@RequestBody RequestObject requestObject ,
                               @RequestHeader("api_key") String api_key,
                               @RequestHeader("org_id") String org_id);

    @PostMapping("/fiu/process/rawReport")
    byte[] getResponse(@RequestBody RequestObjectReport requestObject ,
                       @RequestHeader("api_key") String api_key,
                       @RequestHeader("org_id") String org_id);
}
