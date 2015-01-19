package accesstaiwan.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;

import accesstaiwan.dao.transportation.hsr.impl.StopDAOImpl;
import accesstaiwan.model.transportation.hsr.Stop;
import accesstaiwan.model.transportation.hsr.StopTime;
import accesstaiwan.model.transportation.hsr.Train;

import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class HSRDataProcessing {

	@Resource(name="mongoTemplate")
	private static MongoTemplate mongoTemplate;
	private static String [] Slocation = {"Taipei","Banciao","Taoyuan","Hsinchu","Taichung","Chiayi","Tainan","Zuoying"};	
	private static String [] Nlocation = {"Taipei","Banciao","Taoyuan","Hsinchu","Taichung","Chiayi","Tainan","Zuoying"};
	
	@Resource(name="StopDAOImpl")
	private StopDAOImpl dao;
	
	public static void main(String [] args) {
	
		try {
			mongoTemplate = new MongoTemplate(new Mongo(new DBAddress( args[0] + ":27017", "test" )), "test");			
			
			
			//mongoTemplate.dropCollection("hsr.trains");
			//insertHSRTrainData(args[1]);
			//insertHSRTrainWeekeneData(args[2]);
			
			mongoTemplate.dropCollection("hsr.stops");
			insertHSRStopData();
		 	
			
		} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (MongoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
		
	}
	
	private static void insertHSRTrainWeekeneData(String filePath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					new File(filePath)));
			
			String str = "";
			StopTime [] st;
			ArrayList<StopTime> stopTime;
			String [] onlyOnWeekDay = new String[2];
			
			while((str=br.readLine())!=null) {
				
				String [] trainArr = str.split("\t");
				stopTime = new ArrayList<StopTime>();
				String trainId = trainArr[0];
				
				int numOfTime = 0;
				 Map<String, Integer> map = new HashMap<String, Integer>();
				for(int i=0 ; i<Slocation.length ; i++) {
					if(!trainArr[i+2].equals("-")) {
						numOfTime++;
						map.put(trainArr[i+2], i);
					}
				}
				
				st = new StopTime[numOfTime];
				
				for(int i=0 ; i<Slocation.length ; i++) {
					int innerCount = 0;
					if(!trainArr[i+2].equals("-")) {
						st[innerCount] = new StopTime();
						st[innerCount].setStop(Slocation[map.get(trainArr[i+2])]);
						st[innerCount].setTime(trainArr[i+2]);
						stopTime.add(innerCount, st[innerCount]);
						innerCount++;
					}
				}
				
				if(trainArr[trainArr.length-1].equals("67")) {
					onlyOnWeekDay[0] = "6";
					onlyOnWeekDay[1] = "7";
				} else if(trainArr[trainArr.length-1].equals("6")) {
					onlyOnWeekDay[0] = "6";
					onlyOnWeekDay[1] = "-";
				} else {
					onlyOnWeekDay[0] = "-";
					onlyOnWeekDay[1] = "7";
				}
				
				Train train;
				train = new Train();
				train.setTrainId(trainId);
				train.setStopTimes(stopTime);
				train.setOnlyOnDayOfWeek(onlyOnWeekDay);
				mongoTemplate.insert("hsr.trains", train);				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertHSRTrainData(String filePath) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					new File(filePath)));
			
			String str = "";
			ArrayList<StopTime> stopTime;
			StopTime [] st;
			String [] onlyOnWeekDay = new String[2];
			int lineCount = 0;
			
			while((str=br.readLine())!=null) {
				if(lineCount!=0) {
					String [] trainArr = str.split(",");
					stopTime = new ArrayList<StopTime>();
					String trainId = trainArr[0];
					
					int numOfTime = 0;
					Map<String, Integer> map = new HashMap<String, Integer>();
					for(int i=0 ; i<Slocation.length ; i++) {
						if(!trainArr[i+2].equals("-")) {
							numOfTime++;
							map.put(trainArr[i+2], i);
						}
					}
					
					st = new StopTime[numOfTime];
					
					for(int i=0 ; i<Slocation.length ; i++) {
						int innerCount = 0;
						if(!trainArr[i+2].equals("-")) {
							st[innerCount] = new StopTime();
							st[innerCount].setStop(Slocation[map.get(trainArr[i+2])]);
							st[innerCount].setTime(trainArr[i+2]);
							stopTime.add(innerCount, st[innerCount]);
							innerCount++;
						}
					}
					
					onlyOnWeekDay[0] = "-";
					onlyOnWeekDay[1] = "-";
					
					Train train;
					train = new Train();
					train.setTrainId(trainId);
					train.setStopTimes(stopTime);
					train.setOnlyOnDayOfWeek(onlyOnWeekDay);
					mongoTemplate.insert("hsr.trains", train);				
				}
				lineCount++;
			}
				
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	public static void insertHSRStopData() {
		mongoTemplate.dropCollection("hsr.stops");
		Stop [] stop = new Stop [8];
		
		for(int i=0 ; i<stop.length ; i++)
			stop[i] = new Stop();
		
		stop[0].setName("Taipei");
		stop[0].setLatitude(25.04799f);
		stop[0].setLongitude(121.51643f);
		mongoTemplate.insert("hsr.stops", stop[0]);
		
		stop[1].setName("Banciao");
		stop[1].setLatitude(25.01453f);
		stop[1].setLongitude(121.46340f);
		mongoTemplate.insert("hsr.stops", stop[1]);
		
		stop[2].setName("Taoyuan");
		stop[2].setLatitude(25.01289f);
		stop[2].setLongitude(121.21515f);
		mongoTemplate.insert("hsr.stops", stop[2]);
		
		stop[3].setName("Hsinchu");
		stop[3].setLatitude(24.80768f);
		stop[3].setLongitude(121.04041f);
		mongoTemplate.insert("hsr.stops", stop[3]);
		
		stop[4].setName("Taichung");
		stop[4].setLatitude(24.80768f);
		stop[4].setLongitude(121.04041f);
		mongoTemplate.insert("hsr.stops", stop[4]);
		
		stop[5].setName("Chiayi");
		stop[5].setLatitude(23.45933f);
		stop[5].setLongitude(120.32341f);
		mongoTemplate.insert("hsr.stops", stop[5]);
		
		stop[6].setName("Tainan");
		stop[6].setLatitude(22.92496f);
		stop[6].setLongitude(120.28575f);
		mongoTemplate.insert("hsr.stops", stop[6]);
		
		stop[7].setName("Zuoying");
		stop[7].setLatitude(22.68690f);
		stop[7].setLongitude(120.30780f);
		mongoTemplate.insert("hsr.stops", stop[7]);
	}
}
