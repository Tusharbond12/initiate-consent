package com.demo.request;


import lombok.Data;

@Data
public class RequestObjectReport {

    String txnId;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }



}
