package com.birdex.activiti.service;

import org.springframework.stereotype.Component;

import com.birdex.activiti.dao.entity.Group;
import com.birdex.activiti.dao.entity.User;

@Component  
public class AccountManager {

	// private UserDao userDao;
	// private GroupDao groupDao;
	// private ShiroDbRealm shiroRealm;
	//
	 public Group getGroupByGroupId(String groupId) {
		 //return groupDao.findByGroupId(groupId);
		 return null;
	 }
	//
	// //-- User Manager --//
	public User getUser(Long id) {
		// return userDao.findOne(id);
		return null;
	}

}
