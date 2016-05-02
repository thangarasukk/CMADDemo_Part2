package com.mydomain.model.dto;

import java.util.Date;
import org.bson.types.ObjectId;
import com.mydomain.model.BlogComment;

/**
 * User of a particular site
 * @author thangarasu & animesh
 *
 */
public class BlogCommentDTO {
    private String comment;
    private String postedUserName;
    private Date postedDate= new Date();
    private String postedUserId;
    private String blogId;
    private String id;
    
    public BlogComment toModel(){
    	BlogComment blogComment = new BlogComment();
    	if(id!=null)
    		blogComment.setId(new ObjectId(id));
    	blogComment.setComment(comment);
    	blogComment.setPostedUserName(postedUserName);
    	blogComment.setPostedDate(postedDate);
    	if(postedUserId!=null)
    		blogComment.setPostedUserId(new ObjectId(postedUserId));
    	if(blogId!=null)
    		blogComment.setBlogId(new ObjectId(blogId));
    	return blogComment;
    }
    
    public BlogCommentDTO fillFromModel(BlogComment blogComment){
    	id = blogComment.getId()!=null?blogComment.getId().toHexString():null;
    	comment = blogComment.getComment();
    	postedUserName = blogComment.getPostedUserName();
    	postedDate = blogComment.getPostedDate();
    	blogId = blogComment.getBlogId()!=null?blogComment.getBlogId().toHexString():null;
    	postedUserId = blogComment.getPostedUserId()!=null?blogComment.getPostedUserId().toHexString():null;
    	return this;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	public String getPostedUserId() {
		return postedUserId;
	}
	public void setPostedUserId(String postedUserId) {
		this.postedUserId = postedUserId;
	}

	public String getPostedUserName() {
		return postedUserName;
	}

	public void setPostedUserName(String postedUserName) {
		this.postedUserName = postedUserName;
	}
}
