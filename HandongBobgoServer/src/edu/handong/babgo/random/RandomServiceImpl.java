package edu.handong.babgo.random;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.handong.babgo.dao.UserDao;
import edu.handong.babgo.model.User;

public class RandomServiceImpl implements RandomService {
	@Autowired UserDao userDao;
		
	@Override
	public User getRandomBabo(String key) {
		// TODO Auto-generated method stub
		User me = userDao.getUser(key);
		if(me == null) return new User();
		List<User> list = userDao.getUsers();
		if(list.size() == 0) return null;
		List<User> newList = new ArrayList<User>();
		for(User u : list){
			if(u.getMajor().equals(me.getMajor())){
				newList.add(u);
			}
		}
		if(newList.size() == 0)
			return list.get(((int)(Math.random()*9))%list.size());
		
		return newList.get(((int)(Math.random()*9))%newList.size());
	}
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userDao.getUsers();
	}
}
