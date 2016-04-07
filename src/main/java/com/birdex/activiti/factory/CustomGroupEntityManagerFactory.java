package com.birdex.activiti.factory;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的Activiti用户组会话工厂
 */
public class CustomGroupEntityManagerFactory implements SessionFactory {
	private GroupEntityManager groupEntityManager;  
	  
    @Autowired  
    public void setGroupEntityManager(GroupEntityManager groupEntityManager) {  
        this.groupEntityManager = groupEntityManager;  
    }  
  
    public Class<?> getSessionType() {  
        // 返回原始的GroupEntityManager类型  
        return GroupEntityManager.class;  
    }  
  
    public Session openSession() {  
        // 返回自定义的GroupEntityManager实例  
        return groupEntityManager;  
    }  
}