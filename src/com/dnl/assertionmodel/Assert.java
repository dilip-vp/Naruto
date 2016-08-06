package com.dnl.assertionmodel;

import com.dnl.automationengine.WebRobot;

public class Assert {
	
	private static WebRobot robot=WebRobot.getWebRobot();

	private Assert(){
	}


	public static void assertElementPresent(String element,String passMessage,String failMessage){
		robot.assertElementPresent(element,passMessage,failMessage);
	}

	public static void assertElementEnabled(String locator,String passMessage,String failMessage){
		robot.assertElementEnabled(locator, passMessage, failMessage);
	}

	public static void assertElementDisabled(String locator,String passMessage,String failMessage){
		robot.assertElementDisabled(locator, passMessage, failMessage);
	}

	public static void assertElementNotPresent(String element,String passMessage,String failMessage){
		robot.assertElementNotPresent(element,passMessage,failMessage);
	}

	public static void assertElementValue(String locator,String element,String passMessage,String failMessage){
		robot.assertElementValue(locator,element,passMessage,failMessage);
	}

	public static void assertTextBoxValue(String locator,String element,String passMessage,String failMessage){
		robot.assertTextBoxValue(locator,element,passMessage,failMessage);
	}

	public static void assertTextPresent(String expectedValue,String passMessage,String failMessage){
		robot.assertTextPresent(expectedValue,passMessage,failMessage);
	}

	public static void assertElementVisible(String locator,String passMessage,String failMessage){
		robot.assertElementVisible(locator,passMessage,failMessage);
	}

	public static void assertElementNotVisible(String locator,String passMessage,String failMessage){
		robot.assertElementNotVisible(locator,passMessage,failMessage);
	}

	public static void assertcheck(String locator,String passMessage,String failMessage){
		robot.assertChecked(locator,passMessage,failMessage);
	}

	public static void assertNotcheck(String locator,String passMessage,String failMessage){
		robot.assertNotChecked(locator,passMessage,failMessage);
	}

	public static void assertSelect(String locator,String listDropDownOption,String passMessage,String failMessage){
		robot.assertSelectValue(locator,listDropDownOption,passMessage,failMessage);
	}

	public static void assertEquals(String Expected,String Actual,String passMessage,String failMessage){
		robot.assertEquals(Expected,Actual,passMessage,failMessage);
	}
	public static void assertEquals(Integer Expected,Integer Actual,String passMessage,String failMessage){
		robot.assertEquals(Expected,Actual,passMessage,failMessage);

	}
	public static void assertElementValueContains(String locator,String element,String passMessage,String failMessage){
		robot.assertElementValueContains(locator,element,passMessage,failMessage);
	}

	public static void assertNotEquals(Integer Expected,Integer Actual,String passMessage,String failMessage){
		robot.assertNotEquals(Expected,Actual,passMessage,failMessage); 
	}

	public static void assertGreator(Integer Expected,Integer Actual,String passMessage,String failMessage){
		robot.assertGreator(Expected, Actual, passMessage, failMessage);
	}


	public static void assertNotEquals(String Expected,String Actual,String passMessage,String failMessage){
		robot.assertNotEquals(Expected,Actual,passMessage,failMessage);
	}


	public static void assertTrue(boolean condition,String passMessage,String failmessage){
			robot.assertTrue(condition,passMessage,failmessage);
	}
	
}
