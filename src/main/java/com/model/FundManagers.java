package com.model;


import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.constant.PositionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.RegexUtil;

/**
 * @description 基金经理及助理 
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-08-09
 * @version 1.0
 */
public class FundManagers implements Serializable {
	private static final long serialVersionUID = 5738601199734316131L;
	private Integer id;				// 主键
	private String ename;			// 人员英文简称
	private String cname;			// 人员中文名称
	private PositionType ctype;		// 人员类型 （基金经理，基金经理助理）
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date itime;				// 入职日期
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date otime;				// 离职日期
	private String resume;			// 个人简历
	
	// VO
	private PositionType[] ctypes;
	private String[] fcodes;
	private String[] fnames;
	
	public FundManagers() {}
	
	public FundManagers(Integer id) {
		this.id=id;
	}
	
	public FundManagers(String cname) {
		this.cname=cname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public PositionType getCtype() {
		return ctype;
	}

	public String getCtypeDes() {
		return RegexUtil.notEmpty(ctype) ? ctype.getDes():"";
	}
	
	public void setCtype(PositionType ctype) {
		this.ctype = ctype;
	}

	public Date getItime() {
		return itime;
	}

	public void setItime(Date itime) {
		this.itime = itime;
	}

	public Date getOtime() {
		return otime;
	}

	public void setOtime(Date otime) {
		this.otime = otime;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public PositionType[] getCtypes() {
		return ctypes;
	}

	public void setCtypes(PositionType[] ctypes) {
		this.ctypes = ctypes;
	}

	public String[] getFcodes() {
		return fcodes;
	}

	public void setFcodes(String[] fcodes) {
		this.fcodes = fcodes;
	}

	public String[] getFnames() {
		return fnames;
	}

	public void setFnames(String[] fnames) {
		this.fnames = fnames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cname == null) ? 0 : cname.hashCode());
		result = prime * result + ((ctype == null) ? 0 : ctype.hashCode());
		result = prime * result + ((ename == null) ? 0 : ename.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itime == null) ? 0 : itime.hashCode());
		result = prime * result + ((otime == null) ? 0 : otime.hashCode());
		result = prime * result + ((resume == null) ? 0 : resume.hashCode());
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
		FundManagers other = (FundManagers) obj;
		if (cname == null) {
			if (other.cname != null)
				return false;
		} else if (!cname.equals(other.cname))
			return false;
		if (ctype == null) {
			if (other.ctype != null)
				return false;
		} else if (!ctype.equals(other.ctype))
			return false;
		if (ename == null) {
			if (other.ename != null)
				return false;
		} else if (!ename.equals(other.ename))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itime == null) {
			if (other.itime != null)
				return false;
		} else if (!itime.equals(other.itime))
			return false;
		if (otime == null) {
			if (other.otime != null)
				return false;
		} else if (!otime.equals(other.otime))
			return false;
		if (resume == null) {
			if (other.resume != null)
				return false;
		} else if (!resume.equals(other.resume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FundManagers [id=").append(id).append(", ename=").append(ename).append(", cname=").append(cname)
				.append(", ctype=").append(ctype).append(", itime=").append(itime).append(", otime=").append(otime)
				.append(", resume=").append(resume).append("]");
		return builder.toString();
	}
}
