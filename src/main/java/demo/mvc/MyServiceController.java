package demo.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.openwebflow.mvc.tool.WebFlowParam;
import org.openwebflow.permission.impl.ActivityPermissionImpl;
import org.openwebflow.tool.ContextToolHolder;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.service.impl.VacationRequestServiceImpl;

@Controller
public class MyServiceController
{
	@Autowired
	ProcessEngineTool _processEngineEx;

	@Autowired
	VacationRequestServiceImpl _vacationRequestService;

	@RequestMapping("/doGrantActivity.action")
	public String doGrantActivity(@WebFlowParam
	ContextToolHolder holder, String activityId, String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProcessDefinitionEntity pde = holder.getProcessDefinitionTool().getProcessDefinitionEntity();

		if (assigneeExpression.isEmpty())
			assigneeExpression = null;

		ActivityImpl activity = (ActivityImpl) pde.findActivity(activityId);
		ActivityPermissionImpl ap = new ActivityPermissionImpl();
		ap.setProcessDefId(pde.getProcessDefinition().getId());
		ap.setActivityId(activityId);
		ap.setAssignedUser(assigneeExpression);
		ap.setGrantedGroups(candidateGroupIdExpressions);
		ap.setGrantedUsers(candidateUserIdExpressions);
		_processEngineEx.getActivityPermissionService().update(ap);

		model.put("activity", activity);

		return "/doGrantActivity";
	}

	private String getExpressionsString(Set<Expression> expressions)
	{
		List<String> groups = new ArrayList<String>();
		for (Expression expr : expressions)
		{
			groups.add(expr.getExpressionText());
		}

		return StringUtils.arrayToDelimitedString(groups.toArray(), ";");
	}

	@RequestMapping("/grantActivity.action")
	public String grantActivity(@WebFlowParam
	ContextToolHolder holder, String activityId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ProcessDefinitionEntity pde = holder.getProcessDefinitionTool().getProcessDefinitionEntity();

		ActivityImpl activity = (ActivityImpl) pde.findActivity(activityId);
		model.put("activity", activity);
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activity.getActivityBehavior()).getTaskDefinition();
		model.put("taskDefinition", taskDefinition);
		model.put("processDefinition", pde);
		Expression assigneeExpr = taskDefinition.getAssigneeExpression();
		model.put("assignee", assigneeExpr == null ? "" : assigneeExpr.getExpressionText());
		model.put("candidateGroupIds", getExpressionsString(taskDefinition.getCandidateGroupIdExpressions()));
		model.put("candidateUserIds", getExpressionsString(taskDefinition.getCandidateUserIdExpressions()));

		return "/grantActivity";
	}

	@RequestMapping("/showVacationRequestForm.action")
	public String showVacationRequestForm(String processId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		model.put("bean", _vacationRequestService.getByProcessId(processId));
		return "/showVacationRequestForm";
	}
}
