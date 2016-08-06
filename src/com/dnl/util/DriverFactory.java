package com.dnl.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;


public final class DriverFactory {
	
	private static WebDriver driver=null;
	private static WebDriverWait wait=null;
	private static int timeoutSeconds;
	
	public static String BROWSER_NAME = Constants.browser;
	public static String BROWSER_VERSION="";
	public static String PLATFORM="";
	
	
	public static WebDriver getDriver(){
		
		if(BROWSER_NAME.equals("firefox")){
			getFFDriver();
		}else if(BROWSER_NAME.equals("chrome")){
			getChromeDriver();
		}else if(BROWSER_NAME.equals("ie")){
			getIEDriver();
		}else{
			getFFDriver();
		}
		
		return driver;
	}
	
	private static WebDriver getIEDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setJavascriptEnabled(true);			
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/resource/drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);	
		
		return driver;
	}

	private static WebDriver getFFDriver(){			
		FirefoxProfile firefoxProfile =  new FirefoxProfile();
		//firefoxProfile.setPreference("network.proxy.type", 4);//Auto detect Proxy settings			
		firefoxProfile.setPreference("app.update.enabled", false); //Disable auto update
		firefoxProfile.setPreference("browser.shell.checkDefaultBrowser", false); //Disable default browser check
		firefoxProfile.setAcceptUntrustedCertificates(true); //Accept certificates
		firefoxProfile.setPreference("browser.tabs.autoHide", true); //Hide tabs
		firefoxProfile.setPreference("browser.tabs.warnOnClose", false); //Disable warning on tab open
		firefoxProfile.setPreference("browser.tabs.warnOnOpen", false); //Disable warning on tab close
		firefoxProfile.setPreference("browser.rights.3.shown", true); //Disable Know your rights Option	
		driver = new FirefoxDriver(firefoxProfile);		
		setWait(15);
		driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);	
		
		return driver;
	}
	
	
	private static WebDriver getChromeDriver(){
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/resource/driver/chromedriver.exe");
		driver=new ChromeDriver();
		setWait(15);
		driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);	
		
		return driver;
	}
	
	
	public static WebDriverWait getWait(){
		if(wait==null){
			wait=new WebDriverWait(driver, timeoutSeconds);
		}
		return wait;
	}
	
	public static void setWait(int timeoutSecs){
		timeoutSeconds=timeoutSecs;
		wait=new WebDriverWait(driver, timeoutSeconds);
	}
	
	public static void setImplicitWait(int timeoutSeconds){
		if(driver!=null)
			driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);
	}
	
	public static void closeDriver(){
		try{
			if(driver!=null)
				driver.quit();
			
			driver=null;
			wait=null;
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	
	
	
}
