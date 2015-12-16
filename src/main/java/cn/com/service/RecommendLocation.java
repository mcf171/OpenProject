package cn.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.model.Location;
import cn.com.model.People;

public class RecommendLocation {

	public static Location recommendLocation(int peopleIndex, People[] peopleArray){
		
		List<Location> locationList = new ArrayList<Location>();
		
		
		
		Map<Double, Integer> locationCount = new HashMap<Double, Integer>();
		for(Integer friend : peopleArray[peopleIndex].getFriends()){
			People people = peopleArray[friend];
			for(Location location : people.getLists()){
				
				Integer count = locationCount.get(location.getLocation());
				if(count == null)
					locationCount.put(location.getLocation(), 1);
				else
					locationCount.put(location.getLocation(), count + 1);
					
			}
		}
		Iterator iter1 = locationCount.entrySet().iterator();
		double maxLocation = 0;
		double maxCount = 0;
		while (iter1.hasNext()) {
			
			Map.Entry entry = (Map.Entry) iter1.next();
			
			Integer val = (Integer) entry.getValue();
			
			if(val > maxCount){
				maxCount = val;
				maxLocation = (Double)entry.getKey();
			}
		}
		Location location = new Location();
		location.setLocation(maxLocation);
		System.out.println("the top 1 places that your friends usually go to is " + ((Double)maxLocation).intValue() + " and times is " + maxCount );
		return location;
	}
}
