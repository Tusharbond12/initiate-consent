package com.hdfc.controller;

import com.hdfc.feignClient.FeignService;
import com.hdfc.request.RequestObject;
import com.hdfc.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsentController {

    @Autowired
    FeignService feignService;

    @PostMapping("/createConsent")
    public ResponseObject getResponse(@RequestBody RequestObject requestObject)
    {
        return feignService.getResponse(requestObject,"cee922276e91f9109d8f01a1eff1370dd151e995a69090f266a43220773a8770","hdfc");
    }

    @RequestMapping("/hello")
    public String hello()
    {
        return "Hello World";
    }


}
