package cn.com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.model.Location;
import cn.com.model.People;

public class TextUtil {

	public static double minX = Double.MAX_VALUE, maxX = 0, avgX = 0, minY = Double.MAX_VALUE, maxY = 0,avgY = 0;
	public static double maxDistance = 0, minDistance = Double.MAX_VALUE,avgDistance = 0;
	
	public static List<Integer> markFriends(List<Integer> peopleList,People[] peopleArray){
		
		int labelCount = 0;
		
		for(int peopleIndex :peopleList){
			
			if(peopleArray[peopleIndex].getTrueLabel().equals("unknown")){
				setLabel(peopleIndex, labelCount + "",peopleArray);
				labelCount ++;
			}
		}
		
		
		return peopleList;
	}
	
	public static void setLabel(int peopleIndex, String label,People[] peopleArray){
		
		if(peopleArray[peopleIndex].getTrueLabel().equals("unknown")){
			peopleArray[peopleIndex].setTrueLabel(label);
			for(int friendIndex : peopleArray[peopleIndex].getFriends())
				setLabel(friendIndex, label,peopleArray);
		}
	}
	
	public static List<Integer> getFriends(String fileName, People[] peopleArray){
		
		List<Integer> peopleList = new ArrayList<Integer>();
		FileReader fr;
		try {
			fr = new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
	        String line="";
	        String[] arrs=null;
			int labelCount = 0;
			while ((line=br.readLine())!=null) {
				
				arrs = line.split("\t");
				
				int peopleIndex = Integer.parseInt(arrs[0]);
				int friendIndex = Integer.parseInt(arrs[1]);
				
				if(peopleArray[peopleIndex] != null && peopleArray[friendIndex] != null){
					
					if(!peopleArray[peopleIndex].getFriends().contains(peopleArray[friendIndex])){
						String trueLabel = "";
						if(peopleArray[peopleIndex].getTrueLabel().equals("unknown") &&peopleArray[friendIndex].getTrueLabel().equals("unknown")){
							
							trueLabel = "" + labelCount;
							labelCount++;
						}else{
							
							if(!peopleArray[peopleIndex].getTrueLabel().equals("unknown"))
								trueLabel = peopleArray[peopleIndex].getTrueLabel();
							if(!peopleArray[friendIndex].getTrueLabel().equals("unknown"))
								trueLabel = peopleArray[friendIndex].getTrueLabel();
							
						}
						
						peopleArray[friendIndex].setTrueLabel(trueLabel);
						peopleArray[peopleIndex].setTrueLabel(trueLabel);
						
						peopleArray[peopleIndex].getFriends().add(friendIndex);
						if(!peopleArray[friendIndex].getFriends().contains(peopleArray[peopleIndex]))
							peopleArray[friendIndex].getFriends().add(peopleIndex);
					}
					
				}
				
			}
			for(int i = 0; i < peopleArray.length ; i ++)
				if(peopleArray[i]!=null){
					if(peopleArray[i].getTrueLabel().equals("unknown")){
						peopleArray[i].setTrueLabel("" + labelCount);
						labelCount++;
					}
					peopleList.add(i);
				}
			
	        br.close();
	        fr.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return peopleList;
	}
	
	public static List<People> getLocation(String fileName){
		
		List<People> lists = new ArrayList<People>();
		FileReader fr;
		int lineNumber = 0;
		try {
			fr = new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
	        String line="";
	        String[] arrs=null;

			double minRecordLocationCount = Integer.MAX_VALUE, avgRecordLocationCount = 0;
			double minRecordPeopleCount = Integer.MAX_VALUE, avgRecordPeopleCount = 0;
			double maxLocation = 0, minLocation = Double.MAX_VALUE;
			Map<Double, Integer> countLocation = new HashMap<Double, Integer>();
			Map<Double, Integer> countPeople = new HashMap<Double, Integer>();
			
			while ((line=br.readLine())!=null) {
				
				line = line.trim();
	            arrs=line.split("\t");
	            	
	            if(Double.parseDouble(arrs[4]) > maxLocation)
	            	maxLocation = Double.parseDouble(arrs[4]);
	            if(Double.parseDouble(arrs[4]) < minLocation)
	            	minLocation = Double.parseDouble(arrs[4]);
	            
				Integer locationLabel = countLocation.get(Double.parseDouble(arrs[4]));
				Integer peopleLabel = countPeople.get(Double.parseDouble(arrs[0]));
				if(locationLabel == null)
					countLocation.put(Double.parseDouble(arrs[4]), 1);
				else
					countLocation.put(Double.parseDouble(arrs[4]), locationLabel + 1);
				
				if(peopleLabel == null)
					countPeople.put(Double.parseDouble(arrs[4]), 1);
				else
					countPeople.put(Double.parseDouble(arrs[4]), peopleLabel + 1);
			}
			System.out.println("maxLocation is " + maxLocation + ", minLocation is " + minLocation);
			Iterator iter = countLocation.entrySet().iterator();
			int locationCount = 0;
			while (iter.hasNext()) {
				
				locationCount ++;
				Map.Entry entry = (Map.Entry) iter.next();
				
				Integer val = (Integer) entry.getValue();
				
				avgRecordLocationCount += val;
				if(val < minRecordLocationCount)
					minRecordLocationCount = val;
			}
			
			Iterator iter1 = countPeople.entrySet().iterator();
			int peopleCount = 0;
			while (iter1.hasNext()) {
				
				peopleCount ++;
				Map.Entry entry = (Map.Entry) iter1.next();
				
				Integer val = (Integer) entry.getValue();
				
				avgRecordPeopleCount += val;
				if(val < minRecordPeopleCount)
					minRecordPeopleCount = val;
			}
			
			avgRecordLocationCount /= locationCount;
			avgRecordPeopleCount /= peopleCount;
			System.out.println("min people count is : " + minRecordPeopleCount + ", avg people count is :" + avgRecordPeopleCount);
			System.out.println("min location count is : " + minRecordLocationCount + ", avg local count is :" + avgRecordLocationCount);
	
	        /*
	        while ((line=br.readLine())!=null) {
	        
	        	line = line.trim();
	            arrs=line.split("\t");
	            People people = new People();
	            lineNumber++;
	            people.setUserId(Integer.parseInt(arrs[0]));
	            
	            if(lists.contains(people))
	            	people = lists.get(lists.indexOf(people));
	            else
	            	lists.add(people);
	            
	            
	            Location location = new Location();
	            location.setTime(arrs[1]);
	            location.setLatitude(Double.parseDouble(arrs[2]));
	            location.setLongitude(Double.parseDouble(arrs[3]));
	            location.setLocation(Double.parseDouble(arrs[4]));
	            
	            people.getLists().add(location);
	            
	            if(lineNumber%100000 == 0){
	            	System.out.println("lineNumber is :" + lineNumber);
	            }
	        }
	        */
	        
	        br.close();
	        fr.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return lists;
	}
	
	public static People[] getLimitedPeople(List<Map<Double,Integer>> numbers,String fileName,int limitedLocation, int limitedPeople){
		
		People[] peopleArray = new People[200000];
		int[] peopleAllowed = new int[200000];
		int[] locationAllowed = new int[6000000];
		
		Map<Double, Integer> countLocation = numbers.get(1);
		Map<Double, Integer> countPeople = numbers.get(0);
		
		Iterator iter = countLocation.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry entry = (Map.Entry) iter.next();
			
			Integer val = (Integer) entry.getValue();
			
			if(val > limitedLocation)
				locationAllowed[((Double)entry.getKey()).intValue()] = 1;
		}
		
		Iterator iter1 = countPeople.entrySet().iterator();
		while (iter1.hasNext()) {
			
			Map.Entry entry = (Map.Entry) iter1.next();
			
			Integer val = (Integer) entry.getValue();
			
			if(val > limitedPeople)
				peopleAllowed[((Double) entry.getKey()).intValue()]=1;
			
		}
		
		
		//System.out.println(" original location size is : " + countLocation.size() + ". after reduce, the location size is :" + allowLocation.size());
		//System.out.println(" original people size is : " + countPeople.size() + ". after reduce, the people size is :" + allowPeople.size());
		
		FileReader fr;
		int lineNumber = 0;
		try {
			fr = new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
	        String line="";
	        String[] arrs=null;

	        while ((line=br.readLine())!=null) {
	        
	        	line = line.trim();
	            arrs=line.split("\t");
	            lineNumber++;
	            if(locationAllowed[Integer.parseInt(arrs[4])]==1  && peopleAllowed[Integer.parseInt(arrs[0])] == 1){
	            	
	            	People people = peopleArray[Integer.parseInt(arrs[0])];
		            
		            if(people == null){
		            	
		            	peopleArray[Integer.parseInt(arrs[0])] = new People();
		            	people = peopleArray[Integer.parseInt(arrs[0])];
		            	people.setUserId(Integer.parseInt(arrs[0]));
		            	people.setTrueLabel("unknown");
		            }
		            
		            Location location = new Location();
		            location.setTime(arrs[1]);
		            location.setLatitude(Double.parseDouble(arrs[2]));
		            location.setLongitude(Double.parseDouble(arrs[3]));
		            location.setLocation(Double.parseDouble(arrs[4]));
		            
		            
		            people.getLists().add(location);
	            }
	            	
	            
	        }
	        
	        
	        br.close();
	        fr.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return peopleArray;
	}
	
	public static List<Map<Double,Integer>> getNumber(String fileName){
		List<Map<Double,Integer>> numbers = new ArrayList<Map<Double,Integer>>();
		FileReader fr;
		try {
			fr = new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
	        String line="";
	        String[] arrs=null;
			
			Map<Double, Integer> countLocation = new HashMap<Double, Integer>();
			Map<Double, Integer> countPeople = new HashMap<Double, Integer>();
			
			while ((line=br.readLine())!=null) {
				
				line = line.trim();
	            arrs=line.split("\t");
	            	
				Integer locationLabel = countLocation.get(Double.parseDouble(arrs[4]));
				Integer peopleLabel = countPeople.get(Double.parseDouble(arrs[0]));
				if(locationLabel == null)
					countLocation.put(Double.parseDouble(arrs[4]), 1);
				else
					countLocation.put(Double.parseDouble(arrs[4]), locationLabel + 1);
				
				if(peopleLabel == null)
					countPeople.put(Double.parseDouble(arrs[0]), 1);
				else
					countPeople.put(Double.parseDouble(arrs[0]), peopleLabel + 1);
			}
			
	       numbers.add(countPeople);
	       numbers.add(countLocation); 
	        br.close();
	        fr.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return numbers;
	}
}
