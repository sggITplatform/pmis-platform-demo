package demo.ioc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class SpringToolBox implements ServletContextAware
{
	String _name;

	Map<String, Object> _tools = new HashMap<String, Object>();

	public String getName()
	{
		return _name;
	}

	public Map<String, Object> getTools()
	{
		return _tools;
	}

	public void setName(String name)
	{
		_name = name;
	}

	@Override
	public void setServletContext(ServletContext servletContext)
	{
		servletContext.setAttribute(_name, _tools);
	}

	public void setTools(Map<String, Object> tools)
	{
		_tools = tools;
	}
}
