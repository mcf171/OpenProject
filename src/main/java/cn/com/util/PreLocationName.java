/**
 *
 */
package cn.com.util;

import java.util.List;

import cn.com.model.Location;
import cn.com.model.People;

public class PreLocationName {
	
	public static void preLocationName(People p, List<Double> locationName) {
		
		List<Location> location = p.getLists();
		List<Location> processedLocation = p.getProcessedLocation();
		for(int i=0; i<location.size(); i++) {
			double name = location.get(i).getLocation();
			if(locationName.contains(name)) {
				processedLocation.add(location.get(i));
			}
		}
	}

}
