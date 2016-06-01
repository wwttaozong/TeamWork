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
 * ��������ֱ��ͼ
 * 
 * @author wwt
 *
 */
public class TimeSeriesChart {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Set<String> productsSet = new HashSet<String>();
	// �洢��Ŀ�ĳ�ʼͶ�ʽ��
	private static Map<String, Double> initMoney = new HashMap<String, Double>();
	// �洢��Ŀ�ĳ�ʼͶ��ʱ��
	private static Map<String, Date> initDate = new HashMap<String, Date>();
	// ��ʼͶ��ʱ��
	private static Date initDay;

	// �洢�����ֵ
	private static Map<Date, Double> incomes = new HashMap<Date, Double>();
	// �洢Ͷ�ʵ�ֵ
	private static Map<Date, Double> invests = new HashMap<Date, Double>();
	// �洢������
	private static Map<Date, Double> interests = new HashMap<Date, Double>();

	ChartPanel panel;

	public ChartPanel drawIncomeGraph(List<InvestRecord> records) {
		XYDataset dataSet = collectIncomeData(records);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("����ͳ��ͼ", "����",
				"����", dataSet, true, true, true);
		XYPlot xyPlot = chart.getXYPlot();
		// ��ȡʱ����
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
		panel = new ChartPanel(chart, true);
		// ��������
		dateAxis.setLabelFont(new Font("����", Font.BOLD, 14));
		dateAxis.setTickLabelFont(new Font("����", Font.BOLD, 12));
		// ��ȡ��ֵ����
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("����", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		return panel;
	}

	public ChartPanel drawInvestGraph(List<InvestRecord> records) {
		XYDataset dataSet = collectInvestData(records);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Ͷ��ͳ��ͼ", "����",
				"Ͷ�ʽ��", dataSet, true, true, true);
		XYPlot xyPlot = chart.getXYPlot();
		// ��ȡʱ����
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
		panel = new ChartPanel(chart, true);
		// ��������
		dateAxis.setLabelFont(new Font("����", Font.BOLD, 14));
		dateAxis.setTickLabelFont(new Font("����", Font.BOLD, 12));
		// ��ȡ��ֵ����
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("����", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		return panel;
	}

	public ChartPanel drawInterestGraph(List<InvestRecord> records) {
		XYDataset dataSet = collectInterestData(records);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("������ͳ��ͼ", "����",
				"������", dataSet, true, true, true);
		XYPlot xyPlot = chart.getXYPlot();
		// ��ȡʱ����
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
		panel = new ChartPanel(chart, true);
		// ��������
		dateAxis.setLabelFont(new Font("����", Font.BOLD, 14));
		dateAxis.setTickLabelFont(new Font("����", Font.BOLD, 12));
		// ��ȡ��ֵ����
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("����", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		return panel;
	}

	/**
	 * ��ȡ�����ϵ�����-����
	 * 
	 * @param map
	 * @return
	 */
	private XYDataset collectIncomeData(List<InvestRecord> records) {

		// ��ȡ������Ŀ�ĳ�ʼͶ��ֵ��Ͷ������
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			String id = records.get(i).getProduct();
			// �жϻ�û������������Ŀ����һ��������Ȼ�ǹ���
			if (!productsSet.contains(id)) {
				productsSet.add(id);
				initMoney.put(id, record.getMoney());
				initDate.put(id, InvestRecord.formateDate(record.getDate()));
			}
		}
		Map<Date, Double> map = getIncomeValue(records);
		TimeSeries timeseries = new TimeSeries("����ͳ������",
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
	 * ��ȡͶ�������ϵ�����
	 * 
	 * @param records
	 * @return
	 */
	private XYDataset collectInvestData(List<InvestRecord> records) {
		// ��ȡ������Ŀ�ĳ�ʼͶ��ֵ��Ͷ������
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			String id = records.get(i).getProduct();
			// �жϻ�û������������Ŀ����һ��������Ȼ�ǹ���
			if (!productsSet.contains(id)) {
				productsSet.add(id);
				initMoney.put(id, record.getMoney());
				initDate.put(id, InvestRecord.formateDate(record.getDate()));
			}
		}
		Map<Date, Double> map = getInvestmentValue(records);
		TimeSeries timeseries = new TimeSeries("Ͷ��ͳ������",
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
	 * ��ȡ�����������ϵ�����
	 * 
	 * @param records
	 * @return
	 */
	private XYDataset collectInterestData(List<InvestRecord> records) {
		// ��ȡ������Ŀ�ĳ�ʼͶ��ֵ��Ͷ������
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			String id = records.get(i).getProduct();
			// �жϻ�û������������Ŀ����һ��������Ȼ�ǹ���
			if (!productsSet.contains(id)) {
				productsSet.add(id);
				initMoney.put(id, record.getMoney());
				initDate.put(id, InvestRecord.formateDate(record.getDate()));
			}
		}
		Map<Date, Double> map = getInterestValue(records);
		TimeSeries timeseries = new TimeSeries("����ͳ������",
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
	 * ��ȡ����ֵ
	 * 
	 * @throws ParseException
	 */
	public Map<Date, Double> getIncomeValue(List<InvestRecord> records) {
		// ����ʱ������
		Collections.sort(records);
		try {
			String s = records.get(0).getDate();
			s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-"
					+ s.substring(6, 8);
			initDay = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// �����Ӧ���ڵ�����
		Date temp = InvestRecord.formateDate(records.get(0).getDate());
		double sum = 0.0;
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			Date date = InvestRecord.formateDate(record.getDate());
			if (date.compareTo(temp) == 0) {
				if (record.getOperation().equals("����")) {
					int duration = date.getDay()
							- initDate.get(record.getProduct()).getDay();
					double money = initMoney.get(record.getProduct());
					if (duration != 0) {
						sum = sum
								+ (money * (1 + 0.084) * duration / 365 - money);
					}
				} else if (record.getOperation().equals("���")) {
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
		// ����ʱ������
		Collections.sort(records);
		// �����Ӧ���ڵ�����
		Date temp = InvestRecord.formateDate(records.get(0).getDate());
		double sum = 0.0;
		for (int i = 0; i < records.size(); i++) {
			InvestRecord record = records.get(i);
			Date date = InvestRecord.formateDate(record.getDate());
			if (date.compareTo(temp) == 0) {
				if (record.getOperation().equals("����")) {
					sum += record.getMoney();
				} else if (record.getOperation().equals("���")) {
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
	 * ��ȡ������
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
