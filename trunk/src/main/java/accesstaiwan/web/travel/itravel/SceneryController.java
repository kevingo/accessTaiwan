package accesstaiwan.web.travel.itravel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import accesstaiwan.dao.transportation.hsr.impl.TrainDAOImpl;
import accesstaiwan.dao.travel.impl.SceneryDaoImpl;
import accesstaiwan.model.travel.itravel.Scenery;

@Controller
@RequestMapping("travel")
public class SceneryController {

	private static String defaultLang = "cht";
	
	@Resource(name="SceneryDaoImpl")
	private SceneryDaoImpl dao;
	
	@RequestMapping(value="itravel/scenery", method=RequestMethod.GET)
	public @ResponseBody List<Scenery> showScenery(@RequestParam("lang") String lang) {
		System.out.println("lang: " + lang);
		return dao.get();
	}
	
}
