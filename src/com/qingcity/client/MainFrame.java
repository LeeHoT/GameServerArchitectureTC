package com.qingcity.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.qingcity.constants.ChatConstant;
import com.qingcity.proto.Chat.C2S_Chat;
import com.qingcity.proto.Player.PlayerInfo;
import com.qingcity.proto.PlayerInfo.Player;
import com.qingcity.proto.PlayerInfo.S2C_GetPlayerInfo;
import com.qingcity.util.JTableUtil;
import com.qingcity.util.MsgUtil;
import com.qingcity.util.StringUtil;
import com.test.qingcity.entity.ChatMessage;
import com.test.qingcity.entity.MsgEntity;
import com.test.qingcity.util.BaseConnection;

import java.awt.TextArea;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -6728789372481829158L;

	private JPanel contentPane;
	private JTable table_1;
	private JTextField textField_22;
	private JTextPane textPane;
	
	
	private MsgEntity msgEntity;
	private S2C_GetPlayerInfo basePlayerInfo;
	
	private int userId;
	private JTable table;
	private JTextField textField;

	public S2C_GetPlayerInfo getBasePlayerInfo() {
		return basePlayerInfo;
	}

	public void setBasePlayerInfo(S2C_GetPlayerInfo basePlayerInfo) {
		this.basePlayerInfo = basePlayerInfo;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// MainFrame2 frame = new MainFrame2();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		this.setBounds(100, 100, 600, 509);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.contentPane.setLayout(null);
		this.setLocationRelativeTo(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 80, 565, 382);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		tabbedPane.addTab("聊天窗口", null, panel, null);

		JButton button = new JButton("发送");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chat();
			}
		});
		button.setBounds(396, 154, 117, 29);
		panel.add(button);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 309, 318);
		panel.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setColumnHeaderView(textArea_1);
		
		textPane = new JTextPane();
		textPane.setBounds(329, 10, 221, 130);
		panel.add(textPane);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("游戏逻辑转发", null, panel_4, null);
		panel_4.setLayout(null);
		
		textField_22 = new JTextField();
		textField_22.setText("0");
		textField_22.setColumns(10);
		textField_22.setBounds(291, 47, 69, 28);
		panel_4.add(textField_22);
		
		JLabel label_17 = new JLabel("发送协议号:");
		label_17.setBounds(218, 52, 82, 16);
		panel_4.add(label_17);
		
		JButton button_16 = new JButton("发送");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handlerLogic();
			}
		});
		button_16.setBounds(218, 156, 132, 29);
		panel_4.add(button_16);
		
		JLabel label_1 = new JLabel("接收协议号:");
		label_1.setBounds(218, 103, 82, 16);
		panel_4.add(label_1);
		
		textField = new JTextField();
		textField.setText("0");
		textField.setColumns(10);
		textField.setBounds(291, 98, 69, 28);
		panel_4.add(textField);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(11, 10, 564, 60);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("基础信息");
		label.setBounds(21, 22, 54, 15);
		panel_1.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(85, 0, 469, 60);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setRowHeaderView(table);
	}
	
	
	public void setLogin(MsgEntity msg){
		System.out.println("------------["+msg.getUserId()+"]登录成功------------" );
		userId = msg.getUserId();
	}
	
	
	
	public void setBaseInfo(S2C_GetPlayerInfo player,int userId){
		
		Vector<String> vec = new Vector<String>();
		vec.add("ID");
		vec.add("昵称");
		vec.add("头像");
		
		Vector<Object> data =  new Vector<Object>();
		int Id = userId;
		String nickname = player.getPlayer().getNickname();
		String avatar = player.getPlayer().getAvatar()+"";
		Vector<Object> v = new Vector<Object>();
		v.add(Id);
		v.add(nickname);
		v.add(avatar);
		data.add(v);
		
		JTableUtil.setTableData(table, data, vec);
		JTableUtil.fitTableColumns(table);
	}
	
	public void setChatMessage(ChatMessage chat){
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("昵称");
		columnNames.add("头像");
		columnNames.add("内容");

		Vector<Object> data =  JTableUtil.getVector(table_1);
		if(data == null){
			data = new Vector<Object>();
		}
		String content = chat.getContent();
		
		if(chat!=null && StringUtil.isNotNull(content)){
			Vector<Object> v = new Vector<Object>();
			v.add(chat.getNickname());
			v.add(chat.getAvatar());
			v.add(content);
			data.add(v);
		}
		
		JTableUtil.setTableData(table_1, data, columnNames);
		JTableUtil.fitTableColumns(table_1);
	}
	
	/**
	 * 游戏逻辑转发
	 */
	private void handlerLogic(){
		short cmd = Short.parseShort(textField_22.getText());
		
		C2S_Chat.Builder c2s_chat = C2S_Chat.newBuilder();
		c2s_chat.setContent(textPane.getText());
		c2s_chat.setTarget(cmd);
		
		MsgUtil.handlerSendMsg(c2s_chat.build(), cmd, userId, BaseConnection.getInstance().getChannel());
		
	}
	
	/**
	 * 聊天
	 */
	private void chat() {
		C2S_Chat.Builder c2s_chat = C2S_Chat.newBuilder();
		c2s_chat.setContent(textPane.getText());
		c2s_chat.setTarget(ChatConstant.WORLD_MSG);
		MsgUtil.handlerSendMsg(c2s_chat.build(), ChatConstant.WORLD_MSG, userId, BaseConnection.getInstance().getChannel());
		
		//为本地客户端赋值
		ChatMessage chat = new ChatMessage();
		chat.setContent(textPane.getText());
		chat.setAvatar(basePlayerInfo.getPlayer().getAvatar()+"");
		chat.setNickname(basePlayerInfo.getPlayer().getNickname());
		chat.setId(111);
		setChatMessage(chat);
	}
}
