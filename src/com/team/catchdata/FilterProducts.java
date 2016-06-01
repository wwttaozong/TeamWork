package com.team.catchdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.team.util.ProductDetail;
import com.team.window.MainWindow;

/**
 * 用于筛选网络上的信息
 * 
 * @author wwt
 *
 */
public class FilterProducts {

	// 基础URL
	private static String baseUrl = "https://list.lu.com/list/transfer-p2p"
			+ "?minMoney=&maxMoney=&minDays=&maxDays=&minRate=&maxRate="
			+ "&mode=&tradingMode=&isCx=&currentPage=&orderCondition="
			+ "&isShared=&canRealized=&productCategoryEnum=";

	/**
	 * 提供给外部的接口，传入相关参数并获取网页内容
	 * 
	 * @param pageNumber页数
	 * @param minMoney金额下限
	 * @param maxMoney金额上限
	 * @param minDays日期下限
	 * @param maxDays日期上限
	 * @param minRate利率下限
	 * @param maxRate利率上限
	 * @return
	 * @throws IOException
	 */
	public static List<ProductDetail> getFilterContents(int pageNumber,
			int minMoney, int maxMoney, int minDays, int maxDays,
			double minRate, double maxRate) throws IOException {
		String url = getFilterURL(pageNumber, minMoney, maxMoney, minDays,
				maxDays, minRate, maxRate);
		return getContentsFromURL(url);
	}

	/**
	 * 拿到用于获取数据的url
	 * 
	 * @param pageNumber页数
	 * @param minMoney金额下限
	 * @param maxMoney金额上限
	 * @param minDays日期下限
	 * @param maxDays日期上限
	 * @param minRate利率下限
	 * @param maxRate利率上限
	 * @return 返回构造出来的URL
	 */
	public static String getFilterURL(int pageNumber, int minMoney,
			int maxMoney, int minDays, int maxDays, double minRate,
			double maxRate) {

		// 判断参数是否合法
		if (pageNumber <= 0) {
			JOptionPane
					.showMessageDialog(MainWindow.window, "非法页数，请重新确认您的筛选条件");
			return null;
		}
		if (minMoney > maxMoney) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"非法投资金额区间，请重新确认您的筛选条件");
			return null;
		}
		if (minDays > maxDays) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"非法投资期限区间，请重新确认您的筛选条件");
			return null;
		}
		if (minRate > maxRate) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"非法收益率区间，请重新确认您的筛选条件");
			return null;
		}

		String sPageNumber = pageNumber == 0 ? "" : pageNumber + "";
		String sMinMoney = minMoney == 0 ? "" : minMoney + "";
		String sMaxMoney = maxMoney == 0 ? "" : maxMoney + "";
		String sMinDays = minDays == 0 ? "" : minDays + "";
		String sMaxDays = maxDays == 0 ? "" : maxDays + "";
		String sMinRate = (minRate / 1.0) == 0.0 ? "" : minRate / 100.0 + "";
		String sMaxRate = (maxRate / 1.0) == 0.0 ? "" : maxRate / 100.0 + "";

		String[] parts = baseUrl.split("&currentPage=");
		String url = parts[0] + "&currentPage=" + sPageNumber + parts[1];
		parts = url.split("minMoney=&maxMoney=");
		url = parts[0] + "minMoney=" + sMinMoney + "&maxMoney=" + sMaxMoney
				+ parts[1];
		parts = url.split("&minDays=&maxDays=");
		url = parts[0] + "&minDays=" + sMinDays + "&maxDays=" + sMaxDays
				+ parts[1];
		parts = url.split("&minRate=&maxRate=");
		url = parts[0] + "&minRate=" + sMinRate + "&maxRate=" + sMaxRate
				+ parts[1];
		return url;
	}

	/**
	 * 用于从指定的URL获取内容
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static List<ProductDetail> getContentsFromURL(String url) {
		// 非法参数可能会返回空的url
		if (url == null) {
			return null;
		}
		List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
		Document document = null;
		try {
			document = Jsoup.connect(url).timeout(5000).get();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"项目信息拉取失败，请检查您的网络连接");
		}
		// 项目名
		Elements productNames = document.getElementsByClass("product-name");
		// 收益率
		Elements interestRates = document.getElementsByClass("interest-rate");
		// 剩余期限
		Elements investPeriods = document.getElementsByClass("invest-period");
		// 起投金额
		Elements productAmounts = document.getElementsByClass("product-amount");
		for (int i = 0; i < productNames.size(); i++) {
			String productName = productNames.get(i).child(0).text();
			String productAmount = productAmounts.get(i).child(1).text();
			String interestRate = interestRates.get(i).child(1).text();
			String investPeriod = investPeriods.get(i).child(1).text();
			ProductDetail productDetail = new ProductDetail(productName,
					interestRate, investPeriod, productAmount);
			productDetails.add(productDetail);
		}
		return productDetails;
	}

}
