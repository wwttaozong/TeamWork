package com.team.catchdata;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.team.util.ProductDetail;

/**
 * ���ڴ�������ץȡȫ������
 * 
 * @author wwt
 *
 */
public class CatchProjectDetails {

	// �����������ڵĳ�ʼURL��ַ
	private static String basePath = "https://list.lu.com/list/transfer-p2p"
			+ "?minMoney=&maxMoney=&minDays=&maxDays=&minRate=&maxRate=&mode=&tradingMode="
			+ "&isCx=&currentPage=1&orderCondition=&isShared=&canRealized=&productCategoryEnum=";
	// ��ǰ������ҳ��ı��
	private static int currentPage = 1;
	// ��ǰ������ҳ���URL
	private static String srcPath;

	/**
	 * ��һ��URL�л�ȡ���йؼ����ݲ�����
	 * 
	 * @throws IOException
	 */
	public static ArrayList<ProductDetail> catchHTMLContent()
			throws IOException {
		ArrayList<ProductDetail> productDetails = new ArrayList<ProductDetail>();

		while (true) {
			// ��ȡ��ǰ׼��ץȡ��ҳ����������ڵ�URL
			srcPath = basePath.replace("currentPage=1", "currentPage="
					+ currentPage++);
			Document document = Jsoup.connect(srcPath).get();
			int size = document.getElementsByClass("product-name").size();
			if (size > 0) {
				// ��Ŀ��
				Elements productNames = document
						.getElementsByClass("product-name");
				// ������
				Elements interestRates = document
						.getElementsByClass("interest-rate");
				// ʣ������
				Elements investPeriods = document
						.getElementsByClass("invest-period");
				// ��Ͷ���
				Elements productAmounts = document
						.getElementsByClass("product-amount");
				for (int i = 0; i < productNames.size(); i++) {
					String productName = productNames.get(i).child(0).text();
					String productAmount = productAmounts.get(i).child(1)
							.text();
					String interestRate = interestRates.get(i).child(1).text();
					String investPeriod = investPeriods.get(i).child(1).text();
					ProductDetail productDetail = new ProductDetail(
							productName, interestRate, investPeriod,
							productAmount);
					productDetails.add(productDetail);
				}
			} else {
				break;
			}
		}
		return productDetails;
	}

}
