package accesstaiwan.dao.transportation.metroKao.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import accesstaiwan.dao.transportation.metroKao.StopDao;
import accesstaiwan.model.transportation.metroKao.Stop;

@Service("MetroKaoStopDaoImpl")
@Transactional
public class MetroKaoStopDaoImpl implements StopDao {

	protected static Logger logger = Logger.getLogger("MetroKaoStopDaoImpl");
	private String collectionName = "metroKao.stops";
	
	private String [] line1 = 
		{"Ciaotou Station", "Ciaotou Sugar Refinery", "Cingpu", "Metropolitan Park", "Houjing", 
		"Zanzih Export Processing Zone", "Oil Refinery Elementary School", "World Games", "Zuoying", 
		"Ecological District", "Kaohsiung Arena", "Aozihdi", "Houyi", "Kaohsiung Main Station", 
		"Formosa Boulevard", "Central Park", "Sanduo Shopping District", "Shihjia", "Kaisyuan", "Cianjhen Senior High School", 
		"Caoya", "Kaohsiung International Airport", "Siaogang"};
	
	private String [] line2 = {"Sizihwan", "Yanchengpu", "City Council", "Formosa Boulevard", "Sinyi Elementary School", "Cultural Center", "Wukuaicuo", 
			"Martial Arts Stadium", "Weiwuying", "Fongshan West", "Fongshan", "Dadong", "Fongshan Junior High School", "Daliao"};
	
	private String [] intersection = {"Formosa Boulevard"};
	
	Set<String> line1Set;
	Set<String> line2Set;
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public List<Stop> list() {
		logger.debug("Retrieving all stops");				
		return mongoTemplate.getCollection(collectionName, Stop.class);
	}
	
	public List<Stop> get(String stopName) {
		return mongoTemplate.find(collectionName, new Query(Criteria.where("name").is(stopName)), Stop.class);		
	}
	
	public List<Stop> get(String from, String to) {
		//return mongoTemplate.find(collectionName, new Query(Criteria.where("name").is(stopName)), Stop.class);
		
		List<Stop> stops = new LinkedList<Stop>();
		List<String> stopName = getStopsName(from, to);
		
		Iterator ite = stopName.iterator();
		while(ite.hasNext()) {
			String name = String.valueOf(ite.next());
			System.out.println(name);
			//mongoTemplate.findOne("metroKao.stops", new Query(Criteria.where("name").is(name)), Stop.class);
			stops.add(mongoTemplate.findOne("metroKao.stops", new Query(Criteria.where("name").is(name)), Stop.class));
		}
		
		return stops;
	}

	

	public Stop get(Object _id) {
		
		return null;
	}

	public void update(Object _id, Stop obj) {
				
	}

	public void create(Stop obj) {
				
	}

	public void delete(Object _id) {
				
	}
	
	private List<String> getStopsName(String from, String to) {
		
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
		
		List<String> list = new LinkedList<String>();
		for(int i=0 ; i<allStops.split("\n").length ; i++)
			list.add(allStops.split("\n")[i]);
		
		return list;
	}

}
