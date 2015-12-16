package cn.com.model;

import java.util.ArrayList;
import java.util.List;

public class People {

	private int userId;
	private String label;
	private String trueLabel;
	
	private List<Integer> recommend = new ArrayList<Integer>();
	private List<Integer> friends = new ArrayList<Integer>();
	private List<Location> locationLists = new ArrayList<Location>();
	private List<Location> processedLocation = new ArrayList<Location>();
	
	
	
	public List<Location> getProcessedLocation() {
		return processedLocation;
	}

	public void setProcessedLocation(List<Location> processedLocation) {
		this.processedLocation = processedLocation;
	}

	public List<Integer> getFriends() {
		return friends;
	}

	public void setFriends(List<Integer> friends) {
		this.friends = friends;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Location> getLists() {
		return locationLists;
	}

	public void setLists(List<Location> lists) {
		this.locationLists = lists;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTrueLabel() {
		return trueLabel;
	}

	public void setTrueLabel(String trueLabel) {
		this.trueLabel = trueLabel;
	}

	public List<Integer> getRecommend() {
		return recommend;
	}

	public void setRecommend(List<Integer> recommend) {
		this.recommend = recommend;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(obj instanceof People){
			
			People compare = (People)obj;
			flag = this.userId == compare.getUserId();		
		}
			
		return flag;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String description = "user id :" + userId + ", friends size is :" + friends.size() + ",location size is :" + locationLists.size();
		return description;
	}
	
	
	
}
