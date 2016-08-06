package com.dnl.util;

import static com.dnl.util.Constants.FRAMEWORK_CONFIG_FILE;
import static com.dnl.util.Constants.BROWSER_FF;
import static com.dnl.util.Constants.BROWSER_CHROME;
import static com.dnl.util.Constants.BROWSER_IE;
import static com.dnl.util.Constants.PLATFORM_WINDOWS;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FrameWorkConfig {
	
	public static HashMap<String, String> configMap = null;
	
	public static void readConfig() {
		Properties p = null;
		
		try{
			if (configMap == null) {
				configMap = new HashMap<String, String>();
				p = loadConfig(System.getProperty("user.dir")+"/resource/config/"+FRAMEWORK_CONFIG_FILE);
				if (p != null)
					configMap.putAll((Map) p);
			}
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	private static Properties loadConfig(String fileName){
		//String className=this.getClass().getName();
		//className=className.substring(className.lastIndexOf('.')+1,className.length());
		try{
			Properties p=new Properties();
			
			p.load(new FileInputStream(fileName));
			
			//tempGUIMap=new HashMap<String,String>((Map)p);
			
			return p;
		}catch(Exception e){
			e.getStackTrace();
		}
		return null;
	}
	
	public static String getBrowser(){
		String browser=configMap.get("browserName");
		//String browser="GC";
		if(browser==null)
			return BROWSER_IE; //default to IE
		
		if (browser.equalsIgnoreCase(BROWSER_FF)){
			return BROWSER_FF;
		}
		else if (browser.equalsIgnoreCase(BROWSER_CHROME)){
			return BROWSER_CHROME;
		}else {
			return BROWSER_IE;
		}
	}
	
	public static String getPlatform(){
		/*String platform=configMap.get("platform");
		//return platform;
		if(platform==null){
			return PLATFORM_WINDOWS; //default to Windows	
		}*/
		return PLATFORM_WINDOWS; //default to Windows
		
	}
	
	public static int getBrowserTimeOut() {
		String timeout;
		
		try{
			timeout=configMap.get("browserTimeOut");
			if(timeout==null)
				timeout="10"; //default to 10 seconds
		}catch(Exception e){
			timeout="10"; //default to 10 seconds
			
			e.getStackTrace();
		}
		return new Integer(timeout);
	}
	
}
