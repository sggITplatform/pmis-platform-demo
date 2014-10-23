package demo.mvc;

import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workflow/")
public class WebFlowListViewController
{
	@Autowired
	private ProcessEngineTool _processEngineTool;

	@RequestMapping("listActiveProcesses.action")
	public String listActiveProcesses(ModelMap model)
	{
		model.put("processes", _processEngineTool.listActiveProcessInstances());
		model.put("listActiveProcesses", true);
		return "/listHistoricProcesses";
	}

	@RequestMapping("listAssignedTasks.action")
	public String listAssignedTasks(ModelMap model)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("tasks", _processEngineTool.listAssignedTasks(userId));
		return "/listTasks";
	}

	@RequestMapping("listHistoricActivities.action")
	public String listHistoricActivities(String processId, ModelMap model)
	{
		model.put("activities", _processEngineTool.listHistoricActivities(processId));
		return "/listHistoricActivities";
	}

	@RequestMapping("listHistoricProcesses.action")
	public String listHistoricProcesses(ModelMap model)
	{
		model.put("processes", _processEngineTool.listHistoricProcesseInstances());
		return "/listHistoricProcesses";
	}

	@RequestMapping("listMyActiveProcesses.action")
	public String listMyActiveProcesses(ModelMap model)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("processes", _processEngineTool.listActiveProcessInstances(userId));
		model.put("listActiveProcesses", true);
		return "/listHistoricProcesses";
	}

	@RequestMapping("listMyHistoricProcesses.action")
	public String listMyHistoricProcesses(ModelMap model)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("processes", _processEngineTool.listHistoricProcesseInstances(userId));
		return "/listHistoricProcesses";
	}

	@RequestMapping("listProcessVariables.action")
	public String listProcessVariables(String processId, boolean historic, ModelMap model)
	{
		if (historic)
		{
			model.put("vars", _processEngineTool.getHistoricProcessVariables(processId));
		}
		else
		{
			model.put("vars", _processEngineTool.getActiveProcessVariables(processId));
		}

		return "/listVariables";
	}

	@RequestMapping("listTaskQueue.action")
	public String listTaskQueue(ModelMap model)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("tasks", _processEngineTool.listTaskQueue(userId));
		return "/listTasks";
	}
}
