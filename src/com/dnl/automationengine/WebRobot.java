package com.dnl.automationengine;



import static com.dnl.util.DriverFactory.*;
import static com.dnl.report.XMLReporter.*;
import static com.dnl.result.TestCaseResult.*;
import static com.dnl.Logger.GSLogger.gsLogger;
import static com.dnl.pages.common.Page.getCurrentPage;
import static com.dnl.globals.Constants.*;
import static com.dnl.result.StepDefinitionResult.*;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils; 
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dnl.globals.TestType;
//import com.dnl.pages.LandingPage;
import com.dnl.pages.common.Page;
import com.dnl.report.XMLReporter;
import com.dnl.result.*;
import com.dnl.util.DriverFactory;
import com.dnl.util.FrameWorkConfig;
import com.dnl.automationengine.WebRobot;
import com.dnl.result.StepDefinitionResult;
import com.dnl.result.TestCaseResult;

import static com.dnl.globals.Constants.*;
import com.dnl.result.TestStepResult;

public class WebRobot {
	
	private WebDriver driver;
	private WebDriverWait wait = null;
	private TestCaseResult testcaseResult;
	private StepDefinitionResult stepDefinitionResult;
	private TestStepResult stepResult;
	private static final String SCREENSHOTS_DIR = "screenshots";
	
	private String browser = null;
	public static WebRobot robot = null;
	private static String context=null;
	
	protected WebRobot()
	{
		driver = getDriver();
		if(FrameWorkConfig.getPlatform().equals(PLATFORM_WINDOWS))
		{
			context=RUNCONTEXT_WEB;
		}
		else
		{
			context=RUNCONTEXT_MOBILE;
		}
		wait=getWait();
		testcaseResult = geTestCaseResult();
		stepDefinitionResult = geStepDefinitionResult();
	}
	
	//Not setting WebRobot to null currently
	//execution will terminate at the end of suite
	public static WebRobot getWebRobot(){
		if(robot==null){
			robot = new WebRobot();
		}
		return robot;
	}
	
	/*
	 * Method for getting "By" element with locator string
	 * id==Password
	 * partition[0]=id
	 * partition[1]="Password"
	 */
	private By GetBy(String locator)
	{
		String[] partition = locator.split("==");
		switch(partition[0])
		{
		case "Id": 
				return By.id(partition[1]);
			
		case "XPath": 
				return By.xpath(partition[1]);
			
		case "CSS": 
				return By.cssSelector(partition[1]);
		
		case "Class": 
				return By.className(partition[1]);
		
		case "Link": 
				return By.linkText(partition[1]);
		
		case "PartialLink": 
			    return By.partialLinkText(partition[1]);
			    
		case "Name":
			return By.name(partition[1]);

		default:
			return By.id(partition[1]);

		}
	}
	
	/*
	 * This method call when find element with 
	 * locator
	 * ::No need to call resolve object second time
	 */
	public WebElement getElement(String locator)
	{
				return FindElement(GetBy(locator));
			//}
			/*else {
				throw new RuntimeException();
			}*/

		/*
		catch(Exception e)
		{
			System.out.println("Cannot find element");
			return null;
		}*/
	}
	
	public WebElement FindElement(By by)
	{
		try 
		{
			if(isElementPresent(by))
			{
				return driver.findElement(by);
			}
			else
			{
				throw new RuntimeException();
			}
		}
		catch (Exception e) {
		String fileName = takeScreenShot("FindElement");
		setStepFail("FindElement",e, fileName);
		gsLogger.writeERROR(
				"Exception occured-> WebRobot -> FindElement() -> ", e);
		return null;
		} finally {
		if (TestType.getTestType().equals(FUNCTIONAL_TEST))
			testcaseResult.addTestStepResult(stepResult);
		else
			stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	public List<WebElement> getElements(String locator)
	{
		try
		{
			return FindElements(GetBy(locator));
		}catch(Exception ex)
		{
			System.out.println("Cannot find identifiers with locator : " +locator + 
					", Exception : "+ ex.getMessage());
		}
		return null;
	}
	
	private List<WebElement> FindElements(By by)
	{
		try
		{
			return driver.findElements(by);
		}catch(Exception ex)
		{
			System.out.println("Cannot find identifiers" + 
					", Exception : "+ ex.getMessage());
		}
		return null;
	}
	/*copied 
	 * 
	 */
	public String getContext()
	{
		return context;
	}

	public void navigateToBack()
	{
		driver.navigate().back();
	}
	
	public String getCurrentpageTitle()
	{
		return driver.getTitle();
	}
	public String getCurrentURL() {
		String text=null;
		try {
			text=driver.getCurrentUrl();
			implicitWait(5);
			setStepPass("getCurrentURL() passed");
		} catch (Exception e) {
			String fileName = takeScreenShot("getCurrentURL");
			setStepFail("getCurrentURL() Failed", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> getCurrentURL() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

		return text;
	}
	
	public int getXpathCount(String sXpath) {
		int iXpathCount=0;
		try {
			iXpathCount=robot.getWebElementList(sXpath).size();
			implicitWait(5);
			setStepPass("getXpathCount() passed");
		} catch (Exception e) {
			String fileName = takeScreenShot("getXpathCount");
			setStepFail("getXpathCount() Failed", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> getXpathCount() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
		return iXpathCount;
	}

	public List<WebElement> getWebElementList(String locator)

	{
		List<WebElement> list=null;

		try {

			locator = resolveGUIObject(locator);
			list = getElements(locator);

			//return driver.findElements(by.)
			if (list !=null) {
				setStepPass("getWebElementList pass ");
			} else {
				throw new RuntimeException("Locator is not returning more than one web element with locator as" +locator);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail("Locator is not returning more than one web element" +locator, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> getWebElementList() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

		// TODO Auto-generated method stub

		return  list;

	}
	
	private void setStepPass(String step) {
		stepResult = new TestStepResult();

		stepResult.setStep(step);
		stepResult.setStatus(PASS);
	}
	private void setStepFail(String step, Exception e,
			String errorScreenShotFile) {
		stepResult = new TestStepResult();

		stepResult.setStep(step);
		stepResult.setStatus(FAIL);
		stepResult.setErrMsg(e.getMessage());
		stepResult.setErrorScreenshotFile(errorScreenShotFile);

		StringWriter stacktrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stacktrace));
		stepResult.setStackTrace(stacktrace.toString());
	}
	public void implicitWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	private void setStepWarn(String step, Exception e,
			String errorScreenShotFile) {
		stepResult = new TestStepResult();

		stepResult.setStep(step);
		stepResult.setStatus(WARNING);
		stepResult.setErrMsg(e.getMessage());
		stepResult.setErrorScreenshotFile(errorScreenShotFile);

		StringWriter stacktrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stacktrace));
		stepResult.setStackTrace(stacktrace.toString());
	}

	
	private String takeScreenShot(String step) {
		try {
			String path = createScreenshotDir();

			String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(Calendar.getInstance().getTime());
			String fileName = testcaseResult.getTestcaseName() + "_" + step
					+ "_" + dateTime + ".jpg";

			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			// WebDriver augmentedDriver =new Augmenter().augment(driver);
			// File
			// screenshot=((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screenshot, new File(path + "/" + fileName));

			// return relative path to store in report
			return "../" + SCREENSHOTS_DIR + "/" + fileName;
		} catch (Exception e) {
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> takeScreenShot() -> ", e);
			return "";
		}
	}
	
	private String createScreenshotDir() {
		XMLReporter xmlReporter = getXMLReporter();
		File f = new File(xmlReporter.getReportPath() + "/" + SCREENSHOTS_DIR);

		if (!f.exists())
			f.mkdir();

		return f.getAbsolutePath();
	}
	
	private String resolveGUIObject(String locator) {
		try {
			// Page p =(Page) Class.forName(currentPage).newInstance();
			Page p = (Page) Class.forName(getCurrentPage()).newInstance();
			return p.resolveObject(locator);
		} catch (Exception e) {
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> resolveGUIObject() -> ", e);
			// return passed in locator without processing
			return locator;
		}
	}
	
	// Wait for element to be visible or timeout
		public void waitForElementVisible(String locator) {
			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.visibilityOfElementLocated(GetBy(locator)));	
				setStepPass("WaitForElementVisible(" + locator + ")");
			} catch (TimeoutException ex) {
				String fileName = takeScreenShot("WaitForElementVisible");
				setStepWarn("WaitForElementVisible(" + locator + ")", ex, fileName);
			} catch (Exception e) {
				String fileName = takeScreenShot("WaitForElementVisible");
				setStepFail("WaitForElementVisible(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> WaitForElementVisible() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Wait for element to be clickable or timeout
		public void waitForElementClickable(String locator) {
			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.elementToBeClickable(GetBy(locator)));
				setStepPass("WaitForElementClickable(" + locator + ")");
			} catch (TimeoutException ex) {
				String fileName = takeScreenShot("WaitForElementClickable");
				setStepWarn("WaitForElementClickable(" + locator + ")", ex,
						fileName);
			} catch (Exception ex) {
				String fileName = takeScreenShot("WaitForElementClickable");
				setStepFail("WaitForElementClickable(" + locator + ")", ex,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> waitForElementClickable() -> ",
						ex);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public boolean isElementPresent(String locator){
			try {
				locator = resolveGUIObject(locator);
				return isElementPresent(GetBy(locator));
			} catch (Exception ex) {
				return false;
			}
		}
		
		private boolean isElementPresent(By by)
		{
			try
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(by));
				return true;
			}
			catch (Exception ex)
			{
				return false;
			}
		}
		
		public void waitForElement(String locator) {
			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.presenceOfElementLocated(GetBy(locator)));
				setStepPass("WaitForElement(" + locator + ")");
			} catch (TimeoutException ex) {
				String fileName = takeScreenShot("WaitForElement");
				setStepWarn("WaitForElement(" + locator + ")", ex, fileName);
			} catch (Exception e) {
				String fileName = takeScreenShot("WaitForElement");
				setStepFail("WaitForElement(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> waitForElement() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void sendKeys(String locator, String text) {
			try {
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				System.out.println("web el"+e);
				e.clear();
				e.sendKeys(text);

				setStepPass("SendKeys(" + locator + "," + text + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("SendKeys");
				setStepFail("SendKeys(" + locator + "," + text + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> sendKeys() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}
		
		// This method is added for debugging purpose
		public void writePageSource() {
			gsLogger.writeINFO("PageTitle " + driver.getTitle());
			gsLogger.writeINFO("PageSource " + driver.getPageSource());
			gsLogger.writeINFO("GETTING MORE WINDOWS");
			Set<String> windows = driver.getWindowHandles();

			Iterator<String> i = windows.iterator();
			while (i.hasNext()) {
				gsLogger.writeINFO("ONE MORE WINDOW");
				String handle = i.next();

				driver.switchTo().window(handle);

				gsLogger.writeINFO("PageTitle " + driver.getTitle());
				gsLogger.writeINFO("PageSource " + driver.getPageSource());
			}
		}

		public void navigate(String url) {
			try {
				driver.get(url);
				Thread.sleep(5000);
				setStepPass("Navigate(" + url + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("Navigate");
				setStepFail("Navigate(" + url + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> navigate() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		public void click(String locator) {
			try {
				waitForElementClickable(locator);
				locator = resolveGUIObject(locator);
				
				WebElement e = getElement(locator);
				/*
				Actions builder = new Actions(driver);
				builder.moveToElement(e).click(e).perform();*/
				
				e.click();

				setStepPass("Click(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("Click");
				setStepFail("Click(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> click() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		public void submit(String locator) {
			try {
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				e.submit();

				setStepPass("Submit(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("Submit");
				setStepFail("Submit(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> submit() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void doubleClick(String locator) {
			try {
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.doubleClick(e).build().perform();

				setStepPass("DoubleClick(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("DoubleClick");
				setStepFail("DoubleClick(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> doubleClick() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Performs a context-click at the current mouse location.
		public void contextClick() {
			try {
				Actions a = new Actions(driver);
				a.contextClick().build().perform();

				setStepPass("ContextClick(At Current Mouse Location)");
			} catch (Exception e) {
				String fileName = takeScreenShot("ContextClick");
				setStepFail("ContextClick(At Current Mouse Location)", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> ContextClick() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		//performs context click at element at some locator point
		public void contextClick(String locator) {
			try {
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.contextClick(e).build().perform();

				setStepPass("ContextClick(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("Click");
				setStepFail("ContextClick(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> contextClick() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		public void mouseOver(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.moveToElement(e).build().perform();
				setStepPass("MouseOver(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("MouseOver");
				setStepFail("MouseOver(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> mouseOver() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void doubleClick() {
			try {
				Actions a = new Actions(driver);
				a.doubleClick().build().perform();

				setStepPass("DoubleClick(At Current Mouse Location)");
			} catch (Exception e) {
				String fileName = takeScreenShot("DoubleClick");
				setStepFail("DoubleClick(At Current Mouse Location)", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> doubleClick() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Performs a modifier key Down Press.
		public void keyDown() {
			try {
				Actions a = new Actions(driver);
				a.keyDown(Keys.DOWN).build().perform();

				setStepPass("KeyDown()");
			} catch (Exception e) {
				String fileName = takeScreenShot("KeyDown");
				setStepFail("KeyDown()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> keyDown() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Performs a modifier key Down press after focusing on an element.
		public void keyDown(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.keyDown(e, Keys.DOWN).build().perform();

				setStepPass("KeyDown(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("KeyDown(" + locator + ")");
				setStepFail("KeyDown(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> keyDown("
						+ locator + ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Performs a modifier key Up Press.
		public void keyUp() {
			try {
				Actions a = new Actions(driver);
				a.keyUp(Keys.UP).build().perform();

				setStepPass("KeyUP()");
			} catch (Exception e) {
				String fileName = takeScreenShot("KeyUp");
				setStepFail("KeyUp()", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> keyUp() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Performs a modifier key Up press after focusing on an element.
		public void keyUp(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.keyUp(e, Keys.UP).build().perform();

				setStepPass("KeyUp(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("KeyUp(" + locator + ")");
				setStepFail("KeyUp(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> keyUp("
						+ locator + ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Clicks (without releasing) at the current mouse location.
		public void clickAndHold() {
			try {
				Actions a = new Actions(driver);
				a.clickAndHold().build().perform();

				setStepPass("ClickAndHold()");
			} catch (Exception e) {
				String fileName = takeScreenShot("ClickAndHold");
				setStepFail("ClickAndHold()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> clickAndHold() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Clicks (without releasing) in the middle of the given element.
		public void clickAndHold(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.clickAndHold(e).build().perform();

				setStepPass("ClickAndHold(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("ClickAndHold(" + locator + ")");
				setStepFail("ClickAndHold(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> clickAndHold("
						+ locator + ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Releases the depressed left mouse button at the current mouse location.
		public void release() {
			try {
				Actions a = new Actions(driver);
				a.release().build().perform();

				setStepPass("Release()");
			} catch (Exception e) {
				String fileName = takeScreenShot("Release");
				setStepFail("Release()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> release() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Releases the depressed left mouse button, in the middle of the given
		// element.
		public void release(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.release(e).build().perform();

				setStepPass("Release(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("Release(" + locator + ")");
				setStepFail("Release(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> release("
						+ locator + ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Clicks at the current mouse location.
		public void click() {
			try {
				Actions a = new Actions(driver);
				a.click().build().perform();

				setStepPass("Click()");
			} catch (Exception e) {
				String fileName = takeScreenShot("Click");
				setStepFail("Click()", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> click() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Moves the mouse to the middle of the element.
		public void moveToElement(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.moveToElement(e).build().perform();

				setStepPass("MoveToElement(" + locator + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("MoveToElement(" + locator + ")");
				setStepFail("MoveToElement(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> moveToElement(" + locator
						+ ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Moves the mouse to an offset from the top-left corner of the element.
		public void moveToElement(String locator, int xOffset, int yOffset) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.moveToElement(e, xOffset, yOffset).build().perform();

				setStepPass("MoveToElement(" + locator + "," + xOffset + ","
						+ xOffset + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("MoveToElement(" + locator + ")");
				setStepFail("MoveToElement(" + locator + "," + xOffset + ","
						+ xOffset + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> moveToElement(" + locator
						+ ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Moves the mouse from its current position (or 0,0) by the given offset.
		public void moveByOffset(int xOffset, int yOffset) {
			try {
				Actions a = new Actions(driver);
				a.moveByOffset(xOffset, yOffset).build().perform();

				setStepPass("MoveByOffset(" + xOffset + "," + xOffset + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("MoveByOffset");
				setStepFail("MoveByOffset(" + xOffset + "," + xOffset + ")", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> moveByOffset() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// A convenience method that performs click-and-hold at the location of the
		// source element,
		// moves to the location of the target element, then releases the mouse.
		public void dragAndDrop(String sourceElement, String targetElement) {
			try {
				sourceElement = resolveGUIObject(sourceElement);
				targetElement = resolveGUIObject(targetElement);

				WebElement se = getElement(sourceElement);
				WebElement te = getElement(targetElement);
				Actions a = new Actions(driver);
				a.dragAndDrop(se, te).build().perform();

				setStepPass("DragAndDrop(" + sourceElement + "," + targetElement
						+ ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("DragAndDrop");
				setStepFail("DragAndDrop(" + sourceElement + "," + targetElement
						+ ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> dragAndDrop() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// A convenience method that performs click-and-hold at the location of the
		// source element,
		// moves by a given offset, then releases the mouse.
		public void dragAndDropBy(String locator, int xOffset, int yOffset) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.dragAndDropBy(e, xOffset, yOffset).build().perform();

				setStepPass("DragAndDropBy(" + locator + "," + xOffset + ","
						+ yOffset + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("DragAndDropBy");
				setStepFail("DragAndDropBy(" + locator + "," + xOffset + ","
						+ yOffset + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> dragAndDropBy() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Sets the value for slider
		public void setSliderValue(String locator, String scrollValue) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Actions a = new Actions(driver);
				a.clickAndHold(e).perform();
				a.moveByOffset(new Integer(scrollValue), 0);
				a.release().build().perform();

				setStepPass("SetSliderValue(" + locator + "," + scrollValue + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("SetSliderValue(" + locator + ")");
				setStepFail("SetSliderValue(" + locator + "," + scrollValue + ")",
						e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> setSliderValue(" + locator
						+ ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}


		
		// Verify alert is present
		public void verifyAlertPresent(String passMessage, String failMessage) {
			try {
				driver.switchTo().alert();
				setStepPass("VerifyAlertPresent() [" + passMessage + "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyAlertPresent");
				setStepFail("VerifyAlertPresent() [" + failMessage + "]", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyAlertPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public String getTextInAlert() {
			String text=null;
			try {
				driver.switchTo().alert();
				text=driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();
				implicitWait(5);

				setStepPass("getTextInAlert() passed");
			} catch (Exception e) {
				String fileName = takeScreenShot("getTextInAlert");
				setStepFail("getTextInAlert() Failed", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyAlertPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
			return text;
		}
		
		// verify element is present
		public void verifyElementPresent(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				getElement(locator);
				setStepPass("VerifyElementPresent(" + locator + ") [" + passMessage
						+ "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementPresent");
				setStepFail("VerifyElementPresent(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify element is enabled
		public void verifyElementEnabled(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				if (e.isEnabled())
					setStepPass("VerifyElementEnabled(" + locator + ") ["
							+ passMessage + "]");
				else
					throw new RuntimeException(failMessage);
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementEnabled");
				setStepFail("VerifyElementEnabled(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementEnabled() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify element is disabled
		public void verifyElementDisabled(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				if (e.isEnabled())
					throw new RuntimeException(failMessage);
				else
					setStepPass("VerifyElementDisabled(" + locator + ") ["
							+ passMessage + "]");

			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementDisabled");
				setStepFail("VerifyElementDisabled(" + locator + ") ["
						+ failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementDisabled() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify value of given element
		public void verifyElementValue(String locator, String expectedValue,
				String passMessage, String failMessage) {
			try {
				waitForElement(locator);
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				String actualValue = e.getText();
				if (expectedValue.trim().equals(actualValue.trim())) {
					setStepPass("VerifyElementValue(" + locator + ","
							+ expectedValue + ") [" + passMessage + "]");
				} else {
					String fileName = takeScreenShot("VerifyElementValue");
					setStepFail("VerifyElementValue(" + locator + ","
							+ expectedValue + ") [" + failMessage + "]",
							new Exception("Expected: " + expectedValue + " Got: "
									+ actualValue), fileName);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementValue");
				setStepFail("VerifyElementValue(" + locator + "," + expectedValue
						+ ") [" + failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementValue() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify text present inside an textbox
		public void verifyTextBoxValue(String locator, String expectedValue,
				String passMessage, String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				String actualValue = e.getAttribute("value");

				if (expectedValue.equals(actualValue)) {
					setStepPass("VerifyTextBoxValue(" + locator + ","
							+ expectedValue + ") [" + passMessage + "]");
				} else {
					String fileName = takeScreenShot("VerifyTextBoxValue");
					setStepFail("VerifyTextBoxValue(" + locator + ","
							+ expectedValue + ") [" + failMessage + "]",
							new Exception("Expected: " + expectedValue + " Got: "
									+ actualValue), fileName);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyTextBoxValue");
				setStepFail("VerifyTextBoxValue(" + locator + "," + expectedValue
						+ ") [" + failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyTextBoxValue() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Verify text is present on page
		public void verifyTextPresent(String expectedValue, String passMessage,
				String failMessage) {
			try {
				if (driver.getPageSource().contains(expectedValue)) {
					setStepPass("VerifyTextPresent(" + expectedValue + ") ["
							+ passMessage + "]");
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyTextPresent");
				setStepFail("VerifyTextPresent(" + expectedValue + ") ["
						+ failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyTextPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Verify alert is not present
		public void verifyAlertNotPresent(String passMessage, String failMessage) {
			try {
				driver.switchTo().alert();
				String fileName = takeScreenShot("VerifyAlertNotPresent");
				setStepFail("VerifyAlertNotPresent() [" + failMessage + "]",
						new Exception("Alert present"), fileName);
			} catch (NoAlertPresentException a) {
				setStepPass("VerifyAlertNotPresent() [" + passMessage + "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyAlertNotPresent");
				setStepFail("VerifyAlertNotPresent() [" + failMessage + "]", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyAlertNotPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify element is not present
		public void verifyElementNotPresent(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				getElement(locator);
				String fileName = takeScreenShot("VerifyElementNotPresent");
				setStepFail("VerifyElementNotPresent(" + locator + ") ["
						+ failMessage + "]", new Exception("Element " + locator
								+ " present"), fileName);
			} catch (Exception e) {
				setStepPass("VerifyElementNotPresent(" + locator + ") ["
						+ passMessage + "]");
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Verify element is visible
		public void verifyElementVisible(String locator, String passMessage,
				String failMessage) {

			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.visibilityOfElementLocated(GetBy(locator)));
				setStepPass("VerifyElementVisible(" + locator + ") [" + passMessage
						+ "]");
			} catch (TimeoutException ex) {
				String fileName = takeScreenShot("VerifyElementVisible");
				setStepWarn("VerifyElementVisible(" + locator + ")", ex, fileName);
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementVisible");
				setStepFail("VerifyElementVisible(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementVisible() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Verify element is not visible
		public void verifyElementNotVisible(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.visibilityOfElementLocated(GetBy(locator)));
				String fileName = takeScreenShot("VerifyElementNotVisible");
				setStepFail("VerifyElementNotVisible(" + locator + ") ["
						+ failMessage + "]", new Exception("Element " + locator
								+ " visible"), fileName);
			} catch (TimeoutException ex) {
				setStepPass("VerifyElementNotVisible(" + locator + ") ["
						+ passMessage + "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementNotVisible");
				setStepFail("VerifyElementNotVisible(" + locator + ") ["
						+ failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementNotVisible() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify if check box is checked
		public void verifyChecked(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				if (e.isSelected()) {
					setStepPass("VerifyChecked(" + locator + ") [" + passMessage
							+ "]");
				} else {
					throw new Exception("Element " + locator + " not checked");
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyChecked");
				setStepFail("VerifyChecked(" + locator + ") [" + failMessage + "]",
						e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyChecked() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// verify if check box is not checked
		public void verifyNotChecked(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);

				if (!e.isSelected()) {
					setStepPass("VerifyNotChecked(" + locator + ") [" + passMessage
							+ "]");
				} else {
					throw new Exception("Element " + locator + " checked");
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyNotChecked");
				setStepFail("VerifyNotChecked(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyNotChecked() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// /verify value selected in dropdown
		public void verifySelectValue(String locator, String listDropDownOption,
				String passMessage, String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Select lstDropDown = new Select(e);

				if (listDropDownOption.startsWith("label=")) {
					lstDropDown.selectByVisibleText(locator.substring(6));
					setStepPass("VerifySelectValue(" + locator + ") ["
							+ passMessage + "]");
				}

				if (listDropDownOption.startsWith("value=")) {
					lstDropDown.selectByValue(locator.substring(6));
					setStepPass("VerifySelectValue(" + locator + ") ["
							+ passMessage + "]");
				}

				if (listDropDownOption.startsWith("index=")) {
					lstDropDown.selectByIndex(new Integer(locator.substring(6)));
					setStepPass("VerifySelectValue(" + locator + ") ["
							+ passMessage + "]");
				}

				lstDropDown.selectByVisibleText(locator);
				setStepPass("VerifySelectValue(" + locator + ","
						+ listDropDownOption + ") [" + passMessage + "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("Select");
				setStepFail("VerifySelectValue(" + locator + ","
						+ listDropDownOption + ") [" + failMessage + "]", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifySelectValue("
								+ locator + ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// assert element is present
		public void assertElementPresent(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				getElement(locator);
				setStepPass("AssertElementPresent(" + locator + ") [" + passMessage
						+ "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementPresent");
				setStepFail("AssertElementPresent(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementPresent() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		public String getElementText(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				setStepPass("GetElementText(" + locator +"="+ e.getText()+ ")");
				return e.getText();
			} catch (Exception e) {
				String fileName = takeScreenShot("GetElementText");
				setStepFail("GetElementText(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> getElementText() -> ", e);
				return null;
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public String getTextBoxValue(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				setStepPass("GetTextBoxValue(" + locator + ")");
				return e.getAttribute("value");
			} catch (Exception e) {
				String fileName = takeScreenShot("GetTextBoxValue");
				setStepFail("GetTextBoxValue(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> getTextBoxValue() -> ", e);
				return null;
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// TODO- Need to test
		// Selects option in listbox
		public void select(String locator, String listDropDownOption) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Select lstDropDown = new Select(e);

				if (listDropDownOption.startsWith("label=")) {
					lstDropDown.selectByVisibleText(listDropDownOption.substring(6));
					setStepPass("Select(" + locator + ")");
				}

				if (listDropDownOption.startsWith("value=")) {
					lstDropDown.selectByValue(listDropDownOption.substring(6));
					setStepPass("Select(" + locator + ")");
				}

				if (listDropDownOption.startsWith("index=")) {
					lstDropDown.selectByIndex(new Integer(listDropDownOption.substring(6)));
					setStepPass("Select(" + locator + ")");
				}

				setStepPass("Select(" + locator + "," + listDropDownOption + ")");
			} catch (Exception e) {
				String fileName = takeScreenShot("Select");
				setStepFail("Select(" + locator + "," + listDropDownOption + ")",
						e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> select("
						+ locator + ") -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}


		// Check a toggle-button (checkbox/radio)
		public void check(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);

				if (e.isSelected()) {
					setStepPass("Check(" + locator + ")");
				}

				e.click();

				if (e.isSelected()) {
					setStepPass("Check(" + locator + ")");
				} else {
					String fileName = takeScreenShot("check");
					setStepFail("Check(" + locator + ")", new Exception("Element "
							+ locator + " not selected"), fileName);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("Check");
				setStepFail("Check(" + locator + ")", e, fileName);
				gsLogger.writeERROR("Exception occured-> WebRobot -> check() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Uncheck a toggle-button (checkbox/radio)
		public void uncheck(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);

				if (!e.isSelected()) {
					setStepPass("Uncheck(" + locator + ")");
				}

				e.click();

				if (!e.isSelected()) {
					setStepPass("Uncheck(" + locator + ")");
				} else {
					String fileName = takeScreenShot("Uncheck");
					setStepFail("Uncheck(" + locator + ")", new Exception(
							"Element " + locator + " selected"), fileName);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("Uncheck");
				setStepFail("Uncheck(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> uncheck() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Verify if toggle-button (checkbox/radio) is Checked
		public void isChecked(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);

				if (e.isSelected()) {
					setStepPass("IsChecked(" + locator + ")");
				} else {
					String fileName = takeScreenShot("IsChecked");
					setStepFail("IsChecked(" + locator + ")", new Exception(
							"Element " + locator + " not Checked"), fileName);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail("IsChecked(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> isChecked() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Verify if toggle-button (checkbox/radio) is Unchecked
		public void isUnChecked(String locator) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);

				if (!e.isSelected()) {
					setStepPass("IsUnChecked(" + locator + ")");
				} else {
					String fileName = takeScreenShot("IsUnChecked");
					setStepFail("IsChecked(" + locator + ")", new Exception(
							"Element " + locator + " is Checked"), fileName);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsUnChecked");
				setStepFail("IsUnChecked(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> isUnChecked() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Click OK on alert
		public void chooseOKOnAlert() {
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				setStepPass("ChooseOKOnAlert()");
			} catch (Exception e) {
				String fileName = takeScreenShot("ChooseOKOnAlert");
				setStepFail("ChooseOKOnAlert()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> chooseOKOnAlert() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Maximize the window
		public void maximizeWindow() {
			try {
				driver.manage().window().maximize();
				setStepPass("MaximizeWindow()");
			} catch (Exception e) {
				String fileName = takeScreenShot("MaximizeWindow");
				setStepFail("MaximizeWindow()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> maximizeWindow() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// TODO - Need to Test & Confirm
		// Switch windows
		public void switchWindow(String locator) {
			boolean windowFound = false;
			try {
				locator = resolveGUIObject(locator);

				String currentWindowHandle = driver.getWindowHandle();
				for (String handle : driver.getWindowHandles()) {
					driver.switchTo().window(handle);
					driver.getCurrentUrl().contains(locator);
					windowFound = true;
					break;
				}
				if (windowFound == true) {
					setStepPass("SwitchWindow()");
				} else {
					driver.switchTo().window(currentWindowHandle);
					String fileName = takeScreenShot("SwitchWindow");
					setStepFail("SwitchWindow(" + locator + ")", new Exception(
							"Element " + locator + " not found"), fileName);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("SwitchWindow");
				setStepFail("SwitchWindow(" + locator + ")", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> switchWindow() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// Browser close. Closes the browser instance.
		public void closeDriver() {
			try {
				driver.close();
				// driver.quit();
				//DriverFactory.closeDriver();
				setStepPass("CloseDriver()");
			} catch (Exception e) {
				String fileName = takeScreenShot("CloseDriver");
				setStepFail("CloseDriver()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> closeDriver() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		
		// assert element is enabled
		public void assertElementEnabled(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				if (e.isEnabled())
					setStepPass("AssertElementEnabled(" + locator + ") ["
							+ passMessage + "]");
				else
					throw new RuntimeException(failMessage);
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementEnabled");
				setStepFail("AssertElementEnabled(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementEnabled() -> ",
						e);

				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// assert element is disabled
		public void assertElementDisabled(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				if (e.isEnabled())
					throw new RuntimeException(failMessage);
				else
					setStepPass("AssertElementDisabled(" + locator + ") ["
							+ passMessage + "]");

			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementDisabled");
				setStepFail("AssertElementDisabled(" + locator + ") ["
						+ failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementDisabled() -> ",
						e);

				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// assert element is not present
		public void assertElementNotPresent(String locator, String passMessage,
				String failMessage) {
			boolean assertFail = false;
			try {
				locator = resolveGUIObject(locator);

				getElement(locator);
				Exception ex = new RuntimeException("Element " + locator
						+ " present");
				String fileName = takeScreenShot("AssertElementNotPresent");
				setStepFail("AssertElementNotPresent(" + locator + ") ["
						+ failMessage + "]", ex, fileName);
				assertFail = true;
				// throwing above object ex forces to handle it at compile time when
				// we throw it again from catch block
				throw new RuntimeException("Element " + locator + " present");
			} catch (Exception e) {
				if (!assertFail) {
					setStepPass("AssertElementNotPresent(" + locator + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException(e.getMessage());
				}
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// assert value of given element
		public void assertElementValue(String locator, String expectedValue,
				String passMessage, String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				String actualValue = e.getText();

				if (expectedValue.equals(actualValue)) {
					setStepPass("AssertElementValue(" + locator + ","
							+ expectedValue + ") [" + passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expectedValue
							+ " Got: " + actualValue);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementValue");
				setStepFail("AssertElementValue(" + locator + "," + expectedValue
						+ ") [" + failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementValue() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Assert TextBox Value is present
		public void assertTextBoxValue(String locator, String expectedValue,
				String passMessage, String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				String actualValue = e.getAttribute("value");

				if (expectedValue.equals(actualValue)) {
					setStepPass("AssertTextBoxValue(" + locator + ","
							+ expectedValue + ") [" + passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expectedValue
							+ " Got: " + actualValue);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertTextBoxValue");
				setStepFail("AssertTextBoxValue(" + locator + "," + expectedValue
						+ ") [" + failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertTextBoxValue() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Assert text is present on page
		public void assertTextPresent(String expectedValue, String passMessage,
				String failMessage) {
			try {
				if (driver.getPageSource().contains(expectedValue)) {
					setStepPass("AssertTextPresent(" + expectedValue + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected Value: " + expectedValue
							+ " not present");
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertTextPresent");
				setStepFail("AssertTextPresent(" + expectedValue + ") ["
						+ failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertTextPresent() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		

		// Assert element is visible
		public void assertElementVisible(String locator, String passMessage,
				String failMessage) {

			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.visibilityOfElementLocated(GetBy(locator)));
				setStepPass("AssertElementVisible(" + locator + ") [" + passMessage
						+ "]");
			} catch (TimeoutException ex) {
				String fileName = takeScreenShot("AssertElementVisible");
				setStepWarn("AssertElementVisible(" + locator + ")", ex, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementVisible() -> ",
						ex);
				// Throw again so TestNG can abort the testcase
				throw ex;
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementVisible");
				setStepFail("AssertElementVisible(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementVisible() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// Assert element is not visible
		public void assertElementNotVisible(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				wait.until(ExpectedConditions.visibilityOfElementLocated(GetBy(locator)));
				Exception ex = new RuntimeException("Element " + locator
						+ " visible");
				String fileName = takeScreenShot("AssertElementNotVisible");
				setStepFail("AssertElementNotVisible(" + locator + ") ["
						+ failMessage + "]", ex, fileName);
				// throwing above object ex forces to handle it at compile time when
				// we throw it again from catch block
				throw new RuntimeException("Element " + locator + " visible");
			} catch (TimeoutException ex) {
				String fileName = takeScreenShot("AssertElementNotVisible");
				setStepPass("AssertElementNotVisible(" + locator + ") ["
						+ passMessage + "]");
				// Throw again so TestNG can abort the testcase
				throw ex;
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementNotVisible");
				setStepFail("AssertElementNotVisible(" + locator + ") ["
						+ failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementNotVisible() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		// assert check box is checked
		public void assertChecked(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);

				if (e.isSelected()) {
					setStepPass("AssertChecked(" + locator + ") [" + passMessage
							+ "]");
				} else {
					throw new RuntimeException("Element " + locator
							+ " not checked");
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertChecked");
				setStepFail("AssertChecked(" + locator + ") [" + failMessage + "]",
						e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertcheck() -> ", e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		// assert checkbox is not checked
		public void assertNotChecked(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);

				if (!e.isSelected()) {
					setStepPass("AssertNotChecked(" + locator + ") [" + passMessage
							+ "]");
				} else {
					throw new RuntimeException("Element " + locator + " checked");
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertNotChecked");
				setStepFail("AssertNotChecked(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertNotChecked() -> ", e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void assertSelectValue(String locator, String listDropDownOption,
				String passMessage, String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				Select lstDropDown = new Select(e);

				if (listDropDownOption.startsWith("label=")) {
					lstDropDown.selectByVisibleText(locator.substring(6));
					setStepPass("AssertSelectValue(" + locator + ") ["
							+ passMessage + "]");
				}

				if (listDropDownOption.startsWith("value=")) {
					lstDropDown.selectByValue(locator.substring(6));
					setStepPass("AssertSelectValue(" + locator + ")  ["
							+ passMessage + "]");
				}

				if (listDropDownOption.startsWith("index=")) {
					lstDropDown.selectByIndex(new Integer(locator.substring(6)));
					setStepPass("AssertSelectValue(" + locator + ") ["
							+ passMessage + "]");
				}

				lstDropDown.selectByVisibleText(locator);
				setStepPass("AssertSelectValue(" + locator + ","
						+ listDropDownOption + ") [" + passMessage + "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertSelectValue");
				setStepFail("AssertSelectValue(" + locator + ","
						+ listDropDownOption + ") [" + failMessage + "]", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertSelectValue("
								+ locator + ") -> ", e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void assertEquals(String expected, String actual,
				String passMessage, String failMessage)

		{

			//driver.findElement(ExpectedConditions.visibilityOfElementLocated(""))
			// System.out.println("in robot class eqls method");

			try {

				if (expected.equals(actual)) {
					setStepPass("AssertEquals(" + expected + "," + actual + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expected + " Got: "
							+ actual);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail(failMessage, e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> asertEquals() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}

		public void assertEquals(Integer expected, Integer actual,
				String passMessage, String failMessage)

		{

			// System.out.println("in robot class eqls method");

			try {

				if (expected.equals(actual)) {
					setStepPass("AssertEquals(" + expected + "," + actual + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expected + " Got: "
							+ actual);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail(failMessage, e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> asertEquals() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}
		

		public void verifyEquals(String expected, String actual,
				String passMessage, String failMessage)

		{

			// System.out.println("in robot class eqls method");
			System.out.println(expected);
			System.out.println(actual);

			try {

				if (expected.trim().equals(actual.trim())) {
					setStepPass("VerifyEquals(" + expected + "," + actual + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expected + " Got: "
							+ actual);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail(failMessage, e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyEquals() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}

		public String getValueFromProperties(String locator) {
			try {
				// Page p =(Page) Class.forName(currentPage).newInstance();
				Page p = (Page) Class.forName(getCurrentPage()).newInstance();
				return p.resolveObject(locator);
			} catch (Exception e) {
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> resolveGUIObject() -> ", e);
				// return passed in locator without processing
				return locator;
			}
		}

		public void pageLoadTimeout(int time) {
			driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
		}

		public void verifyEquals(Boolean expected, Boolean actual,
				String passMessage, String failMessage)
		{
			try {
				if (expected.equals(actual)) {
					setStepPass("VerifyEquals(" + expected + "," + actual + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expected + " Got: "
							+ actual);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail(failMessage, e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyEquals() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

			// TODO Auto-generated method stub

		}

		public Set<String> getWindowHandles()
		{
			return driver.getWindowHandles();	
		}


		public void verifyEquals(Integer expected, Integer actual,
				String passMessage, String failMessage) 
		{

			try {

				if (expected.equals(actual)) {
					setStepPass("VerifyEquals(" + expected + "," + actual + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expected + " Got: "
							+ actual);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail(failMessage, e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyEquals() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

			// TODO Auto-generated method stub

		}


		public void acceptAlert() {

			try {
				driver.switchTo().alert();
				driver.switchTo().alert().accept();
				implicitWait(5);

				setStepPass("acceptAlert() passed");
			} catch (Exception e) {
				String fileName = takeScreenShot("getTextInAlert");
				setStepFail("getTextInAlert() Failed", e,
						fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyAlertPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}		
		}

		public String getAttrType(String Locator,String Attr)
		{
			String Loc=resolveGUIObject(Locator);
			WebElement locator=getElement(Loc);
			return locator.getAttribute(Attr);

		}
		
		public void verifyLinkpresent(String locator, String passMessage,
				String failMessage) {
			try {
				locator = resolveGUIObject(locator);
				getElement(locator);
				setStepPass("VerifyElementPresent(" + locator + ") [" + passMessage
						+ "]");
			} catch (Exception e) {
				String fileName = takeScreenShot("VerifyElementPresent");
				setStepFail("VerifyElementPresent(" + locator + ") [" + failMessage
						+ "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyElementPresent() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);

			}
		}

		public void verifyAlertAndClick(String alertPresentMsg) {
			try {
				Alert alert=driver.switchTo().alert();
				setStepPass("VerifyAlertPresent() [" + alertPresentMsg + "]");
				alert.accept();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void verifyContains(String expected, String actual,

				String passMessage, String failMessage)
		{

			try {

				if (actual.trim().contains(expected.trim())) {

					setStepPass("VerifyEquals(" + expected + "," + actual + ") ["

					+ passMessage + "]");

				} else {

					throw new RuntimeException("Expected: " + expected + " Got: "

					+ actual);

				}

			} catch (Exception e) {

				String fileName = takeScreenShot("IsChecked");

				setStepFail(failMessage, e, fileName);

				gsLogger.writeERROR(

						"Exception occured-> WebRobot -> verifyEquals() -> ", e);

			} finally {

				if (TestType.getTestType().equals(FUNCTIONAL_TEST))

					testcaseResult.addTestStepResult(stepResult);

				else

					stepDefinitionResult.addTestStepResult(stepResult);

			}

		}
		
		public void assertElementValueContains(String locator, String expectedValue,
				String passMessage, String failMessage) {
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				String actualValue = e.getText();



				if (actualValue.trim().contains(expectedValue.trim())) {
					setStepPass("AssertElementValueContains(" + locator + ","
							+ expectedValue + ") [" + passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expectedValue
							+ " Got: " + actualValue);
				}
			} catch (Exception e) {
				String fileName = takeScreenShot("AssertElementValueContains");
				setStepFail("AssertElementValueContains(" + locator + "," + expectedValue
						+ ") [" + failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertElementValueContains() -> ",
						e);
				// Throw again so TestNG can abort the testcase
				throw new RuntimeException(e.getMessage());
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void assertNotEquals(int expected, int actual,

				String passMessage, String failMessage)

		{

			//driver.findElement(ExpectedConditions.visibilityOfElementLocated(""))

			// System.out.println("in robot class eqls method");

			try {

				if (expected!=actual) {

					setStepPass("AssertNotEquals(" + expected + "," + actual + ") ["

	                                    + passMessage + "]");

				} else {

					throw new RuntimeException("Expected: " + expected + " Got: "

	                                    + actual);

				}

			} catch (Exception e) {

				String fileName = takeScreenShot("IsChecked");

				setStepFail(failMessage, e, fileName);

				gsLogger.writeERROR(

						"Exception occured-> WebRobot -> asertNotEquals() -> ", e);

			} finally {

				if (TestType.getTestType().equals(FUNCTIONAL_TEST))

					testcaseResult.addTestStepResult(stepResult);

				else

					stepDefinitionResult.addTestStepResult(stepResult);

			}

		}

		public void assertGreator(Integer expected, Integer actual,

				String passMessage, String failMessage)
		{

			try {

				if (actual>expected) {

					setStepPass("assertGreator(" + expected + "," + actual + ") ["

	                                    + passMessage + "]");

				} else {

					throw new RuntimeException("Expected: " + expected + " Got: "

	                                    + actual);

				}

			} catch (Exception e) {

				String fileName = takeScreenShot("IsChecked");

				setStepFail(failMessage, e, fileName);

				gsLogger.writeERROR(

						"Exception occured-> WebRobot -> assertGreator() -> ", e);

			} finally {

				if (TestType.getTestType().equals(FUNCTIONAL_TEST))

					testcaseResult.addTestStepResult(stepResult);

				else

					stepDefinitionResult.addTestStepResult(stepResult);

			}

		}
		public Point getlocation(String locator)
		{
			locator = resolveGUIObject(locator);
			WebElement e = getElement(locator);
			Point p = e.getLocation();
			int x=p.getX();
			int y=p.getY();
			p=new Point(x, y);
			return p;
		}

		public void assertNotEquals(String expected, String actual, String passMessage,
				String failMessage) {
			// TODO Auto-generated method stub

			try {

				if (!expected.equals(actual)) {
					setStepPass("AssertEquals(" + expected + "," + actual + ") ["
							+ passMessage + "]");
				} else {
					throw new RuntimeException("Expected: " + expected + " Got: "
							+ actual);
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail(failMessage, e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> asertEquals() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}
		
		public void verifyCurrentpageTitle(String expectedTitle,String passMessage,String failMessage)
		{
			try{
				String actualTitle = driver.getTitle();
				if(actualTitle.trim().equals(expectedTitle.trim()))
					setStepPass("verifyCurrentpageTitle[" + passMessage+ "]");
			}
			catch(Exception e){
				String fileName = takeScreenShot("getTitle");
				setStepFail("getTitle Failed", e,fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyCurrentpageTitle() -> "+failMessage,	e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}		
		}
		
		public void clickButtonThroughJavaScript(String locator)
		{


			try {

				locator = resolveGUIObject(locator);
				WebElement e = getElement(locator);
				if(driver instanceof JavascriptExecutor)
				{
					JavascriptExecutor js=(JavascriptExecutor)driver;
					js.executeScript("arguments[0].click();", e);
					setStepPass("clickButtonThroughJavaScript() passed");
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("IsChecked");
				setStepFail("clickButtonThroughJavaScript failed()", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> clickButtonThroughJavaScript() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}

		public void assertTrue(boolean condition, String passMessage) 
		{
			// TODO Auto-generated method stub


			try {

				if (condition) {
					setStepPass("assertTrue ("+condition +","+ passMessage + ")");
				} else {
					throw new RuntimeException("assertTrue ("+condition +")");
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("assertTrue");
				setStepFail("", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertTrue() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}
		
		public void verifyTextBoxMaxLength(String locator, int Expected,
				String passMessage, String failMessage) {
			// TODO Auto-generated method stub
			try {
				locator = resolveGUIObject(locator);

				WebElement e = getElement(locator);
				int ActualLength=e.getText().length();

				if (ActualLength==Expected) {
					setStepPass("verifyTextBoxMaxLength(" + locator + ","
							+ Expected + ") [" + passMessage + "]");
				} 

				else {
					String fileName = takeScreenShot("verifyTextBoxMaxLength");
					setStepFail("verifyTextBoxMaxLength(" + locator + ","
							+ Expected + ") [" + failMessage + "]",
							new Exception("Expected: " + Expected + " Got: "
									+ ActualLength), fileName);
				}

			} 
			catch (Exception e) {
				String fileName = takeScreenShot("verifyTextBoxMaxLength");
				setStepFail("verifyTextBoxMaxLength(" + locator + "," + Expected
						+ ") [" + failMessage + "]", e, fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> verifyTextBoxMaxLength() -> ",
						e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}		
		}

		public void assertTrue(boolean condition, String passMessage, String failMessage) 
		{
			// TODO Auto-generated method stub
			/*
			 * 
			 * 
			 String fileName = takeScreenShot("verifyTextBoxMaxLength");
					setStepFail("verifyTextBoxMaxLength(" + locator + ","
							+ Expected + ") [" + failMessage + "]",
							new Exception("Expected: " + Expected + " Got: "
									+ ActualLength), fileName);
			 */

			try {

				if (condition) {
					setStepPass("assertTrue ("+condition +","+ passMessage + ")");
				} else {
					throw new RuntimeException("assertTrue ("+condition +")");
				}

			} catch (Exception e) {
				String fileName = takeScreenShot("assertTrue");
				setStepFail("assertTrue() failed : "+failMessage+"",e,fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> assertTrue() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}
		}

		public void executeJavaScript(String javaScript) {
			// TODO Auto-generated method stub
		
			
			try{
				
				JavascriptExecutor Js=(JavascriptExecutor)driver;
				Js.executeScript(javaScript);
				}
				
			 catch (Exception e) {
				String fileName = takeScreenShot("executeJavaScript");
				setStepFail("executeJavaScript() failed :",e,fileName);
				gsLogger.writeERROR(
						"Exception occured-> WebRobot -> executeJavaScript() -> ", e);
			} finally {
				if (TestType.getTestType().equals(FUNCTIONAL_TEST))
					testcaseResult.addTestStepResult(stepResult);
				else
					stepDefinitionResult.addTestStepResult(stepResult);
			}

		}

		

		
	public Set<String> getCurrentWindowHandles()
	{
		return driver.getWindowHandles();
	}

	public void switchToFrame(int frameIndex) {
		// TODO Auto-generated method stub
		try{
			
		driver.switchTo().frame(frameIndex);
		}
		
	 catch (NoSuchFrameException e) {
		String fileName = takeScreenShot("switchToFrame");
		setStepFail("switchToFrame() failed :",e,fileName);
		gsLogger.writeERROR(
				"Exception occured-> WebRobot -> switchToFrame() -> ", e);
	} finally {
		if (TestType.getTestType().equals(FUNCTIONAL_TEST))
			testcaseResult.addTestStepResult(stepResult);
		else
			stepDefinitionResult.addTestStepResult(stepResult);
	}
	}

	public void switchToFrame(String frameName) {
		// TODO Auto-generated method stub
		try{
			
		driver.switchTo().frame(frameName);
		}
		
	 catch (NoSuchFrameException e) {
		String fileName = takeScreenShot("switchToFrame");
		setStepFail("switchToFrame() failed :",e,fileName);
		gsLogger.writeERROR(
				"Exception occured-> WebRobot -> switchToFrame() -> ", e);
	} finally {
		if (TestType.getTestType().equals(FUNCTIONAL_TEST))
			testcaseResult.addTestStepResult(stepResult);
		else
			stepDefinitionResult.addTestStepResult(stepResult);
	}
	}


	public void switchToFrame(WebElement element) {
		// TODO Auto-generated method stub
		try{
			
		driver.switchTo().frame(element);
		}
		
	 catch (NoSuchFrameException e) {
		String fileName = takeScreenShot("switchToFrame");
		setStepFail("switchToFrame() failed :",e,fileName);
		gsLogger.writeERROR(
				"Exception occured-> WebRobot -> switchToFrame() -> ", e);
	} finally {
		if (TestType.getTestType().equals(FUNCTIONAL_TEST))
			testcaseResult.addTestStepResult(stepResult);
		else
			stepDefinitionResult.addTestStepResult(stepResult);
	}
	}
	public void defaultContent() {
		// TODO Auto-generated method stub
		
		try{
			
			driver.switchTo().defaultContent();
			}
			
		 catch (NoSuchFrameException e) {
			String fileName = takeScreenShot("defaultContent");
			setStepFail("defaultContent() failed :",e,fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> defaultContent() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	try{
			
		driver.navigate().refresh();
		setStepPass("refresh() pass");
			}
			
		 catch (NoSuchFrameException e) {
			String fileName = takeScreenShot("refresh()");
			setStepFail("refresh() failed :",e,fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> refresh() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}
	
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
	
	//test method 
	public String testmethod(String locator){
	return	resolveGUIObject(locator);
	}
}
