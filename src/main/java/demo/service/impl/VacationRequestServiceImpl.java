package demo.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import demo.service.VacationRequestService;

@Service
public class VacationRequestServiceImpl implements VacationRequestService
{
	@Resource(name = "bussinessDataSource")
	DataSource _bussinessDataSource;

	public Map<String, Object> getByProcessId(String processId)
	{
		return new JdbcTemplate(_bussinessDataSource).queryForMap(
			"select * from VACATION_REQUEST_TAB where PROCESSID=?", processId);
	}

	public void insert(long id, long days, String motivation, String processId)
	{
		new JdbcTemplate(_bussinessDataSource).update(
			"insert into VACATION_REQUEST_TAB (ID,DAYS,MOTIVATION,PROCESSID) values (?,?,?,?)", id, days, motivation,
			processId);
	}
}
