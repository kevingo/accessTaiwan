package accesstaiwan.dao.transportation.hsr;

import java.util.List;

import accesstaiwan.model.transportation.hsr.Price;

public interface PriceDao {
	public List<Price> list();
	public Price get(Object _id);
	public void update(Object _id, Price obj);
	public void create(Price obj);
	public void delete(Object _id);
	
	public Price get(String stop1, String stop2);
}
