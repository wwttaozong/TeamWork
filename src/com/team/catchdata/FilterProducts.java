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
 * ����ɸѡ�����ϵ���Ϣ
 * 
 * @author wwt
 *
 */
public class FilterProducts {

	// ����URL
	private static String baseUrl = "https://list.lu.com/list/transfer-p2p"
			+ "?minMoney=&maxMoney=&minDays=&maxDays=&minRate=&maxRate="
			+ "&mode=&tradingMode=&isCx=&currentPage=&orderCondition="
			+ "&isShared=&canRealized=&productCategoryEnum=";

	/**
	 * �ṩ���ⲿ�Ľӿڣ�������ز�������ȡ��ҳ����
	 * 
	 * @param pageNumberҳ��
	 * @param minMoney�������
	 * @param maxMoney�������
	 * @param minDays��������
	 * @param maxDays��������
	 * @param minRate��������
	 * @param maxRate��������
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
	 * �õ����ڻ�ȡ���ݵ�url
	 * 
	 * @param pageNumberҳ��
	 * @param minMoney�������
	 * @param maxMoney�������
	 * @param minDays��������
	 * @param maxDays��������
	 * @param minRate��������
	 * @param maxRate��������
	 * @return ���ع��������URL
	 */
	public static String getFilterURL(int pageNumber, int minMoney,
			int maxMoney, int minDays, int maxDays, double minRate,
			double maxRate) {

		// �жϲ����Ƿ�Ϸ�
		if (pageNumber <= 0) {
			JOptionPane
					.showMessageDialog(MainWindow.window, "�Ƿ�ҳ����������ȷ������ɸѡ����");
			return null;
		}
		if (minMoney > maxMoney) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"�Ƿ�Ͷ�ʽ�����䣬������ȷ������ɸѡ����");
			return null;
		}
		if (minDays > maxDays) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"�Ƿ�Ͷ���������䣬������ȷ������ɸѡ����");
			return null;
		}
		if (minRate > maxRate) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"�Ƿ����������䣬������ȷ������ɸѡ����");
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
	 * ���ڴ�ָ����URL��ȡ����
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static List<ProductDetail> getContentsFromURL(String url) {
		// �Ƿ��������ܻ᷵�ؿյ�url
		if (url == null) {
			return null;
		}
		List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
		Document document = null;
		try {
			document = Jsoup.connect(url).timeout(5000).get();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(MainWindow.window,
					"��Ŀ��Ϣ��ȡʧ�ܣ�����������������");
		}
		// ��Ŀ��
		Elements productNames = document.getElementsByClass("product-name");
		// ������
		Elements interestRates = document.getElementsByClass("interest-rate");
		// ʣ������
		Elements investPeriods = document.getElementsByClass("invest-period");
		// ��Ͷ���
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
