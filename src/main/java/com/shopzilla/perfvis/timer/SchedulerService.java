package com.shopzilla.perfvis.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	@Autowired
	@Qualifier("syncWorker")
	private PerfDataCollector perfDataCollector;

	//@Scheduled(fixedDelay=5000)	
	public void doSchedule() {
		  //logger.debug("Start schedule");
		   
		  
		   perfDataCollector.work();
		   
		   
		  //logger.debug("End schedule");
		 }

}
