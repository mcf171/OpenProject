package cn.com.util;

import java.util.List;
import java.util.Map;

import cn.com.model.People;
import junit.framework.TestCase;

public class TextUtilTest extends TestCase {

	public void testGetLocation() {
		
		List<People> peopleList = TextUtil.getLocation("data/Gowalla_totalCheckins.txt");
		
		this.assertNotNull(peopleList);
	}
	
	public void testGetLimitedPeople(){
		
		String fileName = "data/test.txt";
		List<Map<Double,Integer>> numbers = TextUtil.getNumber(fileName);
		
		People[] list = TextUtil.getLimitedPeople(numbers, fileName, 2, 2);
		int peopleCount = 0;
		for(People people :list)
			if(people!= null)
				peopleCount++;
		this.assertEquals(peopleCount, 1);
		fileName = "data/Gowalla_totalCheckins.txt";
		numbers = TextUtil.getNumber(fileName);
		
		list = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		peopleCount = 0;
		for(People people :list)
			if(people!= null)
				peopleCount++;
		System.out.println(peopleCount);
	}

}
