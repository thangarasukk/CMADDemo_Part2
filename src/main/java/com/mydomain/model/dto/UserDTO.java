package com.mydomain.model.dto;

import java.io.Serializable;
import org.bson.types.ObjectId;
import com.mydomain.model.User;

/**
 * DTO class for transfering User data over json
 * @author thangarasu & animesh
 *
 */
public class UserDTO implements Serializable{
    private String name;
    private String email;
    private String password;
    private int age;
    private String id;
    
    public User toModel(){
    	User u = new User();
    	if(id!=null)
    		u.setId(new ObjectId(id));
    	u.setName(name);
    	u.setEmail(email);
    	u.setPassword(password);
    	u.setAge(age);
    	return u;
    }
    
    public User toModel(String id){
    	User u = new User();
    	if(id!=null)
    		u.setId(new ObjectId(id));
    	return u;
    }
    
    public UserDTO fillFromModel(User u){
    	id = u.getId()!=null?u.getId().toHexString():null;
    	name = u.getName();
    	email = u.getEmail();
    	password = u.getPassword();
    	age = u.getAge();
    	return this;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}    
}
