package com.birdex.activiti.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
@Service  
public class CustomGroupEntityManager extends GroupEntityManager {  
    private static final Log logger = LogFactory.getLog(CustomGroupEntityManager.class);  
  
    @Autowired  
    private AccountManager accountManager;  
  
    //@Override  
    public GroupEntity findGroupById(final String groupCode) {  
        if (groupCode == null)  
            return null;  
            try {  
            	com.birdex.activiti.dao.entity.Group bGroup = accountManager.getGroupByGroupId(groupCode);  
                GroupEntity e = new GroupEntity();  
                e.setRevision(1);  
                // activiti有3种预定义的组类型：security-role、assignment、user  
                // 如果使用Activiti  
                // Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务  
                e.setType("assignment");  
                e.setId(bGroup.getId());  
                e.setName(bGroup.getName());  
                return e;  
            } catch (EmptyResultDataAccessException e) {  
                return null;  
            }  
              
    }  
  
    @Override  
    public List<Group> findGroupsByUser(final String userCode) {  
        if (userCode == null)  
            return null;  
  
        List<com.birdex.activiti.dao.entity.Group> bGroupList = accountManager.getUser(Long.valueOf(userCode)).getActIdGroups();  
          
        List<Group> gs = new ArrayList<Group>();  
        GroupEntity g;  
        for (com.birdex.activiti.dao.entity.Group bGroup : bGroupList) {  
            g = new GroupEntity();  
            g.setRevision(1);  
            g.setType("assignment");  
  
            g.setId(bGroup.getId());  
            g.setName(bGroup.getName());  
            gs.add(g);  
        }  
        return gs;  
    }  
  
    @Override  
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  
