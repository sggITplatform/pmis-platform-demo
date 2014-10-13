package demo.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openwebflow.mvc.event.DoStartProcessEventContext;
import org.openwebflow.mvc.event.EventContextHolder;
import org.openwebflow.mvc.event.EventHandlerClass;
import org.openwebflow.mvc.event.EventHandlerMethod;
import org.openwebflow.mvc.event.EventType;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import demo.service.VacationRequestService;

@EventHandlerClass
@Component()
public class MyEventHandler
{
	@Resource(name = "vacationRequestService")
	VacationRequestService _vacationRequestService;

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
