package com.sonartrading.fxclient.application;

import com.sonartrading.fxclient.view.TableDataView;

public class Main extends TableDataView {
	public static Boolean APPLICAITON_STAGE_STOPED = false;
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void stop(){
	    System.out.println("Application is closing");
	    APPLICAITON_STAGE_STOPED = true;
	    System.out.println(0);
	}
}
