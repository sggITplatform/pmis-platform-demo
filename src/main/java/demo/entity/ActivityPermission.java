package demo.entity;

public class ActivityPermission
{
	private long _id;

	private String _processDefId;

	public String getProcessDefId()
	{
		return _processDefId;
	}

	public void setProcessDefId(String processDefId)
	{
		_processDefId = processDefId;
	}

	private String _grantedUsers;

	public long getId()
	{
		return _id;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public String getGrantedUsers()
	{
		return _grantedUsers;
	}

	public void setGrantedUsers(String grantedUsers)
	{
		_grantedUsers = grantedUsers;
	}

	public String getGrantedGroups()
	{
		return _grantedGroups;
	}

	public void setGrantedGroups(String grantedGroups)
	{
		_grantedGroups = grantedGroups;
	}

	public String getActivityId()
	{
		return _activityId;
	}

	public void setActivityId(String activityId)
	{
		_activityId = activityId;
	}

	public String getAssignedUser()
	{
		return _assignedUser;
	}

	public void setAssignedUser(String assignedUser)
	{
		_assignedUser = assignedUser;
	}

	public long getTime()
	{
		return _time;
	}

	public void setTime(long time)
	{
		_time = time;
	}

	private String _grantedGroups;

	private String _activityId;

	private String _assignedUser;

	private long _time;
}
