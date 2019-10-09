package com.sdkdjn.atmbms.dao;

import java.util.List;

import com.sdkdjn.atmbms.po.TradingRecord;
import com.sdkdjn.atmbms.po.User;

public interface TradingRecordDao {
	/**
	 * 根据用户查找账单
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<TradingRecord> selectTradingRecordByUserId(User user)
			throws Exception;

	/**
	 * 插入交易记录
	 * 
	 * @param tr
	 * @return
	 * @throws Exception
	 */
	public int insertTradingRecord(TradingRecord tr) throws Exception;
}
