package com.dnl.test.sample;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dnl.assertionmodel.Assert;
import com.dnl.assertionmodel.Verify;
import com.dnl.pages.login.*;
import com.dnl.pages.common.*;
import com.dnl.pages.management.*;
import com.dnl.pages.template.CreateNewCarrierTemplate;

import bsh.Console;

public class DenovoLabTest {
	LoginPage gPage;
	@BeforeClass
	public void init()
	{
		gPage =new LoginPage();
	}
	
	@Test
	public void testgui()
	{
		CreateNewCarrierTemplate objPageNewCarrierTemplate = new CreateNewCarrierTemplate();
	String s =objPageNewCarrierTemplate.getResolvedObject("CarrierTemplateTableEdit-Denovolab 123");
	}
	
	@Test
	  public void testGoogle() 
	  {
		  gPage.openGoogleSearchPage();
		  Verify.verifyEquals("selenium - Google Search", gPage.getCurrentPageTitle(), "got expected title", "got un expected title");
	  }
	
	@Test
	public void testDenovo()
	{
		gPage.LoginToDenovoLab();
		gPage.LogoutFromDenovoLab();
		gPage.sleep(30000);
	}
	@Test
	public void testUsername()
	{
		gPage.LoginToDenovoLab();
		Verify.verifyElementValue("loggedInUser", "admin", "User logged in as Admin","Admin username not found");
		gPage.LogoutFromDenovoLab();
		gPage.sleep(30000);
	}
	
	@Test
	public void testEmptyUsernameAndSignIn()
	{
		gPage.navigateToLoginDenovoLabUrl();
		gPage.EnterUsername("admin");
		gPage.ClickSignIn();
		//Assert.assertElementPresent("errorOnEmptyInput","Error Message Pop up Came ", "Error Message did not came");
		Verify.verifyElementValue("errorOnEmptyInput", "Please input user name and password", "Error Message Pop up Came ","Error Message did not came");
	}
	
	@Test
	public void testEmptyPasswordAndSignIn()
	{
		LoginPage gPage = new LoginPage();
		gPage.navigateToLoginDenovoLabUrl();
		gPage.EnterPassword("123456");
		gPage.ClickSignIn();
		//Assert.assertElementPresent("errorOnEmptyInput","Error Message Pop up Came ", "Error Message did not came");
		Verify.verifyElementValue("errorOnEmptyInput", "Please input user name and password", "Error Message Pop up Came ","Error Message did not came");
	}

	@Test
	public void testEmptyUsernameAndPassword()
	{
		LoginPage gPage = new LoginPage();
		gPage.navigateToLoginDenovoLabUrl();
		gPage.EnterUsername("notAdmin");
		gPage.EnterPassword("12345678");
		gPage.ClickSignIn();
		//Assert.assertElementPresent("errorOnEmptyInput","Error Message Pop up Came ", "Error Message did not came");
		Verify.verifyElementValue("errorOnWrongInput", "Incorrect username or password.", "Error Message Pop up Came ","Error Message did not came");
	}
	@Test
	public void testRememberMe()
	{
		LoginPage gPage = new LoginPage();
		gPage.navigateToLoginDenovoLabUrl();
		gPage.EnterUsername("admin");
		gPage.EnterPassword("123456");
		gPage.ClickRememberMe();
		gPage.ClickSignIn();
		Assert.assertElementVisible("LogoutLock", "User Logged In Sccessfully", "User is not Logged in Sccessfully");
		gPage.navigateToLoginDenovoLabUrl();
		Assert.assertElementVisible("LogoutLock", "Remember Me option is Passed", "Remember Me option is failed");
	}
	@Test 
	public void testResetCreateNewClient()
	{
		gPage.LoginToDenovoLab();
		NavigatePage navigatePage = new  NavigatePage();
		navigatePage.sleep(2000);
		navigatePage.OpenManangement();
		navigatePage.sleep(2000);
		navigatePage.OpenCarrier();
		navigatePage.sleep(2000);
		CarrierPage carrierPage = new CarrierPage();
		navigatePage.sleep(2000);
		carrierPage.CreateNewClick();
		navigatePage.sleep(2000);
		CarrierCreateNewClient cNew = new CarrierCreateNewClient();
		cNew.EnterName("Demo Denovo 123")
		.SelectCurrency()
		.SelectStatus()
		.SelectMode()
		.EnterTestCredit("0.5")
		.EnterCPSLimit("100")
		.EnterCallLimit("500")
		.EnterMinimumProfitablilty("5")
		.SelectminimumProfitablilty()
		.ClickResetButton()
		.EnterName("New Demo After Reset");
		Verify.verifyElementValue("ClientName", "New Demo After Reset", "Element Reset Worked", "Element is not Reset.");
		carrierPage.sleep(3000);
		gPage.LogoutFromDenovoLab();
	}
	
	@Test 
	public void testCreateNewClient()
	{
		gPage.LoginToDenovoLab();
		NavigatePage navigatePage = new  NavigatePage();
		navigatePage.sleep(2000);
		navigatePage.OpenManangement();
		navigatePage.sleep(2000);
		navigatePage.OpenCarrier();
		navigatePage.sleep(2000);
		CarrierPage carrierPage = new CarrierPage();
		navigatePage.sleep(2000);
		carrierPage.CreateNewClick();
		navigatePage.sleep(2000);
		CarrierCreateNewClient cNew = new CarrierCreateNewClient();
		cNew.EnterName("Demo Denovo 123")
		.SelectCurrency()
		.SelectStatus()
		.SelectMode()
		.EnterTestCredit("0.5")
		.EnterCPSLimit("100")
		.EnterCallLimit("500")
		.EnterMinimumProfitablilty("5")
		.SelectminimumProfitablilty()
		.ClickSaveButton();
		Assert.assertElementPresent("SaveSuccess", "Client created Successfuly.", "Client is not created Successfully");
		carrierPage.sleep(3000);
		gPage.LogoutFromDenovoLab();
	}
	
	
	@AfterClass
	public void testEnd()
	{
		gPage.closeBrowser();
	}
}

