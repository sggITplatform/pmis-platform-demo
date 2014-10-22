package demo.ioc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.openwebflow.identity.MyUserManager;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class PrepareTestUserRecords implements InitializingBean
{
	@Autowired
	private ProcessEngineTool _processEngineEx;

	List<String> _dirtyBussinessTableNames = new ArrayList<String>();

	public List<String> getDirtyBussinessTableNames()
	{
		return _dirtyBussinessTableNames;
	}

	public void setDirtyBussinessTableNames(List<String> dirtyBussinessTableNames)
	{
		_dirtyBussinessTableNames = dirtyBussinessTableNames;
	}

	MyUserManager _userManager;

	public MyUserManager getUserManager()
	{
		return _userManager;
	}

	public void setUserManager(MyUserManager userManager)
	{
		_userManager = userManager;
	}

	private DataSource _dataSource;

	public DataSource getDataSource()
	{
		return _dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		_dataSource = dataSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		//写入测试数据
		_userManager.createGroup("management", "管理者");
		_userManager.createGroup("admin", "系统管理员");
		_userManager.createGroup("engineering", "工程师");

		_userManager.createMembership("kermit", "admin");
		_userManager.createMembership("kermit", "management");
		_userManager.createMembership("kermit", "engineering");
		_userManager.createMembership("fozzie", "engineering");

		//删除业务表里面的记录，因为每次根据内存表生成出来的流程会出现重复ID
		for (String tableName : _dirtyBussinessTableNames)
		{
			new JdbcTemplate(_dataSource).execute("delete from " + tableName);
		}
	}
}
