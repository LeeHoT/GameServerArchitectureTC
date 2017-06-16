package com.test.qingcity.client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.qingcity.base.constants.CmdConstant;
import com.qingcity.proto.PlayerInfo.C2S_Login;
import com.test.qingcity.base.util.BaseConnection;
import com.test.qingcity.base.util.MsgUtil;
import com.test.qingcity.base.util.XmlUtil;

import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JButton;

public class LoginFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	private BaseConnection connect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("登录");
		frame.setBounds(100, 100, 380, 280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(115, 50, 184, 23);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(114, 103, 184, 23);
		frame.getContentPane().add(textField_1);

		label = new JLabel("手机号");
		label.setBounds(51, 54, 54, 15);
		frame.getContentPane().add(label);

		label_1 = new JLabel("密码：");
		label_1.setBounds(50, 107, 54, 15);
		frame.getContentPane().add(label_1);

		JButton button = new JButton("登录");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		button.setBounds(139, 161, 93, 23);
		frame.getContentPane().add(button);

		// 连接服务器
		BaseConnection.getInstance().connectServer(1);
	}

	private void login() {
		String phone = textField.getText();
		String password = textField_1.getText();
		C2S_Login.Builder c2s_login = C2S_Login.newBuilder();
		c2s_login.setPhone(phone);
		c2s_login.setPassword(password);
		MsgUtil.handlerSendMsg(c2s_login.build(), CmdConstant.C2S_USER_LOGIN, 0, BaseConnection.getInstance().getChannel());
	}

	/**
	 * opid相关
	 */
	public static LinkedList<String> opidList = null;
	private static String confPath = System.getProperty("user.dir");
	private JLabel label;
	private JLabel label_1;

	/** 记录最后一次登陆的用户名和密码 */
	public void setOpid(String opid, String pwd) {
		LinkedList<String> set = (LinkedList<String>) getOpidList();

		String info = opid + "@" + pwd;
		if (set.contains(info)) {
			return;
		}

		set.addFirst(info);
		if (set.size() > 10) {
			for (int i = set.size(); i < 10; i--) {
				set.remove(i);
			}
		}

		if (set.isEmpty()) {
			return;
		}

		Document document = DocumentHelper.createDocument();
		// 创建根结点
		Element root = document.addElement("confDefine");
		for (String string : set) {
			// 为根结点添加一个节点
			Element element = root.addElement("conf");
			element.addElement("opid").setText(string);
		}

		// 输出xml
		XmlUtil.xmlFile(document, "/conf.xml", System.getProperty("user.dir"));
	}

	/**
	 * 获得apid列表
	 * 
	 * @return
	 */
	private List<String> getOpidList() {
		if (opidList == null) {
			opidList = new LinkedList<String>();
			try {
				initOpids();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return opidList;
	}

	private static void initOpids() throws DocumentException {
		File file = new File(confPath + "/" + "conf.xml");

		if (file != null && file.exists()) {
			SAXReader sr = new SAXReader();
			sr.setEncoding("utf-8");
			Document doc = sr.read(file);
			Element root = doc.getRootElement();
			String xpath = "./conf";
			@SuppressWarnings("unchecked")
			List<Element> eList = root.selectNodes(xpath);
			for (Element v : eList) {

				String opid = v.elementText("opid");
				opidList.add(opid);
			}
		}
	}
}
