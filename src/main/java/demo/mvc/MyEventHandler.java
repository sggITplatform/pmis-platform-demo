package demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openwebflow.mvc.ext.DoStartProcessEventContext;
import org.openwebflow.mvc.ext.EventContextHolder;
import org.openwebflow.mvc.ext.EventHandlerClass;
import org.openwebflow.mvc.ext.EventHandlerMethod;
import org.openwebflow.mvc.ext.EventType;
import org.springframework.ui.ModelMap;


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
