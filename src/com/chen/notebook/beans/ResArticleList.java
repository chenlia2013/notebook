package com.chen.notebook.beans;

import java.util.List;

public class ResArticleList {

	private String count;
	
	private String request_id;
	
	private List<Article> records;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public List<Article> getRecords() {
		return records;
	}

	public void setRecords(List<Article> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "ResArticleList [count=" + count + ", request_id=" + request_id
				+ ", records=" + records + "]";
	}
	
	
}
