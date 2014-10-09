package demo.mvc;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pmis.webflow.mvc.support.TaskHelper;
import pmis.webflow.mvc.support.WebFlowHelperHolder;
import pmis.webflow.mvc.support.WebFlowParam;

@Controller
public class MyController
{
	@Resource(name = "processEngine")
	private ProcessEngine _processEngine;

	@RequestMapping("/doCompleteAdjustTask.action")
	public String doCompleteAdjustTask(@WebFlowParam
	WebFlowHelperHolder holder, @RequestParam
	Map<String, Object> formValues, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		TaskHelper helper = holder.getTaskHelper();
		model.put("task", helper.getTask());
		model.put("process", helper.getProcessInstance());

		helper.completeTask(formValues);
		Logger.getLogger(this.getClass()).info("...........doCompleteAdjustTask...........");

		return "/doCompleteTask";
	}

	@RequestMapping("/doLogin.action")
	public String doLogin(String R1)
	{
		String userId = R1;//SecurityContextHolder.getContext().getAuthentication().getName();
		SecurityContextHolder.getContext().setAuthentication(
			new PreAuthenticatedAuthenticationToken(new MyUserDetails(userId, userId, true), null));
		return "redirect:/";
	}
}
