package demo.ioc;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProcessTool
{
	@Resource(name = "processEngine")
	private ProcessEngine _processEngine;

	public long getActiveProcessesCount()
	{
		//return _processEngine.getRuntimeService().createProcessInstanceQuery().active().count();
		return _processEngine.getHistoryService().createHistoricProcessInstanceQuery().unfinished().count();
	}

	public long getAssignedTasksCount()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngine.getTaskService().createTaskQuery().taskAssignee(userId).count();
	}

	public long getHistoricProcessesCount()
	{
		return _processEngine.getHistoryService().createHistoricProcessInstanceQuery().finished().count();
	}

	public List<ProcessDefinition> getProcessDefs()
	{
		//String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngine.getRepositoryService().createProcessDefinitionQuery().list();
	}

	public long getProcessDefsCount()
	{
		//String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngine.getRepositoryService().createProcessDefinitionQuery().count();
	}

	public long getTaskQueueCount()
	{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return _processEngine.getTaskService().createTaskQuery().taskCandidateUser(userId).count();
	}
}
