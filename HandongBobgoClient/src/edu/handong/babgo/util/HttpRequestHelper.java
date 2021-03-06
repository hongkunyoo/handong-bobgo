package edu.handong.babgo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public class HttpRequestHelper implements RequestHelper {
	StringBuffer sb;
	Convertor convertor = new Convertor();
	public Day getTodaysBabgo(String id) {
		// TODO Auto-generated method stub
		String strURL = GlobalVariables.SERVER_ADDR+"/todays/get?key="+id;
		String json = this.getJsonStringByGet(strURL);
		if(json == null) return null;
		return convertor.convertToDay(json);
	}

	public Schedule getScheduleBabo(String id) {
		// TODO Auto-generated method stub
		String strURL = GlobalVariables.SERVER_ADDR+"/search/schedule/get?key="+id;
		String json = this.getJsonStringByGet(strURL);
		if(json == null) return null;
		return convertor.convertToSchedule(json);
	}

	public List<User> getRandomBabgo(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message askMeal(Message m) {
		// TODO Auto-generated method stub
		String strURL = GlobalVariables.SERVER_ADDR+"/message/askMeal";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json",convertor.convertToJson(m)));
		String json = this.getJsonStringByPost(strURL, params);
		return convertor.convertToMessage(json);
	}

	public Message setSchedule(String id, Schedule s) {
		// TODO Auto-generated method stub
		String strURL = GlobalVariables.SERVER_ADDR+"/settings/schedule/put";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key",id));
		params.add(new BasicNameValuePair("json",convertor.convertToJson(s)));
		
		String json = this.getJsonStringByPost(strURL, params);
		return convertor.convertToMessage(json);
	}

	public Message setAccount(String id, String hisnetId, String password,
			int random) {
		// TODO Auto-generated method stub
		InputStream is = null;
		String result = null;
		String hisnetHome = "http://hisnet.handong.edu";
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("Language", "Korean"));
		pair.add(new BasicNameValuePair("id", hisnetId));
		pair.add(new BasicNameValuePair("password", password));
		pair.add(new BasicNameValuePair("image.x", "0"));
		pair.add(new BasicNameValuePair("image.y", "0"));
		HttpClient client = new DefaultHttpClient();
		HttpPost method = new HttpPost(hisnetHome);
		try {
			method.setEntity(new UrlEncodedFormEntity(pair));
			HttpResponse response = client.execute(method);
			is = response.getEntity().getContent();
			sb = new StringBuffer();
			int c;
			while((c = is.read()) != -1){
				sb.append((char)c);
			}
			result = sb.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//String result = this.getJsonStringByPost(hisnetHome, pair);
		if(result.contains("http://hisnet.handong.edu/main.asp")){
			HttpGet go = new HttpGet("http://hisnet.handong.edu/main.asp?memo=");
			try {
				HttpResponse goResponse = client.execute(go);
				is = goResponse.getEntity().getContent();
				sb = new StringBuffer();
				int c;
				while((c = is.read()) != -1){
					sb.append((char)c);
				}
				String result2 = sb.toString();
				String hakbun = "no hakbun";
				if(result2.contains("("+hisnetId+")<br>")){
					hakbun = result2.split(hisnetId)[1];
					//hakbun = hakbun.split("[0-9]{8}")[1].split("</td>")[0];
					hakbun = hakbun.substring(12, 20);
				}
				
				HttpGet userInfo = new HttpGet("http://hisnet.handong.edu/userinfo/detailuserinfo11.asp?usernum="+hakbun);
				HttpResponse userInfoResponse = client.execute(userInfo);
				
				is = userInfoResponse.getEntity().getContent();
				InputStreamReader isr = new InputStreamReader(is,"euc-kr");
				sb = new StringBuffer();
				while((c = isr.read()) != -1){
					sb.append((char)c);
				}
				String page = sb.toString();
				page = page.split("User Information")[2];
				page = page.split("<!--본문-->")[0];
				String name = page.split("한글 이름")[1].substring(42, 45);
				String gender = page.split("성별")[1].substring(52,53);
				String major = page.split("소속")[1].substring(52);
				major = major.split("</td>")[0];
				User user = new User();
				user.setName(name);
				user.setMajor(major);
				user.setGender(gender.equals("남")? GlobalVariables.MAN: GlobalVariables.WOMAN);
				user.setPhone(id);
				user.setHisnetId(hisnetId);
				//user.setMajor(password);
				String strUrl = GlobalVariables.SERVER_ADDR+"/settings/user/put";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("key",id));
				params.add(new BasicNameValuePair("json",convertor.convertToJson(user)));
				String json = this.getJsonStringByPost(strUrl, params);
				return convertor.convertToMessage(json);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Message retM = new Message();
		retM.setMessage(GlobalVariables.FAILED);
		return retM;
	}

	public List<Message> getNewUpdates(String id) {
		// TODO Auto-generated method stub
		String strUrl = GlobalVariables.SERVER_ADDR+"/message/new/get?key="+id;
		String json = this.getJsonStringByGet(strUrl);
		return convertor.convertToMessages(json);
	}

	public Message acceptMeal(Message m) {
		// TODO Auto-generated method stub
		String strUrl = GlobalVariables.SERVER_ADDR+"/message/acceptMeal";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json",convertor.convertToJson(m)));
		
		String json = this.getJsonStringByPost(strUrl, params);
		return convertor.convertToMessage(json);
	}

	public Message refuseMeal(Message m) {
		// TODO Auto-generated method stub
		String strUrl = GlobalVariables.SERVER_ADDR+"/message/refuseMeal";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("json",convertor.convertToJson(m)));
		String json = this.getJsonStringByPost(strUrl, params);
		return convertor.convertToMessage(json);
	}

	public Message sendMessage(String id, Message m) {
		// TODO Auto-generated method stub
		String strUrl = GlobalVariables.SERVER_ADDR+"/message/sendMessage";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id",id));
		params.add(new BasicNameValuePair("json",convertor.convertToJson(m)));
		String json = this.getJsonStringByPost(strUrl, params);
		return convertor.convertToMessage(json);
	}
	
	public String getJsonStringByGet(String strUrl){
		InputStream is = null;
		HttpClient client = new DefaultHttpClient();
		try {
			
		
			//HttpGet method = new HttpGet(URLEncoder.encode(strUrl, "UTF-8"));
			HttpGet method = new HttpGet(strUrl);
			HttpResponse response = client.execute(method);
			is = response.getEntity().getContent();
			sb = new StringBuffer();
			int c;
			while((c = is.read()) != -1){
				sb.append((char)c);
			}
			return sb.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		} finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.toString();
				}
		}
		//return null;
	}
	
	
	public String getJsonStringByPost(String strUrl, List<NameValuePair> params){
		InputStream is = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost method = new HttpPost(strUrl);
		try {
			method.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(method);
			is = response.getEntity().getContent();
			sb = new StringBuffer();
			int c;
			while((c = is.read()) != -1){
				sb.append((char)c);
			}
			return sb.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		} finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.toString();
				}
		}
		//return null;
	}
}
