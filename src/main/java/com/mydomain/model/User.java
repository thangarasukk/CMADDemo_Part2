package com.mydomain.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 * User of a particular site
 * @author thangarasu & animesh
 *
 */
@Entity("users")
@Indexes(
    @Index(value = "name", fields = @Field("name"))
)
public class User {
    private String name;
    private String email;
    private String password;
    private int age;
	@Id
    private ObjectId id;
    
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
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

//	public UpdateOperations<User> createUpdateOperations(UpdateOperations<User> operations){
//		operations.set("name", 		this.getName());
//		operations.set("email", 	this.getEmail());
//		operations.set("password", 	this.getPassword());
//		operations.set("age",		this.getAge());
//		return operations;
//	}
}
