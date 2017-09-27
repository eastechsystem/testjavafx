package com.sonartrading.fxclient.timerservice;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sonartrading.fxclient.application.Main;
import com.sonartrading.fxclient.view.TableDataView.TableViewDataUpdate;

public class TradeTimerService extends TimerTask {

	private static TradeTimerService instance;
	
	/**
	 * private constructor for making this class singleton
	 */
	private TradeTimerService() {
	}
	
	/**
	 * The getInstance() method use to get getInstance instance. Create new
	 * instance if existing is NULL otherwise return same instance.
	 * 
	 * @return instance of TradeTimerService
	 */
	public static TradeTimerService getInstance() {
		if (instance == null) {
			instance = new TradeTimerService();
		}
		return instance;
	}

	@Override
	public void run() {
		if (Boolean.TRUE.equals(Main.APPLICAITON_STAGE_STOPED)) {
			System.exit(0);
		}
		
		System.out.println("DateTime: " + new Date() + " ==> Execute"+ "OrderBookTimerService.scheduleJobForTrade()");
		TableViewDataUpdate.updateTableViewForTrade();
	}
	
	public void scheduleJobForTrade(long delayInMilliSeconds, long periodInMilliSeconds){
		TradeTimerService threadRunner = new TradeTimerService();
		Timer timer  = new Timer();
		timer.scheduleAtFixedRate(threadRunner, delayInMilliSeconds, periodInMilliSeconds);
	}
}
