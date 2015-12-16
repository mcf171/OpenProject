package cn.com.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.model.People;
import cn.com.util.TextUtil;
import junit.framework.TestCase;

public class RecommendLocationTest extends TestCase {

	public void testRecommendLocation() {
		
		People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		List<Integer> people;
		String fileName;
		

		fileName = "data/testFriends.txt";
		
		//获取有效的用户和地名
		numbers = TextUtil.getNumber(fileName);
		
		//通过有效的用户和地名筛选people
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 1, 1);
		
		//获取拥有好友关系和group truth的people
		people = TextUtil.getFriends("data/testEdges.txt", peopleArray);
		
		for(Integer peopleIndex : people){
			
			RecommendLocation.recommendLocation(peopleIndex, peopleArray);
			
		}
		
		
		/*fileName = "data/Gowalla_totalCheckins.txt";
		
		//获取有效的用户和地名
		numbers = TextUtil.getNumber(fileName);
		
		//通过有效的用户和地名筛选people
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		
		//获取拥有好友关系和group truth的people
		people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		
		for(Integer peopleIndex : people){
			
			RecommendLocation.recommendLocation(peopleIndex, peopleArray);
			
		}*/
		System.out.println("success");
	}

}
