package accesstaiwan.dao.transportation.hsr;

import java.util.List;

import accesstaiwan.model.transportation.hsr.Train;

public interface TrainDao {
	public List<Train> list();
	public Train get(Object _id);
	public void update(Object _id, Train train);
	public void create(Train train);
	public void delete(Object _id);
	
	public List<Train> listTrainsFromStop(String from);
	public List<Train> listTrainsToStop(String to);
	
	public List<Train> listTrains(String from, String to);
	public List<Train> listTrainsStartAround(String from, String to, String around);
	public List<Train> listTrainsArriveAround(String from, String to, String around);
	public List<Train> listTrainsStartAround(String from, String to, String around, String dayOfWeek);
	public List<Train> listTrainsArrivefAround(String from, String to, String around, String dayOfWeek);
}
