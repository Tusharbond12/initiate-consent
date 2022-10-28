package com.hdfc.request;

import lombok.*;

import java.util.List;

@Data
public class RequestObject {


	String profileId;
	
	String userId;
	 
	List<String> fiTypes;
	
	String txnId;

	public String getProfileId() {return profileId;}

	public List<String> getFiTypes() {
		return fiTypes;
	}

	public void setFiTypes(List<String> fiTypes) {
		this.fiTypes = fiTypes;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getUserId() {return userId;}

	public void setUserID(String userID) {
		this.userId = userId;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
}
