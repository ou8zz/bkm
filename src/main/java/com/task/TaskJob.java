package com.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description spring定时调度执行处理类，根据注解@Scheduled来定义需要定时调度的任务
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
@Component("taskJob")
public class TaskJob {
private Log log = LogFactory.getLog(TaskJob.class);
	
	
	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
    public void job() {
        try {
			
		} catch (Exception e) {
			log.error("定时执行任务错误", e);
		}
    }
}