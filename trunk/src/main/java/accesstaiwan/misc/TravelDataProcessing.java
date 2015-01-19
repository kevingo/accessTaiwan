package accesstaiwan.misc;

import java.io.*;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;

import accesstaiwan.dao.travel.impl.SceneryDaoImpl;
import accesstaiwan.model.transportation.tra.Stop;
import accesstaiwan.model.travel.itravel.Scenery;

import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TravelDataProcessing {

	@Resource(name="mongoTemplate")
	private static MongoTemplate mongoTemplate;
	
	public static void main(String [] args) {
		
		try {
			mongoTemplate = new MongoTemplate(new Mongo(new DBAddress( args[0] + ":27017", "test" )), "test");
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
		TravelDataProcessing proc = new TravelDataProcessing();
		proc.insertTravelData();
	}

	private void insertTravelData() {
		System.out.println(SceneryDaoImpl.collectionName);
		mongoTemplate.dropCollection(SceneryDaoImpl.collectionName);
		try {
			String filePath = "D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/travel/iscenery_test.csv";
			RandomAccessFile raf = new RandomAccessFile(new File(filePath), "rw");
			String str = "";			
			int count = 0;
			while((str=raf.readLine())!=null) {
				count++;
				System.out.println(count);
			}
			
			Scenery [] scenery = new Scenery [count];
			count = 0;
			raf.seek(0);
			while((str=raf.readLine())!=null) {				
				String name = str.split(",")[1];
				name = new String(name.getBytes("8859_1"),"UTF-8");
				System.out.println(name);
				String addr = str.split(",")[3];
				String latitude = str.split(",")[8];
				String longitude = str.split(",")[7];
				scenery[count] = new Scenery();
				scenery[count].setLatitude(Float.valueOf(latitude));
				scenery[count].setLongitude(Float.valueOf(longitude));
				scenery[count].setName(name);
				mongoTemplate.insert(SceneryDaoImpl.collectionName, scenery[count]);
				count++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
