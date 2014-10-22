package pmis.sys1.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import pmis.sys1.service.UserService;
import pmis.web.support.controller.AbstractController;

@Controller
public class UserController extends AbstractController
{
	@Resource(name = "userService")
	private UserService _userService;

	// 日志
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/listUsers.action")
	public String listUsers(ModelMap map) throws Exception
	{
		map.put("users", _userService.getUsers());
		return "/listUsers";
	}

}
