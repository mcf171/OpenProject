package cn.com.model;

import java.util.ArrayList;

public class TreeNode {

	private double name; //节点名
	private ArrayList<TreeNode> child;  //子节点集合
	private int count;
	private TreeNode parentNode;

	
	public TreeNode() {
		this.name = -1;
		this.child = new ArrayList<TreeNode>();
		this.count = 0;
		parentNode = new TreeNode();
	}


	public double getName() {
		return name;
	}


	public void setName(double name) {
		this.name = name;
	}


	public ArrayList<TreeNode> getChild() {
		return child;
	}


	public void setChild(ArrayList<TreeNode> child) {
		this.child = child;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	public TreeNode getParentNode() {
		return parentNode;
	}


	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}


	public int getChildNode(double name) {
		int index = -1;
		TreeNode node = new TreeNode();
		for(int i=0; i<child.size(); i++) {
			node = child.get(i);
			if(node.name == name) {
				index = i;
				break;				
			}
			
		}
		return -1;
	}
}
