package com.sdkdjn.atmbms.dao;

import java.util.Date;
import com.sdkdjn.atmbms.po.User;

public interface UserDao {
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int insertUser(User user) throws Exception;

	/**
	 * 通过用户ID更新密码
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int updatePassWord(User user, String password) throws Exception;

	/**
	 * 查找用户信息
	 * 
	 * @param uId
	 * @return
	 * @throws Exception
	 */
	public User selectUserById(String uId) throws Exception;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User login(User user) throws Exception;

	/**
	 * 更新用户账户余额
	 * 
	 * @param user
	 * @param money
	 * @return
	 * @throws Exception
	 */
	public int updateRemainingSum(User user, double money) throws Exception;
	
	/**
	 * 根据日期查找当天总取款
	 * @param date
	 * @param businessType
	 * @return
	 * @throws Exception
	 */
	public double findTotalWithdrawMoneyByDate(Date date, User user, String businessType) throws Exception;

}
