package accesstaiwan.web.travel.SML;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import accesstaiwan.dao.travel.impl.SMLEntityDaoImpl;
import accesstaiwan.model.travel.SML.Entity;

@Controller
@RequestMapping("sml")
public class SMLController {
	
	@Resource(name="SMLEntityDaoImpl")
	private SMLEntityDaoImpl dao;
	
	@RequestMapping(value="travel/entity", method=RequestMethod.GET, params={"type", "limit"}) 
	public @ResponseBody List<Entity> getEntitybyType(@RequestParam("type") String type, @RequestParam("limit") String limit) {
		try {
			type = new String(type.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		return dao.getByType(type, limit);
	}
	
	@RequestMapping(value="travel/entity", method=RequestMethod.GET, params={"lng", "lat"}) 
	public @ResponseBody List<Entity> getNearbyLocation(@RequestParam("lng") String lng, @RequestParam("lat") String lat) {		
		return dao.getByLocation(lng, lat);
	}
	
	@RequestMapping(value="travel/entity", method=RequestMethod.GET, params={"lng","lat","limit"}) 
	public @ResponseBody List<Entity> getNearbyLocationWithLimit(@RequestParam("lng") String lng, @RequestParam("lat") String lat, @RequestParam("limit") String limit) {		
		return dao.getByLocationWithLimit(lng, lat, limit);
	}
	
}
