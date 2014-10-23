package demo.ioc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class PrepareTestUserRecords implements InitializingBean
{
	private DataSource _dataSource;

	List<String> _dirtyBussinessTableNames = new ArrayList<String>();

	@Override
	public void afterPropertiesSet() throws Exception
	{
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

	public void setDataSource(DataSource dataSource)
	{
		_dataSource = dataSource;
	}

	public void setDirtyBussinessTableNames(List<String> dirtyBussinessTableNames)
	{
		_dirtyBussinessTableNames = dirtyBussinessTableNames;
	}
}
