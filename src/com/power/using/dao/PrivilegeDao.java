package com.power.using.dao;

import java.util.List;

import com.power.using.domain.Function;
import com.power.using.domain.Role;
import com.power.using.domain.User;

public interface PrivilegeDao {

	/**
	 * 根据用户名和密码查找用户,登录后台
	 * @param username
	 * @param password
	 * @return
	 */
	User find(String username, String password);

	/**
	 * 根据用户信息,查找用户的角色
	 * @param user
	 * @return
	 */
	List<Role> findRolesByUser(User user);

	/**
	 * 根据用户的角色,查找用户有哪些功能
	 * @param role
	 * @return
	 */
	List<Function> findFunctuonsByRole(Role role);

}
