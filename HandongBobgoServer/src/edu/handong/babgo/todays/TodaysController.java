package edu.handong.babgo.todays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.util.Convertor;


@Controller
public class TodaysController {
	@Autowired TodaysService todaysService;
	@Autowired Convertor convertor;
	
	@RequestMapping(value = "/todays/get")
	public String getTodays(@RequestParam("key") String key, Model model) {
		
		Day day = todaysService.getTodaysBabgo(key);
		String message = convertor.convertToJson(day);
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
	
	@RequestMapping(value = "/todays/put")
	public String putTodays(@RequestParam("key") String key, 
			@RequestParam("json") String json, Model model) {
		
		Day d =convertor.convertToDay(json);
		int result = todaysService.putDay(key, d);
		String message = ""+result;
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
}
