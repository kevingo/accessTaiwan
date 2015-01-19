package accesstaiwan.dao.travel.impl;

import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.geo.Point;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.DBCursor;

import accesstaiwan.dao.travel.SMLEntityDao;
import accesstaiwan.model.transportation.tra.Stop;
import accesstaiwan.model.travel.SML.Entity;

@Service("SMLEntityDaoImpl")
@Transactional
public class SMLEntityDaoImpl implements SMLEntityDao {

	@SuppressWarnings("unused")
	public static String collectionName = "SML.data";
	
	@SuppressWarnings({ "restriction", "unused" })
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public List<Entity> get() {
		return mongoTemplate.getCollection(collectionName, Entity.class);
	}
	
	public List<Entity> getByType(String type, String limit) {
		int num = Integer.parseInt(limit);
		return mongoTemplate.find(collectionName, new Query(Criteria.where("type.typeName").is(type)).limit(num), Entity.class);
	}
	
	public List<Entity> getByLocation(String lat, String lng) {
		Float latitude = Float.valueOf(lat);
		Float longitude = Float.valueOf(lng);
		Point point = new Point(latitude, longitude);
		List<Entity> list = mongoTemplate.find(collectionName, new Query(Criteria.where("location").near(point)), Entity.class);
		return list;
	}
	
	public List<Entity> getByLocationWithLimit(String lat, String lng, String limit) {
		int num = Integer.parseInt(limit);
		double latitude = Double.valueOf(lat);
		double longitude = Double.valueOf(lng);
		Point point = new Point(latitude, longitude);
		List<Entity> list = mongoTemplate.find(collectionName, new Query(Criteria.where("location").near(point)).limit(num), Entity.class);
		return list;
	}
	
	public Entity get(Object _id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Object _id, Entity obj) {
		// TODO Auto-generated method stub
		
	}

	public void create(Entity obj) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Object _id) {
		// TODO Auto-generated method stub
		
	}
}
