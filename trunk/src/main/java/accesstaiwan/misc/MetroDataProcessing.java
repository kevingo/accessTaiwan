package accesstaiwan.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.document.mongodb.MongoTemplate;

import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MetroDataProcessing {

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
		//doTaipeiMetroData();
		//doKaoMetroData();
		//insertMetroKaoData();
		insertMetroTpeData();
		
	}

	private static void insertMetroTpeData() {
		mongoTemplate.dropCollection("metroTpe.stops");

		int numOfStops = 0;
		try {
			RandomAccessFile raf = new RandomAccessFile(new File("D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Metro Taipei/stops_latlng.txt"), "rw");
			String str = "";			
			int count = 0;
			while((str=raf.readLine())!=null) {
				count++;
			}
			
			accesstaiwan.model.transportation.metroTpe.Stop [] stop = new accesstaiwan.model.transportation.metroTpe.Stop [count];
			count = 0;
			raf.seek(0);
			while((str=raf.readLine())!=null) {
				System.out.println(str);
				String stopName = str.split(",")[5];
				String latitude = str.split(",")[3];
				String longitude = str.split(",")[4];
				stop[count] = new accesstaiwan.model.transportation.metroTpe.Stop();
				stop[count].setName(stopName);
				stop[count].setLatitude(Float.valueOf(latitude));
				stop[count].setLongitude(Float.valueOf(longitude));
				mongoTemplate.insert("metroTpe.stops", stop[count]);
				count++;
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private static void insertMetroKaoData() {
		mongoTemplate.dropCollection("metroKao.stops");

		int numOfStops = 0;
		try {
			RandomAccessFile raf = new RandomAccessFile(new File("D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Kaohsiung Rapid Transit/stops_latlng.txt"), "rw");
			String str = "";			
			int count = 0;
			while((str=raf.readLine())!=null) {
				count++;
			}
			
			accesstaiwan.model.transportation.metroKao.Stop [] stop = new accesstaiwan.model.transportation.metroKao.Stop [count];
			count = 0;
			raf.seek(0);
			while((str=raf.readLine())!=null) {
				String stopName = str.split(",")[2];
				String latitude = str.split(",")[3];
				String longitude = str.split(",")[4];
				stop[count] = new accesstaiwan.model.transportation.metroKao.Stop();
				stop[count].setName(stopName);
				stop[count].setLatitude(Float.valueOf(latitude));
				stop[count].setLongitude(Float.valueOf(longitude));
				mongoTemplate.insert("metroKao.stops", stop[count]);
				count++;
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		
	}

	private static void doKaoMetroData() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Kaohsiung Rapid Transit/stops.txt")));
			String str = "";
			while((str=br.readLine())!=null) {
				String addr = str.split(",")[1];
				addr = URLEncoder.encode(addr, "UTF-8");
				String url = "http://map.longwin.com.tw/addr_geo.php?addr=" + addr;
				String latlng = doGETLatLng(url);
				String newContent = str + "," + latlng + "\n";
				writeFile(newContent, "D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Kaohsiung Rapid Transit/stops_latlng.txt");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	private static void doTaipeiMetroData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Metro Taipei/stops.txt")));
			HashMap<String, String> dic = taipeiChiEng();
			
			String str = ""; String engName = "";
			while((str=br.readLine())!=null) {
				String chiName = str.split(",")[0];
				String addr = str.split(",")[2];
				addr = URLEncoder.encode(addr, "UTF-8");
				String url = "http://map.longwin.com.tw/addr_geo.php?addr=" + addr;
				String latlng = doGETLatLng(url);
				engName = dic.get(chiName);
				String newContent = str + "," + latlng + "," + engName + "\n";
				writeFile(newContent, "D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Metro Taipei/stops_latlng.txt");

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String doGETLatLng(String sURL) {
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
					while(matcher.find()) { 
						latlng = matcher.group();		        		
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
	
	private static HashMap<String, String> taipeiChiEng() {
		
		HashMap<String, String> dic = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("D:/DropBox/Dropbox/ITRI Project/FY101accessTaiwan/raw_data/Metro Taipei/chiEng.txt")));
			String str = "";
			String chineseName = "";
			String engName = "";
			while((str=br.readLine())!=null) {
				chineseName = str.split(",")[0];
				engName = str.split(",")[1];
				dic.put(chineseName, engName);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dic;
				
	}
	
	private static void writeFile(String content, String filePath) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write(content);
			bw.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}		
	}
	
}
