package com.demo.response;


import lombok.Data;

@Data
public class ResponseObject {
  
	String consentHandle;
	
	String status;
	
	String redirectURL;
	
	String txnId;

	public String getConsentHandle() {
		return consentHandle;
	}

	public void setConsentHandle(String consentHandle) {
		this.consentHandle = consentHandle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
}
