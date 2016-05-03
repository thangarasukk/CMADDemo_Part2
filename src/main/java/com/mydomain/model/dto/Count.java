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
public class Count implements Serializable{
	private int count;

	public Count(int count) {
		super();
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
