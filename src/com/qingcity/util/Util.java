package com.qingcity.util;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Util {
	/***
	 * 显示提示信息
	 * @param msg
	 */
	public static void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg, "提示信息",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		int a=1;
		map.put(1, a++);
		System.out.println(map.get(1));
		
	}
}
