package com.demo.controller;

import com.demo.feignClient.FeignService;
import com.demo.request.RequestObject;
import com.demo.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsentController {

    @Autowired
    FeignService feignService;

    @PostMapping("/createConsent")
    public ResponseObject getConsent(@RequestBody RequestObject requestObject ,@RequestHeader(name = "api_key") String api_key,
                                      @RequestHeader(name = "org_id") String org_id)
    {
        return feignService.getConsent(requestObject,api_key,org_id);
    }

    @RequestMapping("/hello")
    public String hello()
    {
        return "Welcome to New Code version 5 ";
    }


}
