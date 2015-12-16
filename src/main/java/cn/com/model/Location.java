package cn.com.model;

import java.util.Date;

public class Location {

	private String time;
	private double latitude;
	private double longitude;
	private double location;
	private Date realTime;
	
	
	
	public Date getRealTime() {
		return realTime;
	}
	public void setRealTime(Date realTime) {
		this.realTime = realTime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLocation() {
		return location;
	}
	public void setLocation(double location) {
		this.location = location;
	}
	
	
}
