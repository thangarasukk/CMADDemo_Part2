package com.mydomain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Entity("blogs")
@Indexes(
    @Index(value = "title", fields = @Field("title"))
)
public class Blog {
    private String title;
    private String content;
    private String tags;
    private Date postedDate= new Date();
    private String postedUserName;
    private ObjectId postedUserId;
	@Id
    private ObjectId id;
	
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
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getPostedUserName() {
		return postedUserName;
	}
	public void setPostedUserName(String postedUserName) {
		this.postedUserName = postedUserName;
	}
	public ObjectId getPostedUserId() {
		return postedUserId;
	}
	public void setPostedUserId(ObjectId postedUserId) {
		this.postedUserId = postedUserId;
	}
    
}
