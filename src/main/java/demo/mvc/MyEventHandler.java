package demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import pmis.webflow.mvc.ext.DoStartProcessEventContext;
import pmis.webflow.mvc.ext.EventContextHolder;
import pmis.webflow.mvc.ext.EventHandlerClass;
import pmis.webflow.mvc.ext.EventHandlerMethod;
import pmis.webflow.mvc.ext.EventType;

@EventHandlerClass
public class MyEventHandler
{
	@EventHandlerMethod(eventType = EventType.BeforeDoStartProcess, formKey = "/startVacationRequest")
	public void sayHello(EventContextHolder holder, String processDefId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		DoStartProcessEventContext event = holder.getDoStartProcessEventContext();
		event.setBussinessKey("" + System.currentTimeMillis());
	}
}
