package com.dnl.pages.common;

public class NavigatePage extends Page {

	public void OpenConfiguration()
	{
		robot.click("configuration");
	}
	public void OpenFinance()
	{
		robot.click("finance");
	}
	public void OpenLog()
	{
		robot.click("log");
	}
	public void OpenManangement()
	{
		//robot.waitForElement("management");
		robot.mouseOver("management");
	}
	public void OpenMonitoring()
	{
		robot.click("monitoring");
	}
	public void OpenOrigination()
	{
		robot.click("origination");
	}
	public void OpenRouting()
	{
		robot.click("routing");
	}
	public void OpenStatistics()
	{
		robot.click("statistics");
	}
	public void OpenSwitch()
	{
		robot.click("switch");
	}
	public void OpenTemplate()
	{
		//robot.waitForElement("management");
		robot.mouseOver("template");
	}
	public void OpenAgent()
	{
		robot.click("agent");
	}
	public void OpenTools()
	{
		robot.click("tools");
	}
	public void OpenAdmin()
	{
		robot.click("admin");
	}
	public void OpenTempleteCarrierTemplate()
	{
		OpenTemplate();
		robot.click("carriertemplate");
	}
	public void OpenTempleteEgressTrunkTemplate()
	{
		OpenTemplate();
		robot.click("egresstrunktemplate");
	}
	public void OpenTempleteIngressTrunkTemplate()
	{
		OpenTemplate();
		robot.click("ingresstrunktemplate");
	}
	public void OpenTempleteRateEmailTemplate()
	{
		OpenTemplate();
		robot.click("rateemailtemplate");
	}
	public void OpenTempleteRateUploadTemplate()
	{
		OpenTemplate();
		robot.click("rateuploadtemplate");
	}
	
	//Management
	public void OpenCarrier()
	{
		robot.waitForElementClickable("carrier");
		robot.click("carrier");
	}
	
}
