package com.sdkdjn.atmbms.service.impl;

import java.util.List;

import com.sdkdjn.atmbms.connection.ManagerThreadLocal;
import com.sdkdjn.atmbms.dao.TradingRecordDao;
import com.sdkdjn.atmbms.dao.impl.TradingRecordDaoImpl;
import com.sdkdjn.atmbms.po.TradingRecord;
import com.sdkdjn.atmbms.po.User;
import com.sdkdjn.atmbms.service.TradingRecordService;

public class TradingRecordServiceImpl implements TradingRecordService {
	private static TradingRecordDao tr = new TradingRecordDaoImpl();

	@Override
	public List<TradingRecord> examineTradingRecord(User user) {

		try {
			return tr.selectTradingRecordByUserId(user);
		} catch (Exception e) {
			ManagerThreadLocal.rollback();
			e.printStackTrace();
		}finally{
			ManagerThreadLocal.close();
		}
		return null;
	}

}
