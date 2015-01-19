package accesstaiwan.web.transportation.metroKao;

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

import accesstaiwan.dao.transportation.metroKao.impl.MetroKaoStopDaoImpl;
import accesstaiwan.model.transportation.metroKao.Stop;

@Controller
@RequestMapping("traffic")
public class MetroKaoStopController {
	
	@Resource(name="MetroKaoStopDaoImpl")
	private MetroKaoStopDaoImpl dao;
	
	Logger rootLogger  = Logger.getLogger("root");
	Logger logger = Logger.getLogger("root.StopController");   
	
	FileHandler handler;
	
	public MetroKaoStopController() {
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
	
	@RequestMapping(value="metroKao/stops/", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStops(HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
				    ", Time : " + getDateTime() + 
				    ", URL : " + "/metroKao/stops/");

		return dao.list();
	}
	
	@RequestMapping(value="metroKao/stops/{name}", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStop(@PathVariable String name, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/metroKao/stops/" + name);
		return dao.get(name);
	}
	
	@RequestMapping(value="metroKao/stops/{from}/{to}", method = RequestMethod.GET)
	public @ResponseBody List<Stop> showStop(@PathVariable String from, @PathVariable String to,HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/metroKao/stops/" + from + "/" + to);
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
