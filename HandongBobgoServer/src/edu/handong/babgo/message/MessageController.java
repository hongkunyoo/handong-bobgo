package edu.handong.babgo.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.handong.babgo.model.Message;
import edu.handong.babgo.util.Convertor;

@Controller
public class MessageController {
	@Autowired Convertor convertor;
	@Autowired MessageService messageService;
	
	@RequestMapping(value = "/message/new/get")
	public String getNewUpdates(@RequestParam("key") String key, Model model) {
		
		List<Message> returnMessages = messageService.getNewUpdates(key);
		String messages = convertor.convertToJson(returnMessages);
		
		model.addAttribute("message", messages );		
		return "jsonView";
	}
	
	@RequestMapping(value = "/message/askMeal")
	public String askMeal(@RequestParam("json") String json, Model model) {
		
		
		Message m = convertor.convertToMessage(json);
		Message returnMessage = messageService.askMeal(m);
		String message = convertor.convertToJson(returnMessage);
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
	
	@RequestMapping(value = "/message/acceptMeal")
	public String acceptMeal(@RequestParam("json") String json, Model model) {
		
		Message m = convertor.convertToMessage(json);
		Message returnMessage = messageService.acceptMeal(m);
		String message = convertor.convertToJson(returnMessage);
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
	
	@RequestMapping(value = "/message/refuseMeal")
	public String refuseMeal(@RequestParam("json") String json, Model model) {
		
		
		Message m = convertor.convertToMessage(json);
		Message returnMessage = messageService.refuseMeal(m);
		String message = convertor.convertToJson(returnMessage);
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
	
	@RequestMapping(value = "/message/sendMessage")
	public String sendMessage(@RequestParam("json") String json, Model model) {
		
		Message m = convertor.convertToMessage(json);
		Message returnMessage = messageService.sendMessage(m);
		String message = convertor.convertToJson(returnMessage);
		
		model.addAttribute("message", message );		
		return "jsonView";
	}
}
