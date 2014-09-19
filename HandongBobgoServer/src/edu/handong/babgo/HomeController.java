package edu.handong.babgo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.handong.babgo.dao.DayDao;
import edu.handong.babgo.dao.PartnerDao;
import edu.handong.babgo.dao.UserDao;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.Convertor;
import edu.handong.babgo.util.TestProvider;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired PartnerDao partnerDao;
	@Autowired DayDao dayDao;
	@Autowired UserDao userDao;
	@Autowired HomeService homeService;
	TestProvider provider = new TestProvider();
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws UnsupportedEncodingException 
	 */
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String home(@RequestParam("param") String param, Model model) throws UnsupportedEncodingException {
		//logger.info("Welcome home! the client locale is "+ locale.toString());
		//homeService.setMessage(param);
		
		String message = "working?";
		message = new Convertor().convertToJson(homeService.getMessages());
		
//		message = ""+TimeZone.getDefault();
		model.addAttribute("connection", URLDecoder.decode(message,"utf-8") );		
		return "home";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String home2(@RequestParam("param") String param, Model model) {
		//logger.info("Welcome home! the client locale is "+ locale.toString());
		
		homeService.deleteMessage(param);
		String message = "test_suceed!";
		model.addAttribute("connection", message );		
		return "home";
	}
	
	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public String home3(@RequestParam("param") String param, Model model) {
		
		homeService.setMessage(param);
		String message = "test_suceed!";
		model.addAttribute("connection", message );		
		return "home";
	}
	
	@RequestMapping(value = "/test/gcm")
	public String home4(@RequestParam("key") String key, Model model) {
		
		String m = homeService.sendMessage(key);
		
		model.addAttribute("message", m);		
		return "jsonView";
	}
}
