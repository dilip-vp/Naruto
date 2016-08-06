package com.dnl.pages.management;

import com.dnl.pages.common.Page;

public class CarrierCreateNewClient extends Page {
	
	public CarrierCreateNewClient EnterName(String name)
	{
		robot.sendKeys("ClientName", name);
		return this;
	}
	public CarrierCreateNewClient SelectStatus()
	{
		robot.select("ClientStatusDD", "label=Active");
		return this;
	}
	public CarrierCreateNewClient SelectMode()
	{
		robot.select("ClientModeDD", "label=Prepaid");
		return this;
	}
	public CarrierCreateNewClient SelectCurrency()
	{
		robot.select("ClientCurrencyDD", "label=USA");
		return this;
	}
	public CarrierCreateNewClient EnterTestCredit(String number)
	{
		robot.sendKeys("ClientTestCredit", number);
		return this;
	}
	public CarrierCreateNewClient EnterMinimumProfitablilty(String number)
	{
		robot.sendKeys("MinProfitTextBox", number);
		return this;
	}
	public CarrierCreateNewClient SelectminimumProfitablilty()
	{
		robot.select("MinProfitDD", "label=Value");
		return this;
	}
	public CarrierCreateNewClient EnterCPSLimit(String number)
	{
		robot.sendKeys("CPSLimitText", number);
		return this;
	}
	public CarrierCreateNewClient EnterCallLimit(String number)
	{
		robot.sendKeys("CallLimitText", number);
		return this;
	}
	public CarrierCreateNewClient ClickSaveButton()
	{
		robot.moveToElement("SaveButton");
		robot.executeJavaScript("scroll(0,500);");
		robot.click("SaveButton");
		return this;
	}
	public CarrierCreateNewClient ClickResetButton()
	{
		robot.moveToElement("ResetButton");
		robot.executeJavaScript("scroll(0,500);");
		robot.click("ResetButton");
		return this;
	}

}
