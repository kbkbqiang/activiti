package com.birdex.activiti.dao.entity;

import java.io.Serializable;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.ByteArrayRef;

public class User implements Serializable {

	private static final long serialVersionUID = -2050577007988583229L;

	protected String id;
	protected int revision;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String password;

	protected final ByteArrayRef pictureByteArrayRef = new ByteArrayRef();

	private List<Group> actIdGroups;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Group> getActIdGroups() {
		return actIdGroups;
	}

	public void setActIdGroups(List<Group> actIdGroups) {
		this.actIdGroups = actIdGroups;
	}

	public ByteArrayRef getPictureByteArrayRef() {
		return pictureByteArrayRef;
	}

}
