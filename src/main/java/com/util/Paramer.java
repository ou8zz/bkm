package com.util;

import java.io.Serializable;

import com.github.pagehelper.Page;

/**
 * @description 分页参数对象，用于前台传递和后端返回分页HTML标签
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public class Paramer implements Serializable {
	private static final long serialVersionUID = 7954521521071993612L;
	private int pageNum;	 	// 页数 默认第一页
	private int pages;			// 一共有多少页
	private int pageSize;		// 一页多少条数
	private long total;			// 一共有多少条数
	private String func;		// Ajax分页传递函数名称
	private String option;		// 动态加成显示数据大小
	private String pageHtml;	// 分页的HTML标签
	private Object ob;			// 存储对象使用

	public Paramer() {}
	
	public Paramer(int pageNum, int pageSize) {
		// check:
		if (pageNum < 1) pageNum = 1;
		if (pageSize < 1) pageSize = 1;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getPageHtml() {
		return pageHtml;
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	public Object getOb() {
		return ob;
	}

	public void setOb(Object ob) {
		this.ob = ob;
	}
	
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * laypage 前台分页调用方法
	 * @param page
	 */
	public void setPage(Page<Object> page) {
		setOb(page);
		setPages(page.getPages());
		setTotal(page.getTotal());
	}
	
	/**
	 * 后台分页调用方法
	 * 分页HTML处理
	 * @param p					分页对象
	 * @return					分页字符串
	 */
	public String getPageTag(Page<Object> page) {
		setOb(page);									// 分页后的集合放到page对象里面方便传递
		String funcName = getFunc();					// 前台调用ajax分页的函数
		StringBuilder buf = new StringBuilder();
		buf.append("<div data-am-widget='pagination' class='laypage_main laypageskin_molv'><span><label>共" + page.getTotal() + " 条</label><label>第 " + page.getPageNum());
		buf.append(" 页</label><label>共 " + page.getPages() + " 页</label>");

		if (page.getPages() == 0)
			buf.append("<label>没有可显示的条目</label></div>");
		else {
			String option = "<option value='10'>10</option><option value='20'>20</option><option value='40'>40</option><option value='80'>80</option><option value='200'>200</option>";
			if(getOption() != null) option = getOption()+option;
			String val = "value='"+getPageSize()+"'";
			option = option.replace(val, val+" selected");
			
			buf.append("<select style='width:50px;' class='laypage_skip' onchange=\"javascript:"+funcName+"(this, {'func':'"+funcName+"', 'pageNum':" + page.getPageNum() + ", 'pageSize':this.value});\">" + option + "</select></span>");
			
			// first page tag
			buf.append(getPreviousTag(page.getPageNum(), page.getPages(), page.getPageSize(), funcName));
			
			// last page tag and HTML end of page tag
			buf.append(getNextTag(page.getPageNum(), page.getPages(), page.getPageSize(), funcName));
		}
		
		buf.append("<span><label>跳转到<input id='igov' type='number' min='1' onkeyup='this.value=this.value.replace(/\\D/, \'\');' class=laypage_skip/> ");
		buf.append("<a class='laypage_btn' href='javascript:void(0)' onclick=\"javascript:").append(funcName);
		buf.append("(this, {'func':'"+funcName+"', 'pageNum':this.parentNode.getElementsByTagName('input')['igov'].value, 'pageSize':"+getPageSize()+"})\"><b class='am-icon-check'></b></a></label></span></div>");
		//buf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		
		setPageHtml(buf.toString());					// 返回前台的分页HTML标签
		return getPageHtml();
	}

	/**
	 * get previous HTML TAG
	 * @params currentPage	当前分页数
	 * @params totalPage 	分页总页数
	 * @params limit		分页大小
	 * @params func			前台js函数名
	 * @return previous HTML TAG
	 */
	private String getPreviousTag(long currentPage, long totalPage, long limit, String func) {
		if (currentPage == 1)
//			return "<a class='am-pagination-first'>首页</a><a class='am-pagination-prev'>上一页</a>";
			return "";
		else {
			long i = currentPage - 1;
			String link = func + "(this, {'func':'"+func+"', 'pageNum':1, 'pageSize':"+limit+"}";
			String previous = "<label><a href='javascript:void(0)' onclick=\"javascript:" + link + ");\" class='prev_page' rel='prev'>首页</a></label>";
			link = func + "(this, {'func':'"+func+"', 'pageNum':"+i+", 'pageSize':"+limit+"}";
			previous += "<label><a href='javascript:void(0)' onclick=\"javascript:" + link + ");\" class='prev_page' rel='prev'>上一页</a></label>";
			return previous;
		}
	}

	/**
	 * get next HTML TAG
	 * @params currentPage	当前分页数
	 * @params totalPage 	分页总页数
	 * @params limit		分页大小
	 * @params func			前台js函数名
	 * @return next HTML TAG
	 */
	private String getNextTag(long currentPage, long totalPage, long limit, String func) {
		if(currentPage == 1 && totalPage < 2) 
			//return "<a class='am-pagination-next'>下一页</a><a class='am-pagination-last'>尾页</a>";
			return "";
		else if(currentPage == totalPage && currentPage > 1)
			//return "<a class='am-pagination-next'>下一页</a><a class='am-pagination-last'>尾页</a>";
			return "";
		else{
			long i = currentPage + 1;
			StringBuilder str = new StringBuilder();
			str.append("<label><a href='javascript:void(0)' onclick=\"javascript:").append(func);
			str.append("(this, {'func':'"+func+"', 'pageNum':"+i+", 'pageSize':"+limit+"});\"");
			str.append("class='am-pagination-next' rel='next'>下一页</a></label>");
			str.append("<label><a href='javascript:void(0)' onclick=\"javascript:").append(func);
			str.append("(this, {'func':'"+func+"', 'pageNum':"+totalPage+", 'pageSize':"+limit+"});\"");
			str.append("class='am-pagination-last' rel='next'>尾页</a></label>");
			return str.toString();
		}
	}
}
