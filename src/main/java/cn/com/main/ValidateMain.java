/**
 *
 */
package cn.com.main;

import java.util.List;
import java.util.Map;

import cn.com.model.People;
import cn.com.util.TextUtil;

/**
 * @author sand
 *
 */
public class ValidateMain {

	 public static void main(String[]args){
		 
		 
	 }
	 
	 public static void validateGoupMining(){
		 
		People[] peopleArray;
		List<Map<Double,Integer>> numbers;
		List<Integer> people;
		String fileName;
		
		fileName = "data/Gowalla_totalCheckins.txt";
		
		//获取有效的用户和地名
		numbers = TextUtil.getNumber(fileName);
		
		//通过有效的用户和地名筛选people
		peopleArray = TextUtil.getLimitedPeople(numbers, fileName, 5, 40);
		
		//获取拥有好友关系和group truth的people
		people = TextUtil.getFriends("data/Gowalla_edges.txt", peopleArray);
		
		List<List<Integer>>  results;
		
		//List<List<Integer>>  results = GroupMining.
		
		
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
