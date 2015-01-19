package accesstaiwan.dao.transportation.hsr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import accesstaiwan.dao.transportation.hsr.TrainDao;
import accesstaiwan.model.transportation.hsr.Stop;
import accesstaiwan.model.transportation.hsr.StopTime;
import accesstaiwan.model.transportation.hsr.Train;

@Service("TrainDAOImpl")
@Transactional
public class TrainDAOImpl implements TrainDao {
	
	protected static Logger logger = Logger.getLogger("TrainDAOImpl");
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;  

	public List<Train> list() {
		return mongoTemplate.getCollection("hsr.trains", Train.class);		
	}

	public Train get(Object _id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Object _id, Train train) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Train> get(String trainID) {
		return mongoTemplate.find("hsr.trains", new Query(Criteria.where("trainId").is(trainID)), Train.class);
	}
	
	
	/*
	 * 
	 * Get trainID by from and to stop
	 * */
	public List<Train> getTrainByStop(String from, String to) {
		mongoTemplate.dropCollection("hsr.trains.tmp");
		List<Train> list = mongoTemplate.find("hsr.trains", new Query(Criteria.where("stopTimes.stop").is(from)), Train.class);
		Iterator<Train> ite = list.iterator();
		while(ite.hasNext()) {
			Train train = new Train();
			train = (Train)ite.next();
			mongoTemplate.insert("hsr.trains.tmp", train);
		}
		
		return mongoTemplate.find("hsr.trains.tmp", 
				new Query(Criteria.where("stopTimes.stop").is(to)), Train.class);
	}
	
	public List<Train> getTrainByStop(String to) {
		return mongoTemplate.find("hsr.trains", 
				new Query(Criteria.where("stopTimes.stop").is(to)), Train.class);
	}
	
	
	public void create(Train train) {

	}

	public void delete(Object _id) {
		// TODO Auto-generated method stub
		
	}

	public List<Train> listTrainsFromStop(String from) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Train> listTrainsToStop(String to) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Train> listTrains(String from, String to) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Train> listTrainsStartAround(String from, String to,
			String around) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Train> listTrainsArriveAround(String from, String to,
			String around) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Train> listTrainsStartAround(String from, String to,
			String around, String dayOfWeek) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Train> listTrainsArrivefAround(String from, String to,
			String around, String dayOfWeek) {
		// TODO Auto-generated method stub
		return null;
	}

}
