package com.ee.Bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	protected long id;
	protected String name;
	protected String address;
	protected long phone;
	
	public User() { }
	public User (String userName, String userAddress, long phone) throws Exception {
		this.setName(userName);
		this.setAdress(userAddress);
		this.setPhone(phone);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		if (name == null) throw new Exception("Name can`t be null");
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAdress(String address) throws Exception {
		if (address == null) throw new Exception("Address can`t be null");
		this.address = address;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone (long phone) {
		this.phone = phone;
	}

	public long getId() {
		
		return id;
	}
	
	public void setId(long id) throws Exception{
		if(id < 0) throw new Exception("Id can`t be less then 0");
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "id= " + this.getId() + ", name=" + this.getName() + ", address=" + this.getAddress() 
				+ ", phone=" + this.getPhone() ;
	}
	
	
}
