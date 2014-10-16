package demo.mvc;

import org.activiti.engine.IdentityService;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class PrepareTestUserRecords implements InitializingBean
{
	@Autowired
	private ProcessEngineTool _processEngineEx;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		IdentityService is = _processEngineEx.getProcessEngine().getIdentityService();
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
