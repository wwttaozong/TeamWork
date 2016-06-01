package com.team.incomestatistics;

import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * 绘制收益直观图
 * 
 * @author wwt
 *
 */
public class TimeSeriesChart {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Set<String> productsSet = new HashSet<String>();
	// 存储项目的初始投资金额
	private static Map<String, Double> initMoney = new HashMap<String, Double>();
	// 存储项目的初始投资时间
	private static Map<String, Date> initDate = new HashMap<String, Date>();
	// 初始投资时间
	private static Date initDay;

	// 存储收益的值
	private static Map<Date, Double> incomes = new HashMap<Date, Double>();
	// 存储投资的值
	private static Map<Date, Double> invests = new HashMap<Date, Double>();
	// 存储收益率
	private static Map<Date, Double> interests = new HashMap<Date, Double>();

	ChartPanel panel;

	public ChartPanel drawIncomeGraph(List<InvestRecord> records) {
		XYDataset dataSet = collectIncomeData(records);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("收益统计图", "日期",
				"收益", dataSet, true, true, true);
		XYPlot xyPlot = chart.getXYPlot();
		// 获取时间轴
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
		panel = new ChartPanel(chart, true);
		// 标题字体
		dateAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));
		dateAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
		// 获取“值”轴
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
		return panel;
	}

	public ChartPanel drawInvestGraph(List<InvestRecord> records) {
		XYDataset dataSet = collectInvestData(records);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("投资统计图", "日期",
				"投资金额", dataSet, true, true, true);
		XYPlot xyPlot = chart.getXYPlot();
		// 获取时间轴
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
		panel = new ChartPanel(chart, true);
		// 标题字体
		dateAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));
		dateAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
		// 获取“值”轴
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
		return panel;
	}

	public ChartPanel drawInterestGraph(List<InvestRecord> records) {
		XYDataset dataSet = collectInterestData(records);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("收益率统计图", "日期",
				"收益率", dataSet, true, true, true);
		XYPlot xyPlot = chart.getXYPlot();
		// 获取时间轴
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
		panel = new ChartPanel(chart, true);
		// 标题字体
		dateAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));
		dateAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
		// 获取“值”轴
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
		return panel;
	}

	/**
	 * 获取曲线上的数据-收益
	 * 
	 * @param map
	 * @return
	 */
	private XYDataset collectIncomeData(List<InvestRecord> records) {

		// 获取各个项目的初始投资值，投资日期
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			String id = records.get(i).getProduct();
			// 判断还没“碰到”此项目，第一次碰见必然是购买
			if (!productsSet.contains(id)) {
				productsSet.add(id);
				initMoney.put(id, record.getMoney());
				initDate.put(id, InvestRecord.formateDate(record.getDate()));
			}
		}
		Map<Date, Double> map = getIncomeValue(records);
		TimeSeries timeseries = new TimeSeries("收益统计曲线",
				org.jfree.data.time.Day.class);
		for (Date date : map.keySet()) {
			String s = sdf.format(date);
			int year = Integer.parseInt(s.substring(0, 4));
			int month = Integer.parseInt(s.substring(5, 7));
			int day = Integer.parseInt(s.substring(8, 10));
			timeseries.add((new Day(day, month, year)), map.get(date));
		}

		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		return timeseriescollection;
	}

	/**
	 * 获取投资曲线上的数据
	 * 
	 * @param records
	 * @return
	 */
	private XYDataset collectInvestData(List<InvestRecord> records) {
		// 获取各个项目的初始投资值，投资日期
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			String id = records.get(i).getProduct();
			// 判断还没“碰到”此项目，第一次碰见必然是购买
			if (!productsSet.contains(id)) {
				productsSet.add(id);
				initMoney.put(id, record.getMoney());
				initDate.put(id, InvestRecord.formateDate(record.getDate()));
			}
		}
		Map<Date, Double> map = getInvestmentValue(records);
		TimeSeries timeseries = new TimeSeries("投资统计曲线",
				org.jfree.data.time.Day.class);
		for (Date date : map.keySet()) {
			String s = sdf.format(date);
			int year = Integer.parseInt(s.substring(0, 4));
			int month = Integer.parseInt(s.substring(5, 7));
			int day = Integer.parseInt(s.substring(8, 10));
			timeseries.add((new Day(day, month, year)), map.get(date));
		}
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		return timeseriescollection;
	}

	/**
	 * 获取收益率曲线上的数据
	 * 
	 * @param records
	 * @return
	 */
	private XYDataset collectInterestData(List<InvestRecord> records) {
		// 获取各个项目的初始投资值，投资日期
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			String id = records.get(i).getProduct();
			// 判断还没“碰到”此项目，第一次碰见必然是购买
			if (!productsSet.contains(id)) {
				productsSet.add(id);
				initMoney.put(id, record.getMoney());
				initDate.put(id, InvestRecord.formateDate(record.getDate()));
			}
		}
		Map<Date, Double> map = getInterestValue(records);
		TimeSeries timeseries = new TimeSeries("收益统计曲线",
				org.jfree.data.time.Day.class);
		for (Date date : map.keySet()) {
			String s = sdf.format(date);
			int year = Integer.parseInt(s.substring(0, 4));
			int month = Integer.parseInt(s.substring(5, 7));
			int day = Integer.parseInt(s.substring(8, 10));
			timeseries.add((new Day(day, month, year)), map.get(date));
		}
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		return timeseriescollection;
	}

	/**
	 * 获取收益值
	 * 
	 * @throws ParseException
	 */
	public Map<Date, Double> getIncomeValue(List<InvestRecord> records) {
		// 按照时间排序
		Collections.sort(records);
		try {
			String s = records.get(0).getDate();
			s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-"
					+ s.substring(6, 8);
			initDay = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 计算对应日期的收入
		Date temp = InvestRecord.formateDate(records.get(0).getDate());
		double sum = 0.0;
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			Date date = InvestRecord.formateDate(record.getDate());
			if (date.compareTo(temp) == 0) {
				if (record.getOperation().equals("购买")) {
					int duration = date.getDay()
							- initDate.get(record.getProduct()).getDay();
					double money = initMoney.get(record.getProduct());
					if (duration != 0) {
						sum = sum
								+ (money * (1 + 0.084) * duration / 365 - money);
					}
				} else if (record.getOperation().equals("赎回")) {
					sum += record.getMoney()
							- initMoney.get(record.getProduct());
				}
			} else {
				incomes.put(temp, sum);
				temp = date;
				sum = 0.0;
				i--;
			}
		}
		incomes.put(temp, sum);
		return incomes;
	}

	public Map<Date, Double> getInvestmentValue(List<InvestRecord> records) {
		// 按照时间排序
		Collections.sort(records);
		// 计算对应日期的收入
		Date temp = InvestRecord.formateDate(records.get(0).getDate());
		double sum = 0.0;
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			Date date = InvestRecord.formateDate(record.getDate());
			if (date.compareTo(temp) == 0) {
				if (record.getOperation().equals("购买")) {
					sum += record.getMoney();
				} else if (record.getOperation().equals("赎回")) {
					sum -= initMoney.get(record.getProduct());
				}
			} else {
				invests.put(temp, sum);
				temp = date;
				i--;
			}
		}
		invests.put(temp, sum);
		return invests;
	}

	/*
	 * 获取收益率
	 */
	public Map<Date, Double> getInterestValue(List<InvestRecord> records) {
		incomes = getIncomeValue(records);
		for (Date date : incomes.keySet()) {
			System.out.println("date:" + date.getDay());
			System.out.println("initDay:" + initDay.getDay());
			int duration = initDay.getDay() - date.getDay();
			double income = incomes.get(date);
			double interest = income / 100000 / duration * 365;
			interests.put(date, interest);
		}
		return interests;
	}

}
