package accesstaiwan.dao.travel.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import accesstaiwan.dao.travel.SceneryDao;
import accesstaiwan.model.travel.itravel.Scenery;

@Service("SceneryDaoImpl")
@Transactional
public class SceneryDaoImpl implements SceneryDao {

	protected static Logger logger = Logger.getLogger("SceneryDaoImpl");
	
	@SuppressWarnings("unused")
	public static String collectionName = "itravel.scenery";
	
	@SuppressWarnings({ "restriction", "unused" })
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public List<Scenery> get() {
		return mongoTemplate.getCollection(collectionName, Scenery.class);
	}
	
	public Scenery get(Object _id) {		
		return null;
	}

	public void update(Object _id, Scenery obj) {
		
	}

	public void create(Scenery obj) {		
		
	}

	public void delete(Object _id) {		
		
	}

}
