
package com.me.EFF;

public class UserDataWrapper {
	private String id;
	private Object instance;

	public UserDataWrapper(String id, Object instance) {
		this.id = id;
		this.instance = instance;
	}

	public Object getObject() {
		return instance;
	}

	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setData(String id, Object instance) {
		this.id = id;
		this.instance = instance;
	}
	
	public void setObject(Object instance) {
		this.instance = instance;
	}
}
