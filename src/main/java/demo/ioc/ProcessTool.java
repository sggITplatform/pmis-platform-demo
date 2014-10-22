package demo.ioc;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProcessTool
{
	@Autowired
	private ProcessEngineTool _processEngineEx;

	public long getActiveProcessesCount()
	{
		return _processEngineEx.getActiveProcessesCount();
	}

	public long getAssignedTasksCount()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngineEx.getAssignedTasksCount(userId);
	}

	public long getHistoricProcessesCount()
	{
		return _processEngineEx.getHistoricProcessesCount();
	}

	public long getMyActiveProcessesCount()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngineEx.getActiveProcessesCount(userId);
	}

	public long getMyHistoricProcessesCount()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngineEx.getHistoricProcessesCount(userId);
	}

	public List<ProcessDefinition> getProcessDefs()
	{
		return _processEngineEx.getProcessDefs();
	}

	public long getProcessDefsCount()
	{
		return _processEngineEx.getProcessDefsCount();
	}

	public long getTaskQueueCount()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngineEx.getTaskQueueCount(userId);
	}
}
