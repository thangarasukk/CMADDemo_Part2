package com.mydomain.model;

import java.util.Date;

public class Blog {

	private Integer blogId;
	private Integer userId;
	private String title="";
	private String data="";
	private Date postedDate= new Date();

	public Integer getBlogId() {
		return blogId;
	}
	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	@Override
	public String toString() {
		return "Blog [blogId=" + blogId + ", userId=" + userId + ", title=" + title + ", data=" + data + ", postedDate="
				+ postedDate + "]";
	}
	
}
