package com.service;

import com.model.FundInfo;
import com.model.FundManagers;
import com.util.Paramer;

/**
 * @description 基金接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/09
 * @version 1.0
 */
public interface FundsService {
	void addFundManagers(FundManagers fm);
	void editFundManagers(FundManagers fm);
	void delFundManagers(FundManagers fm);
	Paramer getFundManagers(Paramer p);
	FundManagers getFundManagers(FundManagers fm);
	
	void addFundInfo(FundInfo fi);
	void editFundInfo(FundInfo fi);
	void delFundInfo(FundInfo fi);
	Paramer getFundInfo(Paramer p);
	FundInfo getFundInfo(FundInfo fi);
}
