package demo.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.mvc.helper.WebFlowHelperHolder;
import org.openwebflow.mvc.helper.WebFlowParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.service.VacationRequestService;

@Controller
public class MyServiceController
{
	@Resource(name = "vacationRequestService")
	VacationRequestService _vacationRequestService;

	@RequestMapping("/showVacationRequestForm.action")
	public String showVacationRequestForm(String processId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		model.put("bean", _vacationRequestService.getByProcessId(processId));
		return "/showVacationRequestForm";
	}

	@RequestMapping("/grantActivity.action")
	public String grantActivity(@WebFlowParam
	WebFlowHelperHolder holder, String activityId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ProcessDefinitionEntity pde = holder.getProcessDefinitionHelper().getProcessDefinitionEntity();

		ActivityImpl activity = (ActivityImpl) pde.findActivity(activityId);
		model.put("activity", activity);
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activity.getActivityBehavior()).getTaskDefinition();
		model.put("taskDefinition", taskDefinition);
		model.put("processDefinition", pde);
		return "/grantActivity";
	}

	@RequestMapping("/doGrantActivity.action")
	public String doGrantActivity(@WebFlowParam
	WebFlowHelperHolder holder, String activityId, String assigneeExpression, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProcessDefinitionEntity pde = holder.getProcessDefinitionHelper().getProcessDefinitionEntity();

		ActivityImpl activity = (ActivityImpl) pde.findActivity(activityId);
		model.put("activity", activity);
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activity.getActivityBehavior()).getTaskDefinition();
		taskDefinition.setAssigneeExpression(new FixedValue(assigneeExpression));
		return "/doGrantActivity";
	}
}
