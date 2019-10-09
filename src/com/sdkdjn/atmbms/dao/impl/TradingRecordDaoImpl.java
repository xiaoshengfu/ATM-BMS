package com.sdkdjn.atmbms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sdkdjn.atmbms.connection.ManagerThreadLocal;
import com.sdkdjn.atmbms.dao.TradingRecordDao;
import com.sdkdjn.atmbms.po.TradingRecord;
import com.sdkdjn.atmbms.po.User;
import com.sdkdjn.atmbms.utils.DBUtils;

public class TradingRecordDaoImpl implements TradingRecordDao {

	public List<TradingRecord> selectTradingRecordByUserId(User user)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TradingRecord tr = null;
		List<TradingRecord> list = new ArrayList<TradingRecord>();
		conn = ManagerThreadLocal.getConnection();
		ps = conn.prepareStatement("SELECT * FROM trading_record WHERE uid=? OR relateuId=?");
		ps.setString(1, user.getuId());
		ps.setString(2, user.getuId());
		rs = ps.executeQuery();
		while (rs.next()) {
			tr = new TradingRecord();
			tr.settId(rs.getString(1));
			tr.setuId(rs.getString(2));
			tr.setRelateuId(rs.getString(3));
			tr.setOperationData(rs.getTimestamp(4));
			tr.setBusinessType(rs.getString(5));
			tr.setOperationAmount(rs.getDouble(6));
			list.add(tr);
		}
		DBUtils.closeAll(rs, ps);
		return list;
	}

	@Override
	public int insertTradingRecord(TradingRecord tr) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ManagerThreadLocal.getConnection();
		ps = conn.prepareStatement("INSERT INTO trading_record(tId,uId,relateuId,operationData,businessType,operationAmount) VALUES(?,?,?,?,?,?)");
		ps.setString(1, tr.gettId());
		ps.setString(2, tr.getuId());
		ps.setString(3, tr.getRelateuId());
		ps.setTimestamp(4, tr.getOperationData());
		ps.setString(5, tr.getBusinessType());
		ps.setDouble(6, tr.getOperationAmount());
		int i = ps.executeUpdate();
		DBUtils.closeAll(null, ps);
		return i;
	}

}
