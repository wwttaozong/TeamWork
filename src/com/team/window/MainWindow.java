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
 * ������
 * 
 * @author wwt
 *
 */
public class MainWindow {

	// ��ǰ��ȡ����������Ŀ������
	private static List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
	// ����������ʾ��Ŀ�����JLabel
	private static ArrayList<JLabel> labels = new ArrayList<JLabel>();
	// ���浥ѡ��ť��List
	private static ArrayList<JRadioButton> jRadioButtons = new ArrayList<JRadioButton>();
	// ���水ť��List
	private static ArrayList<JButton> jButtons = new ArrayList<JButton>();
	// ������
	private static JFrame window = new JFrame("�в���");
	// ����ѡ��
	private static JTextField startPeriod = new JTextField(4);
	private static JTextField endPeriod = new JTextField(4);
	// ���ѡ��
	private static JTextField startAmount = new JTextField(4);
	private static JTextField endAmount = new JTextField(4);
	// ������ѡ��
	private static JTextField startInterest = new JTextField(4);
	private static JTextField endInterest = new JTextField(4);

	// ��¼��ǰҳ��
	private static int pageNumber = 1;
	// ѡ�н��������
	private static int minMoney = 0;
	private static int maxMoney = 0;
	// ѡ������������
	private static int minDays = 0;
	private static int maxDays = 0;
	// ѡ������������
	private static double minRate = 0;
	private static double maxRate = 0;

	// �����ڵ�λ�úʹ�С
	private static final int WINDOW_WIDTH;
	private static final int WINDOW_HEIGHT;
	private static final int WINDOW_X;
	private static final int WINDOW_Y;
	// �����л�ģ���λ�úʹ�С
	private static final int TAB_WIDTH;
	private static final int TAB_HEIGHT;
	private static final int TAB_X;
	private static final int TAB_Y;
	// ɸѡģ���λ�úʹ�С����һҳ��
	private static final int SELECT_PART_WIDTH;
	private static final int SELECT_PART_HEIGHT;
	private static final int SELECT_PART_X;
	private static final int SELECT_PART_Y;
	// ��Ŀ����ģ���λ�úʹ�С����һҳ��
	private static final int DETAIL_PART_WIDTH;
	private static final int DETAIL_PART_HEIGHT;
	private static final int DETAIL_PART_X;
	private static final int DETAIL_PART_Y;
	// �Ͷ�ҳ�淭תģ�飨��һҳ��
	private static final int FLIP_PART_WIDTH;
	private static final int FLIP_PART_HEIGHT;
	private static final int FLIP_PART_X;
	private static final int FLIP_PART_Y;
	//Ͷ����Ŀ�б�ģ��
	private static final int INVEST_PART_WIDTH;
	private static final int INVEST_PART_HEIGHT;
	private static final int INVEST_PART_X;
	private static final int INVEST_PART_Y;
	//����ͳ����ʾ
	private static final int SHOW_PART_WIDTH;
	private static final int SHOW_PART_HEIGHT;
	private static final int SHOW_PART_X;
	private static final int SHOW_PART_Y;
	// ÿһҳ����ʾ����Ŀ����
	private static final int VISIBLE_LINE = 10;

	/**
	 * ��ȡ�����ڼ����������ͼ��λ�úʹ�С����
	 */
	static {
		// ������
		WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
		WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
		WINDOW_X = WINDOW_WIDTH / 2;
		WINDOW_Y = WINDOW_HEIGHT / 2;

		// �����л�ģ��
		TAB_WIDTH = WINDOW_WIDTH;
		TAB_HEIGHT = WINDOW_HEIGHT;
		TAB_X = 0;
		TAB_Y = 0;

		// ɸѡģ��
		SELECT_PART_WIDTH = WINDOW_WIDTH;
		SELECT_PART_HEIGHT = (int) (WINDOW_HEIGHT * (3 / (10.0)));
		SELECT_PART_X = 0;
		SELECT_PART_Y = 0;

		// ��Ŀϸ��ģ��
		DETAIL_PART_WIDTH = WINDOW_WIDTH;
		DETAIL_PART_HEIGHT = (int) (WINDOW_HEIGHT * (6 / (10.0)));
		DETAIL_PART_X = 0;
		DETAIL_PART_Y = SELECT_PART_HEIGHT;

		// �Ͷ�ҳ�淭תģ��
		FLIP_PART_WIDTH = WINDOW_WIDTH;
		FLIP_PART_HEIGHT = WINDOW_HEIGHT - SELECT_PART_HEIGHT
				- DETAIL_PART_HEIGHT;
		FLIP_PART_X = 0;
		FLIP_PART_Y = SELECT_PART_HEIGHT + DETAIL_PART_HEIGHT;
		
		//Ͷ��ģ��
		INVEST_PART_WIDTH=0;
		INVEST_PART_HEIGHT=WINDOW_HEIGHT/2;
		INVEST_PART_X=0;
		INVEST_PART_Y=0;
		
		//����ͳ�ƽ����ʾ
		SHOW_PART_WIDTH=WINDOW_WIDTH;
		SHOW_PART_HEIGHT=WINDOW_HEIGHT-INVEST_PART_HEIGHT;
		SHOW_PART_X=0;
		SHOW_PART_Y=INVEST_PART_HEIGHT;
	}

	public static void main(String args[]) throws IOException {

		// ��ʼ�����沼��
		MainWindow window = new MainWindow();
		window.initView();

		productDetails = FilterProducts.getFilterContents(pageNumber, minMoney,
				maxMoney, minDays, maxDays, minRate, maxRate);
		updateLables();
	}

	/**
	 * ��ѡ��ť�ļ����¼�
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
	 * ��ť����¼�������
	 * 
	 * @author wwt
	 *
	 */
	private class MyJButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int id;
			switch (id = jButtons.indexOf((JButton) (e.getSource()))) {
			// ɸѡ��ť
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
					System.out.println("���ݻ�ȡʧ��");
				}
				break;
			// ����
			case 1:
				showRushDialog();
				break;
			// ��һҳ
			case 12:
				pageNumber = pageNumber > 1 ? --pageNumber : 1;
				try {
					productDetails = FilterProducts.getFilterContents(
							pageNumber, minMoney, maxMoney, minDays, maxDays,
							minRate, maxRate);
				} catch (IOException e1) {
					System.out.println("���ݻ�ȡʧ��");
				}
				updateLables();
				break;
			// ��һҳ
			case 13:
				pageNumber++;
				try {
					productDetails = FilterProducts.getFilterContents(
							pageNumber, minMoney, maxMoney, minDays, maxDays,
							minRate, maxRate);
				} catch (IOException e1) {
					System.out.println("���ݻ�ȡʧ��");
				}
				updateLables();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * ���ڼ�����
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
	 * ��ʼ������
	 */
	private void initView() {
		window.setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLayout(null);
		window.getContentPane().setBackground(Color.WHITE);
		window.addWindowListener(new MyWindowListener());
		JTabbedPane tab = new JTabbedPane();
		tab.setBounds(TAB_X, TAB_Y, TAB_WIDTH, TAB_HEIGHT);
		
		/************************************************************/
		/*                           ��һҳ                                                    */
		/************************************************************/

		Box firstPart = Box.createVerticalBox();
		// //////////////////////////////ɸѡ���֣��ϲ���//////////////////////////////////////////
		Box selectPart = Box.createVerticalBox();
		selectPart.setBounds(SELECT_PART_X, SELECT_PART_Y, SELECT_PART_WIDTH,
				SELECT_PART_HEIGHT);
		// Ͷ������ɸѡ
		JPanel selectByInvestPeriod = new JPanel(
				new FlowLayout(FlowLayout.LEFT));
		selectByInvestPeriod.setBackground(Color.WHITE);
		addChoiceSubView(selectPart, selectByInvestPeriod, "Ͷ�����ޣ�", "����",
				"6��������", "6~12����", "12��������");
		selectByInvestPeriod.add(startPeriod);
		selectByInvestPeriod.add(new JLabel("����-"));
		selectByInvestPeriod.add(endPeriod);
		selectByInvestPeriod.add(new JLabel("����"));
		selectPart.add(selectByInvestPeriod);

		// ��Ͷ���ɸѡ
		JPanel selectByProductAmount = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		selectByProductAmount.setBackground(Color.WHITE);
		addChoiceSubView(selectPart, selectByProductAmount, "��Ͷ��", "����",
				"1��Ԫ����", "1��~5��Ԫ", "5��~10��Ԫ");
		selectByProductAmount.setBackground(Color.WHITE);
		selectByProductAmount.add(startAmount);
		selectByProductAmount.add(new JLabel("��Ԫ-"));
		selectByProductAmount.add(endAmount);
		selectByProductAmount.add(new JLabel("��Ԫ"));
		selectPart.add(selectByProductAmount);

		// ������ɸѡ
		JPanel selectByInterestRate = new JPanel(
				new FlowLayout(FlowLayout.LEFT));
		selectByInterestRate.setBackground(Color.WHITE);
		addChoiceSubView(selectPart, selectByInterestRate, "�����ʣ�");
		selectByInterestRate.add(startInterest);
		selectByInterestRate.add(new JLabel("%-"));
		selectByInterestRate.add(endInterest);
		selectByInterestRate.add(new JLabel("%"));
		selectPart.add(selectByInterestRate);

		// ɸѡ��ť
		JPanel tools = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tools.setBackground(Color.WHITE);
		JButton filterButton = new JButton("ɸѡ");
		jButtons.add(filterButton);
		filterButton.addActionListener(new MyJButtonListener());
		tools.add(filterButton);
		// ������ť
		JButton rushButton = new JButton("����");
		jButtons.add(rushButton);
		rushButton.addActionListener(new MyJButtonListener());
		tools.add(rushButton);
		selectPart.add(tools);

		// ///////////////////////////////////��Ŀ���飨�в���/////////////////////////////////////

		JPanel detailsPart = new JPanel();
		detailsPart.setBounds(DETAIL_PART_X, DETAIL_PART_Y, DETAIL_PART_WIDTH,
				DETAIL_PART_HEIGHT);
		detailsPart.setBackground(Color.WHITE);
		GridBagLayout gb = new GridBagLayout();
		detailsPart.setLayout(gb);
		GridBagConstraints gbc = new GridBagConstraints();
		// ��Ӳ���
		addDetailSubView(detailsPart, gb, gbc);
		// ���£���ʾ����ʼ��ǩ
		updateLables();

		// //////////////////////////////////////��ҳ��ť���²���////////////////////////////////////
		Box flipPart = Box.createHorizontalBox();
		flipPart.setBounds(FLIP_PART_X, FLIP_PART_Y, FLIP_PART_WIDTH,
				FLIP_PART_HEIGHT);
		flipPart.setBackground(Color.WHITE);
		JButton pageUp = new JButton("��һҳ");
		JButton pageDown = new JButton("��һҳ");
		jButtons.add(pageUp);
		jButtons.add(pageDown);
		pageUp.addActionListener(new MyJButtonListener());
		pageDown.addActionListener(new MyJButtonListener());
		flipPart.add(pageUp);
		flipPart.add(pageDown);
		
		/************************************************************/
		/*                           ��һҳ                                                    */
		/************************************************************/
		
		/************************************************************/
		/*                           �ڶ�ҳ                                                    */
		/************************************************************/
		
		Box rightPart = Box.createVerticalBox();
		// Ͷ�ʼ�¼��
		JScrollPane investPart = new JScrollPane();
		investPart.getViewport().setBackground(Color.RED);
		investPart.setBounds(INVEST_PART_X, INVEST_PART_Y, INVEST_PART_WIDTH,
				INVEST_PART_HEIGHT);
		rightPart.add(investPart);

		//�����ʾ
		JPanel showPart = new JPanel();
		showPart.setBackground(Color.WHITE);
		showPart.setBounds(SHOW_PART_WIDTH, SHOW_PART_HEIGHT, SHOW_PART_X,
				SHOW_PART_Y);
		rightPart.add(showPart);
		
		/************************************************************/
		/*                           �ڶ�ҳ                                                    */
		/************************************************************/

		firstPart.add(selectPart);
		firstPart.add(detailsPart);
		firstPart.add(flipPart);
		tab.addTab("������", firstPart);
		tab.addTab("����ͳ��", rightPart);
		window.add(tab);
		window.setVisible(true);
	}

	/**
	 * Ϊ�����ɸѡ���֣��ϲ������ɸѡ��Ŀ
	 * 
	 * @param container����ӵ�����
	 * @param jPanel����ӵ�ɸѡ��Ŀ
	 * @param names��ɸѡ��Ŀ�ı�ǩ���ƺ�����ѡ�������
	 * @return
	 */
	private void addChoiceSubView(Box container, JPanel jPanel, String... names) {
		ButtonGroup choiceGroup = new ButtonGroup();
		// ���ñ�ǩ
		jPanel.add(new JLabel(names[0]));
		// ����ѡ��
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
	 * Ϊ�������е���Ŀ���鲿��������е�������������ǩ
	 * 
	 * @param detailsPart�����Ϣ������
	 * @param gb�������Ĳ���
	 * @param gbc�ò��ֵ�Լ������
	 */
	private void addDetailSubView(JPanel detailsPart, GridBagLayout gb,
			GridBagConstraints gbc) {
		// ��������������ڵ����ı�ǩ
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		JLabel name = new JLabel("��Ŀ��");
		gb.setConstraints(name, gbc);
		detailsPart.add(name);
		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel interest = new JLabel("������");
		gb.setConstraints(interest, gbc);
		detailsPart.add(interest);
		gbc.gridx = 2;
		gbc.gridy = 0;
		JLabel amount = new JLabel("��Ͷ���");
		gb.setConstraints(amount, gbc);
		detailsPart.add(amount);
		gbc.gridx = 3;
		gbc.gridy = 0;
		JLabel period = new JLabel("Ͷ������");
		gb.setConstraints(period, gbc);
		detailsPart.add(period);
		// �������������ʾ����ı�ǩ
		for (int line = 0; line < VISIBLE_LINE; line++) {
			JLabel productNameLabel = new JLabel();
			JLabel interestRateLabel = new JLabel();
			JLabel minimumMoneyLabel = new JLabel();
			JLabel investPeriodLabel = new JLabel();
			labels.add(productNameLabel);
			labels.add(interestRateLabel);
			labels.add(minimumMoneyLabel);
			labels.add(investPeriodLabel);
			// ��Ŀ��
			gbc.gridx = 0;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(productNameLabel, gbc);
			detailsPart.add(productNameLabel);
			// ������
			gbc.gridx = 1;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(interestRateLabel, gbc);
			detailsPart.add(interestRateLabel);
			// ��Ͷ���
			gbc.gridx = 2;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(minimumMoneyLabel, gbc);
			detailsPart.add(minimumMoneyLabel);
			// Ͷ������
			gbc.gridx = 3;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(investPeriodLabel, gbc);
			detailsPart.add(investPeriodLabel);
			// Ͷ�ʰ�ť
			JButton invest = new JButton("Ͷ��");
			jButtons.add(invest);
			invest.setVisible(false);
			gbc.gridx = 4;
			gbc.gridy = line % VISIBLE_LINE + 1;
			gb.setConstraints(invest, gbc);
			detailsPart.add(invest);
		}
	}

	/**
	 * ���½����еı�ǩ����Ϣ
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
	 * ��ʾ�����Ի���
	 */
	private static void showRushDialog() {
		Dialog dialog = new Dialog(window, "��������������", true);
		dialog.setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH / 3,
				WINDOW_HEIGHT / 3);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dialog.dispose();
			}
		});

		Box box = Box.createVerticalBox();
		// Ͷ�ʽ��
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel1.setBackground(Color.WHITE);
		panel1.add(new JLabel("Ͷ�ʽ�"));
		JTextField minMoney = new JTextField(4);
		panel1.add(minMoney);
		panel1.add(new JLabel("��Ԫ"));
		panel1.add(new JLabel("-"));
		JTextField maxMoney = new JTextField(4);
		panel1.add(maxMoney);
		panel1.add(new JLabel("��Ԫ"));
		box.add(panel1);

		// Ͷ������
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel2.setBackground(Color.WHITE);
		panel2.add(new JLabel("Ͷ�����ޣ�"));
		JTextField minDays = new JTextField(4);
		panel2.add(minDays);
		panel2.add(new JLabel("����"));
		panel2.add(new JLabel("-"));
		JTextField maxDays = new JTextField(4);
		panel2.add(maxDays);
		panel2.add(new JLabel("����"));
		box.add(panel2);

		// ������
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel3.setBackground(Color.WHITE);
		panel3.add(new JLabel("Ͷ�����ޣ�"));
		JTextField minRate = new JTextField(4);
		panel3.add(minRate);
		panel3.add(new JLabel("%"));
		panel3.add(new JLabel("-"));
		JTextField maxRate = new JTextField(4);
		panel3.add(maxRate);
		panel3.add(new JLabel("%"));
		box.add(panel3);

		// ��ʼ����
		JButton rushButton = new JButton("��ʼ����");
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
