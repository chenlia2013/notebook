package com.chen.notebook.beans;

import java.util.Arrays;

public class Article {

	private String _ctime;
	
	private String key;
	
	private String _mtime;
	
	private String _abstract;
	
	private String[] attachments;
	
	private String content;
	
	private String duration;
	
	private String lctime;
	
	private String lmtime;
	
	private String noteid;
	
	private String showtype;
	
	private String title;

	public String get_ctime() {
		return _ctime;
	}

	public void set_ctime(String _ctime) {
		this._ctime = _ctime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String get_mtime() {
		return _mtime;
	}

	public void set_mtime(String _mtime) {
		this._mtime = _mtime;
	}

	public String getAbstract() {
		return _abstract;
	}

	public void setAbstract(String _abstract) {
		this._abstract = _abstract;
	}

	public String[] getAttachments() {
		return attachments;
	}

	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLctime() {
		return lctime;
	}

	public void setLctime(String lctime) {
		this.lctime = lctime;
	}

	public String getLmtime() {
		return lmtime;
	}

	public void setLmtime(String lmtime) {
		this.lmtime = lmtime;
	}

	public String getNoteid() {
		return noteid;
	}

	public void setNoteid(String noteid) {
		this.noteid = noteid;
	}

	public String getShowtype() {
		return showtype;
	}

	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Article [_ctime=" + _ctime + ", key=" + key + ", _mtime="
				+ _mtime + ", _abstract=" + _abstract + ", attachments="
				+ Arrays.toString(attachments) + ", content=" + content
				+ ", duration=" + duration + ", lctime=" + lctime + ", lmtime="
				+ lmtime + ", noteid=" + noteid + ", showtype=" + showtype
				+ ", title=" + title + "]";
	}
	
	
}
