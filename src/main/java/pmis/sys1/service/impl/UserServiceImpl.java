package pmis.sys1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pmis.sys1.dao.impl.UserDaoImpl;
import pmis.sys1.entity.User;
import pmis.sys1.service.UserService;
import pmis.web.support.service.impl.DaoBasedServiceImpl;

@Service("userService")
@Transactional
public class UserServiceImpl extends DaoBasedServiceImpl<User, String> implements UserService {

	@Autowired
	UserServiceImpl( UserDaoImpl userDao)
	{
		super(userDao);
	}
	
	@Override
	public List<User> getUsers() throws Exception
	{
		return super.getObjectsByEntity();
	}
}
