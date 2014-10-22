package demo.ioc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import demo.identity.MyUserManager;

public class PrepareTestUserRecords implements InitializingBean
{
	private DataSource _dataSource;

	List<String> _dirtyBussinessTableNames = new ArrayList<String>();

	@Autowired
	private ProcessEngineTool _processEngineEx;

	MyUserManager _userManager;

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

	public DataSource getDataSource()
	{
		return _dataSource;
	}

	public List<String> getDirtyBussinessTableNames()
	{
		return _dirtyBussinessTableNames;
	}

	public MyUserManager getUserManager()
	{
		return _userManager;
	}

	public void setDataSource(DataSource dataSource)
	{
		_dataSource = dataSource;
	}

	public void setDirtyBussinessTableNames(List<String> dirtyBussinessTableNames)
	{
		_dirtyBussinessTableNames = dirtyBussinessTableNames;
	}

	public void setUserManager(MyUserManager userManager)
	{
		_userManager = userManager;
	}
}
