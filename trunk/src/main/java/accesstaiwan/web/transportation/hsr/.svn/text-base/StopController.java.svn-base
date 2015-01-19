package accesstaiwan.web.transportation.hsr;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import accesstaiwan.dao.transportation.hsr.impl.StopDAOImpl;
import accesstaiwan.misc.HSRDataProcessing;
import accesstaiwan.model.transportation.hsr.Stop;

@Controller
@RequestMapping("traffic")
public class StopController {
	
	@Resource(name="StopDAOImpl")
	private StopDAOImpl dao;
	
	Logger rootLogger  = Logger.getLogger("root");
	Logger logger = Logger.getLogger("root.StopController");   
	boolean append = true;
	FileHandler handler;
	
	public StopController() {
		try {
			handler = new FileHandler("./post.log", true);
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* 
	 * Taipei,25.04799,121.51643
	 * Banciao,25.01453, 121.46340
	 * Taoyuan,25.01289,121.21515
	 *  Hsinchu,24.80878,12104025
		Taichung,24.11202, 120.61611
		Chiayi,23.45933, 120.32341
		Tainan,22.92496, 120.28575		
		Zuoying,22.68690, 120.30780
	 * 
	 * */
	
	@RequestMapping(value="hsr/stops/add", method = RequestMethod.GET)
	public @ResponseBody List<Stop> add(@RequestBody Stop stop) {
		
		HSRDataProcessing.insertHSRStopData();
		return dao.list();
	}
	
	@RequestMapping(value="hsr/stops/deleteALL", method = RequestMethod.GET)
	public @ResponseBody List<Stop> dropCollection() {
		
		dao.dropCollection();
		return dao.list();
	}
	
	@RequestMapping(value="hsr/stops/", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStops(HttpServletRequest req) {
		
		logger.info("IP : " + req.getRemoteAddr() + 
				    ", Time : " + getDateTime() + 
				    ", URL : " + "/hsr/stops/");

		return dao.list();
	}
	
	@RequestMapping(value="hsr/stops/{name}", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStop(@PathVariable String name, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/hsr/stops/" + name);
		return dao.get(name);
	}
	
	private String getDateTime() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		return strDate;
	}
	
}
