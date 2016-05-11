package com.team.window;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.swing.JTextField;

import com.team.catchdata.FilterProducts;
import com.team.rush.Rush;
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
	// 保存所有显示项目详情的JLabel
	private static ArrayList<JLabel> labels = new ArrayList<JLabel>();
	// 保存单选按钮的List
	private static ArrayList<JRadioButton> jRadioButtons = new ArrayList<JRadioButton>();
	// 保存按钮的List
	private static ArrayList<JButton> jButtons = new ArrayList<JButton>();
	// 主窗口
	private static JFrame window = new JFrame("招财蛙");
	// 期限选择
	private static JTextField startPeriod = new JTextField(4);
	private static JTextField endPeriod = new JTextField(4);
	// 金额选择
	private static JTextField startAmount = new JTextField(4);
	private static JTextField endAmount = new JTextField(4);
	// 收益率选择
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
	private static double minRate = 0;
	private static double maxRate = 0;

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
	//投资项目列表模块
	private static final int INVEST_PART_WIDTH;
	private static final int INVEST_PART_HEIGHT;
	private static final int INVEST_PART_X;
	private static final int INVEST_PART_Y;
	//收益统计显示
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
		
		//投资模块
		INVEST_PART_WIDTH=0;
		INVEST_PART_HEIGHT=WINDOW_HEIGHT/2;
		INVEST_PART_X=0;
		INVEST_PART_Y=0;
		
		//收益统计结果显示
		SHOW_PART_WIDTH=WINDOW_WIDTH;
		SHOW_PART_HEIGHT=WINDOW_HEIGHT-INVEST_PART_HEIGHT;
		SHOW_PART_X=0;
		SHOW_PART_Y=INVEST_PART_HEIGHT;
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
		window.setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLayout(null);
		window.getContentPane().setBackground(Color.WHITE);
		window.addWindowListener(new MyWindowListener());
		JTabbedPane tab = new JTabbedPane();
		tab.setBounds(TAB_X, TAB_Y, TAB_WIDTH, TAB_HEIGHT);
		
		/************************************************************/
		/*                           第一页                                                    */
		/************************************************************/

		Box firstPart = Box.createVerticalBox();
		// //////////////////////////////筛选部分（上部）//////////////////////////////////////////
		Box selectPart = Box.createVerticalBox();
		selectPart.setBounds(SELECT_PART_X, SELECT_PART_Y, SELECT_PART_WIDTH,
				SELECT_PART_HEIGHT);
		// 投资期限筛选
		JPanel selectByInvestPeriod = new JPanel(
				new FlowLayout(FlowLayout.LEFT));
		selectByInvestPeriod.setBackground(Color.WHITE);
		addChoiceSubView(selectPart, selectByInvestPeriod, "投资期限：", "不限",
				"6个月以下", "6~12个月", "12个月以上");
		selectByInvestPeriod.add(startPeriod);
		selectByInvestPeriod.add(new JLabel("个月-"));
		selectByInvestPeriod.add(endPeriod);
		selectByInvestPeriod.add(new JLabel("个月"));
		selectPart.add(selectByInvestPeriod);

		// 起投金额筛选
		JPanel selectByProductAmount = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		selectByProductAmount.setBackground(Color.WHITE);
		addChoiceSubView(selectPart, selectByProductAmount, "起投金额：", "不限",
				"1万元以下", "1万~5万元", "5万~10万元");
		selectByProductAmount.setBackground(Color.WHITE);
		selectByProductAmount.add(startAmount);
		selectByProductAmount.add(new JLabel("万元-"));
		selectByProductAmount.add(endAmount);
		selectByProductAmount.add(new JLabel("万元"));
		selectPart.add(selectByProductAmount);

		// 收益率筛选
		JPanel selectByInterestRate = new JPanel(
				new FlowLayout(FlowLayout.LEFT));
		selectByInterestRate.setBackground(Color.WHITE);
		addChoiceSubView(selectPart, selectByInterestRate, "收益率：");
		selectByInterestRate.add(startInterest);
		selectByInterestRate.add(new JLabel("%-"));
		selectByInterestRate.add(endInterest);
		selectByInterestRate.add(new JLabel("%"));
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

		// ///////////////////////////////////项目详情（中部）/////////////////////////////////////

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

		// //////////////////////////////////////翻页按钮（下部）////////////////////////////////////
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
		
		/************************************************************/
		/*                           第一页                                                    */
		/************************************************************/
		
		/************************************************************/
		/*                           第二页                                                    */
		/************************************************************/
		
		Box rightPart = Box.createVerticalBox();
		// 投资记录表
		JScrollPane investPart = new JScrollPane();
		investPart.getViewport().setBackground(Color.RED);
		investPart.setBounds(INVEST_PART_X, INVEST_PART_Y, INVEST_PART_WIDTH,
				INVEST_PART_HEIGHT);
		rightPart.add(investPart);

		//结果显示
		JPanel showPart = new JPanel();
		showPart.setBackground(Color.WHITE);
		showPart.setBounds(SHOW_PART_WIDTH, SHOW_PART_HEIGHT, SHOW_PART_X,
				SHOW_PART_Y);
		rightPart.add(showPart);
		
		/************************************************************/
		/*                           第二页                                                    */
		/************************************************************/

		firstPart.add(selectPart);
		firstPart.add(detailsPart);
		firstPart.add(flipPart);
		tab.addTab("主窗口", firstPart);
		tab.addTab("收益统计", rightPart);
		window.add(tab);
		window.setVisible(true);
	}

	/**
	 * 为界面的筛选部分（上部）添加筛选条目
	 * 
	 * @param container待添加的容器
	 * @param jPanel待添加的筛选条目
	 * @param names该筛选条目的标签名称和所有选项的名称
	 * @return
	 */
	private void addChoiceSubView(Box container, JPanel jPanel, String... names) {
		ButtonGroup choiceGroup = new ButtonGroup();
		// 设置标签
		jPanel.add(new JLabel(names[0]));
		// 设置选项
		for (int index = 1; index < names.length; index++) {
			JRadioButton choice = new JRadioButton(names[index]);
			choice.addActionListener(new MyJRadioButtonListener());
			jRadioButtons.add(choice);
			choice.setBackground(Color.WHITE);
			choiceGroup.add(choice);
			jPanel.add(choice);
			if (index == 1) {
				choice.setSelected(true);
			}
		}
		container.add(jPanel);
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
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
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
		panel3.add(new JLabel("投资期限："));
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

}
