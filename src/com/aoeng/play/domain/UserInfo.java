package com.aoeng.play.domain;

/**
 * 
 * @author aoeng Aug 31, 2014 11:45:00 AM
 */
public class UserInfo {
	private String url;
	private String name;
	private String email;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserInfo [url=" + url + ", name=" + name + ", email=" + email + "]";
	}

}
