package com.birdex.activiti.dao.entity;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {

	private static final long serialVersionUID = 6117212957401219380L;

	protected String id;
	protected int revision;
	protected String name;
	protected String type;
	private List<User> actIdUsers;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<User> getActIdUsers() {
		return actIdUsers;
	}
	public void setActIdUsers(List<User> actIdUsers) {
		this.actIdUsers = actIdUsers;
	}

}
