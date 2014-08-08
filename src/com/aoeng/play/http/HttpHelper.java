package com.aoeng.play.http;

import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.AbstractHttpClient;

/**
 * 
 * @author aoeng Aug 2, 2014 10:21:16 AM
 */
public class HttpHelper {
	public static final String URL = "http://127.0.0.1:8080/play";
	
	public static HttpResult get(String url){
		HttpGet httpGet = new HttpGet(url);
		return execute(url,httpGet);
	}
	public static HttpResult post(String url,byte[] bytes){
		HttpPost httpPost = new HttpPost(url);
		ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes);
		httpPost.setEntity(byteArrayEntity);
		return execute(url, httpPost);
	}
	public static HttpResult download(String url){
		HttpGet httpGet = new HttpGet(url);
		return execute(url, httpGet);
	}
	private static HttpResult execute(String url, HttpRequestBase httpGet) {
		// TODO Auto-generated method stub
		boolean isHttps = url.startsWith("https://");
		AbstractHttpClient httpClient = HttpclientFactory.create(isHttps);
		return null;
	}
	public static class HttpResult {

		public InputStream getInputStream() {
			// TODO Auto-generated method stub
			return null;
		}

		public void close() {
			// TODO Auto-generated method stub

		}

	}


}
