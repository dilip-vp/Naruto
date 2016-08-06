package com.dnl.util;

import com.dnl.automationengine.WebRobot;

public class CookieManager {
	
private static WebRobot robot=WebRobot.getWebRobot();
	
	public static void deleteAllCookies(){
		robot.deleteAllCookies();
	}
	
}
