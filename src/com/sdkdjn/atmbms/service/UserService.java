package com.sdkdjn.atmbms.service;

import com.sdkdjn.atmbms.po.User;

public interface UserService {
	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return 状态码
	 */
	public int register(User user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return 用户对象
	 */
	public User login(User user);

	/**
	 * 用户修改密码
	 * 
	 * @param user
	 * @param password
	 * @return 状态码
	 */
	public int changePassword(User user, String password);

	/**
	 * 用户转账
	 * 
	 * @param user转账用户
	 * @param ruId收款用户id
	 * @param operationAmount转账金额
	 * @return状态码
	 */
	public int transferAccounts(User user, String ruId, double operationAmount);

	/**
	 * 用户存款
	 * 
	 * @param user
	 * @param money
	 * @return 状态码
	 */
	public int depositMoney(User user, double money);

	/**
	 * 用户取款
	 * 
	 * @param user
	 * @param money
	 * @return 状态码
	 */
	public int withdrawMoney(User user, double money);

	/**
	 * 判断用户当日是否可取款
	 * 
	 * @param user
	 * @param money
	 * @return
	 */
	public boolean isWithdrawals(User user, double money);

	/**
	 * 通过id查找用户信息
	 * 
	 * @param uId
	 * @return
	 */
	public User findUserByuId(String uId);
}
