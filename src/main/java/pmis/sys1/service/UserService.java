package pmis.sys1.service;

import java.util.List;

import pmis.sys1.entity.User;

public interface UserService
{

	List<User> getUsers() throws Exception;

}
