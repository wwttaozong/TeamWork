package com.team.window;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;

import com.team.catchdata.FilterProducts;
import com.team.incomestatistics.InvestRecord;
import com.team.incomestatistics.TimeSeriesChart;
import com.team.rush.Rush;
import com.team.util.ExcelTools;
import com.team.util.ProductDetail;

/**
 * 主界面
 * 
 * @author wwt
 *
 */
public class MainWindow {

	// 当前获取到的所有项目的详情
	private static List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
	// 投资记录
	private static List<InvestRecord> records = new ArrayList<InvestRecord>();
	// 保存所有显示项目详情的JLabel
	private static ArrayList<JLabel> labels = new ArrayList<JLabel>();
	// 保存投资记录的表格
	private static JTable table;
	// 表格的数据模型
	private static DefaultTableModel mTableModel;
	// 保存单选按钮的List
	private static ArrayList<JRadioButton> jRadioButtons = new ArrayList<JRadioButton>();
	// 保存按钮的List
	private static ArrayList<JButton> jButtons = new ArrayList<JButton>();
	// 单选按钮的点击监听器
	private MyJRadioButtonListener mJRadioButtonListener = new MyJRadioButtonListener();
	// 按钮点击事件
	private MyJButtonListener mJButtonListener = new MyJButtonListener();

	// 主窗口
	public static JFrame window = new JFrame("招财蛙");
	// 切换窗口
	private static JTabbedPane tab;
	// 显示收益图的容器
	private static JPanel showPart = new JPanel();
	// 期限选择框
	private static JTextField startPeriod = new JTextField(4);
	private static JTextField endPeriod = new JTextField(4);
	// 金额选择框
	private static JTextField startAmount = new JTextField(4);
	private static JTextField endAmount = new JTextField(4);
	// 收益率选择框
	private static JTextField startInterest = new JTextField(4);
	private static JTextField endInterest = new JTextField(4);

	// 记录当前页数
	private static int pageNumber = 1;
	// 选中金额上下限
	private static int minMoney = 0;
	private static int maxMoney = 0;
	// 选中日期上下限
	private static int minDays = 0;
	private static int maxDays = 0;
	// 选中利率上下限
	private static double minRate = 0.0;
	private static double maxRate = 0.0;

	// 主窗口的位置和大小
	private static final int WINDOW_WIDTH;
	private static final int WINDOW_HEIGHT;
	private static final int WINDOW_X;
	private static final int WINDOW_Y;
	// 窗口切换模块的位置和大小
	private static final int TAB_WIDTH;
	private static final int TAB_HEIGHT;
	private static final int TAB_X;
	private static final int TAB_Y;
	// 筛选模块的位置和大小（第一页）
	private static final int SELECT_PART_WIDTH;
	private static final int SELECT_PART_HEIGHT;
	private static final int SELECT_PART_X;
	private static final int SELECT_PART_Y;
	// 项目详情模块的位置和大小（第一页）
	private static final int DETAIL_PART_WIDTH;
	private static final int DETAIL_PART_HEIGHT;
	private static final int DETAIL_PART_X;
	private static final int DETAIL_PART_Y;
	// 低端页面翻转模块（第一页）
	private static final int FLIP_PART_WIDTH;
	private static final int FLIP_PART_HEIGHT;
	private static final int FLIP_PART_X;
	private static final int FLIP_PART_Y;
	// 导入导出工具栏（第二页）
	private static final int INVEST_TOOLS_WIDTH;
	private static final int INVEST_TOOLS_HEIGHT;
	private static final int INVEST_TOOLS_X;
	private static final int INVEST_TOOLS_Y;
	// 投资项目列表模块（第二页）
	private static final int INVEST_PART_WIDTH;
	private static final int INVEST_PART_HEIGHT;
	private static final int INVEST_PART_X;
	private static final int INVEST_PART_Y;
	// 收益统计显示（第二页）
	private static final int SHOW_PART_WIDTH;
	private static final int SHOW_PART_HEIGHT;
	private static final int SHOW_PART_X;
	private static final int SHOW_PART_Y;
	// 每一页能显示的项目数量
	private static final int VISIBLE_LINE = 10;

	/**
	 * 获取主窗口及其各个子视图的位置和大小参数
	 */
	static {
		// 主窗口
		WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
		WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
		WINDOW_X = WINDOW_WIDTH / 2;
		WINDOW_Y = WINDOW_HEIGHT / 2;

		// 窗口切换模块
		TAB_WIDTH = WINDOW_WIDTH;
		TAB_HEIGHT = WINDOW_HEIGHT;
		TAB_X = 0;
		TAB_Y = 0;

		// 筛选模块
		SELECT_PART_WIDTH = WINDOW_WIDTH;
		SELECT_PART_HEIGHT = (int) (WINDOW_HEIGHT * (3 / (10.0)));
		SELECT_PART_X = 0;
		SELECT_PART_Y = 0;

		// 项目细节模块
		DETAIL_PART_WIDTH = WINDOW_WIDTH;
		DETAIL_PART_HEIGHT = (int) (WINDOW_HEIGHT * (6 / (10.0)));
		DETAIL_PART_X = 0;
		DETAIL_PART_Y = SELECT_PART_HEIGHT;

		// 低端页面翻转模块
		FLIP_PART_WIDTH = WINDOW_WIDTH;
		FLIP_PART_HEIGHT = WINDOW_HEIGHT - SELECT_PART_HEIGHT
				- DETAIL_PART_HEIGHT;
		FLIP_PART_X = 0;
		FLIP_PART_Y = SELECT_PART_HEIGHT + DETAIL_PART_HEIGHT;

		// 导入导出工具栏
		INVEST_TOOLS_WIDTH = WINDOW_WIDTH;
		INVEST_TOOLS_HEIGHT = (int) (WINDOW_HEIGHT * (1 / 10.0));
		INVEST_TOOLS_X = 0;
		INVEST_TOOLS_Y = 0;

		// 投资模块
		INVEST_PART_WIDTH = WINDOW_WIDTH;
		INVEST_PART_HEIGHT = WINDOW_HEIGHT / 3;
		INVEST_PART_X = 0;
		INVEST_PART_Y = INVEST_TOOLS_HEIGHT;

		// 收益统计结果显示
		SHOW_PART_WIDTH = WINDOW_WIDTH;
		SHOW_PART_HEIGHT = WINDOW_HEIGHT - INVEST_TOOLS_HEIGHT
				- INVEST_PART_HEIGHT;
		SHOW_PART_X = 0;
		SHOW_PART_Y = INVEST_TOOLS_HEIGHT + INVEST_PART_HEIGHT;
	}

	public static void main(String args[]) throws IOException {

		// 初始化界面布局
		MainWindow window = new MainWindow();
		window.initView();

		productDetails = FilterProducts.getFilterContents(pageNumber, minMoney,
				maxMoney, minDays, maxDays, minRate, maxRate);
		updateLables();
	}

	/**
	 * 单选按钮的监听事件
	 * 
	 * @author wwt
	 *
	 */
	private class MyJRadioButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int id;
			switch (id = jRadioButtons.indexOf((JRadioButton) (e.getSource()))) {
			case 0:
				minDays = 0;
				maxDays = 0;
				break;
			case 1:
				minDays = 0;
				maxDays = 180;
				break;
			case 2:
				minDays = 180;
				maxDays = 360;
				break;
			case 3:
				minDays = 360;
				maxDays = 0;
				break;
			case 4:
				minMoney = 0;
				maxMoney = 0;
				break;
			case 5:
				minMoney = 0;
				maxMoney = 10000;
				break;
			case 6:
				minMoney = 10000;
				maxMoney = 50000;
				break;
			case 7:
				minMoney = 50000;
				maxMoney = 100000;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 按钮点击事件监听器
	 * 
	 * @author wwt
	 *
	 */
	private class MyJButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int id;
			String name;
			switch (id = jButtons.indexOf((JButton) (e.getSource()))) {
			// 筛选按钮
			case 0:
				try {
					productDetails.clear();
					if (!startPeriod.getText().equals("")
							&& !endPeriod.getText().equals("")) {
						minDays = Integer.parseInt(startPeriod.getText());
						maxDays = Integer.parseInt(endPeriod.getText());
					}
					if (!startAmount.getText().equals("")
							&& !endAmount.getText().equals("")) {
						minMoney = Integer.parseInt(startAmount.getText());
						maxMoney = Integer.parseInt(endAmount.getText());
					}
					if (!startInterest.getText().equals("")
							&& !endInterest.getText().equals("")) {
						minRate = Double.parseDouble(startInterest.getText());
						maxRate = Double.parseDouble(endInterest.getText());
					}
					productDetails = FilterProducts.getFilterContents(
							pageNumber, minMoney, maxMoney, minDays, maxDays,
							minRate, maxRate);
					updateLables();
				} catch (IOException e1) {
					System.out.println("数据获取失败");
				}
				break;
			// 抢购
			case 1:
				showRushDialog();
				break;
			case 2:
				name = labels.get(4 * 0).getText();
			case 3:
				name = labels.get(4 * 1).getText();
			case 4:
				name = labels.get(4 * 2).getText();
			case 5:
				name = labels.get(4 * 3).getText();
			case 6:
				name = labels.get(4 * 4).getText();
			case 7:
				name = labels.get(4 * 5).getText();
			case 8:
				name = labels.get(4 * 6).getText();
			case 9:
				name = labels.get(4 * 7).getText();
			case 10:
				name = labels.get(4 * 8).getText();
			case 11:
				name = labels.get(4 * 9).getText();
				String date = new SimpleDateFormat("yyyyMMdd")
						.format(new Date());
				mTableModel.addRow(new Object[] { date, "购买", "", name });
				tab.setSelectedIndex(1);
				break;
			// 上一页
			case 12:
				pageNumber = pageNumber > 1 ? --pageNumber : 1;
				try {
					productDetails = FilterProducts.getFilterContents(
							pageNumber, minMoney, maxMoney, minDays, maxDays,
							minRate, maxRate);
				} catch (IOException e1) {
					System.out.println("数据获取失败");
				}
				updateLables();
				break;
			// 下一页
			case 13:
				pageNumber++;
				try {
					productDetails = FilterProducts.getFilterContents(
							pageNumber, minMoney, maxMoney, minDays, maxDays,
							minRate, maxRate);
				} catch (IOException e1) {
					System.out.println("数据获取失败");
				}
				updateLables();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 窗口监听器
	 * 
	 * @author wwt
	 *
	 */
	private static class MyWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		window.setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH + 20,
				WINDOW_HEIGHT + 50);
		window.setLayout(null);
		window.getContentPane().setBackground(Color.WHITE);
		window.addWindowListener(new MyWindowListener());
		tab = new JTabbedPane();
		tab.setBounds(TAB_X, TAB_Y, TAB_WIDTH, TAB_HEIGHT);

		// 绘制第一个Tab的布局
		drawFirstTabbedPane(tab);
		// 绘制第二个Tab的布局
		drawSecondTabbedPane(tab);

		window.add(tab);
		window.setVisible(true);
	}

	/**
	 * 绘制第一个Tab的布局
	 *
	 * @param tab
	 */
	private void drawFirstTabbedPane(JTabbedPane tab) {
		Box firstTabbedPane = Box.createVerticalBox();
		drawFirstTopPart(firstTabbedPane);
		drawFirstMiddlePart(firstTabbedPane);
		drawFirstBottomPart(firstTabbedPane);
		tab.add("主窗口", firstTabbedPane);
	}

	/**
	 * 绘制第二个Tab的布局
	 * 
	 * @param tab
	 */
	private void drawSecondTabbedPane(JTabbedPane tab) {
		Box secondTabbedPane = Box.createVerticalBox();
		// 导入导出工具栏
		Box investTools = Box.createHorizontalBox();
		investTools.setBounds(INVEST_TOOLS_X, INVEST_TOOLS_Y,
				INVEST_TOOLS_WIDTH, INVEST_TOOLS_HEIGHT);
		investTools.setBackground(Color.WHITE);
		JButton importButton = new JButton("导入");
		JButton exportButton = new JButton("导出");
		JButton addButton = new JButton("增加");
		JButton removeButton = new JButton("删除");
		JButton showIncomeChart = new JButton("收益直观图");
		JButton showInvestChart = new JButton("投资直观图");
		JButton showInterestChart = new JButton("收益率直观图");
		investTools.add(importButton);
		investTools.add(exportButton);
		investTools.add(addButton);
		investTools.add(removeButton);
		investTools.add(showIncomeChart);
		investTools.add(showInvestChart);
		investTools.add(showInterestChart);
		importButton.addActionListener(e -> {
			FileDialog fileDialog = new FileDialog(window, "请选择导入的文件",
					FileDialog.LOAD);
			fileDialog.setVisible(true);
			String selectedFile = fileDialog.getDirectory()
					+ fileDialog.getFile();
			try {
				records = ExcelTools.readFromExcel(selectedFile);
			} catch (Exception e1) {
				System.out.println("记录读出失败，请检查文件或数据的格式是否正确");
			}
			// 更新记录显示
				updateRecords();
			});
		addButton.addActionListener(e -> {
			mTableModel.addRow(new Object[] { "", "", "", "" });
		});
		removeButton.addActionListener(e -> {
			mTableModel.removeRow(table.getSelectedRow());
		});
		exportButton.addActionListener(e -> {
			FileDialog fileDialog = new FileDialog(window, "请选择要导出的文件",
					FileDialog.SAVE);
			fileDialog.setVisible(true);
			String selectedFile = fileDialog.getDirectory()
					+ fileDialog.getFile();
			records.clear();
			getJTableData();
			try {
				ExcelTools.writeToExcel(selectedFile, records);
			} catch (Exception e1) {
				System.out.println("记录导出失败，请检查目的文件格式是否正确");
			}
		});
		showIncomeChart.addActionListener(e -> {
			showPart.removeAll();
			TimeSeriesChart chart = new TimeSeriesChart();
			ChartPanel chartPanel = chart.drawIncomeGraph(records);
			showPart.add(chartPanel);

		});
		showInvestChart.addActionListener(e -> {
			showPart.removeAll();
			TimeSeriesChart chart = new TimeSeriesChart();
			ChartPanel chartPanel = chart.drawInvestGraph(records);
			showPart.add(chartPanel);

		});
		showInterestChart.addActionListener(e -> {
			showPart.removeAll();
			TimeSeriesChart chart = new TimeSeriesChart();
			ChartPanel chartPanel = chart.drawInterestGraph(records);
			showPart.add(chartPanel);
		});
		secondTabbedPane.add(investTools);

		// 投资记录表
		JScrollPane investPart = new JScrollPane();
		investPart.getViewport().setLayout(null);
		investPart.getViewport().setBackground(Color.WHITE);
		investPart.setBounds(INVEST_PART_X, INVEST_PART_Y, INVEST_PART_WIDTH,
				INVEST_PART_HEIGHT);
		table = createTable();
		investPart.getViewport().add(table);
		secondTabbedPane.add(investPart);

		// 结果显示
		showPart.setBackground(Color.WHITE);
		showPart.setBounds(SHOW_PART_WIDTH, SHOW_PART_HEIGHT, SHOW_PART_X,
				SHOW_PART_Y);
		secondTabbedPane.add(showPart);

		tab.add("收益统计", secondTabbedPane);
	}

	/**
	 * 绘制第一个Tab布局的上部（条件筛选模块）
	 * 
	 * @param container
	 */
	private void drawFirstTopPart(Box container) {
		Box selectPart = Box.createVerticalBox();
		selectPart.setBounds(SELECT_PART_X, SELECT_PART_Y, SELECT_PART_WIDTH,
				SELECT_PART_HEIGHT);

		// 投资期限筛选
		JPanel selectByInvestPeriod = new JPanel(
				new FlowLayout(FlowLayout.LEFT));
		selectByInvestPeriod.setBackground(Color.WHITE);
		addChoice(1, selectByInvestPeriod, "投资期限：", "不限", "6个月以下", "6~12个月",
				"12个月以上");
		selectPart.add(selectByInvestPeriod);

		// 起投金额筛选
		JPanel selectByProductAmount = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		selectByProductAmount.setBackground(Color.WHITE);
		addChoice(2, selectByProductAmount, "起投金额：", "不限", "1万元以下", "1万~5万元",
				"5万~10万元");
		selectPart.add(selectByProductAmount);

		// 收益率筛选
		JPanel selectByInterestRate = new JPanel(
				new FlowLayout(FlowLayout.LEFT));
		selectByInterestRate.setBackground(Color.WHITE);
		addChoice(3, selectByInterestRate, "收益率：");
		selectPart.add(selectByInterestRate);

		// 筛选按钮
		JPanel tools = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tools.setBackground(Color.WHITE);
		JButton filterButton = new JButton("筛选");
		jButtons.add(filterButton);
		filterButton.addActionListener(new MyJButtonListener());
		tools.add(filterButton);
		// 抢购按钮
		JButton rushButton = new JButton("抢购");
		jButtons.add(rushButton);
		rushButton.addActionListener(new MyJButtonListener());
		tools.add(rushButton);
		selectPart.add(tools);

		container.add(selectPart);
	}

	/**
	 * 绘制第一个Tab布局的第二部分（项目细节模块）
	 * 
	 * @param container
	 */
	private void drawFirstMiddlePart(Box container) {
		JPanel detailsPart = new JPanel();
		detailsPart.setBounds(DETAIL_PART_X, DETAIL_PART_Y, DETAIL_PART_WIDTH,
				DETAIL_PART_HEIGHT);
		detailsPart.setBackground(Color.WHITE);
		GridBagLayout gb = new GridBagLayout();
		detailsPart.setLayout(gb);
		GridBagConstraints gbc = new GridBagConstraints();
		// 添加布局
		addDetailSubView(detailsPart, gb, gbc);
		// 更新（显示）初始标签
		updateLables();

		container.add(detailsPart);
	}

	/**
	 * 绘制第一个Tab布局的第三部分（页面翻转模块）
	 * 
	 * @param container
	 */
	private void drawFirstBottomPart(Box container) {
		Box flipPart = Box.createHorizontalBox();
		flipPart.setBounds(FLIP_PART_X, FLIP_PART_Y, FLIP_PART_WIDTH,
				FLIP_PART_HEIGHT);
		flipPart.setBackground(Color.WHITE);
		JButton pageUp = new JButton("上一页");
		JButton pageDown = new JButton("下一页");
		jButtons.add(pageUp);
		jButtons.add(pageDown);
		pageUp.addActionListener(new MyJButtonListener());
		pageDown.addActionListener(new MyJButtonListener());
		flipPart.add(pageUp);
		flipPart.add(pageDown);
		container.add(flipPart);
	}

	/**
	 * 为界面筛选部分添加筛选条目
	 * 
	 * @param flag标识哪一个筛选条目
	 * @param jPanel待添加的筛选条目
	 * @param names该筛选条目的标签名称和所有选项的名称
	 * @return
	 */
	private void addChoice(int flag, JPanel jPanel, String... names) {
		ButtonGroup choiceGroup = new ButtonGroup();
		// 设置标签
		jPanel.add(new JLabel(names[0]));
		// 设置选项
		for (int index = 1; index < names.length; index++) {
			JRadioButton choice = new JRadioButton(names[index]);
			choice.addActionListener(mJRadioButtonListener);
			jRadioButtons.add(choice);
			choice.setBackground(Color.WHITE);
			choiceGroup.add(choice);
			jPanel.add(choice);
			if (index == 1) {
				choice.setSelected(true);
			}
		}
		// 选择加上合适的自定义选框
		switch (flag) {
		case 1:
			jPanel.add(startPeriod);
			jPanel.add(new JLabel("个月-"));
			jPanel.add(endPeriod);
			jPanel.add(new JLabel("个月"));
			break;
		case 2:
			jPanel.setBackground(Color.WHITE);
			jPanel.add(startAmount);
			jPanel.add(new JLabel("万元-"));
			jPanel.add(endAmount);
			jPanel.add(new JLabel("万元"));
			break;
		case 3:
			jPanel.add(startInterest);
			jPanel.add(new JLabel("%-"));
			jPanel.add(endInterest);
			jPanel.add(new JLabel("%"));
			break;
		default:
			break;
		}
	}

	/**
	 * 为主界面中的项目详情部分添加所有的容器，包括标签
	 * 
	 * @param detailsPart添加信息的容器
	 * @param gb该容器的布局
	 * @param gbc该布局的约束对象
	 */
	private void addDetailSubView(JPanel detailsPart, GridBagLayout gb,
			GridBagConstraints gbc) {
		// 添加主界面中用于导航的标签
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel name = new JLabel("项目名");
		gb.setConstraints(name, gbc);
		detailsPart.add(name);
		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel interest = new JLabel("收益率");
		gb.setConstraints(interest, gbc);
		detailsPart.add(interest);
		gbc.gridx = 2;
		gbc.gridy = 0;
		JLabel amount = new JLabel("起投金额");
		gb.setConstraints(amount, gbc);
		detailsPart.add(amount);
		gbc.gridx = 3;
		gbc.gridy = 0;
		JLabel period = new JLabel("投资期限");
		gb.setConstraints(period, gbc);
		detailsPart.add(period);
		// 添加主界面中显示详情的标签
		for (int line = 0; line < VISIBLE_LINE; line++) {
			JLabel productNameLabel = new JLabel();
			JLabel interestRateLabel = new JLabel();
			JLabel minimumMoneyLabel = new JLabel();
			JLabel investPeriodLabel = new JLabel();
			labels.add(productNameLabel);
			labels.add(interestRateLabel);
			labels.add(minimumMoneyLabel);
			labels.add(investPeriodLabel);
			// 项目名
			gbc.gridx = 0;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(productNameLabel, gbc);
			detailsPart.add(productNameLabel);
			// 收益率
			gbc.gridx = 1;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(interestRateLabel, gbc);
			detailsPart.add(interestRateLabel);
			// 起投金额
			gbc.gridx = 2;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(minimumMoneyLabel, gbc);
			detailsPart.add(minimumMoneyLabel);
			// 投资期限
			gbc.gridx = 3;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(investPeriodLabel, gbc);
			detailsPart.add(investPeriodLabel);
			// 投资按钮
			JButton invest = new JButton("投资");
			jButtons.add(invest);
			invest.addActionListener(mJButtonListener);
			invest.setVisible(false);
			gbc.gridx = 4;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(invest, gbc);
			detailsPart.add(invest);
		}
	}

	/**
	 * 更新界面中的标签的信息
	 */
	private static void updateLables() {
		if (productDetails.size() == 0) {
			for (int i = 0; i < VISIBLE_LINE; i++) {
				labels.get(i * 4).setText("");
				labels.get(i * 4 + 1).setText("");
				labels.get(i * 4 + 2).setText("");
				labels.get(i * 4 + 3).setText("");
				jButtons.get(i + 2).setVisible(false);
			}
			return;
		}
		for (int i = 0; i < VISIBLE_LINE; i++) {
			ProductDetail productDetail = productDetails.get(i);
			labels.get(i * 4).setText(productDetail.getProductName());
			labels.get(i * 4 + 1).setText(productDetail.getInterestRate());
			labels.get(i * 4 + 2).setText(productDetail.getMinimumMoney());
			labels.get(i * 4 + 3).setText(productDetail.getInvestPeriod());
			jButtons.get(i + 2).setVisible(true);
		}
	}

	/**
	 * 创建表格
	 */
	private static JTable createTable() {
		Object[] title = new Object[] { "日期", "操作", "金额", "项目编号" };
		mTableModel = new DefaultTableModel(null, title);
		mTableModel.addTableModelListener(new MyTableModelListener());
		JTable table = new JTable(mTableModel);
		table.setBounds(INVEST_PART_X, INVEST_PART_Y, INVEST_PART_WIDTH,
				INVEST_PART_HEIGHT);
		return table;
	}

	/**
	 * 更新投资项目的显示
	 */
	private static void updateRecords() {
		if (records.size() == 0) {
			return;
		}
		for (int i = 0; i < records.size(); i++) {
			String date = records.get(i).getDate() + "";
			String operation = records.get(i).getOperation() + "";
			String money = records.get(i).getMoney() + "";
			String product = records.get(i).getProduct() + "";
			mTableModel
					.addRow(new Object[] { date, operation, money, product });
		}
	}

	/**
	 * 数据模型改变监听器
	 * 
	 * @author wwt
	 *
	 */
	private static class MyTableModelListener implements TableModelListener {
		@Override
		public void tableChanged(TableModelEvent e) {

		}
	}

	/**
	 * 显示抢购对话框
	 */
	private static void showRushDialog() {
		Dialog dialog = new Dialog(window, "请设置抢购条件", true);
		dialog.setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH / 3,
				WINDOW_HEIGHT / 3);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dialog.dispose();
			}
		});

		Box box = Box.createVerticalBox();
		// 投资金额
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel1.setBackground(Color.WHITE);
		panel1.add(new JLabel("投资金额："));
		JTextField minMoney = new JTextField(4);
		panel1.add(minMoney);
		panel1.add(new JLabel("万元"));
		panel1.add(new JLabel("-"));
		JTextField maxMoney = new JTextField(4);
		panel1.add(maxMoney);
		panel1.add(new JLabel("万元"));
		box.add(panel1);

		// 投资期限
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel2.setBackground(Color.WHITE);
		panel2.add(new JLabel("投资期限："));
		JTextField minDays = new JTextField(4);
		panel2.add(minDays);
		panel2.add(new JLabel("个月"));
		panel2.add(new JLabel("-"));
		JTextField maxDays = new JTextField(4);
		panel2.add(maxDays);
		panel2.add(new JLabel("个月"));
		box.add(panel2);

		// 收益率
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel3.setBackground(Color.WHITE);
		panel3.add(new JLabel("收益率："));
		JTextField minRate = new JTextField(4);
		panel3.add(minRate);
		panel3.add(new JLabel("%"));
		panel3.add(new JLabel("-"));
		JTextField maxRate = new JTextField(4);
		panel3.add(maxRate);
		panel3.add(new JLabel("%"));
		box.add(panel3);

		// 开始抢购
		JButton rushButton = new JButton("开始抢购");
		rushButton.addActionListener(e -> {
			String sMinMoney = minMoney.getText();
			String sMaxMoney = maxMoney.getText();
			String sMinDays = minDays.getText();
			String sMaxDays = maxDays.getText();
			String sMinRate = minRate.getText();
			String sMaxRate = maxRate.getText();
			Thread rushTask = new Rush(sMinMoney, sMaxMoney, sMinDays,
					sMaxDays, sMinRate, sMaxRate);
			rushTask.start();
		});
		box.add(rushButton);

		dialog.add(box);
		dialog.setVisible(true);
	}

	/**
	 * 获得表格中的数据
	 */
	private void getJTableData() {
		for (int row = 0; row < mTableModel.getRowCount(); row++) {
			InvestRecord record = new InvestRecord();
			record.setDate(mTableModel.getValueAt(row, 0).toString());
			record.setOperation(mTableModel.getValueAt(row, 1).toString());
			record.setMoney(Double.parseDouble(mTableModel.getValueAt(row, 2)
					.toString()));
			record.setProduct(mTableModel.getValueAt(row, 3).toString());
			records.add(record);
		}
	}

}
