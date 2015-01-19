package accesstaiwan.web.transportation.tra;

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

import accesstaiwan.dao.transportation.tra.impl.TraStopDAOImpl;
import accesstaiwan.misc.HSRDataProcessing;
import accesstaiwan.model.transportation.tra.Stop;

@Controller
@RequestMapping("traffic")
public class TraStopController {
	
	@Resource(name="TraStopDAOImpl")
	private TraStopDAOImpl dao;
	
	Logger rootLogger  = Logger.getLogger("root");
	Logger logger = Logger.getLogger("root.StopController");   
	boolean append = true;
	FileHandler handler;
	
	public TraStopController() {
		try {
			handler = new FileHandler("./post.log", true);
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="tra/stops/add", method = RequestMethod.GET)
	public @ResponseBody List<Stop> add(@RequestBody Stop stop) {
		
		HSRDataProcessing.insertHSRStopData();
		return dao.list();
	}
	
	@RequestMapping(value="tra/stops/deleteALL", method = RequestMethod.GET)
	public @ResponseBody List<Stop> dropCollection() {
		
		dao.dropCollection();
		return dao.list();
	}
	
	@RequestMapping(value="tra/stops/", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStops(HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
				    ", Time : " + getDateTime() + 
				    ", URL : " + "/tra/stops/");

		return dao.list();
	}
	
	@RequestMapping(value="tra/stops/{name}", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStop(@PathVariable String name, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/tra/stops/" + name);
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
