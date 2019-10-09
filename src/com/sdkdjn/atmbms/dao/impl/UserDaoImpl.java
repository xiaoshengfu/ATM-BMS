package com.sdkdjn.atmbms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sdkdjn.atmbms.connection.ManagerThreadLocal;
import com.sdkdjn.atmbms.dao.UserDao;
import com.sdkdjn.atmbms.po.User;
import com.sdkdjn.atmbms.utils.DBUtils;

public class UserDaoImpl implements UserDao {

	public int insertUser(User user) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ManagerThreadLocal.getConnection();
		ps = conn.prepareStatement("INSERT INTO user(uId,name,password,remainingSum) VALUES(?,?,?,?)");
		ps.setString(1, user.getuId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		ps.setDouble(4, user.getRemainingSum());
		int i = ps.executeUpdate();
		DBUtils.closeAll(null, ps);
		return i;
	}

	public int updatePassWord(User user,String password) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ManagerThreadLocal.getConnection();
		ps = conn.prepareStatement("UPDATE USER SET PASSWORD=? WHERE uId=?");
		ps.setString(1, password);
		ps.setString(2, user.getuId());
		int i = ps.executeUpdate();
		DBUtils.closeAll(null, ps);
		return i;
	}

	public User selectUserById(String uId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User u = null;
		conn = ManagerThreadLocal.getConnection();
		ps = conn.prepareStatement("SELECT * FROM USER WHERE uId=?");
		ps.setString(1, uId);
		rs = ps.executeQuery();
		if (rs.next()) {
			u = new User();
			u.setuId(rs.getString(1));
			u.setName(rs.getString(2));
			u.setRemainingSum(rs.getDouble(4));
		}
		DBUtils.closeAll(rs, ps);
		return u;
	}

	public User login(User user) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User u = null;
		conn = ManagerThreadLocal.getConnection();
		ps = conn
				.prepareStatement("SELECT * FROM USER WHERE uId=? AND PASSWORD=?");
		ps.setString(1, user.getuId());
		ps.setString(2, user.getPassword());
		rs = ps.executeQuery();
		if (rs.next()) {
			u = new User();
			u.setuId(rs.getString(1));
			u.setName(rs.getString(2));
		}
		DBUtils.closeAll(rs, ps);
		return u;
	}

	@Override
	public int updateRemainingSum(User user, double money) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ManagerThreadLocal.getConnection();
		ps = conn
				.prepareStatement("UPDATE USER SET remainingSum=remainingSum+? WHERE uId=?");
		ps.setDouble(1, money);
		ps.setString(2, user.getuId());
		int i = ps.executeUpdate();
		DBUtils.closeAll(null, ps);
		return i;

	}

	@Override
	public double findTotalWithdrawMoneyByDate(Date date, User user, String businessType) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double totalMoney = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formateDate = dateFormat.format(date) + "%";
		conn = ManagerThreadLocal.getConnection();
		ps = conn
				.prepareStatement("SELECT SUM(operationAmount) AS totalOperationAmount FROM trading_record WHERE uId=? AND businessType=? AND operationData LIKE ?");
		ps.setString(1, user.getuId());
		ps.setString(2, businessType);
		ps.setString(3, formateDate);
		rs = ps.executeQuery();
		if(rs.next()){
			totalMoney = rs.getDouble("totalOperationAmount");
		}
		DBUtils.closeAll(rs, ps);
		return totalMoney;
	}

}
