package com.cnt.model;

public class Coin {
	private String amt;
	private String[] price;
	private String[] priceCnt;
	
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String[] getPrice() {
		return price;
	}
	public void setPrice(String[] price) {
		this.price = price;
	}
	public String[] getPriceCnt() {
		return priceCnt;
	}
	public void setPriceCnt(String[] priceCnt) {
		this.priceCnt = priceCnt;
	}
}
