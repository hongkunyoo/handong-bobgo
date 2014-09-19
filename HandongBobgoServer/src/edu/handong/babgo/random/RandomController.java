package edu.handong.babgo.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.handong.babgo.model.User;
import edu.handong.babgo.util.Convertor;

@Controller
public class RandomController {
	@Autowired Convertor convertor;
	@Autowired RandomService randomService;
	
	@RequestMapping(value = "/random/users/get")
	public String getUsers(@RequestParam("key") String key, Model model) {
		
		User u = randomService.getRandomBabo(key);
		String message = convertor.convertToJson(u);
		model.addAttribute("message", message);		
		return "jsonView";
	}
}
