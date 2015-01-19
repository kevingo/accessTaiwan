package accesstaiwan.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.geo.Point;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;


import accesstaiwan.model.transportation.hsr.StopTime;
import accesstaiwan.model.transportation.hsr.Train;
import accesstaiwan.model.transportation.metroKao.Stop;
import accesstaiwan.model.travel.SML.Entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Test {

	@Resource(name="mongoTemplate")
	private static MongoTemplate mongoTemplate;
	private static String [] Slocation = {"Taipei","Banciao","Taoyuan","Hsinchu","Taichung","Chiayi","Tainan","Zuoying"};	
	private static String [] Nlocation = {"Taipei","Banciao","Taoyuan","Hsinchu","Taichung","Chiayi","Tainan","Zuoying"};
	
	
	private static String [] line1 = 
		{"Ciaotou Station", "Ciaotou Sugar Refinery", "Cingpu", "Metropolitan Park", "Houjing", 
		"Zanzih Export Processing Zone", "Oil Refinery Elementary School", "World Games", "Zuoying", 
		"Ecological District", "Kaohsiung Arena", "Aozihdi", "Houyi", "Kaohsiung Main Station", 
		"Formosa Boulevard", "Central Park", "Sanduo Shopping District", "Shihjia", "Kaisyuan", "Cianjhen Senior High School", 
		"Caoya", "Kaohsiung International Airport", "Siaogang"};
	
	private static String [] line2 = {"Sizihwan", "Yanchengpu", "City Council", "Formosa Boulevard", "Sinyi Elementary School", "Cultural Center", "Wukuaicuo", 
			"Martial Arts Stadium", "Weiwuying", "Fongshan West", "Fongshan", "Dadong", "Fongshan Junior High School", "Daliao"};
	
	private static String [] intersection = {"Formosa Boulevard"};
	
	private static Set<String> line1Set;
	private static Set<String> line2Set;
	
	private static String from = "Yanchengpu", to = "Houjing";
	
	public static void main(String [] args) {

		try {
			mongoTemplate = new MongoTemplate(new Mongo(new DBAddress( "127.0.0.1:27017", "test" )), "test");
			Point point = new Point(25.03598d,121.56894d);
			System.out.println(mongoTemplate.find("SML.data", new Query(Criteria.where("location").near(point).maxDistance(0.01)), Entity.class));
			
			
			//mongoTemplate.dropCollection("hsr.trains");
			/*
			mongoTemplate.dropCollection("hsr.trains.tmp");
			List<Train> list = mongoTemplate.find("hsr.trains", new Query(Criteria.where("stopTimes.stop").is("Taipei")), Train.class);
			Iterator ite = list.iterator();
			while(ite.hasNext()) {
				Train train = new Train();
				train = (Train)ite.next();
				mongoTemplate.insert("hsr.trains.tmp", train);
			}
			System.out.println(list.size());
			//mongoTemplate.insert("hsr.trains.tmp", list);
			List<Train> list2 = mongoTemplate.find("hsr.trains.tmp", new Query(Criteria.where("stopTimes.stop").is("Hsinchu")), Train.class);

			
			System.out.println(list2.size());
			*/
			
			//System.out.println(mongoTemplate.find("hsr.trains",	new Query(Criteria.where("stopTimes.stop").is("Tainan")), Train.class));
			/*
			
			line1Set = new LinkedHashSet<String>();
			line2Set = new LinkedHashSet<String>();
			Vector v1 = new Vector();
			Vector v2 = new Vector();
			for(int i=0 ; i<line1.length ; i++) {
				line1Set.add(line1[i]);
				v1.add(line1[i]);
			}
			
			for(int i=0 ; i<line2.length ; i++) {
				line2Set.add(line2[i]);
				v2.add(line2[i]);
			}			
			
			String allStops = "";
			if(v1.contains(from) && v1.contains(to)) {
				Integer indexFrom = v1.indexOf(from);
				Integer indexTo = v1.indexOf(to);
				int result = indexFrom.compareTo(indexTo);
				if(result>0) { // from>to
					for(int i=indexFrom ; i>=indexTo ; i--) {
						allStops += line1[i] + "\n";
					}			
				} else { // from<to
					for(int i=indexFrom ; i<=indexTo ; i++) 
						allStops += line1[i] + "\n";
				}
				
			} else if(v2.contains(from) && v2.contains(to)) {
				Integer indexFrom = v2.indexOf(from);
				Integer indexTo = v2.indexOf(to);
				int result = indexFrom.compareTo(indexTo);
				if(result>0) { // from>to
					for(int i=indexFrom ; i>=indexTo ; i--) {
						allStops += line2[i] + "\n";
					}			
				} else { // from<to
					for(int i=indexFrom ; i<=indexTo ; i++) 
						allStops += line2[i] + "\n";
				}
			} else {
				Integer indexIntersec; 
				
				if(v1.contains(from)) { // start from line1
					Integer indexFrom = v1.indexOf(from);
					Integer indexTo = v2.indexOf(to);
					indexIntersec = v1.indexOf(intersection[0]);
					int result = indexFrom.compareTo(indexIntersec);

					if(result>0) { // from>intersec
						for(int i=indexFrom ; i>=indexIntersec ; i--) {
							allStops += line1[i] + "\n";
						}			
					} else { // from<intersec
						for(int i=indexFrom ; i<=indexIntersec ; i++) 
							allStops += line1[i] + "\n";
					}
					
					indexIntersec = v2.indexOf(intersection[0]);
					result = indexTo.compareTo(indexIntersec);
					if(result>0) { // to>intersec						
						for(int i=indexIntersec+1 ; i<=indexTo ; i++)
							allStops += line2[i] + "\n";		
					} else { // to<intersec
						for(int i=indexIntersec-1 ; i>=indexTo ; i--) 
							allStops += line2[i] + "\n";
					}
					
				} else { // start from line2
					 Integer indexFrom = v2.indexOf(from);
					 Integer indexTo = v1.indexOf(to);
					 indexIntersec = v2.indexOf(intersection[0]);
					 int result = indexFrom.compareTo(indexIntersec);
					 
					 if(result>0) { // from>intersec
						 for(int i=indexFrom ; i>=indexIntersec ; i--)
							 allStops += line2[i] + "\n";
					 } else { // from<intersec
						 for(int i=indexFrom ; i<=indexIntersec ; i++)
							 allStops += line2[i] + "\n";
					 }
					 
					 indexIntersec = v1.indexOf(intersection[0]);
					 result = indexTo.compareTo(indexIntersec);
					 if(result>0) { // to>intersec
						 for(int i=indexIntersec+1 ; i<=indexTo ; i++)
							 allStops += line1[i] + "\n";
					 } else { // to<intersec
						 for(int i=indexIntersec-1 ; i>=indexTo ; i--)
							 allStops += line1[i] + "\n";
					 }
				}
			}
			//System.out.println(allStops); 
						
			List<String> list = new LinkedList<String>();
			for(int i=0 ; i<allStops.split("\n").length ; i++)
				list.add(allStops.split("\n")[i]);
			System.out.println(list.size());
			
			List<Stop> stops = new LinkedList<Stop>();
			
			Iterator ite = list.iterator();
			while(ite.hasNext()) {
				String name = String.valueOf(ite.next());
				System.out.println(name);
				//mongoTemplate.findOne("metroKao.stops", new Query(Criteria.where("name").is(name)), Stop.class);
				stops.add(mongoTemplate.findOne("metroKao.stops", new Query(Criteria.where("name").is(name)), Stop.class));
				//System.out.println(mongoTemplate.findOne("metroKao.stops", new Query(Criteria.where("name").is(name)), Stop.class));
			}
			
			System.out.println(stops);
			*/
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
