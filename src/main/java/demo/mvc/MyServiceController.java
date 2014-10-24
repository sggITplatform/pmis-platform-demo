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
import org.openwebflow.identity.impl.InMemoryMembershipManager;
import org.openwebflow.mvc.tool.WebFlowParam;
import org.openwebflow.permission.impl.ActivityPermissionImpl;
import org.openwebflow.tool.ContextToolHolder;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.ioc.MyActivityPermissionManager;

@Controller
public class MyServiceController
{
	@Autowired
	InMemoryMembershipManager _groupViewService;

	@Autowired
	MyActivityPermissionManager _activityPermissionManager;

	@Autowired
	ProcessEngineTool _processEngineTool;

	@Autowired
	VacationRequestServiceImpl _vacationRequestService;

	@RequestMapping("/doGrantActivity.action")
	public String doGrantActivity(@WebFlowParam
	ContextToolHolder holder, String activityId, String assigneeExpression,
			@RequestParam(value = "candidateGroupIds", required = false)
			String[] candidateGroupIds, String candidateUserIdExpressions, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ProcessDefinitionEntity pde = holder.getProcessDefinitionTool().getProcessDefinitionEntity();

		if (assigneeExpression.isEmpty())
			assigneeExpression = null;

		ActivityImpl activity = (ActivityImpl) pde.findActivity(activityId);
		ActivityPermissionImpl ap = new ActivityPermissionImpl();
		ap.setProcessDefId(pde.getProcessDefinition().getId());
		ap.setActivityId(activityId);
		ap.setAssignedUser(assigneeExpression);
		ap.setGrantedGroups(StringUtils.arrayToDelimitedString(candidateGroupIds, ";"));
		ap.setGrantedUsers(candidateUserIdExpressions);

		_activityPermissionManager.save(ap);
		//同步更新活动的权限
		_processEngineTool.createActivityTool(ap.getProcessDefId(), ap.getActivityId()).grantPermission(
			ap.getAssignedUser(), ap.getGrantedGroups(), ap.getGrantedUsers());

		model.put("activity", activity);

		return "/doGrantActivity";
	}

	private String getExpressionText(Set<Expression> expressions)
	{
		List<String> names = getExpressionTextList(expressions);
		return StringUtils.arrayToDelimitedString(names.toArray(), ";");
	}

	private List<String> getExpressionTextList(Set<Expression> expressions)
	{
		List<String> names = new ArrayList<String>();
		for (Expression expr : expressions)
		{
			names.add(expr.getExpressionText());
		}
		return names;
	}

	@RequestMapping("/grantActivity.action")
	public String grantActivity(@WebFlowParam
	ContextToolHolder holder, String activityId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ProcessDefinitionEntity pde = holder.getProcessDefinitionTool().getProcessDefinitionEntity();

		ActivityImpl activity = (ActivityImpl) pde.findActivity(activityId);
		model.put("activity", activity);
		final TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activity.getActivityBehavior())
				.getTaskDefinition();
		model.put("taskDefinition", taskDefinition);
		model.put("processDefinition", pde);
		Expression assigneeExpr = taskDefinition.getAssigneeExpression();
		model.put("allGroups", _groupViewService.getGroups());
		model.put("assignee", assigneeExpr == null ? "" : assigneeExpr.getExpressionText());
		model.put("candidateGroupIds", getExpressionText(taskDefinition.getCandidateGroupIdExpressions()));
		model.put("candidateUserIds", getExpressionText(taskDefinition.getCandidateUserIdExpressions()));

		model.put("command", new Object()
		{
			public List<String> getCandidateGroupIds()
			{
				return getExpressionTextList(taskDefinition.getCandidateGroupIdExpressions());
			}
		});

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
