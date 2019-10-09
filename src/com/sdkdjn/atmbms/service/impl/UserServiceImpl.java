package com.sdkdjn.atmbms.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.sdkdjn.atmbms.connection.ManagerThreadLocal;
import com.sdkdjn.atmbms.dao.TradingRecordDao;
import com.sdkdjn.atmbms.dao.UserDao;
import com.sdkdjn.atmbms.dao.impl.TradingRecordDaoImpl;
import com.sdkdjn.atmbms.dao.impl.UserDaoImpl;
import com.sdkdjn.atmbms.po.TradingRecord;
import com.sdkdjn.atmbms.po.User;
import com.sdkdjn.atmbms.service.UserService;
import com.sdkdjn.atmbms.utils.MyStringUtils;

public class UserServiceImpl implements UserService {
	private static UserDao ud = new UserDaoImpl();
	private static TradingRecordDao tr = new TradingRecordDaoImpl();

	@Override
	public int register(User user) {
		try {
			ManagerThreadLocal.startTransacation();
			user.setuId(MyStringUtils.getNumberID());
			user.setPassword(MyStringUtils.getMD5Value(user.getPassword()));
			int i = ud.insertUser(user);
			ManagerThreadLocal.commit();
			return i;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return -1;
	}

	@Override
	public User login(User user) {
		try {
			ManagerThreadLocal.startTransacation();
			ManagerThreadLocal.startTransacation();
			user.setPassword(MyStringUtils.getMD5Value(user.getPassword()));
			User u = ud.login(user);
			ManagerThreadLocal.commit();
			return u;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return null;
	}

	@Override
	public int changePassword(User user, String password) {
		try {
			ManagerThreadLocal.startTransacation();
			password = MyStringUtils.getMD5Value(password);
			int i = ud.updatePassWord(user, password);
			ManagerThreadLocal.commit();
			return i;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return -1;
	}

	@Override
	public User findUserByuId(String uId) {
		try {
			ManagerThreadLocal.startTransacation();
			User u = ud.selectUserById(uId);
			ManagerThreadLocal.commit();
			return u;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return null;
	}

	@Override
	public int depositMoney(User user, double money) {

		try {
			ManagerThreadLocal.startTransacation();
			TradingRecord tradingRecord = new TradingRecord();
			int i = ud.updateRemainingSum(user, money);
			int z = 0;
			if (i > 0) {
				tradingRecord.settId(MyStringUtils.getUUID());
				tradingRecord.setuId(user.getuId());
				tradingRecord.setRelateuId(user.getuId());
				tradingRecord.setOperationData(new Timestamp(System
						.currentTimeMillis()));
				tradingRecord.setBusinessType("存款");
				tradingRecord.setOperationAmount(money);
				z = tr.insertTradingRecord(tradingRecord);
			}
			ManagerThreadLocal.commit();
			return i > 0 && z > 0 ? 1 : 0;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return -1;
	}

	@Override
	public int withdrawMoney(User user, double money) {

		try {
			ManagerThreadLocal.startTransacation();
			TradingRecord tradingRecord = new TradingRecord();
			int i = ud.updateRemainingSum(user, -money);
			int z = 0;
			if (i > 0) {
				tradingRecord.settId(MyStringUtils.getUUID());
				tradingRecord.setuId(user.getuId());
				tradingRecord.setRelateuId(user.getuId());
				tradingRecord.setOperationData(new Timestamp(System
						.currentTimeMillis()));
				tradingRecord.setBusinessType("取款");
				tradingRecord.setOperationAmount(money);
				z = tr.insertTradingRecord(tradingRecord);
			}
			ManagerThreadLocal.commit();
			return i > 0 && z > 0 ? 1 : 0;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return -1;
	}

	@Override
	public boolean isWithdrawals(User user, double money) {
		try {
			ManagerThreadLocal.startTransacation();
			double totalMoney = ud.findTotalWithdrawMoneyByDate(new Date(), user, "取款");
			ManagerThreadLocal.commit();
			if((totalMoney+money)>5000){
				return false;
			}
			return true;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int transferAccounts(User user, String ruId, double operationAmount) {
		try {
			ManagerThreadLocal.startTransacation();
			User u = new User();
			u.setuId(ruId);
			TradingRecord tradingRecord = new TradingRecord();
			int i = ud.updateRemainingSum(user, -operationAmount);
			int j = ud.updateRemainingSum(u, operationAmount);
			int z = 0;
			if (i > 0 && j > 0) {
				tradingRecord.settId(MyStringUtils.getUUID());
				tradingRecord.setuId(user.getuId());
				tradingRecord.setRelateuId(ruId);
				tradingRecord.setOperationData(new Timestamp(System.currentTimeMillis()));
				tradingRecord.setBusinessType("转账");
				tradingRecord.setOperationAmount(operationAmount);
				z = tr.insertTradingRecord(tradingRecord);
			}
			ManagerThreadLocal.commit();
			return i > 0 && j > 0 && z > 0 ? 1 : 0;
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			ManagerThreadLocal.close();
		}
		return -1;
	}

}
