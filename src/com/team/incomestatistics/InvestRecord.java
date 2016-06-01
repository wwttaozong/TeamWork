package com.team.incomestatistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用于存储数据结构
 * 
 * @author wwt
 *
 */
public class InvestRecord implements Comparable<InvestRecord> {

	private String date;
	private String operation;
	private double money;
	private String product;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static List<InvestRecord> records = new ArrayList<InvestRecord>();

	public InvestRecord() {

	}

	public InvestRecord(String date, String operation, double money,
			String product) {
		super();
		this.date = date;
		this.operation = operation;
		this.money = money;
		this.product = product;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "InvestRecord [date=" + date + ", operation=" + operation
				+ ", money=" + money + ", product=" + product + "]";
	}

	@Override
	public int compareTo(InvestRecord o) {
		Date date1 = formateDate(o.getDate());
		Date date2 = formateDate(date);
		return date2.compareTo(date1);
	}

	/**
	 * 将字符串格式化为Date格式
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static Date formateDate(String s) {
		Date date = null;
		s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-"
				+ s.substring(6, 8);
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			System.out.println("日期格式错误");
		}
		return date;
	}

}
