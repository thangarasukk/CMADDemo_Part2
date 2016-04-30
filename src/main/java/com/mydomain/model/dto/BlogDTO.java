package com.mydomain.model.dto;

import java.io.Serializable;
import java.util.Date;
import org.bson.types.ObjectId;
import com.mydomain.model.Blog;


/**
 * DTO class for transfering User data over json
 * @author thangarasu & animesh
 *
 */
public class BlogDTO implements Serializable{
    private String title;
    private String content;
    private String tags;
    private Date postedDate= new Date();
    private String postedUserName;
    private String postedUserId;
    private String id;
    
    public Blog toModel(){
    	Blog blog = new Blog();
    	if(id!=null)
    		blog.setId(new ObjectId(id));
    	blog.setTitle(title);
    	blog.setTags(tags);
    	blog.setPostedDate(postedDate);
    	blog.setPostedUserName(postedUserName);
    	if(postedUserId!=null)
    		blog.setPostedUserId(new ObjectId(postedUserId));
    	return blog;
    }
    
    public BlogDTO fillFromModel(Blog blog){
    	id = blog.getId()!=null?blog.getId().toHexString():null;
    	title = blog.getTitle();
    	content = blog.getContent();
    	tags = blog.getTags();
    	postedDate = blog.getPostedDate();
    	postedUserName = blog.getPostedUserName();
    	postedUserId = blog.getPostedUserId()!=null?blog.getPostedUserId().toHexString():null;
    	return this;
    }
    
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
	public String getPostedUserName() {
		return postedUserName;
	}
	public void setPostedUserName(String postedUserName) {
		this.postedUserName = postedUserName;
	}
	public String getPostedUserId() {
		return postedUserId;
	}
	public void setPostedUserId(String postedUserId) {
		this.postedUserId = postedUserId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
