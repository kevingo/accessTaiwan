package accesstaiwan.web.transportation.hsr;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import accesstaiwan.dao.transportation.hsr.impl.TrainDAOImpl;
import accesstaiwan.misc.HSRDataProcessing;
import accesstaiwan.model.transportation.hsr.Stop;
import accesstaiwan.model.transportation.hsr.Train;

@Controller
@RequestMapping("traffic")
public class TrainController {
	
	@Resource(name="TrainDAOImpl")
	private TrainDAOImpl dao;
	
	public static Logger logger = Logger.getLogger("root.TrainController");

	@RequestMapping(value="hsr/trains/add", method = RequestMethod.GET)
	public @ResponseBody List<Train> add() {
		return dao.list();
	}
	
	@RequestMapping(value="hsr/trains", method = RequestMethod.GET)
	public @ResponseBody List<Train> showAllStops(HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/hsr/trains");
		return dao.list();
	}
	
	@RequestMapping(value="hsr/trains/{id}", method = RequestMethod.GET)
	public @ResponseBody List<Train> showStopById(@PathVariable String id, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/hsr/trains/" + id);
		return dao.get(id);
	}
	
	@RequestMapping(value="hsr/trains/{to}", method = RequestMethod.GET)
	public @ResponseBody List<Train> showStopByTo(@PathVariable String to, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/hsr/trains/" + to);
		return dao.getTrainByStop(to);
	}	
	
	@RequestMapping(value="hsr/trains/{from}/{to}", method = RequestMethod.GET)
	public @ResponseBody List<Train> showStopByFromTo(@PathVariable String from, @PathVariable String to, HttpServletRequest req) {
		logger.info("IP : " + req.getRemoteAddr() + 
			    ", Time : " + getDateTime() + 
			    ", URL : " + "/hsr/trains/" + from + "/" + to);
		return dao.getTrainByStop(from, to);
	}	
	
	private String getDateTime() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		return strDate;
	}
}
