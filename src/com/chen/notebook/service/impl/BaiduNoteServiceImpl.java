package com.chen.notebook.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.chen.notebook.beans.ResArticleList;
import com.chen.notebook.beans.User;
import com.chen.notebook.service.IBaiduNoteService;
import com.chen.notebook.util.UrlEncode;

public class BaiduNoteServiceImpl implements IBaiduNoteService{

public static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	
	public static ResArticleList getArticle(){		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	    nvps.add(new BasicNameValuePair("param", ""));
	    
	    String str = getResByHasHeader("http://note.baidu.com/api/note?method=select&limit=0-100&t=1429688321796",nvps);
	    str = str.replaceAll("_key", "key");
	    System.out.println(str);
		return JSONArray.parseObject(str, ResArticleList.class);
	}
	
	public static void updateArticle(String content,String key){
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("param", "{\"content\":\""+UrlEncode.toUtf8String(content)+"\",\"_key\":\""+key+"\"}"));
	    
	    String str = getResByHasHeader("http://note.baidu.com/api/note?method=update&t=1429689968261",nvps);
	}
	
	public static void delArticle(String key){
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("param", "{\"_key\":[\""+key+"\"]}"));
	    
	    String str = getResByHasHeader("http://note.baidu.com/api/note?method=delete&t=1429694125569",nvps);
	}
	
	private static String getResByHasHeader(String str,List <NameValuePair> nvps){
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(str);
			httpPost.setHeader("Accept", "*/*");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//			httpPost.setHeader("Content-Length", "6");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			httpPost.setHeader("Cookie", "XMLHttpRequest");
			httpPost.setHeader("Host", "note.baidu.com");
			httpPost.setHeader("Referer", "http://note.baidu.com/");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
			httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
			
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static <T> List<T> convertToObject(String str,Class<T> clazz){
		return JSONArray.parseArray(str, clazz);
	}
	
	public static boolean login(User user) {
		
		try {
			HttpGet get= new HttpGet("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true");
			HttpResponse res = httpClient.execute(get);
			System.out.println(""+"[PSOT]Receive >>> " + res.getStatusLine().getStatusCode());
			
			HttpEntity entity = res.getEntity();
			
			get = new HttpGet("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=pp&tangram=true");
			res = httpClient.execute(get);
			System.out.println(""+"[PSOT]Receive >>> " + res.getStatusLine().getStatusCode());
			
			entity = res.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(  
					entity.getContent(), "utf-8"));
			String tempLine = br.readLine();
			StringBuffer sb = new StringBuffer();
			while(tempLine != null){
				if (tempLine.contains("bdPass.api.params.login_token=")) {  
			        sb.append(tempLine.substring(  
			                "bdPass.api.params.login_token=".length() + 1,  
			                tempLine.length() - 2));  
			    } 
			    tempLine = br.readLine();
			}

			HttpPost post = new HttpPost("https://passport.baidu.com/v2/api/?login");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("charset", "utf-8"));
			params.add(new BasicNameValuePair("token", sb.toString()));
			params.add(new BasicNameValuePair("tpl","pp"));
			params.add(new BasicNameValuePair("apiver","v3"));
			params.add(new BasicNameValuePair("tt","1390751409263"));
			params.add(new BasicNameValuePair("safeflg","0"));
			params.add(new BasicNameValuePair("isPhone","false"));
			params.add(new BasicNameValuePair("quick_user","0"));
			params.add(new BasicNameValuePair("logintype","basicLogin"));
			
			params.add(new BasicNameValuePair("username",user.getUsername()));
			params.add(new BasicNameValuePair("password",user.getPassword()));
			params.add(new BasicNameValuePair("isPhone","false"));
			params.add(new BasicNameValuePair("mem_pass","on"));
			params.add(new BasicNameValuePair("verifycode",""));
			params.add(new BasicNameValuePair("callback","parent.bd__pcbs__axjnsn"));
			
			
			UrlEncodedFormEntity uef = new UrlEncodedFormEntity(params,"utf-8");
			post.setEntity(uef);
			res = httpClient.execute(post);
			System.out.println(""+"[POST]Receive >>> " + res.getStatusLine().getStatusCode());
			entity = res.getEntity();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public static void main(String[] args) {
		User user = new  User();
		user.setUsername("506766132@qq.com");
		user.setPassword("c53281874");
		login(user);
		System.out.println(getArticle()); 
		
		updateArticle("111111111111111111111111111111111111111", getArticle().getRecords().get(0).getKey()); 
	}
}
