package com.parser.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.parser.po.Book;

/**
 * 读取xml文件
 */
public class ReaderXMLByDom4j {
	private List<Book> bookList = null;
	private Book book = null;

	public static void main(String[] args) {
		File file = new File("src/com/parser/res/books.xml");
		List<Book> books = new ReaderXMLByDom4j().getBooks(file);
		for (Book book : books) {
			System.out.println(book);
		}
	}

	public List<Book> getBooks(File file) {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(file);
			Element bookstore = document.getRootElement();// 获取根节点元素
			Iterator iterator = bookstore.elementIterator();// 获取的是根节点下面所有的标签,bookstore标签下的所有节点
			bookList = new ArrayList<Book>();
			while (iterator.hasNext()) {
				book = new Book();
				Element bookElement = (Element) iterator.next();
				List<Attribute> attributes = bookElement.attributes();// 遍历bookElement的属性,获取属性值
				for (Attribute attribute : attributes) {
					if (attribute.getName().equals("id")) {
						String id = attribute.getValue();// 获得属性为id对应的值
						book.setId(Integer.parseInt(id));
					}
				}
				Iterator bookit = bookElement.elementIterator();// 获取的是book节点下面所有的标签,该标签下所有标签是没有属性
				while (bookit.hasNext()) {
					Element child = (Element) bookit.next();
					String nodeName = child.getName();// 直接根据标签名称获得对应的值
					if (nodeName.equals("name")) {
						// System.out.println(child.getStringValue());
						String name = child.getStringValue();
						book.setName(name);
					} else if (nodeName.equals("author")) {
						String author = child.getStringValue();
						book.setAuthor(author);
					} else if (nodeName.equals("year")) {
						String year = child.getStringValue();
						book.setYear(Integer.parseInt(year));
					} else if (nodeName.equals("price")) {
						String price = child.getStringValue();
						book.setPrice(Double.parseDouble(price));
					}
				}
				bookList.add(book);
				book = null;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;
	}

}
