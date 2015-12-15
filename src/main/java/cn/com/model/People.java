package cn.com.model;

import java.util.ArrayList;
import java.util.List;

public class People {

	private int userId;
	private String label;
	private String trueLabel;
	
	private List<People> recommend = new ArrayList<People>();
	private List<People> friends = new ArrayList<People>();
	private List<Location> lists = new ArrayList<Location>();
	
	public List<People> getFriends() {
		return friends;
	}

	public void setFriends(List<People> friends) {
		this.friends = friends;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Location> getLists() {
		return lists;
	}

	public void setLists(List<Location> lists) {
		this.lists = lists;
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

	public List<People> getRecommend() {
		return recommend;
	}

	public void setRecommend(List<People> recommend) {
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
	
	
	
}
