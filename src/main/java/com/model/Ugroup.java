package com.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.constant.GroupType;

/**
 * @description Ugroup 
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public class Ugroup implements Serializable {
	private static final long serialVersionUID = -8804809309718930582L;

	private Integer id;
	private String ename;
	private String cname;
	private GroupType gtype;
	
	
	
	// VO
	private Integer[] zids;
	private List<String> znames;
	
	public Ugroup() {}
	
	public Ugroup(Integer id) {
		this.id = id;
	}
	
	public Ugroup(String ename) {
		this.ename = ename;
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
	
	public GroupType getGtype() {
		return gtype;
	}

	public void setGtype(GroupType gtype) {
		this.gtype = gtype;
	}


	public Integer[] getZids() {
		return zids;
	}

	public void setZids(Integer[] zids) {
		this.zids = zids;
	}

	public List<String> getZnames() {
		return znames;
	}

	public void setZnames(List<String> znames) {
		this.znames = znames;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getEname()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ugroup != true)
			return false;
		Ugroup other = (Ugroup) obj;
		return new EqualsBuilder().append(getId(), other.getId()).append(getEname(), other.getEname()).isEquals();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ugroup [id=").append(id).append(", ename=").append(ename).append(", cname=").append(cname)
				.append(", gtype=").append(gtype).append(", zids=").append(Arrays.toString(zids)).append("]");
		return builder.toString();
	}

	
}
