
package cn.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.model.People;

public class GroupMining {

	private int k; //簇的数量
	private int dataSetLength; //数据元素个数，即数据集的长度
	private List<Integer> dataSet; //数据集
	private People[] peopleArray;
	private List<People> center; //簇中心集合
	private List<List<Integer>> cluster; //簇
	private Random random;
	private boolean endFlag;
	private int m = 1; //迭代次数
	
	public void init(int k, People[] peopleArray, List<Integer> dataSet) {
		if(k<=0) {
			k=1;
		}
		this.k = k;
		this.dataSet = dataSet;
		this.peopleArray = peopleArray;
		random = new Random();
		dataSetLength = dataSet.size();
		if(k>dataSetLength) {
			k = dataSetLength;
		}
		center = initCenter();
		cluster = initCluster();
		endFlag = false;
	}
	
	/*
	 * 初始化k个中心点
	 */
	public List<People> initCenter() {
		List<People> center = new ArrayList<People>();
		int[] randoms = new int[k];
		boolean flag;
		int temp = random.nextInt(dataSetLength);
		randoms[0]=temp;
		for(int i=0; i<k; i++) {
			flag = true;
			while(flag) {
				temp = random.nextInt(dataSetLength);
				int j=0;
				while(j<i) {
					if(temp == randoms[j]) {
						break;
					}
					j++;
				}
				if(j == i) {
					flag = false;
				}
			}
			randoms[i] = temp;
		}
		for(int i=0; i<k; i++) {
			center.add(peopleArray[dataSet.get(randoms[i])]);
		}
		return center;
	}
	
	/**
	 * 初始化k个空簇
	 */
	public List<List<Integer>> initCluster() {
		List<List<Integer>> cluster = new ArrayList<List<Integer>>();
		for (int i = 0; i < k; i++) {  
            cluster.add(new ArrayList<Integer>());  
        } 
		return cluster;
	}
	
	/** 
     * 计算两个People之间的非相似度
     */  
    public double distance(People p1, People p2) {  
        double distance = 0.0;  
        List<Integer> friendsOfP1 = p1.getFriends();
        List<Integer> friendsOfP2 = p2.getFriends();
        double numOfSameFriend = 0;
        for(int i=0; i<friendsOfP1.size(); i++) {
        	if(friendsOfP2.contains(friendsOfP1.get(i))) {
        		numOfSameFriend++;
        	}
        }
        distance = 1-(numOfSameFriend/friendsOfP1.size() + numOfSameFriend/friendsOfP2.size())/2;
        return distance;  
    }  
	
    /** 
     * 获取非相似度集合中最小非相似度的位置 
     */  
    public int minDistance(double[] distances) {  
        double minDistance = distances[0];  
        int minLocation = 0;  
        for (int i = 1; i < distances.length; i++) {  
            if (distances[i] < minDistance) {  
                minDistance = distances[i];  
                minLocation = i;  
            } else if (distances[i] == minDistance) // 如果相等，随机返回一个位置  
            {  
                if (random.nextInt(10) < 5) {  
                    minLocation = i;  
                }  
            }  
        }  
        return minLocation;  
    }  
    
    /** 
     * 将当前元素放到最小非相似度中心相关的簇中 
     */  
    public void clusterSet() {  
        double[] distances = new double[k];  
        for (int i = 0; i < dataSetLength; i++) {  
            for (int j = 0; j < k; j++) {  
                distances[j] = distance(peopleArray[dataSet.get(i)], center.get(j));  
            }  
            int minLocation = minDistance(distances);
            String oldLabel = peopleArray[dataSet.get(i)].getLabel();
            if(oldLabel == null || !oldLabel.equals(String.valueOf(minLocation))) {
            	endFlag = false;
            }
            peopleArray[dataSet.get(i)].setLabel(String.valueOf(minLocation));
            cluster.get(minLocation).add(i);
        }  
    } 
    
    /** 
     * 计算非相似度平方和
     */  
    public double SumSimilarity(People p, int t, List<List<Integer>> cluster,double currrentMinSum) {  
        double sum = 0;  
        for (int i = 0; i < cluster.get(t).size(); i++) { 
        	People temP = peopleArray[dataSet.get(cluster.get(t).get(i))];
        	if(!(temP.equals(p))) {
        		sum += distance(temP, p);
        		if(sum > currrentMinSum) {
        			break;
        		}
        	}
        }  
        return sum;  
    }
    
    /** 
     * 设置新的簇中心
     */  
    public void setNewCenter() { 
        for (int i = 0; i < k; i++) {  
            int n = cluster.get(i).size();  
            double currrentMinSum = Double.MAX_VALUE;
            if (n != 0) {  
            	People centerPeople = center.get(i);
            	double minSum = SumSimilarity(centerPeople, i, cluster,currrentMinSum);
                for(int j=0; j<n; j++) {
                	People temp = peopleArray[dataSet.get(cluster.get(i).get(j))];
                	if(!(temp.equals(centerPeople))){
                		double tempSum = SumSimilarity(temp, i, cluster,minSum);
                		if(tempSum < minSum) {
                			minSum = tempSum;
                			centerPeople = temp;
                			center.set(i, temp);
                		}
                	}
                }  
            }  
        }
    }
    
    public List<List<Integer>> kmeans(int k, People[] peopleArray, List<Integer> dataSet) {  
        init(k, peopleArray,dataSet);  
        // 循环分组，直到每个元素的类标不变为止  
        while(!endFlag || m<=10) {
        	System.out.println("第" + m + "次迭代");
        	endFlag = true;
        	setNewCenter();   
        	cluster.clear();  
            cluster = initCluster();
            clusterSet(); 
            m++;
        
        }
        return cluster;
    } 
}
