package com.sonartrading.fxclient.timerservice;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sonartrading.fxclient.application.Main;
import com.sonartrading.fxclient.view.TableDataView.TableViewDataUpdate;


public class OrderBookTimerService extends TimerTask {
	
	private static OrderBookTimerService instance;
	
	/**
	 * private constructor for making this class singleton
	 */
	private OrderBookTimerService() {
	}
	
	/**
	 * The getInstance() method use to get getInstance instance. Create new
	 * instance if existing is NULL otherwise return same instance.
	 * 
	 * @return instance of OrderBookTimerService
	 */
	public static OrderBookTimerService getInstance() {
		if (instance == null) {
			instance = new OrderBookTimerService();
		}
		
		return instance;
	}

	@Override
	public void run() {
		if (Boolean.TRUE.equals(Main.APPLICAITON_STAGE_STOPED)) {
			System.exit(0);
		}
		
		System.out.println("DateTime: " + new Date() + " ==> Execute"+ "OrderBookTimerService.scheduleJobForOrderBook()");
		TableViewDataUpdate.updateTableViewForOrderBook();
	}
	
	public void scheduleJobForOrderBook(long delayInMilliSeconds, long periodInMilliSeconds){
		OrderBookTimerService threadRunner = new OrderBookTimerService();
		Timer timer  = new Timer();
		timer.scheduleAtFixedRate(threadRunner, delayInMilliSeconds, periodInMilliSeconds);
	}
}
