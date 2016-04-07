package com.birdex.activiti.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.birdex.activiti.util.UserUtil;
import com.birdex.activiti.web.model.UserDetail;

/**
 * 首页控制器
 * 
 * @author zhaoqiang
 *
 */
@Controller
@RequestMapping("/main")
public class MainController {

	@RequestMapping(value = "/index")
	public String index(HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return "redirect:/login";
		}
		return "/main/index";
	}

	@RequestMapping(value = "/welcome")
	public String welcome(HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return "redirect:/login";
		}
		return "/main/welcome";
	}

	@RequestMapping(value = "/reactjs")
	public String toReactJs() {
		return "/testreactjs/list";
	}
	
	@RequestMapping(value = "/reactjs/html")
	public String toReactJsHTML() {
		return "/testreactjs/NewFile";
	}

	@RequestMapping(value = "/angularjs")
	public String toAngularJs() {
		return "/angularjs/list";
	}

	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public @ResponseBody List<UserDetail> getUserList() {
		List<UserDetail> userList = new ArrayList<UserDetail>();
		for (int i = 0; i < 10; i++) {
			UserDetail userDetail = new UserDetail();
			userDetail.setId("id" + i);
			userDetail.setEmail("abc@abc." + i);
			userDetail.setFirstName("firstName" + i);
			userDetail.setLastName("lastName" + i);
			userList.add(userDetail);
		}
		return userList;
	}

}
