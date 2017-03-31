package com.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * XML CRUD operate
 * 
 * @author Janwer
 * 
 */
public class XmlHelper {
	private static DocumentBuilder builder;
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	public static Document createXML() {
		builder();
		return builder.newDocument();
	}

	public static Document ParserXML(File xmlpath) {
		builder();
		Document doc = null;
		try {
			doc = builder.parse(xmlpath);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	private static void builder() {
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * udpate the Node 
	 * @param e Node Object
	 * @param textConten element text content
	 * @param m element attributes
	 */
	public static void updateXMLInfo(String expression, Document doc, String textConten, Map<String, String> m) {
		Node e = new QueryXMLInfo<Node>().getXMLInfoByXPath(expression, doc, XPathConstants.NODE);
		e.setTextContent(textConten);
		NamedNodeMap a = e.getAttributes();
		for (Map.Entry<String, String> entity : m.entrySet())
			a.getNamedItem(entity.getKey()).setNodeValue(entity.getValue());
	}

	/**
	 * output result to object
	 * @param ds input DOM Source
	 * @param sr output stream result
	 */
	public static void OutputXmlStream(DOMSource ds, StreamResult sr) {
		Transformer t;
		try {
			t = TransformerFactory.newInstance().newTransformer();
			t.transform(ds, sr);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * add a element
	 * @param root father element
	 * @param child child element
	 */
	public static void addInfoToXML(Element root, Element child) {
		root.appendChild(child);
	}

	/**
	 * 添加tagName到parent节点中
	 * @param parent
	 * @param tagName
	 * @return
	 */
	public static Element createElement(Element parent, String tagName) {
        Document doc=parent.getOwnerDocument();
        Element child=doc.createElement(tagName);
        parent.appendChild(child);        
        return child;
    }
	
	/**
	 * 添加elementName和text内容到parent节点中
	 * @param parent
	 * @param elementName
	 * @param text
	 */
	public static void createElement(Element parent, String elementName, String text){
		Document doc=parent.getOwnerDocument();
		Element id = doc.createElement(elementName);
		id.setTextContent(text);
		parent.appendChild(id);
	}
	 
	/**
	 * delete element
	 * 
	 * @param root
	 * @param removeElement
	 */
	public static void deleteInfoFromXML(Document doc, String expression) {
		Element root = doc.getDocumentElement();
		Node de = new QueryXMLInfo<Node>().getXMLInfoByXPath(expression, doc,
				XPathConstants.NODE);
		root.removeChild(de);
	}

	public static QName QueryXML(String expression, Document doc,
			QName resultType) {
		return new QueryXMLInfo<QName>().getXMLInfoByXPath(expression, doc,
				resultType);
	}
}

/**
 * 通过XPath来定位信息，并返回<code><li>QName<li><code>类型的值
 * 
 * @param <T>
 *            返回类型(String,Node,NodeSet,Number,boolean)
 */
class QueryXMLInfo<T> {
	@SuppressWarnings("unchecked")
	T getXMLInfoByXPath(String expression, Document doc, QName resultType) {
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath path = xpfactory.newXPath();
		T e = null;
		try {
			e = (T) path.evaluate(expression, doc, resultType);
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
		return e;
	}

}
