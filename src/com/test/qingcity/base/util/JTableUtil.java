package com.test.qingcity.base.util;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author: BST 创建时间：2016年9月2日 下午12:02:02 类说明 :
 */
public class JTableUtil {
	/**
	 * 给 jtable赋值
	 * 
	 * @param target
	 * @param cells
	 */
	public static void setTableData(JTable target, Object[][] cells) {
		String[] columnNames = { "name", "value" };
		DefaultTableModel model = new MyTableModel(cells, columnNames);
		target.setModel(model);
		// 居中显示文字
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		target.setDefaultRenderer(Object.class, r);
	}

	/**
	 * 给 jtable赋值
	 * 
	 * @param target
	 * @param cells
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void setTableData(JTable target, Vector data, Vector columnNames) {
		/* 方案一 */
		TableModel model = target.getModel();
		if (model instanceof MyTableModel2) {
			((MyTableModel2) model).setDataVector(data, columnNames);
		} else {
			model = new MyTableModel2(data, columnNames);
			target.setModel(model);
		}
		/* 方案二 */
		/*
		 * DefaultTableModel model = new MyTableModel2(data, columnNames);
		 * target.setModel(model); target.setAutoCreateColumnsFromModel(false);
		 */
		// 居中显示文字
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		target.setDefaultRenderer(Object.class, r);
	}
	
	@SuppressWarnings("unchecked")
	public static Vector<Object> getVector(JTable target){
		TableModel model = target.getModel();
		if (model instanceof MyTableModel2){
			return ((MyTableModel2) model).getDataVector();
		}else if(model instanceof DefaultTableModel){
			return ((DefaultTableModel) model).getDataVector();
		}
		return null;
	}

	/**
	 * 自适应列宽
	 * 
	 * @param myTable
	 */
	public static void fitTableColumns(JTable myTable) {
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int columnCount = myTable.getColumnCount();
		int rowCount = myTable.getRowCount();

		int totalWidth = 0;
		for (int col = 0; col < columnCount; col++) {
			int width = myTable.getColumnModel().getColumn(col).getPreferredWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			TableColumn column = myTable.getColumnModel().getColumn(col);
			myTable.getTableHeader().setResizingColumn(column); // 此行很重要
			column.setWidth(width + myTable.getIntercellSpacing().width * 2);
			totalWidth += width + myTable.getIntercellSpacing().width * 2;
		}
		if (myTable.getParent() == null) {
			return;
		}
		// 如果表格实际宽度小于父容器的宽度，则让表格自适应
		if (totalWidth < myTable.getParent().getPreferredSize().width) {
			myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		return;
	}

	/**
	 * 给指定uid的行着色
	 * @param table
	 * @param 指定的uid
	 */
	@SuppressWarnings("serial")
	public static void makeFace(JTable table, final long uid) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					if(Long.parseLong(table.getValueAt(row, 0).toString()) == uid){
						// 指定行设置蓝色
						setBackground(new Color(176, 221, 251));
					} else {
						// 其余行白色
						setBackground(Color.white);
					}
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
			};
			// 为每一列设置renderer
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

class MyTableModel extends DefaultTableModel {

	/**
	 * 自创表格类
	 */
	private static final long serialVersionUID = 1L;

	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);// 这里一定要覆盖父类的构造方法，否则不能实例myTableModel
	}

	public boolean isCellEditable(int row, int column) {
		return false;// 父类的方法里面是 return true的，所以就可以编辑了~~~
	}
}

class MyTableModel2 extends DefaultTableModel {

	/**
	 * 自创表格类
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public MyTableModel2(Vector data, Vector columnNames) {
		super(data, columnNames);// 这里一定要覆盖父类的构造方法，否则不能实例myTableModel
	}

	public boolean isCellEditable(int row, int column) {
		return false;// 父类的方法里面是 return true的，所以就可以编辑了~~~
	}
}
