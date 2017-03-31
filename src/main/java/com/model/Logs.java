package com.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.constant.OperationMode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.DateUtil;

/**
 * @description Log 
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-07-28
 * @version 1.0
 */
public class Logs implements Serializable {
	private static final long serialVersionUID = -2887464532090323232L;
	
	private Integer id;				// 主键
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date ctime = DateUtil.current();				// 操作时间
	private OperationMode cmode;	// 操作方式（ADD新增，EDIT修改，DELETE删除）
	private String cfunc;			// 功能
	private Integer userid;			// 操作人ID
	private String uname;			// 操作人名称
	private String title;			// 标题描述
	private Integer cid;			// 数据唯一ID
	private String content;			// 日志信息记录
	
	// VO查询条件
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;			// 开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;			// 结束时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTimeShort;	// 开始时间(短格式，用于界面查询条件)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTimeShort;		// 结束时间(短格式，用于界面查询条件)
	
	private OperationMode[] cmodes;	// 操作方式（ADD新增，EDIT修改，DELETE删除）
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public OperationMode getCmode() {
		return cmode;
	}
	public void setCmode(OperationMode cmode) {
		this.cmode = cmode;
	}
	public String getCfunc() {
		return cfunc;
	}
	public void setCfunc(String cfunc) {
		this.cfunc = cfunc;
	}

	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public OperationMode[] getCmodes() {
		return cmodes;
	}
	public void setCmodes(OperationMode[] cmodes) {
		this.cmodes = cmodes;
	}
	public Date getStartTimeShort() {
		return startTimeShort;
	}
	public void setStartTimeShort(Date startTimeShort) {
		this.startTimeShort = startTimeShort;
	}
	public Date getEndTimeShort() {
		return endTimeShort;
	}
	public void setEndTimeShort(Date endTimeShort) {
		this.endTimeShort = endTimeShort;
	}

	public Integer getUserid() {
		return userid;
	}
	
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cfunc == null) ? 0 : cfunc.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((cmode == null) ? 0 : cmode.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((ctime == null) ? 0 : ctime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logs other = (Logs) obj;
		if (cfunc == null) {
			if (other.cfunc != null)
				return false;
		} else if (!cfunc.equals(other.cfunc))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (cmode != other.cmode)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (ctime == null) {
			if (other.ctime != null)
				return false;
		} else if (!ctime.equals(other.ctime))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Logs [id=").append(id).append(", ctime=").append(ctime).append(", cmode=").append(cmode)
				.append(", cfunc=").append(cfunc).append(", userid=").append(userid).append(", uname=").append(uname)
				.append(", title=").append(title).append(", cid=").append(cid).append(", content=").append(content)
				.append(", startTime=").append(startTime).append(", endTime=").append(endTime).append("]");
		return builder.toString();
	}
}
