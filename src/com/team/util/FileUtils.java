package com.team.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * 用于对项目数据进行文件的读写操作
 * 
 * @author wwt
 *
 */
public class FileUtils {

	// 数据的本地存放路径
	private static final String DES_PATH = "C:/Users/Administrator/Desktop/data.txt";

	/**
	 * 将记录写入文件
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
