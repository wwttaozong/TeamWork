package com.team.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

	private static final String SRC_PATH = "https://list.lu.com/list/transfer-p2p";
	private static final String DES_PATH = "C:/Users/Administrator/Desktop/data.txt";

	/**
	 * 从指定URL获取html文本内容
	 * 
	 * @throws IOException
	 */

	public static void catchHTML() throws IOException {
		URL url = new URL(SRC_PATH);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (connection.getResponseCode() == 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(DES_PATH));
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line + "\r\n";
				writer.write(line);
			}
			reader.close();
			writer.close();
		}
	}

}
