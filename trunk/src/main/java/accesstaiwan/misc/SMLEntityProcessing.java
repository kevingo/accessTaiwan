package accesstaiwan.misc;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;

import accesstaiwan.dao.travel.impl.SMLEntityDaoImpl;
import accesstaiwan.model.travel.SML.Addr;
import accesstaiwan.model.travel.SML.County;
import accesstaiwan.model.travel.SML.Entity;
import accesstaiwan.model.travel.SML.Name;
import accesstaiwan.model.travel.SML.Price;
import accesstaiwan.model.travel.SML.Time;
import accesstaiwan.model.travel.SML.Traffic;
import accesstaiwan.model.travel.SML.Type;
import accesstaiwan.model.travel.SML.Values;

import com.mongodb.DBAddress;
import com.mongodb.Mongo;

public class SMLEntityProcessing {

	@Resource(name="mongoTemplate")
	private static MongoTemplate mongoTemplate;
	
	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("RawData.properties");
	private static Properties prop = new Properties();
	
	public static void main(String [] args) {
		SMLEntityProcessing s = new SMLEntityProcessing();

		try {
			mongoTemplate = new MongoTemplate(new Mongo(new DBAddress( args[0] + ":27017", "test" )), "test");
			prop.load(s.inputStream);
			String filePath = prop.getProperty("SML");
			BufferedReader br = getFile(filePath);
			mongoTemplate.dropCollection(SMLEntityDaoImpl.collectionName);
			int count = 0;
			String str = "";
			while((str=br.readLine())!=null) {
				count++;
				//System.out.println(new String(str.getBytes("8859_1"), "UTF-8"));
				System.out.println(str);
				String chtName = str.split(",")[0];
				String chtAddr = str.split(",")[1];
				String tel = str.split(",")[2];
				String typeName = str.split(",")[3];
				String chtCounty = str.split(",")[4];
				double lat = Double.valueOf(str.split(",")[5]);
				double lng = Double.valueOf(str.split(",")[6]);
				String chtTime = str.split(",")[7];
				String chtPrice = str.split(",")[8];
				String chtTraffic = str.split(",")[9];
				String chtDes = str.split(",")[10];
				
				Type type = new Type();
				type.setTypeName(typeName);
				
				Name name = new Name();
				name.setCHT(chtName);
				name.setCHS("-");
				name.setEN("-");
				name.setJP("-");
				name.setKR("-");
				County county = new County();
				county.setCHT(chtCounty);
				county.setCHS("-");
				county.setEN("-");
				county.setJP("-");
				county.setKR("-");
				Traffic traffic = new Traffic();
				traffic.setCHT(chtTraffic);
				traffic.setCHS("-");
				traffic.setEN("-");
				traffic.setJP("-");
				traffic.setKR("-");
				Price price = new Price();
				price.setCHT(chtPrice);
				price.setCHS("-");
				price.setEN("-");
				price.setJP("-");
				price.setKR("-");
				Addr addr = new Addr();
				addr.setCHT(chtAddr);
				addr.setCHS("-");
				addr.setEN("-");
				addr.setJP("-");
				addr.setKR("-");
				Time time = new Time();
				time.setCHT(chtTime);
				time.setCHS("-");
				time.setEN("-");
				time.setJP("-");
				time.setKR("-");
				
				Entity entity = new Entity();
				entity.setName(name);
				entity.setTel(tel);
				entity.setType(type);
				entity.setLocaiton(lng, lat);
				entity.setCounty(county);
				entity.setPrice(price);
				entity.setAddr(addr);
				entity.setTime(time);
				mongoTemplate.insert(SMLEntityDaoImpl.collectionName, entity);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedReader getFile(String path) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;
	}
}
