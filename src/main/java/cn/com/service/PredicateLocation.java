package cn.com.service;

import java.util.ArrayList;
import java.util.List;

import cn.com.model.Location;
import cn.com.model.People;
import cn.com.model.TreeNode;
import cn.com.util.PreLocationName;

public class PredicateLocation {

	public double predictLocation(People p, List<Integer> trainData, People[] peopleArray) {
		double nextLocation = -1;
		List<Double> locationNames = new ArrayList<Double>();  //存储训练集中出现过的所有Location
		for(int i=0; i<trainData.size(); i++) {
			List<Location> location = peopleArray[trainData.get(i)].getLists();
			for(int j=0; j<location.size(); j++) {
				if(!(locationNames.contains(location.get(j).getLocation()))) {
					locationNames.add(location.get(j).getLocation());
				}
			}
		}
		PreLocationName.preLocationName(p, locationNames);
		List<Location> locationList = p.getProcessedLocation();  //存储用户p中所有经过处理的Location
		TreeNode root = buildTree(peopleArray,trainData,locationNames);
		TreeNode nowNode = new TreeNode();
		for(int i=0; i<locationList.size()-1; i++) {
			int index = -1;
			double name = locationList.get(i).getLocation();
			if(i == 0) {
				index = root.getChildNode(name);
				nowNode = root.getChild().get(index);
			}else{
				index = nowNode.getChildNode(name);
				if(index != -1) {					
					nowNode = nowNode.getChild().get(index);
				}else{
					while(index == -1) {						
						nowNode = nowNode.getParentNode();
						index = nowNode.getChildNode(name);
					}
					nowNode = nowNode.getChild().get(index);
				}
			}
		}
		int maxCount = nowNode.getChild().get(0).getCount();
		nextLocation = nowNode.getChild().get(0).getName();
		for(int i=1; i<nowNode.getChild().size(); i++) {
			if(nowNode.getChild().get(i).getCount() > maxCount) {
				nextLocation = nowNode.getChild().get(0).getName();
			}
		}
		return nextLocation;
	}
	
	public TreeNode buildTree(People[] peopleArray,List<Integer> datas, List<Double> locationName) {
		TreeNode root = new TreeNode();
		root.setName(-1);
		for(int i=0; i<locationName.size(); i++) {
			TreeNode node = new TreeNode();
			node.setName(locationName.get(i));
			root.getChild().add(node);
		}
		for(int i=0; i<datas.size(); i++) {
			People p = peopleArray[datas.get(i)];
			List<Location> locationList = p.getLists();
			TreeNode nowNode = new TreeNode();
			nowNode = root;
			for(int j=0; j<locationList.size(); j++) {
				Location location = locationList.get(j);
				nowNode = getNextNode(location,nowNode);
			}
		}
		return root;
	}
	
	public TreeNode getNextNode(Location location, TreeNode nowNode) {
		TreeNode nextNode = nowNode;
		int index = nowNode.getChildNode(location.getLocation());
		if(index != -1) {	
			int count = nowNode.getChild().get(index).getCount() + 1;
			nowNode.getChild().get(index).setCount(count);
			nextNode = nowNode.getChild().get(index);
		}else{
			TreeNode node = new TreeNode();
			node.setName(location.getLocation());
			nowNode.getChild().add(node);
			nextNode = node;
		}
		return nextNode;
	}	
}
