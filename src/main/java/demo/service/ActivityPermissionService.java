package demo.service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import demo.entity.ActivityPermission;

public class ActivityPermissionService
{
	@Resource(name = "bussinessDataSource")
	DataSource _bussinessDataSource;

	public ActivityPermission getByActivityId(String processDefId, String activityId)
	{
		return new JdbcTemplate(_bussinessDataSource).queryForObject(
			"select * from ACTIVITY_PERMISSION_TAB where PROCESS_DEF_ID=? and ACTIVITY_ID=?", new Object[] {
					processDefId, activityId }, new RowMapper()
			{
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return null;
				}
			});
	}

	public void insert(ActivityPermission ap)
	{
		new JdbcTemplate(_bussinessDataSource)
				.update(
					"insert into ACTIVITY_PERMISSION_TAB (PROCESS_DEF_ID,ACTIVITY_ID,ASSIGNED_USER,GRANTED_GROUPS, GRANTED_USERS,OP_TIME) values (?,?,?,?)",
					ap.getProcessDefId(), ap.getActivityId(), ap.getAssignedUser(), ap.getGrantedGroups(),
					ap.getGrantedUsers(), new Date(System.currentTimeMillis()));
	}
}
