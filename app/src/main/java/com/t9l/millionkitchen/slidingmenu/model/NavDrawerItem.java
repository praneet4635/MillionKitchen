package com.t9l.millionkitchen.slidingmenu.model;

public class NavDrawerItem {

	private String title;
	private String subTitle;
	private String userImage;
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	private int icon;

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public NavDrawerItem() {
	}

	public NavDrawerItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
	}
	public NavDrawerItem(String title,String subTitle, int icon,String userImage) {
		this.title = title;
		this.subTitle=subTitle;
		this.icon = icon;
		this.userImage=userImage;
	}
	public NavDrawerItem(String title, int icon, boolean isCounterVisible,
			String count) {
		this.title = title;
		this.icon = icon;
	}

	public String getTitle() {
		return this.title;
	}

	public int getIcon() {
		return this.icon;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
