package com.dnl.test.milestone1;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dnl.assertionmodel.Assert;
import com.dnl.assertionmodel.Verify;
import com.dnl.pages.common.NavigatePage;
import com.dnl.pages.login.LoginPage;
import com.dnl.pages.template.CreateNewCarrierTemplate;

public class TestCasesOne {
	
	LoginPage gPage;
	NavigatePage nPage;
	CreateNewCarrierTemplate ctPage;
	
	@BeforeClass
	public void OpenBrowser(){
		System.out.println("Nothing to work");
		ctPage = new CreateNewCarrierTemplate();
		nPage = new NavigatePage();
		gPage = new LoginPage();
		
	}
	
	@BeforeMethod
	public void LoginToDenovo(){
		gPage.LoginToDenovoLab();
		System.out.println("Login Complete");
	}
	
	@Test
	public void EnterTemplateNameAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name")
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	//Create new and save	
	@Test
	public void EnterTillBasicInfoAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 1")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 1", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	@Test
	public void EnterTillBasicSettingsAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 2")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 2", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	@Test
	public void EnterTillFraudDetectionAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 3")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("20")
		.EnterHourlyLimit("6")
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 3", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	//Create new and reset
	@Test
	public void EnterTemplateNameAndReset(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 1")
		.ClickResetButton();
		Verify.verifyTextBoxValue("TemplateName", "", "The textbox is empty", "The textbox is not empty");
		
	}
	
	@Test
	public void EnterTillBasicInfoAndReset(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 2")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.ClickResetButton();
		Verify.verifyTextBoxValue("TemplateName", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "", "The textbox is empty", "The textbox is not empty");
		
	}
	
	@Test
	public void EnterTillBasicSettingsAndReset(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 3")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickResetButton();
		Verify.verifyTextBoxValue("TemplateName", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is selected", "The check box is not selected");	
		
	}
	
	@Test
	public void EnterTillFraudDetectionAndReset(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 4")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("20")
		.EnterHourlyLimit("6")
		.ClickResetButton();
		Verify.verifyTextBoxValue("TemplateName", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("DailyLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("HourlyLimit", "", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is selected", "The check box is not selected");
		
	}
	
	//Create new save and edit
	@Test
	public void EnterTemplateNameAndSaveThenEdit(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData();
		Verify.verifyTextBoxValue("TemplateName", "This is me", "The textbox is empty", "The textbox is not empty");
		
	}
	
	@Test
	public void EnterTillBasicInfoAndSaveThenEdit(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me 1")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo();
		Verify.verifyTextBoxValue("TemplateName", "This is me 1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		
	}
	
	@Test
	public void EnterTillBasicSettingsAndSaveThenEdit(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me 2")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		
	}
	
	@Test
	public void EnterTillFraudDetectionAndSaveThenEdit(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 3")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("20")
		.EnterHourlyLimit("6")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting()
		.CollapseFraudDetection();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("DailyLimit", "20", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("HourlyLimit", "6", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		
	}
	
	//Create new save and delete
	@Test
	public void EnterTemplateNameAndSaveThenDelete(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 4")
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 4", "The carrier template is available", "The carrier template element is not available");
		ctPage
		.ClickDeleteButtonOfSavedData();
		
	}
	
	@Test
	public void EnterTillBasicInfoAndSaveThenDelete(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 5")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 5", "The carrier template is available", "The carrier template element is not available");
		ctPage
		.ClickDeleteButtonOfSavedData();
		
	}
	
	@Test
	public void EnterTillBasicSettingsAndSaveThenDelete(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 6")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 6", "The carrier template is available", "The carrier template element is not available");
		ctPage
		.ClickDeleteButtonOfSavedData();
		
	}
	
	@Test
	public void EnterTillFraudDetectionAndSaveThenDelete(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 7")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("20")
		.EnterHourlyLimit("6")
		.ClickSaveButton();
		Assert.assertTextPresent("This is my name 7", "The carrier template is available", "The carrier template element is not available");
		ctPage
		.ClickDeleteButtonOfSavedData();
		
	}
	
	//Create new save, edit, back and verify
	@Test
	public void EnterTemplateNameAndSaveThenEditAndBack(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData();
		Verify.verifyTextBoxValue("TemplateName", "This is me", "The textbox is empty", "The textbox is not empty");
		ctPage
		.ClickEditPageBackButton()
		.ClickEditButtonOfSavedData();
		Verify.verifyTextBoxValue("TemplateName", "This is me", "The textbox is empty", "The textbox is not empty");
		
	}
	
	@Test
	public void EnterTillBasicInfoAndSaveThenEditAndBack(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me 1")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo();
		Verify.verifyTextBoxValue("TemplateName", "This is me 1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		ctPage
		.ClickEditPageBackButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo();
		Verify.verifyTextBoxValue("TemplateName", "This is me 1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		
	}
	
	@Test
	public void EnterTillBasicSettingsAndSaveThenEditAndBack(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me 2")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		ctPage
		.ClickEditPageBackButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		
	}
	
	@Test
	public void EnterTillFraudDetectionAndSaveThenEditAndBack(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 3")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("20")
		.EnterHourlyLimit("6")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting()
		.CollapseFraudDetection();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("DailyLimit", "20", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("HourlyLimit", "6", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		ctPage
		.ClickEditPageBackButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting()
		.CollapseFraudDetection();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("DailyLimit", "20", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("HourlyLimit", "6", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		
	}
	
	//Create new save and edit and then save 
	@Test
	public void EnterTemplateNameAndSaveThenModifyAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData();
		Verify.verifyTextBoxValue("TemplateName", "This is me", "The textbox is empty", "The textbox is not empty");
		ctPage
		.EnterTemplateName("Modify")
		.ClickSaveButton();
		Assert.assertTextPresent("Modify", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	@Test
	public void EnterTillBasicInfoAndSaveThenModifyAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me 1")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo();
		Verify.verifyTextBoxValue("TemplateName", "This is me 1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		ctPage
		.EnterTemplateName("Modify 1")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("3.5")
		.EnterMinProfitability("2")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("150")
		.EnterCallLimit("300")
		.ClickSaveButton();
		Assert.assertTextPresent("Modify 1", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	@Test
	public void EnterTillBasicSettingsAndSaveThenModifyAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is me 2")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		ctPage
		.EnterTemplateName("Modify 2")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("11.5")
		.EnterMinProfitability("11")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("1100")
		.EnterCallLimit("2100")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.ClickSaveButton();
		Assert.assertTextPresent("Modify 2", "The carrier template is available", "The carrier template element is not available");
		
	}
	
	@Test
	public void EnterTillFraudDetectionAndSaveThenModifyAndSave(){
		
		nPage.OpenTempleteCarrierTemplate();
		ctPage
		.ClickCreateNewCarrierTemplate()
		.EnterTemplateName("This is my name 3")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("2.5")
		.EnterMinProfitability("1")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("100")
		.EnterCallLimit("200")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("20")
		.EnterHourlyLimit("6")
		.ClickSaveButton()
		.ClickEditButtonOfSavedData()
		.CollapseBasicInfo()
		.CollapseBasicSetting()
		.CollapseFraudDetection();
		Verify.verifyTextBoxValue("TemplateName", "This is me 2", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("TestCredit", "2.5", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CPSLimit", "100", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("MinProfitability", "1", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("CallLimit", "200", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("DailyLimit", "20", "The textbox is empty", "The textbox is not empty");
		Verify.verifyTextBoxValue("HourlyLimit", "6", "The textbox is empty", "The textbox is not empty");
		Verify.verifycheck("SendTrunkUpdate", "The check box is not selected", "The check box is selected");
		ctPage
		.EnterTemplateName("Modify 3")
		.CollapseBasicInfo()
		.SelectMode()
		.SelectCurrency()
		.EnterTestCredit("5.5")
		.EnterMinProfitability("12")
		.SelectMinProfitabilityPercentage()
		.EnterCPSLimit("1000")
		.EnterCallLimit("2000")
		.CollapseBasicSetting()
		.ClickSendTrunkUpdate()
		.CollapseFraudDetection()
		.EnterDailyLimit("10")
		.EnterHourlyLimit("3")
		.ClickSaveButton();
		Assert.assertTextPresent("Modify 3", "The carrier template is available", "The carrier template element is not available");
		
		
	}
	
	
	@AfterMethod
	public void LogoutFromDenovo(){
		gPage.LogoutFromDenovoLab();
		System.out.println("Logout complete");
	}
	
	@AfterClass
	public void CloseBrowser(){
		gPage.closeBrowser();
	}
	
}
