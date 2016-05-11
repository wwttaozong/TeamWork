package com.team.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * ���ڶ���Ŀ���ݽ����ļ��Ķ�д����
 * 
 * @author wwt
 *
 */
public class FileUtils {

	// ���ݵı��ش��·��
	private static final String DES_PATH = "C:/Users/Administrator/Desktop/data.txt";

	/**
	 * ����¼д���ļ�
	 * 
	 * @param productDetails
	 * @throws IOException
	 */
	public static void writeToFile(ArrayList<ProductDetail> productDetails)
			throws IOException {
		PrintStream printStream = new PrintStream(
				new FileOutputStream(DES_PATH));
		for (ProductDetail productDetail : productDetails) {
			printStream.println(productDetail.getProductName());
			printStream.println(productDetail.getInterestRate());
			printStream.println(productDetail.getInvestPeriod());
			printStream.println(productDetail.getMinimumMoney());
			printStream.println();
		}
		printStream.close();
	}

	public static void readFromFile() {

	}

}
