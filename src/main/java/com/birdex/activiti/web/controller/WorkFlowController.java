package com.birdex.activiti.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.birdex.activiti.util.UserUtil;
import com.birdex.activiti.util.Variable;

/**
 * 流程管理控制器
 * 
 * @author zhaoqiang
 *
 */
@Controller
@RequestMapping(value = "/workflow/auto")
public class WorkFlowController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected FormService formService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected HistoryService historyService;

	/**
	 * 外置form流程列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/process-list")
	public ModelAndView processDefinitionList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/process/formkey-process-list");

		/*
		 * 只读取表单：leave-formkey
		 */
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery().active().orderByDeploymentId()
				.desc();
		List<ProcessDefinition> list = query.list();

		Map<String, Object> map = new HashMap<String, Object>();
		mav.addObject("page", list);
		return mav;
	}

	/**
	 * 统一任务启动入口
	 * 
	 * @param processId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getstartinfo/{processInstanceId}")
	public ModelAndView createTask(@PathVariable String processInstanceId,
			HttpServletRequest request, HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView mav = new ModelAndView("/workflow/create");

		// 启动流程
		// 获取表单，返回到页面
		// 页面展示HTML属性
		// StartFormData startFormData =	 formService.getStartFormData(processInstanceId);
		// System.out.println("===" + startFormData.getFormProperties().toString());
		Object obj = formService.getRenderedStartForm(processInstanceId);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("formBody", obj.toString());
		ProcessDefinition processDef = repositoryService.getProcessDefinition(processInstanceId);
		mav.addObject("renderedTaskForm", obj.toString());
		mav.addObject("processInstanceId", processInstanceId);
		mav.addObject("description", processDef.getDescription());
		return mav;
	}

	/**
	 * 提交任务开始
	 * 
	 * @param processId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String start(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("/workflow/process-list");
		User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return "redirect:/login";
		}
		Map<String, Object> formProperties = new HashMap<String, Object>();

		// 从request中读取参数然后转换
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			/*
			 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
			 */
			if (StringUtils.defaultString(key).startsWith("fp_")) {
				String[] paramSplit = key.split("_");
				formProperties.put(paramSplit[1], entry.getValue()[0]);
			} else {
				formProperties.put(key, entry.getValue()[0]);
			}
		}
		String processId = formProperties.get("processInstanceId").toString();
		String processKey = processId.substring(0, processId.indexOf(":"));
		String businessKey = "11112";
		ProcessInstance processInstance = null;
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(user.getId());

			processInstance = runtimeService.startProcessInstanceByKey(
					processKey, businessKey, formProperties);
			String processInstanceId = processInstance.getId();
			// entity.setProcessInstanceId(processInstanceId);
			logger.debug(
					"start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { processKey, businessKey, processInstanceId,
							formProperties });
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

		return "redirect:/workflow/auto/list/task";
	}

	/**
	 * 查询流程定义对象
	 *
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}


	/**
	 * 读取Task的表单
	 * 
	 * @param processInstanceId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get-form/task/{taskId}")
	@ResponseBody
	public ModelAndView findTaskForm(
			@PathVariable("taskId") String taskId,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("workflow/audit");
		// 获取当前登陆人信息。
		/* User user = UserUtil.getUserFromSession(request.getSession()); */

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task == null) {
			ModelAndView mav2 = new ModelAndView("workflow/finish");
			return mav2;
		}
		String processInstanceId = task.getProcessInstanceId();
		Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
		System.out.println(renderedTaskForm.toString());
		mav.addObject("renderedTaskForm", renderedTaskForm.toString());// 整个页面，参数已经赋值
		mav.addObject("taskId", task.getId());
		mav.addObject("processInstanceId", processInstanceId);
		return mav;
	}

	/**
	 * 办理任务，提交task的并保存form
	 */
	@RequestMapping(value = "task/complete/{taskId}/{processInstanceId}")
	@SuppressWarnings("unchecked")
	public String completeTask(@PathVariable("taskId") String taskId,
			@PathVariable("processInstanceId") String processInstanceId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Map<String, String> formProperties = new HashMap<String, String>();

		// 从request中读取参数然后转换
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();

			/*
			 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
			 */
			if (StringUtils.defaultString(key).startsWith("fp_")) {
				String[] paramSplit = key.split("_");
				formProperties.put(paramSplit[1], entry.getValue()[0]);
			}
		}

		logger.debug("start form parameters: {}", formProperties);

		try {
			formService.submitTaskFormData(taskId, formProperties);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

		redirectAttributes
				.addFlashAttribute("message", "任务完成：taskId=" + taskId);
		return "redirect:/workflow/auto/list/task";
	}

	/**
	 * 查询当前自己的任务列表
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/task")
	public ModelAndView taskList(HttpSession session, HttpServletRequest request) {
		User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return new ModelAndView("redirect:/login");
		}

		// 根据当前人的ID查询
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId());
		List<Task> tasks = taskQuery.list();

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active()
					.singleResult();
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("task", task);
			ProcessDefinition processDefinition = getProcessDefinition(processInstance.getProcessDefinitionId());
			map.put("processDefinition", processDefinition);
			map.put("processInstance", processInstance);// 存入“流程实例”
			mapList.add(map);
		}

		return new ModelAndView("/workflow/taskList", "results", mapList);
	}
	
	/**
     * 签收任务 -哪个签收就后期哪个就是任务的受理人
     */
    @RequestMapping(value = "task/claim/{id}")
    public String claim(@PathVariable("id") String taskId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return "redirect:/login";
		}
        taskService.claim(taskId, user.getId());
        redirectAttributes.addFlashAttribute("message", "任务已签收");
        return "redirect:/workflow/auto/list/task";
    }
    
    /**
     * 完成任务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "complete/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String complete(@PathVariable("id") String taskId, Variable var) {
        try {
            Map<String, Object> variables = var.getVariableMap();
            taskService.complete(taskId, variables);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
    }
    
    /**
     * 用户参与的流程-已结束的流程实例
     * 
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "process-instance/finished/list")
    public ModelAndView finished(HttpServletRequest request,HttpSession session) {
    	User user = UserUtil.getUserFromSession(session);
		if (user == null || StringUtils.isBlank(user.getId())) {
			return new ModelAndView("redirect:/login");
		}
        ModelAndView mav = new ModelAndView("/workflow/finished-list");

        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().involvedUser(user.getId());
        //HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave-formkey").orderByProcessInstanceEndTime().desc().finished();
        List<HistoricProcessInstance> list = query.list();
        mav.addObject("page", list);
        return mav;
    }
}
