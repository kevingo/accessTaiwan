package accesstaiwan.dao.transportation.hsr.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import accesstaiwan.dao.transportation.hsr.PriceDao;
import accesstaiwan.model.transportation.hsr.Price;

import com.mongodb.WriteResult;

@Service("PriceDAOImpl")
@Transactional
public class PriceDAOImpl implements PriceDao {

	protected static Logger logger = Logger.getLogger("PriceDAOImpl");
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public List<Price> list() {
		// TODO Auto-generated method stub
		logger.debug("list prices");
		
		List<Price> prices = mongoTemplate.getCollection(Price.class);
		
		return prices;
	}

	public Price get(Object _id) {
		// TODO Auto-generated method stub
		logger.debug("Retrieving price by id");
		
		Query query = new Query(Criteria.where("business").is(new Integer(_id.toString()).intValue()));
		Price price = mongoTemplate.findOne(query, Price.class);
		
		return price;
	}

	public void update(Object _id, Price obj) {
		// TODO Auto-generated method stub
		logger.debug("update price by id");
		
		Query query = new Query(Criteria.where("business").is(new Integer(_id.toString()).intValue()));
		Update update = new Update();
		update.push("businessGroup", 201);
		update.push("businessConcessionary", 102);
		WriteResult result = mongoTemplate.updateFirst(query, update);
	}

	public void create(Price obj) {
		// TODO Auto-generated method stub
		logger.debug("Create a price");
		
		mongoTemplate.insert(obj);
	}

	public void delete(Object _id) {
		// TODO Auto-generated method stub
		logger.debug("deleting price by id");
		
		Query query = new Query(Criteria.where("businessGroup").is(new Integer(_id.toString()).intValue()));
		mongoTemplate.remove(query);
	}

	public Price get(String stop1, String stop2) {
		// TODO Auto-generated method stub
		logger.debug("Retrieving price by stop");
		
		//Query query = new Query(Criteria.where("business").is(new Integer(_id.toString()).intValue()));
		//Price price = mongoTemplate.findOne(query, Price.class);
		
		return null;
	}

}
