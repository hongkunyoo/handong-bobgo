package edu.handong.babgo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public class Convertor {
	ConvertorTemplate template = new ConvertorTemplate();
	private Convertor convertor = this;
	public String convertToJson(User user){
		return template.convert(user, new Matcher<User, String>(){

			public String match(User obj) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", obj.getId());
				map.put("name", obj.getName());
				map.put("hisnetId", obj.getHisnetId());
				map.put("phone", obj.getPhone());
				map.put("gender", ""+obj.getGender());
				map.put("major", obj.getMajor());
				map.put("random", ""+obj.getRandom());
				map.put("registerId", obj.getRegisterId());
				
				JSONObject json = new JSONObject(map);
				String jsonStr = json.toString();
				String replaced = jsonStr.replaceAll(" ", "");
				try {
					String ret = URLEncoder.encode(replaced, "UTF-8");
					return ret;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					return e.toString();
				}
			}
		});
	}
	public String convertToJson(Partner partner){
		return template.convert(partner, new Matcher<Partner, String>(){

			public String match(Partner obj) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("key", obj.getKey());
				map.put("dow", ""+obj.getDow());
				map.put("partnerName", obj.getPartnerName());
				map.put("partnerPhone", obj.getPartnerPhone());
				map.put("partnerKey", ""+obj.getPartnerKey());
				map.put("fixed", ""+obj.getFixed());
				map.put("state", ""+obj.getState());
				map.put("meal", obj.getMeal());
				
				JSONObject json = new JSONObject(map);
				String jsonStr = json.toString();
				String replaced = jsonStr.replaceAll(" ", "");
				try {
					String retVal = URLEncoder.encode(replaced, "UTF-8");
					return retVal;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					return e.toString();
				}
				
			}
			
		});
	}
	public String convertToJson(Day day){
		return template.convert(day, new Matcher<Day, String>(){

			public String match(Day obj) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("month", ""+obj.getMonth());
				map.put("date", ""+obj.getDate());
				map.put("dow", ""+obj.getDow());
				map.put("breakfast", convertor.convertToJson(obj.getBreakfast()));
				map.put("lunch", convertor.convertToJson(obj.getLunch()));
				map.put("dinner", convertor.convertToJson(obj.getDinner()));
				
				JSONObject json = new JSONObject(map);
				String jsonStr = json.toString();
				return jsonStr.replaceAll(" ", "");
			}
			
		});
	}
	public String convertToJson(Schedule s){
		return template.convert(s, new Matcher<Schedule, String>(){

			public String match(Schedule obj) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				Vector<Day> days = obj.getSchedule();
				map.put("key", ""+obj.getKey());
				map.put("sun", ""+convertor.convertToJson(days.get(Calendar.SUNDAY-1)));
				map.put("mon", ""+convertor.convertToJson(days.get(Calendar.MONDAY-1)));
				map.put("tue", ""+convertor.convertToJson(days.get(Calendar.TUESDAY-1)));
				map.put("wed", ""+convertor.convertToJson(days.get(Calendar.WEDNESDAY-1)));
				map.put("thu", ""+convertor.convertToJson(days.get(Calendar.THURSDAY-1)));
				map.put("fri", ""+convertor.convertToJson(days.get(Calendar.FRIDAY-1)));
				map.put("sat", ""+convertor.convertToJson(days.get(Calendar.SATURDAY-1)));
				
				JSONObject json = new JSONObject(map);
				String jsonStr = json.toString();
				return jsonStr.replaceAll(" ", "");
			}
			
		});
	}
	public String convertToJson(Message m){
		return template.convert(m, new Matcher<Message, String>(){


			public String match(Message obj) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("key", ""+obj.getKey());
				map.put("fromId", ""+obj.getFromId());
				map.put("from", ""+obj.getFrom());
				map.put("toId", ""+obj.getToId());
				map.put("to", ""+obj.getTo());
				map.put("date", ""+obj.getDate());
				map.put("month", ""+obj.getMonth());
				map.put("dow", ""+obj.getDow());
				map.put("meal", ""+obj.getMeal());
				map.put("fromPhone", ""+obj.getFromPhone());
				map.put("message", ""+obj.getMessage());
				map.put("state", ""+obj.getState());
				map.put("confirm", ""+obj.getConfirm());
				
				JSONObject json = new JSONObject(map);
				String jsonStr = json.toString();
				String replaced =  jsonStr.replaceAll(" ", "");
				try {
					String ret = URLEncoder.encode(replaced, "UTF-8");
					return ret;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					return e.toString();
				}
			}
			
		});
	}
	
	
	
	public User convertToUser(String json){
		return template.convert(json, new Matcher<String, User>(){

			public User match(String obj) {
				// TODO Auto-generated method stub
				try {
					obj = URLDecoder.decode(obj, "UTF-8");
					User user = new User();
					JSONObject json = new JSONObject(obj);
					user.setId(json.getString("id"));
					user.setName(json.getString("name"));
					user.setHisnetId(json.getString("hisnetId"));
					user.setPhone(json.getString("phone"));
					user.setGender(json.getInt("gender"));
					user.setMajor(json.getString("major"));
					user.setRandom(json.getInt("random"));
					user.setRegisterId(json.getString("registerId"));
					
					return user;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public Partner convertToPartner(String json){
		return template.convert(json, new Matcher<String, Partner>(){

			public Partner match(String obj) {
				// TODO Auto-generated method stub
				try {
					obj = URLDecoder.decode(obj, "UTF-8");
					Partner partner = new Partner();
					JSONObject json = new JSONObject(obj);
					partner.setKey(json.getString("key"));
					partner.setDow(json.getInt("dow"));
					partner.setPartnerKey(json.getString("partnerKey"));
					partner.setPartnerName(json.getString("partnerName"));
					partner.setPartnerPhone(json.getString("partnerPhone"));
					partner.setFixed(json.getInt("fixed"));
					partner.setState(json.getInt("state"));
					partner.setMeal(json.getString("meal"));
					
					return partner;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public Day convertToDay(String json){
		return template.convert(json, new Matcher<String, Day>(){

			public Day match(String obj) {
				// TODO Auto-generated method stub
				try {
					Day day = new Day();
					JSONObject json = new JSONObject(obj);
					day.setMonth(json.getInt("month"));
					day.setDate(json.getInt("date"));
					day.setDow(json.getInt("dow"));
					day.setBreakfast(convertor.convertToPartner(json.getString("breakfast")));
					day.setLunch(convertor.convertToPartner(json.getString("lunch")));
					day.setDinner(convertor.convertToPartner(json.getString("dinner")));
					
					return day;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public Schedule convertToSchedule(String json){
		return template.convert(json, new Matcher<String, Schedule>(){

			public Schedule match(String obj) {
				// TODO Auto-generated method stub
				try {
					Schedule s = new Schedule();
					JSONObject json = new JSONObject(obj);
					s.setKey(json.getString("key"));
					Vector<Day> days = new Vector<Day>();
					days.add(convertor.convertToDay(json.getString("sun")));
					days.add(convertor.convertToDay(json.getString("mon")));
					days.add(convertor.convertToDay(json.getString("tue")));
					days.add(convertor.convertToDay(json.getString("wed")));
					days.add(convertor.convertToDay(json.getString("thu")));
					days.add(convertor.convertToDay(json.getString("fri")));
					days.add(convertor.convertToDay(json.getString("sat")));
					s.setSchedule(days);
					
					return s;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	public Message convertToMessage(String json){
		return template.convert(json, new Matcher<String, Message>(){

			public Message match(String obj) {
				// TODO Auto-generated method stub
				try {
					obj = URLDecoder.decode(obj, "UTF-8");
					Message m = new Message();
					JSONObject json = new JSONObject(obj);
					m.setKey(json.getString("key"));
					m.setFromId(json.getString("fromId"));
					m.setFrom(json.getString("from"));
					m.setToId(json.getString("toId"));
					m.setTo(json.getString("to"));
					m.setDate(json.getInt("date"));
					m.setMonth(json.getInt("month"));
					m.setDow(json.getInt("dow"));
					m.setMeal(json.getString("meal"));
					m.setFromPhone(json.getString("fromPhone"));
					m.setMessage(json.getString("message"));
					m.setState(json.getInt("state"));
					m.setConfirm(json.getInt("confirm"));
					
					return m;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	
	public String convertToJson(List<Message> messages){
		StringBuffer sb = new StringBuffer();
		sb.append("{messages:[");
		for(int i = 0 ; i < messages.size() ; i++){
			sb.append(this.convertor.convertToJson(messages.get(i)));
			if(i != messages.size() -1)
				sb.append(",");
		}
		sb.append("]}");
		return sb.toString();
	}
	
	public List<Message> convertToMessages(String strJson){
		List<Message> list = new ArrayList<Message>();
		try {
			JSONObject json = new JSONObject(strJson);
			JSONArray ja = json.getJSONArray("messages");
			for(int i = 0 ; i < ja.length() ; i++){
				list.add(this.convertToMessage(ja.getString(i)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	class ConvertorTemplate{
		
		public <T,E> E convert(T obj, Matcher<T, E> matcher){
			
			return matcher.match(obj);
			
		}
	}
}
