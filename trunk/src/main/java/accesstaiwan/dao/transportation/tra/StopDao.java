package accesstaiwan.dao.transportation.tra;

import java.util.List;

import accesstaiwan.model.transportation.tra.Stop;

public interface StopDao {
	public List<Stop> list();
	public Stop get(Object _id);
	public void update(Object _id, Stop obj);
	public void create(Stop obj);
	public void delete(Object _id);
}
