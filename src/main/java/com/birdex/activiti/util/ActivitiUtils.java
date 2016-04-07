package com.birdex.activiti.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.birdex.activiti.dao.entity.User;

public class ActivitiUtils {

	public static UserEntity toActivitiUser(User bUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(bUser.getId().toString());
		userEntity.setFirstName(bUser.getFirstName());
		userEntity.setLastName(bUser.getLastName());
		userEntity.setPassword(bUser.getPassword());
		userEntity.setEmail(bUser.getEmail());
		userEntity.setRevision(1);
		return userEntity;
	}

	public static GroupEntity toActivitiGroup(
			com.birdex.activiti.dao.entity.Group bGroup) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setRevision(1);
		groupEntity.setType("assignment");

		groupEntity.setId(bGroup.getId());
		groupEntity.setName(bGroup.getName());
		return groupEntity;
	}

	public static List<org.activiti.engine.identity.Group> toActivitiGroups(
			List<com.birdex.activiti.dao.entity.Group> bGroups) {
		List<org.activiti.engine.identity.Group> groupEntitys = new ArrayList<org.activiti.engine.identity.Group>();
		for (com.birdex.activiti.dao.entity.Group bGroup : bGroups) {
			GroupEntity groupEntity = toActivitiGroup(bGroup);
			groupEntitys.add(groupEntity);
		}
		return groupEntitys;
	}
}
