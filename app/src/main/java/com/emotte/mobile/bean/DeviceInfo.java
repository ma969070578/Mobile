package com.emotte.mobile.bean;

import java.util.Date;

public class DeviceInfo extends EntityBase {

	// 序号 字段名 类型 长度 允许空 默认值 说明
	// 1 devicecode nvarchar 200 否 　 设备号
	// 2 systemtype smallint 5 是 　 系统类型 1ios 2android
	// 3 mobilebrand nvarchar 200 是 　 手机品牌
	// 4 mobilemodel nvarchar 200 是 　 手机型号
	// 5 systemname varchar 100 是 　 系统名称
	// 6 systemversion varchar 50 是 　 系统版本
	// 7 appname nvarchar 100 是 　 应用名称
	// 8 appversion varchar 50 是 　 应用版本号
	// 9 channel nvarchar 200 是 　 渠道
	// 10 createtime datetime 23 是 (getdate()) 创建时间

	int id;
	String devicecode;
	int systemtype;
	String mobilebrand;
	String mobilemodel;
	String systemname;
	String systemversion;
	String appname;
	String appversion;
	String channel;
	Date createtime;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	public int getSystemtype() {
		return systemtype;
	}

	public void setSystemtype(int systemtype) {
		this.systemtype = systemtype;
	}

	public String getMobilebrand() {
		return mobilebrand;
	}

	public void setMobilebrand(String mobilebrand) {
		this.mobilebrand = mobilebrand;
	}

	public String getMobilemodel() {
		return mobilemodel;
	}

	public void setMobilemodel(String mobilemodel) {
		this.mobilemodel = mobilemodel;
	}

	public String getSystemname() {
		return systemname;
	}

	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}

	public String getSystemversion() {
		return systemversion;
	}

	public void setSystemversion(String systemversion) {
		this.systemversion = systemversion;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "DeviceInfo [id=" + id + ", devicecode=" + devicecode
				+ ", systemtype=" + systemtype + ", mobilebrand=" + mobilebrand
				+ ", mobilemodel=" + mobilemodel + ", systemname=" + systemname
				+ ", systemversion=" + systemversion + ", appname=" + appname
				+ ", appversion=" + appversion + ", channel=" + channel
				+ ", createtime=" + createtime + "]";
	}
	
	
	
	// devicecode", DeviceCommon.getDeviceId(ctx));
	// systemtype", AppType.ANDROID_PHONE.id
	// mobilebrand", DeviceCommon.brand())
	// mobilemodel", DeviceCommon.model());
	// systemname", DeviceCommon.sysCodeName())
	// systemversion", DeviceCommon.version());
	// appname", DeviceCommon.getAppName(ctx));
	// appversion", DeviceCommon.getAppVersion(ctx));
	// channel", "xiaomi");

}
