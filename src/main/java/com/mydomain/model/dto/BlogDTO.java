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
	private String synopsis;
    private String content;
    private String posterUrl;
    private String tags;
    private int viewedCount;
    private Date postedDate= new Date();
    private String postedUserName;
    private String postedUserId;
    private String id;
    
    public Blog toModel(){
    	Blog blog = new Blog();
    	if(id!=null)
    		blog.setId(new ObjectId(id));
    	blog.setTitle(title);
    	blog.setSynopsis(synopsis);
    	blog.setContent(content);
    	blog.setPosterUrl(posterUrl);
    	blog.setTags(tags);
    	blog.setViewedCount(viewedCount);
    	blog.setPostedDate(postedDate);
    	blog.setPostedUserName(postedUserName);
    	if(postedUserId!=null)
    		blog.setPostedUserId(new ObjectId(postedUserId));
    	return blog;
    }
    
    public Blog toModel(String id){
    	Blog blog = new Blog();
    	if(id!=null)
		try{
			blog.setId(new ObjectId(id));
		}
    	catch(IllegalArgumentException execption){
    		System.out.println("BlogDTO.toModel() IllegalArgumentException");
    	}
    		
    	return blog;
    }
    
    public BlogDTO fillFromModel(Blog blog){
    	id = blog.getId()!=null?blog.getId().toHexString():null;
    	title = blog.getTitle();
    	synopsis = blog.getSynopsis();
    	content = blog.getContent();
    	posterUrl = blog.getPosterUrl();
    	tags = blog.getTags();
    	viewedCount = blog.getViewedCount();
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
	
    @Override
	public String toString() {
		return "BlogDTO [title=" + title + ", content=" + content + ", tags=" + tags + ", postedDate=" + postedDate
				+ ", postedUserName=" + postedUserName + ", postedUserId=" + postedUserId + ", id=" + id + "]";
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public int getViewedCount() {
		return viewedCount;
	}

	public void setViewedCount(int viewedCount) {
		this.viewedCount = viewedCount;
	}
}
