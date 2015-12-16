/**
 *
 */
package cn.com.service;

import java.util.List;
import java.util.Map;

import cn.com.model.People;
import cn.com.util.TextUtil;
import junit.framework.TestCase;

public class GroupMiningTest extends TestCase {

	/**
	 * Test method for {@link cn.com.service.GroupMining#kmeans(int, cn.com.model.People[], java.util.List)}.
	 */
	public void testKmeans() {
		People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		List<Integer> people;
		String fileName;
		
		

		fileName = "data/Gowalla_totalCheckins.txt";
		
		//获取有效的用户和地名
		numbers = TextUtil.getNumber(fileName);
		
		//通过有效的用户和地名筛选people
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 1, 1);
		
		//获取拥有好友关系和group truth的people
		people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		
		GroupMining gm = new GroupMining();
		gm.kmeans(3, peopleArray, people);
		int count = 0;
		for(int i=0; i<people.size(); i++) {
			People p = peopleArray[people.get(i)];

			if(p.getLabel().equals(p.getTrueLabel())){
				count ++;
			}
		}
		System.out.println(count);
	}
}
