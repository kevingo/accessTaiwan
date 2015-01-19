package accesstaiwan.dao.travel;

import accesstaiwan.model.travel.SML.Entity;

public interface SMLEntityDao {

	public Entity get(Object _id);
	public void update(Object _id, Entity obj);
	public void create(Entity obj);
	public void delete(Object _id);
	
}
