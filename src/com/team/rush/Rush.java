package com.team.rush;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.team.catchdata.FilterProducts;
import com.team.util.ProductDetail;

public class Rush extends Thread {

	private List<ProductDetail> rushProducts = new ArrayList<ProductDetail>();

	int sMinMoney;
	int sMaxMoney;
	int sMinDays;
	int sMaxDays;
	double sMinRate;
	double sMaxRate;

	public Rush(String sMinMoney, String sMaxMoney, String sMinDays,
			String sMaxDays, String sMinRate, String sMaxRate) {
		super();
		this.sMinMoney = sMinMoney.equals("") ? 0 : Integer.parseInt(sMinMoney);
		this.sMaxMoney = sMaxMoney.equals("") ? 0 : Integer.parseInt(sMaxMoney);
		this.sMinDays = sMinDays.equals("") ? 0 : Integer.parseInt(sMinDays);
		this.sMaxDays = sMaxDays.equals("") ? 0 : Integer.parseInt(sMaxDays);
		this.sMinRate = sMinRate.equals("") ? 0 : Double.parseDouble(sMinRate);
		this.sMaxRate = sMaxRate.equals("") ? 0 : Double.parseDouble(sMaxRate);
	}

	@Override
	public void run() {
		int tempPage = 1;
		try {
			while (true) {
				rushProducts = FilterProducts.getFilterContents(tempPage,
						sMinMoney, sMaxMoney, sMinDays, sMaxDays, sMinRate,
						sMaxRate);
				// 找到则打开网页
				if (rushProducts.size() != 0) {
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler "
									+ FilterProducts.getFilterURL(tempPage,
											sMinMoney, sMaxMoney, sMinDays,
											sMaxDays, sMinRate, sMaxRate));
					break;
				}
				tempPage++;
			}
		} catch (IOException e) {
			System.out.println("网络连接出错");
		}
		super.run();
	}

}
