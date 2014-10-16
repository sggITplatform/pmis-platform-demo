package demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openwebflow.mvc.event.EventType;
import org.openwebflow.mvc.event.ctx.DoStartProcessEventContext;
import org.openwebflow.mvc.event.ctx.EventContextHolder;
import org.openwebflow.mvc.event.handler.EventHandlerClass;
import org.openwebflow.mvc.event.handler.EventHandlerMethod;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import demo.service.impl.VacationRequestServiceImpl;

@EventHandlerClass
@Component()
public class MyEventHandler
{
	@Autowired
	private ProcessEngineTool _processEngineEx;

	@Autowired
	VacationRequestServiceImpl _vacationRequestService;

	@EventHandlerMethod(eventType = EventType.BeforeDoStartProcess, formKey = "/startVacationRequest")
	public void beforeDoStartVacationRequest(EventContextHolder holder, String processDefId, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
		event.setBussinessKey("" + System.currentTimeMillis());
	}

	@EventHandlerMethod(eventType = EventType.AfterDoStartProcess, formKey = "/startVacationRequest")
	public void afterDoStartVacationRequest(EventContextHolder holder, String processDefId, long var_numberOfDays,
			String var_vacationMotivation, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
		_vacationRequestService.insert(Long.parseLong(event.getBussinessKey()), var_numberOfDays,
			var_vacationMotivation, event.getProcessInstance().getId());
	}
}
