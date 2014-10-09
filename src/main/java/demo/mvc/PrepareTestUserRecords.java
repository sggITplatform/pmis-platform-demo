package demo.mvc;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.InitializingBean;

public class PrepareTestUserRecords implements InitializingBean
{
	ProcessEngine _processEngine;

	public ProcessEngine getProcessEngine()
	{
		return _processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine)
	{
		_processEngine = processEngine;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		IdentityService is = _processEngine.getIdentityService();
		is.saveUser(is.newUser("kermit"));
		is.saveUser(is.newUser("fozzie"));
		is.saveGroup(is.newGroup("admin"));
		is.saveGroup(is.newGroup("management"));
		is.saveGroup(is.newGroup("engineering"));
		is.createMembership("kermit", "admin");
		is.createMembership("kermit", "management");
		is.createMembership("kermit", "engineering");
		is.createMembership("fozzie", "engineering");
	}

}
