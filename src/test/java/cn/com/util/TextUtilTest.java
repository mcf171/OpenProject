package cn.com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import cn.com.model.People;

public class TextUtilTest extends TestCase {

	public void testMarkFriends(){
		
		People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		List<Integer> people;
		String fileName = "data/testFriends.txt";
		numbers = TextUtil.getNumber(fileName);
		
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 1, 1);
		
		people = TextUtil.getFriends("data/testEdges.txt", peopleArray);
		this.assertEquals(people.size(), 3);
		//people = TextUtil.markFriends(people,peopleArray);
		System.out.println("success");
		

		fileName = "data/Gowalla_totalCheckins.txt";
		numbers = TextUtil.getNumber(fileName);
		
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		
		people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		
		//people = TextUtil.markFriends(people,peopleArray);
		System.out.println("success");
	}
	
	public void testGetFriends(){

		People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		String fileName = "data/Gowalla_totalCheckins.txt";
		numbers = TextUtil.getNumber(fileName);
		
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		List<Integer> people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		
		System.out.println(people.size());
	}
	
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
		
		List<People> exitstedPeople = new ArrayList<People>();
		for(People people :list)
			if(people!= null)
				exitstedPeople.add(people);
		System.out.println(peopleCount);
	}

}
