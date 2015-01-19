package accesstaiwan.dao.transportation.hsr.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.data.document.mongodb.query.Criteria.where;
import accesstaiwan.dao.transportation.hsr.StopDao;
import accesstaiwan.model.transportation.hsr.Stop;


@Service("StopDAOImpl")
@Transactional
public class StopDAOImpl implements StopDao {
	
	protected static Logger logger = Logger.getLogger("StopDAOImpl");
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public List<Stop> list() {
		logger.debug("Retrieving all stops");				
		return mongoTemplate.getCollection("hsr.stops", Stop.class);
	}
	
	public Stop get(Object _id) {
		return null;

	}
	
	public List<Stop> get(String stopName) {
		return mongoTemplate.find("hsr.stops", new Query(Criteria.where("name").is(stopName)), Stop.class);		
	}
	
	public List<Stop> update(String stopName, Stop newStop) {
		Stop stop = mongoTemplate.findOne("hsr.stops", new Query(Criteria.where("name").is(stopName)), Stop.class);
		stop.setName(newStop.getName());
		stop.setLatitude(newStop.getLatitude());
		stop.setLongitude(newStop.getLongitude());
		return mongoTemplate.find("hsr.stops", new Query(Criteria.where("name").is(stopName)), Stop.class);
	}
	
	public void update(Object _id, Stop obj) {
		// TODO Auto-generated method stub
		
	}
	
	public void create(Stop stop) {
		logger.debug("Create a stop");
		mongoTemplate.insert(stop);
	}
	
	public void delete(Object _id) {
		// TODO Auto-generated method stub
		
	}
	
	public void dropCollection() {
		mongoTemplate.dropCollection("hsr.stops");
	}
}
