package com.dnl.pages.common;
import static com.dnl.globals.Constants.RUNCONTEXT_MOBILE;
import static com.dnl.Logger.GSLogger.gsLogger;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.WebElement;

import com.dnl.automationengine.WebRobot;

public class Page {
	
	protected static WebRobot robot=null;
	protected static HashMap<String,Object>guiMap=null;
	protected static String currentPage=null;

public Page(){
	HashMap<String,String>pageMap=null;	
	Properties p;

	if(robot==null)
		robot=WebRobot.getWebRobot();

	String page=this.getClass().getSimpleName();

	if(guiMap==null){
		guiMap=new HashMap<String, Object>();
	}

	//If GUI map for this page is not loaded then load it
	if(guiMap.get(page)==null){
		pageMap=new HashMap<String,String>();

		//load the Global GUI Map
		p=loadGUIMap("Global");

		if(p!=null)
			pageMap.putAll((Map)p);

		//load the Page specific GUI Map
		//Any objects present in Global GUI map will be overwritten by Page specific GUI map if found
		//String className=this.getClass().getName();

		p=loadGUIMap(this.getClass().getSimpleName());

		if(p!=null)
			pageMap.putAll((Map)p);

		//Load any mobile changes if running in mobile context
		//Any object present in Page specific GUI map will be overwritten by Mobile specific object
		
		if(robot.getContext().equalsIgnoreCase(RUNCONTEXT_MOBILE)){
			p=loadMobileGUIMap(this.getClass().getSimpleName());

			if(p!=null)
				pageMap.putAll((Map)p);
		}
		

		//Add the map for this page in GUI map
		guiMap.put(page, pageMap);
		
	}
	setCurrentPage(this.getClass().getName());
}

private void setCurrentPage(String pageName) {
	currentPage=pageName;
}

public static String getCurrentPage(){
	return currentPage;
}

public String resolveObject(String locator){
	HashMap<String,String>pageMap=null;
	String keyword = "";
	if(locator.contains("-"))
	{
		keyword =locator.split("-")[1];
		locator = locator.split("-")[0];
	}
	String page=this.getClass().getSimpleName();
	
	//Get the map for this page
	pageMap=(HashMap<String, String>) guiMap.get(page);

	if(pageMap== null)
		return locator;

	String object=pageMap.get(locator);

	//if object is not found return the locator passed in
	if(object==null)
		return locator;
	if(keyword != "")
	{
		object = object.replace("keyword", keyword);
	}
	
	return object;
}

private Properties loadGUIMap(String mapName){
	//String className=this.getClass().getName();
	//className=className.substring(className.lastIndexOf('.')+1,className.length());
	try{
		Properties p=new Properties();

		p.load(new FileInputStream(System.getProperty("user.dir")+"/resource/object/"+mapName+".properties"));

		//tempGUIMap=new HashMap<String,String>((Map)p);

		return p;
	}catch(Exception e){
		//For now just print stack trace
		gsLogger.writeERROR("Exception occured-> Page -> loadGUIMap() -> ", e);
		//e.printStackTrace();
	}
	return null;
}

private Properties loadMobileGUIMap(String mapName){
	//String className=this.getClass().getName();
	//className=className.substring(className.lastIndexOf('.')+1,className.length());
	try{
		Properties p=new Properties();

		p.load(new FileInputStream(System.getProperty("user.dir")+"/resource/object/mobile/"+mapName+".properties"));

		//tempGUIMap=new HashMap<String,String>((Map)p);

		return p;
	}catch(Exception e){
		//For now just print stack trace
		gsLogger.writeERROR("Exception occured-> Page -> loadMobileGUIMap() -> ", e);
		//e.printStackTrace();
	}
	return null;
}

public void sleep(int time){
	try {
		Thread.sleep(time);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block

		e.printStackTrace();
	}
}

public void returnToPreviouspage(){
	try{
		robot.navigateToBack();
	}catch (Exception e){
		e.printStackTrace();
	}

	}


public void navigateToGNBLinks(String gnbLinkName) 
{
	
	//robot.click(gnbLinkName);
	//robot.pageLoadTimeout(5000);
	
}

/*
 *Load Denovo lab url 
 * 
 */
public void navigateToLoginDenovoLabUrl()
{
	robot.maximizeWindow();
	robot.navigate(resolveObject("DenovoLabUrl"));
	robot.implicitWait(5);
}


public String getCurrentUrl() {
	// TODO Auto-generated method stub
	return "";//robot.getCurrentURL();
}

public String getCurrentPageTitle() {
	// TODO Auto-generated method stub
	return robot.getCurrentpageTitle();
}

public void navigateToHomePage()
{
	robot.waitForElement("lnk_FrenchPlace");
	robot.click("lnk_FrenchPlace");
}

public int xpathCount(String locator)
{
	return robot.getXpathCount(locator);
}

public void navigateToFrenchStoryPage(String locator) {
	// TODO Auto-generated method stub
	
	robot.waitForElement(locator);
	robot.click(locator);
}

public void navigateToSalePage(String locator) {
	// TODO Auto-generated method stub
	
	robot.waitForElement(locator);
	robot.click(locator);
}
/*public void logOut() {
	// TODO Auto-generated method stub
	robot.click("Logoutlink");
	
}*/


public Set<String> getCurrentWindowHandles()
{
return robot.getCurrentWindowHandles();	
}
public void navigateToFrame(int frameIndex)
{
robot.switchToFrame(frameIndex);
}
public void navigateToFrame(String frameName)
{
robot.switchToFrame(frameName);
}
public void navigateToFrame(WebElement element)
{
robot.switchToFrame(element);
}
public void closeBrowser()
{
robot.closeDriver();
}

}
