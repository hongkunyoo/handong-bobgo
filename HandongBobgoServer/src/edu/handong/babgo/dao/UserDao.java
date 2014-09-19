package edu.handong.babgo.dao;

import java.util.List;

import edu.handong.babgo.model.User;

public interface UserDao {
	public List<User> getUsers();
	public User getUser(String key);
	public void setUser(String key, User user);
	public void deleteUser(String key);
}
