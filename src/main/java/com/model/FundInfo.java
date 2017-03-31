package com.model;


import java.io.Serializable;

/**
 * @description 基金产品信息
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-08-10
 * @version 1.0
 */
public class FundInfo implements Serializable {
	private static final long serialVersionUID = -526988579080725671L;
	private Integer id;				// 主键
	private String fcode;			// 基金代码
	private String fname;			// 基金名称
	private Integer userid;			// 管理人ID
	
	// VO
	private String[] ids;			// 修改基金管理者对应组合的参数
	private String uname;			// 管理人
	
	public FundInfo() {}
	
	public FundInfo(Integer id) {
		this.id=id;
	}
	
	public FundInfo(String fcode) {
		this.fcode=fcode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fcode == null) ? 0 : fcode.hashCode());
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FundInfo other = (FundInfo) obj;
		if (fcode == null) {
			if (other.fcode != null)
				return false;
		} else if (!fcode.equals(other.fcode))
			return false;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FundInfo [id=").append(id).append(", fcode=").append(fcode).append(", fname=").append(fname)
				.append("]");
		return builder.toString();
	}
}
