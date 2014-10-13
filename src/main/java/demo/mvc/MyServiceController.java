package demo.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.service.VacationRequestService;

@Controller
public class MyServiceController
{
	@Resource(name="vacationRequestService")
	VacationRequestService _vacationRequestService;

	@RequestMapping("/showVacationRequestForm.action")
	public String showVacationRequestForm(String processId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		model.put("bean", _vacationRequestService.getByProcessId(processId));
		return "/showVacationRequestForm";
	}
}
