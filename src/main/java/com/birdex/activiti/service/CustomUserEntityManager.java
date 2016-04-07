package com.birdex.activiti.service;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.birdex.activiti.util.ActivitiUtils;
  
/** 
 * 自定义的Activiti用户管理器 
 *  
 * @author dragon 
 *  
 */  
  
@Service  
public class CustomUserEntityManager extends UserEntityManager {  
    private static final Log logger = LogFactory.getLog(CustomUserEntityManager.class);  
  
    @Autowired  
    private AccountManager accountManager;  
  
    @Override  
    public UserEntity findUserById(final String userCode) {  
        if (userCode == null)  
            return null;  
  
        try {  
            UserEntity userEntity = null;  
            com.birdex.activiti.dao.entity.User bUser = accountManager.getUser(Long.valueOf(userCode));  
            userEntity = ActivitiUtils.toActivitiUser(bUser);  
            return userEntity;  
        } catch (EmptyResultDataAccessException e) {  
            return null;  
        }  
    }  
  
    @Override  
    public List<Group> findGroupsByUser(final String userCode) {  
        if (userCode == null)  
            return null;  
  
            List<com.birdex.activiti.dao.entity.Group> bGroups = accountManager.getUser(Long.valueOf(userCode)).getActIdGroups(); 
              
            List<Group> gs = ActivitiUtils.toActivitiGroups(bGroups); 
            return gs;  
              
    }  
  
    @Override  
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,  
            String key) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public List<String> findUserInfoKeysByUserIdAndType(String userId,  
            String type) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findUserCountByQueryCriteria(UserQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  