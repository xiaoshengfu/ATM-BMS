package com.sdkdjn.atmbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sdkdjn.atmbms.po.TradingRecord;
import com.sdkdjn.atmbms.po.User;
import com.sdkdjn.atmbms.service.TradingRecordService;
import com.sdkdjn.atmbms.service.UserService;
import com.sdkdjn.atmbms.service.impl.TradingRecordServiceImpl;
import com.sdkdjn.atmbms.service.impl.UserServiceImpl;

public class ATMWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private User user = new User();
	private UserService userService = new UserServiceImpl();
	private TradingRecordService trService = new TradingRecordServiceImpl();
	private double operationAmount;
	
	// JFrame不能直接添加组件，需要用getContentPane()函数获取内容面板，再在内容面板上进行添加组件
	Container con;
	
	// JPanel是轻量级容器通过JPanel的setVisible();方法来改变页面
	JPanel currentCenterJPanel;
	JPanel loginCenterPanel;//登录界面
	JPanel mainCenterPanel;//主菜单界面
	JPanel registerCenterPanel;//注册界面
	JPanel depositCenterPanel;//存款界面
	JPanel balanceCenterPanel;//余额界面
	JPanel billCenterPanel;//账单界面
	JPanel errorCenterPanel;//错误页面
	JPanel withdrawMoneyCenterPanel;//取款页面
	JPanel modifyPasswordCenterPanel1;//修改密码界面1
	JPanel modifyPasswordCenterPanel2;//修改密码界面2
	JPanel transferAccountsCenterPanel1;//转账页面1
	JPanel transferAccountsCenterPanel2;//转账页面2
	JPanel successCenterPanel;//成功页面
	JPanel successThenLoginCenterPanel;//成功后登录页面
	
	
	//公共组件
	JLabel title;
	JLabel information;
	JButton leftButton;
	JButton rightButton;
	
	//登录界面组件
	JTextField textId;
	JPasswordField textPwd;
	
	//主页面组件
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	JButton button6;
	
	//注册组件
	JTextField textName;
	
	//转账组件
	JTextField textMoney;
	
	//存款界面
	JTextField depositMoney;
	
	//取款页面
	JTextField withdrawMoney;
	
	//修改密码界面1
	JPasswordField oldPassword;
	
	//修改密码界面2
	JPasswordField newPassword;
	JPasswordField repeatPassword;
	
	//转账页面1
	JTextField transferId;
	JTextField transferMoney;
	
	public ATMWin() {
		con = getContentPane();
		setTitle("ATM_BMS");
		setLayout(new BorderLayout());
		title = new JLabel();
		title.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(0, 191, 255));
		northPanel.add(title);
		con.add(northPanel, "North");
		leftButton = new JButton();
		rightButton = new JButton();
		loginWin();
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(0, 191, 255));
		southPanel.add(leftButton);
		southPanel.add(rightButton);
		leftButton.addMouseListener(new SouthButtonMouseListener());
		rightButton.addMouseListener(new SouthButtonMouseListener());
		con.add(southPanel, "South");
		
		// Dimension类只是用来封装单个对象中组件的宽度和高度
		setSize(new Dimension(600, 440));
		// 设置窗口居中
		setLocationRelativeTo(null);
		// 设置窗口大小不可改变
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private class SouthButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == leftButton) {
				if (currentCenterJPanel == loginCenterPanel) {
					if (textId.getText().length() == 15 && textPwd.getPassword().length >= 6) {
						user.setuId(textId.getText());
						user.setPassword(new String(textPwd.getPassword()));
						user = userService.login(user);
						if (user != null) {
							mainWin();
							return;
						} else {
							user = new User();
							information.setText("账号或密码输入错误!");
							information.setVisible(true);
							textPwd.setText("");
							validate();
							return;
						}
					} else {
							information.setVisible(true);
							textPwd.setText("");
							validate();
							return;
					}
				}
				if (currentCenterJPanel == registerCenterPanel) {
					if ("".equals(textName.getText())) {
						information.setVisible(true);
						validate();
						return;
					} else {
						user.setName(textName.getText());
						int state = userService.register(user);
						if (state == 1) {
							successThenLoginWin("注册成功",
									"您的账号为:" + user.getuId());
							return;
						} else {
							errorWin();
							return;
						}
					}
				}
				if(currentCenterJPanel == mainCenterPanel){
					user = new User();
					operationAmount = 0;
					loginWin();
				}
				if(currentCenterJPanel == depositCenterPanel){
					if("".equals(depositMoney.getText())){
						information.setVisible(true);
						validate();
						return;
					} else {
						double money = Double.parseDouble(depositMoney
								.getText());
						if (money > 0) {
							int i = userService.depositMoney(user, money);
							if (i > 0) {
								successWin("存款成功", "成功存款" + money + "元");
								return;
							} else {
								errorWin();
								return;
							}
						} else {
							information.setVisible(true);
							validate();
							return;
						}
					}
				}
				if(currentCenterJPanel == balanceCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == billCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == errorCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == withdrawMoneyCenterPanel){
					if("".equals(withdrawMoney.getText())){
						information.setText("取款金额不能为空且应大于0");
						information.setVisible(true);
						validate();
						return;
					} else {
						double money = Double.parseDouble(withdrawMoney.getText());
						if (money > 0) {
							if (userService.isWithdrawals(user, money)) {
								double remainingSum = 0;
								User u = userService.findUserByuId(user.getuId());
								if(u != null){
									remainingSum = u.getRemainingSum();
								}else{
									errorWin();
									return;
								}
								if (remainingSum >= money) {
									int i = userService.withdrawMoney(user, money);
									if (i > 0) {
										successWin("取款成功", "成功取款" + money + "元");
										return;
									} else {
										errorWin();
										return;
									}
								}else{
									information.setText("余额不足!");
									information.setVisible(true);
									validate();
									return;
								}
							} else {
								information.setText("ATM当日取款不能超过5000元");
								information.setVisible(true);
								validate();
								return;
							}
						}else{
							information.setText("取款金额不能为空且应大于0");
							information.setVisible(true);
							validate();
							return;
						}
					}	
				}
				if(currentCenterJPanel == successCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == successThenLoginCenterPanel){
					user = new User();
					operationAmount = 0;
					loginWin();
					return;
				}
				if(currentCenterJPanel == modifyPasswordCenterPanel1){
					if(oldPassword.getPassword().length >= 6){
						User u = new User();
						u.setuId(user.getuId());
						u.setPassword(new String(oldPassword.getPassword()));
						u = userService.login(u);
						if(u != null){
							modifyPasswordWin2();
						}else{
							information.setText("密码输入错误!");
							information.setVisible(true);
							oldPassword.setText("");
							validate();
						}
						return;
					}else{
						information.setText("密码长度大于等于6位!");
						information.setVisible(true);
						oldPassword.setText("");
						validate();
						return;
					}
				}
				if(currentCenterJPanel == modifyPasswordCenterPanel2){
					String newPwd = new String(newPassword.getPassword());
					String repeatPwd = new String(repeatPassword.getPassword());
					repeatPassword.getPassword();
					if(newPwd.length() > 6){
						if (newPwd.equals(repeatPwd)) {
							int i = userService.changePassword(user, newPwd);
							if (i > 0) {
								successThenLoginWin("修改密码成功", "请重新登录");
								return;
							} else {
								errorWin();
								return;
							}
						}else{
							information.setText("两次输入密码不一致!");
							information.setVisible(true);
							newPassword.setText("");
							repeatPassword.setText("");
							validate();
							return;
						}
					}else{
						information.setText("密码长度大于等于6位!");
						information.setVisible(true);
						newPassword.setText("");
						repeatPassword.setText("");
						validate();
						return;
					}
				}
				if(currentCenterJPanel == transferAccountsCenterPanel1){
					if(transferId.getText().length() == 15 && !"".equals(transferMoney.getText())){
						operationAmount = Double.parseDouble(transferMoney.getText());
						if(operationAmount > 0){
							User u = userService.findUserByuId(transferId.getText());
							if(u != null){
								transferAccountsWin2(u);
								return;
							}else{
								operationAmount = 0;
								information.setText("没有此账户!");
								information.setVisible(true);
								validate();
								return;
							}
						}else{
							information.setText("转账金额不能为负数!");
							information.setVisible(true);
							validate();
							return;
						}
					}else{
						information.setText("账号为15位且转账金额不能为空!");
						information.setVisible(true);
						validate();
						return;
					}
				}
				if(currentCenterJPanel == transferAccountsCenterPanel2){
					double remainingSum = 0;
					User u = userService.findUserByuId(user.getuId());
					if(u != null){
						remainingSum = u.getRemainingSum();
					}else{
						errorWin();
						return;
					}
					if (remainingSum >= operationAmount) {
						int i = userService.transferAccounts(user, transferId.getText(), operationAmount);
						if(i >0){
							successWin("转账成功", "成功向账户"+transferId.getText()+"转款"+operationAmount+"元");
						}else{
							errorWin();
							
						}
						return;
					}else{
						information.setVisible(true);
						validate();
						return;
					}
				}
			} else {
				if (currentCenterJPanel == loginCenterPanel) {
					registerWin();
					return;
				}
				if (currentCenterJPanel == registerCenterPanel) {
					loginWin();
					return;
				}
				if(currentCenterJPanel == mainCenterPanel){
					System.exit(0);
				}
				if(currentCenterJPanel == billCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == balanceCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == depositCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == errorCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == withdrawMoneyCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == successCenterPanel){
					mainWin();
					return;
				}
				if(currentCenterJPanel == successThenLoginCenterPanel){
					user = new User();
					operationAmount = 0;
					loginWin();
					return;
				}
				if(currentCenterJPanel == modifyPasswordCenterPanel1){
					mainWin();
					return;
				}
				if(currentCenterJPanel == modifyPasswordCenterPanel1){
					mainWin();
					return;
				}
				if(currentCenterJPanel == transferAccountsCenterPanel1){
					mainWin();
					return;
				}
				if(currentCenterJPanel == transferAccountsCenterPanel2){
					mainWin();
					return;
				}
				if(currentCenterJPanel == modifyPasswordCenterPanel2){
					mainWin();
					return;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	private class mainButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == button1) {
				User u = userService.findUserByuId(user.getuId());
				if(u != null){
					balanceWin(u.getRemainingSum());
				}else{
					errorWin();
				}
				return;
			}
			if (e.getSource() == button2) {
				List<TradingRecord> examineTradingRecord = trService.examineTradingRecord(user);
				if(examineTradingRecord != null){
				billWin(examineTradingRecord);
				}else{
					errorWin();
				}
				return;
			}
			if (e.getSource() == button3) {
				modifyPasswordWin1();
				return;
			}
			if (e.getSource() == button4) {
				depositWin();
				return;
			}
			if (e.getSource() == button5) {
				withdrawMoneyWin();
				return;
			}
			if (e.getSource() == button6) {
				transferAccountsWin1();
				return;
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}
	//登录界面
	private void loginWin(){
		if(currentCenterJPanel != null){
			currentCenterJPanel.setVisible(false);
		}
		title.setText("欢迎使用ATM管理系统");
		//提示信息
		information = new JLabel("账号和密码不能为空且账号为15位密码不小于6位!");
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelId = new JLabel("账    号:");
		labelId.setFont(new Font("宋体", Font.BOLD, 20));
		labelId.setBounds(115, 70, 100, 40);
		JLabel labelPwd = new JLabel("密    码:");
		labelPwd.setFont(new Font("宋体", Font.BOLD, 20));
		labelPwd.setBounds(115, 140, 100, 40);
		textId = new JTextField(15);
		textId.setFont(new Font("宋体", Font.BOLD, 15));
		textId.setBounds(235, 70, 240, 40);
		textPwd = new JPasswordField(6);
		textPwd.setFont(new Font("宋体", Font.BOLD, 15));
		textPwd.setBounds(235, 140, 240, 40);
		loginCenterPanel = new JPanel();
		loginCenterPanel.setBackground(new Color(0, 191, 255));
		loginCenterPanel.setLayout(null);
		loginCenterPanel.add(labelId);
		loginCenterPanel.add(labelPwd);
		loginCenterPanel.add(textId);
		loginCenterPanel.add(textPwd);
		loginCenterPanel.add(information);
		con.add(loginCenterPanel, "Center");
		currentCenterJPanel = loginCenterPanel;
		leftButton.setText("登    录");
		rightButton.setText("注    册");	
		validate();
	}
	
	//错误界面
	private void errorWin() {
		currentCenterJPanel.setVisible(false);
		title.setText("系统繁忙");
		JLabel labelerror = new JLabel("系统繁忙,请稍后重试...");
		labelerror.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errorCenterPanel = new JPanel();
		errorCenterPanel.setBackground(new Color(0,191,255));
		errorCenterPanel.add(labelerror);
		con.add(errorCenterPanel, "Center");
		currentCenterJPanel = errorCenterPanel;
		leftButton.setText("确        认");
		rightButton.setText("返回主菜单");
		validate();
	}

	//主菜单界面
	private void mainWin() {
		currentCenterJPanel.setVisible(false);
		title.setText("欢迎" + user.getName());
		button1 = new JButton("余额查询");
		button1.setFont(new Font("宋体", Font.BOLD, 20));
		button1.setBounds(60, 30, 200, 60);
		button1.addMouseListener(new mainButtonMouseListener());
	    button2 = new JButton("交易明细");
		button2.setFont(new Font("宋体", Font.BOLD, 20));
		button2.setBounds(60, 100, 200, 60);
		button2.addMouseListener(new mainButtonMouseListener());
		button3 = new JButton("修改密码");
		button3.setFont(new Font("宋体", Font.BOLD, 20));
		button3.setBounds(60, 170, 200, 60);
		button3.addMouseListener(new mainButtonMouseListener());
		button4 = new JButton("存款业务");
		button4.setFont(new Font("宋体", Font.BOLD, 20));
		button4.setBounds(330, 30, 200, 60);
		button4.addMouseListener(new mainButtonMouseListener());
		button5 = new JButton("取款业务");
		button5.setFont(new Font("宋体", Font.BOLD, 20));
		button5.setBounds(330, 100, 200, 60);
		button5.addMouseListener(new mainButtonMouseListener());
		button6 = new JButton("转账业务");
		button6.setFont(new Font("宋体", Font.BOLD, 20));
		button6.setBounds(330, 170, 200, 60);
		button6.addMouseListener(new mainButtonMouseListener());
		mainCenterPanel = new JPanel();
		mainCenterPanel.setLayout(null);
		mainCenterPanel.setBackground(new Color(0, 191, 255));
		mainCenterPanel.add(button1);
		mainCenterPanel.add(button2);
		mainCenterPanel.add(button3);
		mainCenterPanel.add(button4);
		mainCenterPanel.add(button5);
		mainCenterPanel.add(button6);
		con.add(mainCenterPanel, "Center");
		leftButton.setText("注 销 登 录");
		rightButton.setText("退 出 系 统");
		currentCenterJPanel = mainCenterPanel;
		// 刷新重画的界面
		validate();
	}
	//注册界面
	private void registerWin() {
		currentCenterJPanel.setVisible(false);
		title.setText("欢迎注册");
		information = new JLabel("姓名不能为空");
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelName = new JLabel("姓    名:");
		labelName.setBounds(115, 100, 100, 40);
		labelName.setFont(new Font("宋体", Font.BOLD, 20));
		textName = new JTextField(15);
		textName.setFont(new Font("宋体", Font.BOLD, 15));
		textName.setBounds(235, 100, 240, 40);
		registerCenterPanel = new JPanel();
		registerCenterPanel.setLayout(null);
		registerCenterPanel.setBackground(new Color(0,191,255));
		registerCenterPanel.add(labelName);
		registerCenterPanel.add(textName);
		registerCenterPanel.add(information);
		con.add(registerCenterPanel, "Center");
		leftButton.setText("注    册");
		rightButton.setText("返    回");
		currentCenterJPanel = registerCenterPanel;
		validate();
	}
	//余额界面
	private void balanceWin(double money){
		currentCenterJPanel.setVisible(false);
		title.setText("余额查询");
		JLabel balance = new JLabel("您的账户余额为"+money+"元");
		balance.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		balanceCenterPanel = new JPanel();
		balanceCenterPanel.setBackground(new Color(0,191,255));
		balanceCenterPanel.add(balance);
		con.add(balanceCenterPanel, "Center");
		leftButton.setText("确    认");
		rightButton.setText("返    回");
		currentCenterJPanel = balanceCenterPanel;
		validate();
	}
	//账单界面
	public void billWin(List<TradingRecord> examineTradingRecord) {
		String[] columnName = { "交易编号", "交易时间", "交易类型", "交易账号", "交易金额" };
		currentCenterJPanel.setVisible(false);
		title.setText("账单查询");
		int size = examineTradingRecord.size();
		Object[][] objs = new Object[size][5];
		for (int i = 0; i < size; i++) {
			TradingRecord tr = examineTradingRecord.get(i);
			objs[i][0] = tr.gettId();
			objs[i][1] = tr.getOperationData();
			objs[i][2] = tr.getBusinessType();
			if(user.getuId().equals(tr.getRelateuId())){
				objs[i][3] = tr.getuId();
			}else{
				objs[i][3] = tr.getRelateuId();
			}
			objs[i][4] = tr.getOperationAmount();
		}
		JTable table = new JTable(objs, columnName);
		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(0, 0, 600, 300);
		billCenterPanel = new JPanel();
		billCenterPanel.setBackground(new Color(0, 191, 255));
		billCenterPanel.setLayout(null);
		billCenterPanel.add(jScrollPane);
		con.add(billCenterPanel, "Center");
		leftButton.setText("确    认");
		rightButton.setText("返    回");
		currentCenterJPanel = billCenterPanel;
		validate();
	}
	//存款界面
	private void depositWin(){
		currentCenterJPanel.setVisible(false);
		title.setText("请输入存款金额");
		information = new JLabel("存款金额不能为空并大于0");
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelDeposit = new JLabel("存款金额:");
		labelDeposit.setBounds(115, 100, 100, 40);
		labelDeposit.setFont(new Font("宋体", Font.BOLD, 20));
		depositMoney = new JTextField(15);
		depositMoney.setFont(new Font("宋体", Font.BOLD, 15));
		depositMoney.setBounds(225, 100, 240, 40);
		JLabel money = new JLabel("¥");
		money.setBounds(470, 100, 100, 40);
		money.setFont(new Font("宋体", Font.BOLD, 25));
		depositCenterPanel = new JPanel();
		depositCenterPanel.setLayout(null);
		depositCenterPanel.setBackground(new Color(0,191,255));
		depositCenterPanel.add(labelDeposit);
		depositCenterPanel.add(information);
		depositCenterPanel.add(depositMoney);
		depositCenterPanel.add(money);
		con.add(depositCenterPanel, "Center");
		leftButton.setText("确    认");
		rightButton.setText("返    回");
		currentCenterJPanel = depositCenterPanel;
		validate();
	}
	
	//取款界面
	private void withdrawMoneyWin() {
		currentCenterJPanel.setVisible(false);
		title.setText("请输入取款金额");
		information = new JLabel();
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelWithdrawMoney = new JLabel("取款金额:");
		labelWithdrawMoney.setBounds(115, 100, 100, 40);
		labelWithdrawMoney.setFont(new Font("宋体", Font.BOLD, 20));
		withdrawMoney = new JTextField(15);
		withdrawMoney.setFont(new Font("宋体", Font.BOLD, 15));
		withdrawMoney.setBounds(225, 100, 240, 40);
		JLabel money = new JLabel("¥");
		money.setBounds(470, 100, 100, 40);
		money.setFont(new Font("宋体", Font.BOLD, 25));
		withdrawMoneyCenterPanel = new JPanel();
		withdrawMoneyCenterPanel.setLayout(null);
		withdrawMoneyCenterPanel.setBackground(new Color(0,191,255));
		withdrawMoneyCenterPanel.add(information);
		withdrawMoneyCenterPanel.add(labelWithdrawMoney);
		withdrawMoneyCenterPanel.add(withdrawMoney);
		withdrawMoneyCenterPanel.add(money);
		con.add(withdrawMoneyCenterPanel, "Center");
		leftButton.setText("确    认");
		rightButton.setText("返    回");
		currentCenterJPanel = withdrawMoneyCenterPanel;
		validate();
		
	}
	
	//密码修改界面
	private void modifyPasswordWin1() {
		currentCenterJPanel.setVisible(false);
		title.setText("请输入原密码");
		information = new JLabel();
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelName = new JLabel("原 密 码:");
		labelName.setBounds(115, 100, 100, 40);
		labelName.setFont(new Font("宋体", Font.BOLD, 20));
		oldPassword = new JPasswordField(15);
		oldPassword.setFont(new Font("宋体", Font.BOLD, 15));
		oldPassword.setBounds(235, 100, 240, 40);
		modifyPasswordCenterPanel1 = new JPanel();
		modifyPasswordCenterPanel1.setLayout(null);
		modifyPasswordCenterPanel1.setBackground(new Color(0,191,255));
		modifyPasswordCenterPanel1.add(labelName);
		modifyPasswordCenterPanel1.add(oldPassword);
		modifyPasswordCenterPanel1.add(information);
		con.add(modifyPasswordCenterPanel1, "Center");
		leftButton.setText("确    认");
		rightButton.setText("返    回");
		currentCenterJPanel = modifyPasswordCenterPanel1;
		validate();
	}
	private void modifyPasswordWin2() {
		currentCenterJPanel.setVisible(false);
		title.setText("请输入新密码");
		//提示信息
		information = new JLabel("密码不能为空且密码不小于6位");
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelnPwd = new JLabel("新  密  码:");
		labelnPwd.setFont(new Font("宋体", Font.BOLD, 20));
		labelnPwd.setBounds(100, 70, 130, 40);
		JLabel labeloPwd = new JLabel("重复新密码:");
		labeloPwd.setFont(new Font("宋体", Font.BOLD, 20));
		labeloPwd.setBounds(100, 140, 130, 40);
		newPassword = new JPasswordField(15);
		newPassword.setFont(new Font("宋体", Font.BOLD, 15));
		newPassword.setBounds(240, 70, 240, 40);
		repeatPassword = new JPasswordField(15);
		repeatPassword.setFont(new Font("宋体", Font.BOLD, 15));
		repeatPassword.setBounds(240, 140, 240, 40);
		modifyPasswordCenterPanel2 = new JPanel();
		modifyPasswordCenterPanel2.setBackground(new Color(0, 191, 255));
		modifyPasswordCenterPanel2.setLayout(null);
		modifyPasswordCenterPanel2.add(labelnPwd);
		modifyPasswordCenterPanel2.add(labeloPwd);
		modifyPasswordCenterPanel2.add(newPassword);
		modifyPasswordCenterPanel2.add(repeatPassword);
		modifyPasswordCenterPanel2.add(information);
		con.add(modifyPasswordCenterPanel2, "Center");
		currentCenterJPanel = modifyPasswordCenterPanel2;
		leftButton.setText("确    认");
		rightButton.setText("返    回");
		validate();
	}
	private void successThenLoginWin(String textTitle, String message){
		currentCenterJPanel.setVisible(false);
		title.setText(textTitle);
		JLabel labelerror = new JLabel(message);
		labelerror.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		successThenLoginCenterPanel = new JPanel();
		successThenLoginCenterPanel.setBackground(new Color(0,191,255));
		successThenLoginCenterPanel.add(labelerror);
		con.add(successThenLoginCenterPanel, "Center");
		leftButton.setText("确    认");
		rightButton.setText("返回登录");
		currentCenterJPanel = successThenLoginCenterPanel;
		validate();
	}

	private void transferAccountsWin1() {
		currentCenterJPanel.setVisible(false);
		title.setText("转账业务");
		//提示信息
		information = new JLabel();
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		JLabel labelId = new JLabel("转入账号:");
		labelId.setFont(new Font("宋体", Font.BOLD, 20));
		labelId.setBounds(115, 70, 100, 40);
		JLabel labelMoney = new JLabel("转账金额:");
		labelMoney.setFont(new Font("宋体", Font.BOLD, 20));
		labelMoney.setBounds(115, 140, 100, 40);
		transferId = new JTextField(15);
		transferId.setFont(new Font("宋体", Font.BOLD, 15));
		transferId.setBounds(235, 70, 240, 40);
		transferMoney = new JTextField(15);
		transferMoney.setFont(new Font("宋体", Font.BOLD, 15));
		transferMoney.setBounds(235, 140, 240, 40);
		transferAccountsCenterPanel1 = new JPanel();
		transferAccountsCenterPanel1.setBackground(new Color(0, 191, 255));
		transferAccountsCenterPanel1.setLayout(null);
		transferAccountsCenterPanel1.add(labelId);
		transferAccountsCenterPanel1.add(labelMoney);
		transferAccountsCenterPanel1.add(transferId);
		transferAccountsCenterPanel1.add(transferMoney);
		transferAccountsCenterPanel1.add(information);
		con.add(transferAccountsCenterPanel1, "Center");
		currentCenterJPanel = transferAccountsCenterPanel1;
		leftButton.setText("确     认");
		rightButton.setText("返     回");
		validate();
	}
	private void transferAccountsWin2(User payee) {
		String name = "*";
		String[] names= payee.getName().split("");
		for (int i = 2; i < names.length; i++) {
			name += names[i];
		}
		//提示信息
		information = new JLabel("余额不足!");
		information.setFont(new Font("宋体", Font.BOLD, 20));
		information.setBounds(115, 30, 485, 40);
		information.setForeground(Color.red);
		information.setVisible(false);
		currentCenterJPanel.setVisible(false);
		title.setText("请确认转账交易信息");;
		JLabel labelId = new JLabel("转入卡号:");
		labelId.setFont(new Font("宋体", Font.BOLD, 20));
		labelId.setBounds(115, 60, 100, 40);
		JLabel labelPayee = new JLabel("收款人:");
		labelPayee.setFont(new Font("宋体", Font.BOLD, 20));
		labelPayee.setBounds(115, 120, 100, 40);
		JLabel labelMoney = new JLabel("转账金额:");
		labelMoney.setFont(new Font("宋体", Font.BOLD, 20));
		labelMoney.setBounds(115, 180, 100, 40);
		JLabel textId = new JLabel(""+payee.getuId());
		textId.setFont(new Font("宋体", Font.BOLD, 20));
		textId.setBounds(235, 60, 240, 40);
		JLabel textName = new JLabel(name);
		textName.setFont(new Font("宋体", Font.BOLD, 20));
		textName.setBounds(235, 120, 240, 40);
		JLabel textMoney = new JLabel(operationAmount + "元");
		textMoney.setFont(new Font("宋体", Font.BOLD, 20));
		textMoney.setBounds(235, 180, 240, 40);
		transferAccountsCenterPanel2 = new JPanel();
		transferAccountsCenterPanel2.setBackground(new Color(0, 191, 255));
		transferAccountsCenterPanel2.setLayout(null);
		transferAccountsCenterPanel2.add(labelId);
		transferAccountsCenterPanel2.add(labelPayee);
		transferAccountsCenterPanel2.add(labelMoney);
		transferAccountsCenterPanel2.add(textId);
		transferAccountsCenterPanel2.add(textName);
		transferAccountsCenterPanel2.add(textMoney);
		transferAccountsCenterPanel2.add(information);
		con.add(transferAccountsCenterPanel2, "Center");
		currentCenterJPanel = transferAccountsCenterPanel2;
		leftButton.setText("确           认");
		rightButton.setText("返回主菜单");
		validate();
	}
	private void successWin(String textTitle, String message){
		currentCenterJPanel.setVisible(false);
		title.setText(textTitle);
		JLabel labelSuccess = new JLabel(message);
		labelSuccess.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		successCenterPanel = new JPanel();
		successCenterPanel.setBackground(new Color(0,191,255));
		successCenterPanel.add(labelSuccess);
		con.add(successCenterPanel, "Center");
		leftButton.setText("确         认");
		rightButton.setText("返回主菜单");
		currentCenterJPanel = successCenterPanel;
		validate();
	}	
}
