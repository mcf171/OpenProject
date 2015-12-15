package cn.com.util;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPoints extends JPanel {
	
	private List<Object[]> values = new ArrayList<Object[]>();
	
	public void calculatePoint(){
		
		List<Map<Double,Integer>> numbers = TextUtil.getNumber("data/Gowalla_totalCheckins.txt");
		
		Map<Double, Integer> countLocation = numbers.get(1);
		Map<Double, Integer> countPeople = numbers.get(0);
		
		double minRecordLocationCount = Integer.MAX_VALUE, maxRecordLocationCount = 0 ,avgRecordLocationCount = 0;
		double minRecordPeopleCount = Integer.MAX_VALUE, maxRecordPeopleCount = 0, avgRecordPeopleCount = 0;

		Iterator iter = countLocation.entrySet().iterator();
		int locationCount = 0;
		while (iter.hasNext()) {
			
			locationCount ++;
			Map.Entry entry = (Map.Entry) iter.next();
			
			Double val = (Double) entry.getKey();
			Object [] value = new Object[2];
			value[0] = (Double) entry.getKey();
			value[1] = (Integer) entry.getValue();
			values.add(value);
			avgRecordLocationCount += val;
			if(val < minRecordLocationCount)
				minRecordLocationCount = val;
			if(val > maxRecordLocationCount)
				maxRecordLocationCount = val;
		}
		
		Iterator iter1 = countPeople.entrySet().iterator();
		int peopleCount = 0;
		while (iter1.hasNext()) {
			
			peopleCount ++;
			Map.Entry entry = (Map.Entry) iter1.next();
			
			Double val = (Double) entry.getKey();
//			Object [] value = new Object[2];
//			value[0] = (Double) entry.getKey();
//			value[1] = (Integer) entry.getValue();
//			values.add(value);
			avgRecordPeopleCount += val;
			if(val < minRecordPeopleCount)
				minRecordPeopleCount = val;
			if(val > maxRecordPeopleCount)
				maxRecordPeopleCount = val;
		}
		
		avgRecordLocationCount /= locationCount;
		avgRecordPeopleCount /= peopleCount;
		System.out.println("min people count is : " + minRecordPeopleCount + ", avg people count is :" + avgRecordPeopleCount);
		System.out.println("min location count is : " + minRecordLocationCount + ", avg local count is :" + avgRecordLocationCount);

		
		
		for(Object[] value : values){
			
			//value[0] = ((Double)value[0] - minRecordPeopleCount)/(maxRecordPeopleCount - minRecordPeopleCount);
			value[0] = ((Double)value[0] - minRecordLocationCount)/(maxRecordLocationCount - minRecordLocationCount);
		}
		
		
	}
	
	public static void main(String[] args) {
		//JFrame frame = new JFrame("people分布图(>40)");
		JFrame frame = new JFrame("location分布图(>5)");
		DrawPoints dp = new DrawPoints();
		dp.calculatePoint();
		frame.getContentPane().add(dp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 800);
		frame.setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int count1 = 0,count2 = 0,count3 = 0,count4 = 0,count5 = 0,count6 = 0,count7 = 0,count8 = 0;
		for(Object[] point : values){
			Double x = (Double) point[0]*1000;
			Integer y = (Integer) point[1];
			//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			if(y > 1){
//				count1++;
//				//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
//			if(y > 2){
//				count2++;
//				//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
//			if(y > 3){
//				count3++;
//				//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
//			if(y > 5){
//				count4++;
//				//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
//			if(y > 10){
//				count5++;
//				//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
//			if(y > 20){
//				count6++;
//			}
//			if(y > 30){
//				count7++;
//				//g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
//			if(y > 40){
//				count8++;
//				g.drawLine(x.intValue(), 0, x.intValue(), y);
//			}
			if(y > 1){
				count1++;
				//g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
			if(y > 2){
				count2++;
				//g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
			if(y > 3){
				count3++;
				//g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
			if(y > 4){
				count4++;
				//g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
			if(y > 5){
				count5++;
				//g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
			if(y > 6){
				count6++;
			}
			if(y > 7){
				count7++;
				//g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
			if(y > 8){
				count8++;
				g.drawLine(x.intValue(), 0, x.intValue(), y);
			}
		}
		System.out.println("reduce point size (>1):" + count1);
		System.out.println("reduce point size (>2):" + count2);
		System.out.println("reduce point size (>3):" + count3);
		System.out.println("reduce point size (>4):" + count4);
		System.out.println("reduce point size (>5):" + count5);
		System.out.println("reduce point size (>6):" + count6);
		System.out.println("reduce point size (>7):" + count7);
		System.out.println("reduce point size (>8):" + count8);
		System.out.println("point size :" + values.size());
	}
}