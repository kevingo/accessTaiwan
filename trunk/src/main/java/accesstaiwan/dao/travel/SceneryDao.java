package accesstaiwan.dao.travel;

import accesstaiwan.model.travel.itravel.Scenery;

public interface SceneryDao {

	public Scenery get(Object _id);
	public void update(Object _id, Scenery obj);
	public void create(Scenery obj);
	public void delete(Object _id);
	
}
