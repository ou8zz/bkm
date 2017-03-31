package com.service;

import com.model.Logs;
import com.util.Paramer;

/**
 * @description 后台日志操作管理接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/07/28
 * @version 1.0
 */
public interface LogsService {
	void addLog(Logs log);
	Paramer getLogs(Paramer p);
	Logs getLog(Logs log);
}
