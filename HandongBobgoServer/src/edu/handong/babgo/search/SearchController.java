package edu.handong.babgo.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.util.Convertor;

@Controller
public class SearchController {
	@Autowired SearchService searchService;
	@Autowired Convertor convertor;
	
	@RequestMapping(value = "/search/schedule/get")
	public String getTodays(@RequestParam("key") String key, Model model) {
		
		Schedule s = searchService.getScheduleBabgo(key);
		String message = convertor.convertToJson(s);
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
}
