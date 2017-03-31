package com.util;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.model.FundInfo;

/**
 * @description 动态读取properties文件
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/06/22
 * @version 1.0
 */
public class ResourceUtil {
	private static Log log = LogFactory.getLog(ResourceUtil.class);
	private final static MyResourceBundleControl ctl = new MyResourceBundleControl();

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws Exception 
	 */
	public static void main(String[] args) throws InterruptedException, Exception {
		FundInfo fi = new FundInfo(1);
		FundInfo fi2 = new FundInfo(1);
		
		Set<FundInfo> set = new HashSet<FundInfo>();
		set.add(fi);
		set.add(fi2);
		
		int hashCode = fi.hashCode();
		int hashCode2 = fi2.hashCode();
		
		System.out.println(fi.equals(fi2));
		System.out.println(fi == fi2);
		System.out.println(hashCode + " " + hashCode2);
		System.out.println(set.size());
		
//		File f = new File("d://test.txt");
//		FileInputStream is = new FileInputStream(f);
//		InputStreamReader fis = new InputStreamReader(is, "gbk");
//		BufferedReader br = new BufferedReader(fis);
//		String point = "";
//		
//		/* 写入Txt文件 */
//		File fw = new File("d://testw.txt");
//        fw.createNewFile();
//        BufferedWriter out = new BufferedWriter(new FileWriter(fw));
//		while (br.ready()) {
//			String s = br.readLine();
//			Pattern p = Pattern.compile("<图片标题>=");
//			Matcher matcher = p.matcher(s);
//			if(matcher.find()) {
//				point = s.replaceFirst(matcher.group(), "");
//				System.out.println(point);
//			}
//			
//			Pattern ps = Pattern.compile("<图片说明>=");
//			Matcher ma = ps.matcher(s);
//			if(ma.find()) {
//				System.out.println(point);
//				out.write(s+point + "\n");
//			} else {
//				out.write(s+ "\n");
//			}
//		}
//        out.flush();
//        out.close();
	}

	private static ResourceBundle getBundle(String pro) {
		return ResourceBundle.getBundle(pro, Locale.getDefault(), ctl);
	}
	
	/**
	 * 读取conf.properties
	 * @param key
	 * @return value
	 */
	public static String getConf(String key) {
		String string = "";
		try {
			string = getBundle("conf").getString(key);
		} catch (Exception e) {
			log.error("getConf error", e);
		}
		return string;
	}
	
	/**
	 * 重载控制器
	 */
	private static class MyResourceBundleControl extends ResourceBundle.Control {

		/**
		 * 如果在加载配置文件中时隔一秒钟文件内容将重新读取
		 */
		@Override
		public long getTimeToLive(String baseName, Locale locale) {
			return 1000;
		}

		@Override
		public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle, long loadTime) {
			return true;
		}
	}
}
