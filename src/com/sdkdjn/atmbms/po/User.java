package com.sdkdjn.atmbms.po;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uId;
	private String name;
	private String password = "123456";
	private double remainingSum = 10000.0;

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getRemainingSum() {
		return remainingSum;
	}

	public void setRemainingSum(double remainingSum) {
		this.remainingSum = remainingSum;
	}

}
