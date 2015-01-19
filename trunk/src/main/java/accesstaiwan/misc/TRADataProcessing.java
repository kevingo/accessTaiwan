package accesstaiwan.misc;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;

import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import accesstaiwan.model.transportation.tra.Stop;

public class TRADataProcessing {
	
	@Resource(name="mongoTemplate")
	private static MongoTemplate mongoTemplate;

	List list = new ArrayList();
	HashMap stopMap = new HashMap();
	HashMap chiEng = new HashMap();
	
	BufferedReader br;
	FileReader fr;
	File file;
	
	public static void main(String [] args) {
		
		try {
			mongoTemplate = new MongoTemplate(new Mongo(new DBAddress( args[0] + ":27017", "test" )), "test");
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
		TRADataProcessing proc = new TRADataProcessing();
		String readFilePath = "C:/Users/user/Desktop/dropbox/ITRI Project/FY101accessTaiwan/TRA_STOP_ADDR.csv";
		String writeFilePath = "C:/Users/user/Desktop/dropbox/ITRI Project/FY101accessTaiwan/TRA_STOP_ADDR_latlng.csv";
		//proc.doReadFile(readFilePath);
		//proc.writeFile(writeFilePath);
		//proc.chiEngtrans();
		proc.insertTraStopData();
	}
	
	private void insertTraStopData() {
		mongoTemplate.dropCollection("tra.stops");
		try {
			RandomAccessFile raf = new RandomAccessFile(new File("D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/tra/stopInfo_latlng_eng.csv"), "rw");
			String str = "";			
			int count = 0;
			while((str=raf.readLine())!=null) {
				count++;
			}
			
			Stop [] stop = new Stop [count];
			count = 0;
			raf.seek(0);
			while((str=raf.readLine())!=null) {				
				String stopName = str.split(",")[5];
				String addr = str.split(",")[1];
				String latitude = str.split(",")[3];
				String longitude = str.split(",")[4];
				stop[count] = new Stop();
				stop[count].setLatitude(Float.valueOf(latitude));
				stop[count].setLongitude(Float.valueOf(longitude));
				stop[count].setName(stopName);
				mongoTemplate.insert("tra.stops", stop[count]);
				count++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Stop stop = new Stop();
	}

	private void chiEngtrans() {
		createChiEngHashMap();
		try {
			br = new BufferedReader(new FileReader(new File("C:/Users/user/Desktop/dropbox/ITRI Project/FY101accessTaiwan/TRA_STOP_ADDR_latlng.csv")));
			String str = "";
			list.clear();
			while((str=br.readLine())!=null) {
				String stopName = str.split(",")[0];
				String englishName = (String) chiEng.get(stopName);
				str += "," + englishName;
				list.add(str);
			}
			writeFile("C:/Users/user/Desktop/dropbox/ITRI Project/FY101accessTaiwan/stopInfo_latlng_eng.csv");
			br.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void createChiEngHashMap() {
		try {
			br = new BufferedReader(new FileReader(new File("C:/Users/user/Desktop/dropbox/ITRI Project/FY101accessTaiwan/chinese_eng_lookuptable.csv")));
			String str = "";
			while((str=br.readLine())!=null) {
				String chinese = str.split(",")[0];
				String english = str.split(",")[1];
				chiEng.put(chinese, english);
			}
			br.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private void doReadFile(String filePath) {
		
		try {
			file = new File(filePath);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String line = "";
			while((line=br.readLine())!=null) {
				
				String stopName = line.split(",")[0];
				String addr = line.split(",")[1];
				String tel = line.split(",")[2];
							
				addr = URLEncoder.encode(addr, "UTF-8");
				String url = "http://map.longwin.com.tw/addr_geo.php?addr=" + addr;
				String latlng = doGET(url);
				line += "," + latlng;
				stopMap.put(stopName, line);
				list.add(line);
			}
			
			fr.close();
			br.close();
			
		} catch(Exception e) {}
	}
	
	private String doGET(String sURL) {
		URL url;
	    boolean doSuccess = false; 
	    BufferedReader in = null; 
	    String latlng = "";
		try {
			Thread.sleep(100);
			url = new URL(sURL);
		    HttpURLConnection URLConn = (HttpURLConnection) url.openConnection();
		    
		    URLConn.setDoInput(true); 
		    URLConn.setDoOutput(true); 
		    URLConn.connect(); 
		    URLConn.getOutputStream().flush(); 
		    in = new BufferedReader(new InputStreamReader(URLConn.getInputStream(), "UTF-8"));
		    
		      String line; 
		      Pattern pattern = Pattern.compile("[0-2][0-9].[0-9]*, [0-9]+.[0-9]+");
		      while ((line = in.readLine()) != null) {
		        if(line.startsWith("<p>¸g½n«×: <strong>(")) {
		        	Matcher matcher =pattern.matcher(line);
		        	while(matcher.find()){ 
		        		latlng = matcher.group();
		        		System.out.println(latlng);
		        	}
		        }	
		      }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return latlng; 

	}
	
	private void writeFile(String writeFilePath) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(writeFilePath)));
			
			Iterator<String> ite = list.iterator();
			while(ite.hasNext()) {
				bw.write((String)ite.next() + "\n");
			}
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
