package com.test.qingcity.base.util;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;

public class XmlUtil {
	 
	public static boolean xmlFile(Document document, String filename, String dirPathTmp) {
	        if(!(new File(dirPathTmp).isDirectory())){//判断文件夹是否存在
	            (new File(dirPathTmp)).mkdir();
	        }
	        
	        filename = dirPathTmp+ filename;
	        boolean flag = true;
	        try {
	        	File file = new File(filename);
	        	if (!file.exists()){
					file.createNewFile();
				}
	        	
	            // 默认为UTF-8格式，指定为"GB2312"
	            OutputFormat format = new OutputFormat("/t", true);
	            format.setEncoding("utf-8");
	            java.io.OutputStream out = new java.io.FileOutputStream(file, false);//falsh为不追加，true为追加
	            Writer wr = new OutputStreamWriter(out, "UTF-8");
	            document.write(wr);
	            wr.close();
	            out.close();
	        } catch (Exception ex) {
	            flag = false;
	            ex.printStackTrace();
	        }
	        return flag;
	    }
	 
//	 public creatXMLTest(){
//			/**
//		  * XML基本操作
//		  */
//		  //创建一个document
//		  Document document=DocumentHelper.createDocument();
//		  //创建根结点
//		  Element root=document.addElement("root");
//		  //为根结点添加一个book节点
//		  Element book1=root.addElement("book");
//		  //为book1添加属性type
//		  book1.addAttribute("type","science");
//		  //为book1添加name子节点
//		  Element name1=book1.addElement("Name");
//		  //并设置其name为"Java"
//		  name1.setText("Java");
//		  //为book1创建一个price节点,并设其价格为100
//		  book1.addElement("price").setText("100");
//		  
//		  //为根结点添加第二个book节点，并设置该book节点的type属性
//		  Element book2=root.addElement("book").addAttribute("type","science");
//		  //为book1添加name子节点
//		  Element name2=book2.addElement("Name");
//		  //并设置其name为"Oracle"
//		  name2.setText("Oracle");
//		  //为book1创建一个price节点,并设其价格为200
//		  book2.addElement("price").setText("200");
//		  
//		  //输出xml
//		  System.out.println(document.asXML());
//
//		  XmlUtil.xmlFile(document, "item.xml",dirPath+"\\bst\\");
		  
//		  <?xml version="1.0" encoding="UTF-8"?>
//		  <root>
//		   <book type="science">
//		    <Name>Java</Name>
//		    <price>100</price>
//		   </book>
//		   <book type="science">
//		    <Name>Oracle</Name>
//		    <price>200</price>
//		   </book>
//		  </root>
//	 }
}
