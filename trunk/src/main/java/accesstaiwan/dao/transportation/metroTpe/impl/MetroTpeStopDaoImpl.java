package accesstaiwan.dao.transportation.metroTpe.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import accesstaiwan.dao.transportation.metroTpe.StopDao;
import accesstaiwan.model.transportation.metroTpe.Stop;

@Service("MetroTpeStopDaoImpl")
@Transactional
public class MetroTpeStopDaoImpl implements StopDao {
	
	protected static Logger logger = Logger.getLogger("MetroTpeStopDaoImpl");
	private String collectionName = "metroTpe.stops";
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;

	private static String [] allLines = {"TAMSHI_XINDIAN_LINE", "NANGANG_ZOO_LINE", "BEITOU_NANSHIJIAO_LINE", "NANGANG_YONGNING_LINE",
		"XIMEN_MEMORIALHALL_LINE", "BEITOU_XINBEITOU_LINE", "QIZHANG_XIAOBITAN", "LUZHOU_ZHONGXIAOXINSHENG_LINE", "FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE"};
	
	private static String [] TAMSHI_XINDIAN_LINE = {"Danshui", "Hongshulin", "Zhuwei", "Guandu", "Zhongyi", "Fuzinggang", "Beitou", "Qiyan", 
		"Qilian", "Shipai", "Mingde", "Zhishan", "Shilin", "Jiantan","Yuanshan", "Minquan W. Rd.", "Shuanglian", "Zhongshan", "Taipei Main Station", 
		"National Taiwan University Hospital", "Chian Kai-Shek Memorial Hall", "Guting", "Taipower Building", "Gongguan", "Wanlong", "Jingmei", "Dapinglin", 
		"Qizhang", "Xiaobitan", "Xindian District Office", "Xindian"};
	private static String [] NANGANG_ZOO_LINE = {"Taipei Nangang Exhibition", "Nangang Software park", "Donghu", "Huzhou", "Dahu Park", "Neihu", "Wende", "Gangqian", 
		"Xihu", "Jiannang Rd.", "Dazhi", "Songshan Airport", "Zhongshan Junior High School", "Nanjing E. Rd.", "Zhongxiao Fuxing", "Daan", "Technology Building", 
		"Liuzhanli", "Linguang", "Xinhai", "Wanfang Hospital", "Wanfang Community", "Muzha", "Taipei Zoo"};
	private static String [] BEITOU_NANSHIJIAO_LINE = {"Beitou", "Qiyan", 
		"Qilian", "Shipai", "Mingde", "Zhishan", "Shilin", "Jiantan","Yuanshan", "Minquan W. Rd.", "Shuanglian", "Zhongshan", "Taipei Main Station", 
		"National Taiwan University Hospital", "Chian Kai-Shek Memorial Hall", "Guting", "Dingxi", "Yongan Market", "Jingan", "Nanshijiao"};	
	private static String [] NANGANG_YONGNING_LINE = {"Taipei Nangang Exhibition", "Nangang", "Kunyang", "Houshanpi", "Yongchun", "Taipei City Hall", "Sun Yat-Sen Memorial Hall", 
		"Zhongxiao Dunhua", "Zhongxiao Fuxing", "Zhongxiao Xinshen", "Shandao Temple", "Taipei Main Station", "Ximen", "Longshan Temple", "Jiangzicui", "Xinpu", "Banqiao", "Fuzhong", 
		"Far Eastern Hospital", "Haishan", "Tuchen", "Yongning"};	
	private static String [] XIMEN_MEMORIALHALL_LINE = {"Ximen", "Xiaonanmen", "Chian Kai-Shek Memorial Hall"};	
	private static String [] BEITOU_XINBEITOU_LINE = {"Beitou", "Xinbeitou"};	
	private static String [] QIZHANG_XIAOBITAN = {"Qizhang", "Xiaobitan"};	
	private static String [] LUZHOU_ZHONGXIAOXINSHENG_LINE = {"Luzhou", "Sanmin Senior High School", "St. Ignatius High School", "Sanhe Junior High School", "Sanchong Elementary School", 
		"Daqiaotou", "Minquan W. Rd.", "Zhongshan Elementary", "Xingtian Temple", "Songjian Nanjing", "Zhongxiao Xinshen"};	
	private static String [] FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE = {"Fu Jen University", "Xinzhuang", "Touqianzhuang", "Xianse Temple", "Sanchong", "Taipei Bridge", "Daqiaotou", 
		"Minquan W. Rd.", "Zhongshan Elementary", "Xingtian Temple", "Songjian Nanjing", "Zhongxiao Xinshen"};
	
	private static String [] interSec = {"Beitou","Daqiaotou","Minquan W. Rd.","Ximen","Taipei Main Station","Zhongxiao Xinshen","Zhongxiao Fuxing", 
		"Chian Kai-Shek Memorial Hall", "Guting", "Qizhang"};
	
	HashMap<String, String[]> map = new HashMap<String, String[]>();
	
	public List<Stop> list() {
		logger.debug("Retrieving all stops");				
		return mongoTemplate.getCollection(collectionName, Stop.class);
	}
	
	public List<Stop> get(String stopName) {
		return mongoTemplate.find(collectionName, new Query(Criteria.where("name").is(stopName)), Stop.class);		
	}
	
	public List<Stop> get(String from, String to) {
		
		List<Stop> stops = new LinkedList<Stop>();
		List<String> stopName = getStopsName(from, to);
		
		Iterator<String> ite = stopName.iterator();
		while(ite.hasNext()) {
			String name = String.valueOf(ite.next());
			System.out.println(name);
			stops.add(mongoTemplate.findOne("metroTpe.stops", new Query(Criteria.where("name").is(name)), Stop.class));
		}
		
		return stops;
	}
	
	@SuppressWarnings("unused")
	private void insertMap() {
		map.put(allLines[0], TAMSHI_XINDIAN_LINE);
		map.put(allLines[1], NANGANG_ZOO_LINE);
		map.put(allLines[2], BEITOU_NANSHIJIAO_LINE);
		map.put(allLines[3], NANGANG_YONGNING_LINE);
		map.put(allLines[4], XIMEN_MEMORIALHALL_LINE);
		map.put(allLines[5], BEITOU_XINBEITOU_LINE);
		map.put(allLines[6], QIZHANG_XIAOBITAN);
		map.put(allLines[7], LUZHOU_ZHONGXIAOXINSHENG_LINE);
		map.put(allLines[8], FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE);
	}
	
	private List<String> getStopsName(String from, String to) {
		String allStops = "";
		
		Vector<String> v1 = new Vector<String>();
		Vector<String> v2 = new Vector<String>();
		Vector<String> v3 = new Vector<String>();
		Vector<String> v4 = new Vector<String>();
		Vector<String> v5 = new Vector<String>();
		Vector<String> v6 = new Vector<String>();
		Vector<String> v7 = new Vector<String>();
		Vector<String> v8 = new Vector<String>();
		Vector<String> v9 = new Vector<String>();
		
		for(int i=0 ; i<TAMSHI_XINDIAN_LINE.length ; i++)  // 1
			v1.add(TAMSHI_XINDIAN_LINE[i]);
		
		for(int i=0 ; i<NANGANG_ZOO_LINE.length ; i++) // 2
			v2.add(NANGANG_ZOO_LINE[i]);
		
		for(int i=0 ; i<BEITOU_NANSHIJIAO_LINE.length ; i++) // 3
			v3.add(BEITOU_NANSHIJIAO_LINE[i]);
		
		for(int i=0 ; i<NANGANG_YONGNING_LINE.length ; i++) // 4
			v4.add(NANGANG_YONGNING_LINE[i]);
		
		for(int i=0 ; i<XIMEN_MEMORIALHALL_LINE.length ; i++) // 5
			v5.add(XIMEN_MEMORIALHALL_LINE[i]);
		
		for(int i=0 ; i<BEITOU_XINBEITOU_LINE.length ; i++) // 6
			v6.add(BEITOU_XINBEITOU_LINE[i]);
		
		for(int i=0 ; i<QIZHANG_XIAOBITAN.length ; i++) // 7
			v7.add(QIZHANG_XIAOBITAN[i]);
		
		for(int i=0 ; i<LUZHOU_ZHONGXIAOXINSHENG_LINE.length ; i++) // 8
			v8.add(LUZHOU_ZHONGXIAOXINSHENG_LINE[i]);
		
		for(int i=0 ; i<FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE.length ; i++) // 9
			v9.add(FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE[i]);
		
		if(v1.contains(from) && v1.contains(to)) {			
			Integer indexFrom = v1.indexOf(from);
			Integer indexTo = v1.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += TAMSHI_XINDIAN_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += TAMSHI_XINDIAN_LINE[i] + "\n";
			}
			
		} else if(v2.contains(from) && v2.contains(to)) {			
			Integer indexFrom = v2.indexOf(from);
			Integer indexTo = v2.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += NANGANG_ZOO_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += NANGANG_ZOO_LINE[i] + "\n";
			}
			
		} else if(v3.contains(from) && v3.contains(to)) {			
			Integer indexFrom = v3.indexOf(from);
			Integer indexTo = v3.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += BEITOU_NANSHIJIAO_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += BEITOU_NANSHIJIAO_LINE[i] + "\n";
			}
			
		} else if(v4.contains(from) && v4.contains(to)) {			
			Integer indexFrom = v4.indexOf(from);
			Integer indexTo = v4.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += NANGANG_YONGNING_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += NANGANG_YONGNING_LINE[i] + "\n";
			}
			
		} else if(v5.contains(from) && v5.contains(to)) {			
			Integer indexFrom = v5.indexOf(from);
			Integer indexTo = v5.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += XIMEN_MEMORIALHALL_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += XIMEN_MEMORIALHALL_LINE[i] + "\n";
			}
			
		} else if(v6.contains(from) && v6.contains(to)) {			
			Integer indexFrom = v6.indexOf(from);
			Integer indexTo = v6.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += BEITOU_XINBEITOU_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += BEITOU_XINBEITOU_LINE[i] + "\n";
			}
			
		} else if(v7.contains(from) && v7.contains(to)) {			
			Integer indexFrom = v7.indexOf(from);
			Integer indexTo = v7.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += QIZHANG_XIAOBITAN[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += QIZHANG_XIAOBITAN[i] + "\n";
			}
			
		} else if(v8.contains(from) && v8.contains(to)) {			
			Integer indexFrom = v8.indexOf(from);
			Integer indexTo = v8.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += LUZHOU_ZHONGXIAOXINSHENG_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += LUZHOU_ZHONGXIAOXINSHENG_LINE[i] + "\n";
			}
		} else if(v9.contains(from) && v9.contains(to)) {			
			Integer indexFrom = v9.indexOf(from);
			Integer indexTo = v9.indexOf(to);
			int result = indexFrom.compareTo(indexTo);
			if(result>0) { // from>to
				for(int i=indexFrom ; i>=indexTo ; i--) {
					allStops += FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE[i] + "\n";
				}			
			} else { // from<to
				for(int i=indexFrom ; i<=indexTo ; i++) 
					allStops += FUJENUNIVERSITY_ZHONGXIAOXINSHENG_LINE[i] + "\n";
			}
		} else {
			// diff lines logics ... 
			
		}
				
		List<String> list = new LinkedList<String>();
		for(int i=0 ; i<allStops.split("\n").length ; i++)
			list.add(allStops.split("\n")[i]);
		
		return list;
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

}
