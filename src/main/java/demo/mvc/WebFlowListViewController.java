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
	private ProcessEngineTool _processEngineEx;

	@RequestMapping("listActiveProcesses.action")
	public String listActiveProcesses(ModelMap model)
	{
		model.put("processes", _processEngineEx.listActiveProcessInstances());
		model.put("listActiveProcesses", true);
		return "/listHistoricProcesses";
	}

	@RequestMapping("listAssignedTasks.action")
	public String listAssignedTasks(ModelMap model)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("tasks", _processEngineEx.listAssignedTasks(userId));
		return "/listTasks";
	}

	@RequestMapping("listHistoricProcesses.action")
	public String listHistoricProcesses(ModelMap model)
	{
		model.put("processes", _processEngineEx.listHistoricProcesseInstances());
		return "/listHistoricProcesses";
	}

	@RequestMapping("listHistoricActivities.action")
	public String listHistoricActivities(String processId, ModelMap model)
	{
		model.put("activities", _processEngineEx.listHistoricActivities(processId));
		return "/listHistoricActivities";
	}

	@RequestMapping("listProcessVariables.action")
	public String listProcessVariables(String processId, boolean historic, ModelMap model)
	{
		if (historic)
		{
			model.put("vars", _processEngineEx.getHistoricProcessVariables(processId));
		}
		else
		{
			model.put("vars", _processEngineEx.getActiveProcessVariables(processId));
		}

		return "/listVariables";
	}

	@RequestMapping("listTaskQueue.action")
	public String listTaskQueue(ModelMap model)
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		model.put("tasks", _processEngineEx.listTaskQueue(userId));
		return "/listTasks";
	}
}
