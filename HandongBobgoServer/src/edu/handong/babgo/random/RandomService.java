package edu.handong.babgo.random;

import java.util.List;

import edu.handong.babgo.model.User;

public interface RandomService {
	public User getRandomBabo(String key);
	public List<User> getUsers();
}
