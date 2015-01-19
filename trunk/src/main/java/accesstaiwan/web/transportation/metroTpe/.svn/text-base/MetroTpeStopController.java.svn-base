package accesstaiwan.web.transportation.metroTpe;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import accesstaiwan.dao.transportation.metroTpe.impl.MetroTpeStopDaoImpl;
import accesstaiwan.model.transportation.metroTpe.Stop;

@Controller
@RequestMapping("traffic")
public class MetroTpeStopController {
	
	@Resource(name="MetroTpeStopDaoImpl")
	private MetroTpeStopDaoImpl dao;
	
	Logger rootLogger  = Logger.getLogger("root");
	Logger logger = Logger.getLogger("root.StopController");   
	
	FileHandler handler;
	
	public MetroTpeStopController() {
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
	
	@RequestMapping(value="metroTpe/stops/", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStops(HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
				    ", Time : " + getDateTime() + 
				    ", URL : " + "/metroTpe/stops/");

		return dao.list();
	}
	
	@RequestMapping(value="metroTpe/stops/{name}", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStop(@PathVariable String name, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/metroTpe/stops/" + name);
		return dao.get(name);
	}
	
	@RequestMapping(value="metroTpe/stops/{from}/{to}", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStop(@PathVariable String from, @PathVariable String to, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/metroTpe/stops/" + from + "/" + to);
		return dao.get(from, to);
	}
	
	private String getDateTime() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		return strDate;
	}

}
