package com.team.util;

/**
 * ��Ϊ��Ŀ����Ĵ洢�ṹ
 * 
 * @author wwt
 *
 */
public class ProductDetail {

	// ��Ŀ��
	private String productName;
	// ������
	private String interestRate;
	// ��Ŀʣ������
	private String investPeriod;
	// ��Ͷ���
	private String productAmount;

	public ProductDetail(String productName, String interestRate,
			String investPeriod, String productAmount) {
		super();
		this.productName = productName;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
		this.productAmount = productAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(String investPeriod) {
		this.investPeriod = investPeriod;
	}

	public String getMinimumMoney() {
		return productAmount;
	}

	public void setMinimumMoney(String minimumMoney) {
		this.productAmount = minimumMoney;
	}

	@Override
	public String toString() {
		return "ProductDetail [productName=" + productName + ", interestRate="
				+ interestRate + ", investPeriod=" + investPeriod
				+ ", minimumMoney=" + productAmount + "]";
	}

}
