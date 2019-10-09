package com.sdkdjn.atmbms.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TradingRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tId;
	private String uId;
	private String relateuId;
	private Timestamp operationData;
	private String businessType;
	private double operationAmount;

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getRelateuId() {
		return relateuId;
	}

	public void setRelateuId(String relateuId) {
		this.relateuId = relateuId;
	}

	public Timestamp getOperationData() {
		return operationData;
	}

	public void setOperationData(Timestamp operationData) {
		this.operationData = operationData;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getOperationAmount() {
		return operationAmount;
	}

	public void setOperationAmount(double operationAmount) {
		this.operationAmount = operationAmount;
	}
}