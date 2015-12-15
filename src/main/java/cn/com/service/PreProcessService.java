package cn.com.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.model.Location;
import cn.com.model.People;

public class PreProcessService {

	public List<People> processData(List<People> peopleLists){
		
		double minRecordLocationCount = Integer.MAX_VALUE, avgRecordLocationCount = 0;
		double minRecordPeopleCount = Integer.MAX_VALUE, avgRecordPeopleCount = 0;
		
		Map<Double, Integer> countLocation = new HashMap<Double, Integer>();
		
		for(People people : peopleLists){
			
			avgRecordPeopleCount += people.getLists().size();
			
			if(people.getLists().size() < minRecordPeopleCount)
				minRecordLocationCount = people.getLists().size();
			
			for(Location location : people.getLists()){
				
				Integer locationLabel = countLocation.get(location.getLocation());
				
				if(locationLabel == null)
					countLocation.put(location.getLocation(), 1);
				else
					countLocation.put(location.getLocation(), countLocation.get(location.getLocation()) + 1);
			}
		}
		
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
		
		avgRecordPeopleCount /= peopleLists.size();
		avgRecordLocationCount /= locationCount;
		
		System.out.println("min people count is : " + minRecordPeopleCount + ", avg people count is :" + avgRecordPeopleCount);
		System.out.println("min location count is : " + minRecordLocationCount + ", avg people count is :" + avgRecordLocationCount);
		return peopleLists;
	}
}
