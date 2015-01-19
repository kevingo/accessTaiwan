package accesstaiwan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("itri")
public class HelloWorldController {
	
	@RequestMapping(value="hello", method = RequestMethod.GET)
	public ModelAndView hello() {
		return new ModelAndView("hello");
	}
}
