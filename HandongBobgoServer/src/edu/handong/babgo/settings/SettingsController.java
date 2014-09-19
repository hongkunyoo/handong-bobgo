package edu.handong.babgo.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.Convertor;
import edu.handong.babgo.util.GlobalVariables;

@Controller
public class SettingsController {
	@Autowired Convertor convertor;
	@Autowired SettingsService settingsService;
	
	@RequestMapping(value = "/settings/schedule/put")
	public String putSettings(@RequestParam("key") String key, 
			@RequestParam("json") String json, Model model) {
		
		Schedule s = convertor.convertToSchedule(json);
		int result = settingsService.setSchedule(key, s);
		//String message = ""+result;
		Message retM = new Message();
		if(result == 1){
			retM.setMessage(GlobalVariables.SUCCEED);
		}
		
		model.addAttribute("message", convertor.convertToJson(retM) );		
		return "jsonView";
	}
	
	@RequestMapping(value = "/settings/user/put")
	public String putAccountSettings(@RequestParam("key") String key, 
			@RequestParam("json") String json, Model model) {
		
		User user = convertor.convertToUser(json);
		int result = settingsService.setUser(key, user);
		Message m = new Message();
		if(result == 1){
			m.setMessage(GlobalVariables.SUCCEED);
		}else if(result == -1){
			m.setMessage(GlobalVariables.DUPLICATED);
		}else{
			m.setMessage(GlobalVariables.FAILED);
		}
		
		model.addAttribute("message", convertor.convertToJson(m));		
		return "jsonView";
	}
	
}
