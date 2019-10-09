package com.sdkdjn.atmbms.service;

import java.util.List;

import com.sdkdjn.atmbms.po.TradingRecord;
import com.sdkdjn.atmbms.po.User;

public interface TradingRecordService {
	/**
	 * 用户账单查询
	 * @param user
	 * @return 交易记录集合 
	 */
	public List<TradingRecord> examineTradingRecord(User user);
}
