package com.aoeng.play.domain;

import java.util.List;

public class AppInfo {
	private long id;//app 的 ID
	private String name;// 軟件名稱
	private String packageName;//軟件包名
	private String iconUrl;//icon 地址
	private float stars;// 評價星級
	private String downloadNum;//下載數量
	private String version;//版本號
	private String date;//發佈日期
	private long size;//軟件大小
	private String downloadUrl;//下載地址
	private String desc;//簡介
	private String author;//作者
	private List<String> screen;//截圖下載地址
	private List<String> safeUrl;//安全信息圖標地址
	private List<String> safeDesUrl;//安全信息圖標描述 地址
	private List<String> safeDes;//安全信息描述
	private List<Integer> safeDesColor;//安全信息文字顏色
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public float getStars() {
		return stars;
	}
	public void setStars(float stars) {
		this.stars = stars;
	}
	public String getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(String downloadNum) {
		this.downloadNum = downloadNum;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getScreen() {
		return screen;
	}
	public void setScreen(List<String> screen) {
		this.screen = screen;
	}
	public List<String> getSafeUrl() {
		return safeUrl;
	}
	public void setSafeUrl(List<String> safeUrl) {
		this.safeUrl = safeUrl;
	}
	public List<String> getSafeDesUrl() {
		return safeDesUrl;
	}
	public void setSafeDesUrl(List<String> safeDesUrl) {
		this.safeDesUrl = safeDesUrl;
	}
	public List<String> getSafeDes() {
		return safeDes;
	}
	public void setSafeDes(List<String> safeDes) {
		this.safeDes = safeDes;
	}
	public List<Integer> getSafeDesColor() {
		return safeDesColor;
	}
	public void setSafeDesColor(List<Integer> safeDesColor) {
		this.safeDesColor = safeDesColor;
	}
	
	

}
