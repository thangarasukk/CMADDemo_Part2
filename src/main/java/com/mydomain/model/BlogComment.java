package com.mydomain.model;

import java.util.Date;

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
@Entity("comments")
@Indexes(
    @Index(value = "comment", fields = @Field("comment"))
)
public class BlogComment {
    private String comment;
    private String postedUserName;
    private Date postedDate= new Date();
    private ObjectId postedUserId;
    private ObjectId blogId;
	@Id
    private ObjectId id;
    
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public ObjectId getPostedUserId() {
		return postedUserId;
	}
	public void setPostedUserId(ObjectId postedUserId) {
		this.postedUserId = postedUserId;
	}
	public ObjectId getBlogId() {
		return blogId;
	}
	public void setBlogId(ObjectId blogId) {
		this.blogId = blogId;
	}
	public String getPostedUserName() {
		return postedUserName;
	}
	public void setPostedUserName(String postedUserName) {
		this.postedUserName = postedUserName;
	}
}
