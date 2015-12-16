/**
 *
 */
package cn.com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.com.model.People;
import cn.com.service.GroupMining;
import cn.com.service.PredicateLocation;
import cn.com.util.EXCELUtil;
import cn.com.util.TextUtil;

/**
 * @author sand
 *
 */
public class ValidateMain {

	 public static void main(String[]args){
		 
		 
	 }
	 
	 public static void validatePredictLocation(){
		
		 People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		List<Integer> people;
		String fileName;
		List<Object[]> purityResults = new ArrayList<Object[]>();
		List<Object[]> FScoreResults = new ArrayList<Object[]>();
		fileName = "data/Gowalla_totalCheckins.txt";
		
		//获取有效的用户和地名
		numbers = TextUtil.getNumber(fileName);
		
		//通过有效的用户和地名筛选people
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		
		//获取拥有好友关系和group truth的people
		people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		int step = 3655;
		double rigteRate = 0;
	
		for(int i = 0 ; i < 10 ; i ++){
			
			System.out.println("iterator time is :" + (i+1));
		
		//获取测试集合
		List<Integer> testList = new ArrayList<Integer>(people);
		//获取训练集合
		List<Integer> trainList = new ArrayList<Integer>();
		Collections.copy(testList, people);
		for(int j = 0 ; j < step; j ++){
			
			Random random = new Random();
	        int s = random.nextInt(testList.size())%(testList.size()-0+1) + 0;
	        
	        trainList.add(testList.get(s));
	        testList.remove(s);
		}
		PredicateLocation predicateLocation = new PredicateLocation();
		
		double rightCount = 0;
			for(Integer testIndex : testList){
				
				double locationPre = predicateLocation.predictLocation(peopleArray[testIndex], trainList, peopleArray);
				if(locationPre == peopleArray[testIndex].getLists().get(peopleArray[testIndex].getLists().size() - 1).getLocation())
					rightCount++;
			}
		rigteRate += rightCount/testList.size();
		}
		rigteRate /=10;
		System.out.println("total rightRate by DecisioinTree is : " + rigteRate*100 + "%");
	 }
	 
	 public static void validateGoupMining(){
		 
		People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		List<Integer> people;
		String fileName;
		List<Object[]> purityResults = new ArrayList<Object[]>();
		List<Object[]> FScoreResults = new ArrayList<Object[]>();
		fileName = "data/Gowalla_totalCheckins.txt";
		
		//获取有效的用户和地名
		numbers = TextUtil.getNumber(fileName);
		
		//通过有效的用户和地名筛选people
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		
		//获取拥有好友关系和group truth的people
		people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		
		List<List<Integer>>  results;
		GroupMining groupMining = new GroupMining();
		
		for(int i = 0 ; i < 20 ; i ++){
			
			results = groupMining.kmeans(i, peopleArray, people);
			double purity = purityValidate(peopleArray, results);

			// 计算F-score
			double FScore = FScoreValidate(peopleArray, results,people);

			// 数组前两位保存本次DBSCAN算法的两个参数e和MinPts的大小，最后一位保存的计算结果
			Object[] purityResult = new Object[3];
			Object[] FscoreResult = new Object[3];

			FscoreResult[0] = purityResult[0] = i;

			purityResult[1] = purity;
			FscoreResult[1] = FScore;

			purityResults.add(purityResult);
			FScoreResults.add(FscoreResult);
			System.out.println("when the k is " + i + "  the number of Cluster is : " + results.size()
			+ " the purity is :" + purity * 100 + "%");
			System.out.println("when the k is " + i + "  the number of Cluster is : " + results.size()
			+ " the F-score is :" + FScore * 100 + "%");
		}
		EXCELUtil.saveAsEXCEL(purityResults, 1, "purityResult", 0);
		EXCELUtil.saveAsEXCEL(FScoreResults, 1, "FScoreResult", 0);
		
	 }
	 
		/**
		 * 计算purity
		 * 
		 * @param points
		 *            所有有标的点
		 * @param clusters
		 *            聚类的结果
		 * @return
		 */
		public static double purityValidate(People[] people, List<List<Integer>> clusters) {

			double purity = 0;

			for (List<Integer> indexs : clusters) {

				double[] countLabel = new double[20];
				for (int index : indexs) {
					countLabel[Integer.parseInt(people[index].getTrueLabel())]++;
				}
				int maxLabelIndex = 0;
				for (int i = 0; i < 20; i++)
					if (countLabel[i] > countLabel[maxLabelIndex])
						maxLabelIndex = i;
				purity += countLabel[maxLabelIndex] / indexs.size();
			}

			return purity;
		}

		/**
		 * 计算F-score
		 * 
		 * @param points
		 *            所有有标的点
		 * @param clusters
		 *            聚类的结果
		 * @return
		 */
		public static double FScoreValidate(People[] people, List<List<Integer>> cluster,List<Integer> peopleIndexLists) {

			double FScore = 0;

			double[] correctNumber = new double[20];
			
			for (int peopleIndex : peopleIndexLists) {

				correctNumber[Integer.parseInt(people[peopleIndex].getTrueLabel())]++;
			}

			for (List<Integer> indexs : cluster) {

				double[] countLabel = new double[20];
				for (int index : indexs) {
					countLabel[Integer.parseInt(people[index].getTrueLabel())]++;
				}
				int maxLabelIndex = 0;
				for (int i = 0; i < 20; i++)
					if (countLabel[i] > countLabel[maxLabelIndex])
						maxLabelIndex = i;

				double r = countLabel[maxLabelIndex] / correctNumber[maxLabelIndex];
				double p = countLabel[maxLabelIndex] / indexs.size();

				double F_i = 2 * (p * r) / (p + r);

				FScore += F_i;
			}

			return FScore / cluster.size();
		}
}
