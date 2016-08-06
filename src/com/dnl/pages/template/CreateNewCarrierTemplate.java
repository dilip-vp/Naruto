package com.dnl.pages.template;

import com.dnl.pages.common.Page;

public class CreateNewCarrierTemplate extends Page {
	
	public CreateNewCarrierTemplate EnterTemplateName(String name){
		robot.sendKeys("TemplateName", name);
		return this;
	}
	public CreateNewCarrierTemplate ClickCreateNewCarrierTemplate(){
		robot.click("CreateNewCarrierTemplate");
		return this;
	}
	public CreateNewCarrierTemplate SelectMode()
	{
		robot.select("Mode", "label=Prepaid");
		return this;
	}
	public CreateNewCarrierTemplate EnterTestCredit(String credit){
		robot.sendKeys("TestCredit", credit);
		return this;
	}
	public CreateNewCarrierTemplate EnterCPSLimit(String cpslimit){
		robot.sendKeys("CPSLimit", cpslimit);
		return this;
	}
	public CreateNewCarrierTemplate SelectCurrency(){
		robot.sendKeys("Currency", "label=USA");
		return this;
	}
	public CreateNewCarrierTemplate EnterMinProfitability(String minProfit){
		robot.sendKeys("MinProfitability", minProfit);
		return this;
	}
	public CreateNewCarrierTemplate SelectMinProfitabilityPercentage(){
		robot.select("MinProfitabilityPercentage", "label=Percentage");
		return this;
	}
	public CreateNewCarrierTemplate EnterCallLimit(String callLimit){
		robot.sendKeys("CallLimit", callLimit);
		return this;
	}
	public CreateNewCarrierTemplate ClickSendTrunkUpdate(){
		robot.click("SendTrunkUpdate");
		return this;
	}
	public CreateNewCarrierTemplate EnterDailyLimit(String dailyLimit){
		robot.sendKeys("DailyLimit", dailyLimit);
		return this;
	}
	public CreateNewCarrierTemplate EnterHourlyLimit(String hourlyLimit){
		robot.sendKeys("HourlyLimit", hourlyLimit);
		return this;
	}
	public CreateNewCarrierTemplate SelectPeriod()
	{
		robot.select("Period", "label=24H");
		return this;
	}
	public CreateNewCarrierTemplate SelectTimeZone()
	{
		robot.select("TimeZone", "label=GMT +00:00");
		return this;
	}
	public CreateNewCarrierTemplate SelectRecipient()
	{
		robot.select("Recipient", "label=Both");
		return this;
	}
	public CreateNewCarrierTemplate SelectSendHour()
	{
		robot.select("SendHour", "label=0:00");
		return this;
	}
	public CreateNewCarrierTemplate ClickIncludeCRD(){
		robot.click("IncludeCRD");
		return this;
	}
	public CreateNewCarrierTemplate ClickDailyUsageSummary(){
		robot.click("DailyUsageSummary");
		return this;
	}
	public CreateNewCarrierTemplate ClickNonZeroOnly(){
		robot.click("NonZeroOnly");
		return this;
	}
	public CreateNewCarrierTemplate SelectGroupBy()
	{
		robot.select("GroupBy", "label=By Country");
		return this;
	}
	public CreateNewCarrierTemplate ClickDailyBalanceSummary(){
		robot.click("DailyBalanceSummary");
		return this;
	}
	public CreateNewCarrierTemplate EnterNumberOfDays(String days){
		robot.sendKeys("NumberOfDays", days);
		return this;
	}
	public CreateNewCarrierTemplate ClickDailyCRDGeneration(){
		robot.click("DailyCRDGeneration");
		return this;
	}
	public CreateNewCarrierTemplate EnterSCCPercentage(String percent){
		robot.sendKeys("SCCPercentage", percent);
		return this;
	}
	public CreateNewCarrierTemplate EnterSCCTime(String time){
		robot.sendKeys("SCCTime", time);
		return this;
	}
	public CreateNewCarrierTemplate EnterSCCCharge(String charge){
		robot.sendKeys("SCCCharge", charge);
		return this;
	}
	public CreateNewCarrierTemplate SelectSCCType()
	{
		robot.select("SCCType", "label=meeting the short duration defined above");
		return this;
	}
	public CreateNewCarrierTemplate ClickAutoInvoice(){
		robot.click("AutoInvoice");
		return this;
	}
	public CreateNewCarrierTemplate ClickIncludeTaxInInvoice(){
		robot.click("IncludeTaxInInvoice");
		return this;
	}
	public CreateNewCarrierTemplate SelectInvoiceFormat()
	{
		robot.select("InvoiceFormat", "label=PDF");
		return this;
	}
	public CreateNewCarrierTemplate SelectClientTimeZone()
	{
		robot.select("ClientTimeZone", "label=GMT +00:00");
		return this;
	}
	public CreateNewCarrierTemplate SelectCDRCompressionFormat()
	{
		robot.select("CDRCompressionFormat", "label=zip");
		return this;
	}
	public CreateNewCarrierTemplate SelectRateValue()
	{
		robot.select("RateValue", "label=Average");
		return this;
	}
	public CreateNewCarrierTemplate SelectPaymentTerms()
	{
		robot.select("PaymentTerms", "label=abc");
		return this;
	}
	public CreateNewCarrierTemplate ClickAttachCDRList(){
		robot.click("AttachCDRList");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowDetail(){
		robot.click("ShowDetail");
		return this;
	}
	public CreateNewCarrierTemplate ClickAddJurisdictionalDetail(){
		robot.click("AddJurisdictionalDetail");
		return this;
	}
	public CreateNewCarrierTemplate SelectNoInvoiceForZeroTraffic()
	{
		robot.select("NoInvoiceForZeroTraffic", "label=Yes");
		return this;
	}
	public CreateNewCarrierTemplate SelectRateDecimalPlace()
	{
		robot.select("RateDecimalPlace", "label=4");
		return this;
	}
	public CreateNewCarrierTemplate EnterLastInvoicedFor(String lastInvoiced){
		robot.sendKeys("LastInvoicedFor", lastInvoiced);
		return this;
	}
	public CreateNewCarrierTemplate ClickEmailInvoice(){
		robot.click("EmailInvoice");
		return this;
	}
	public CreateNewCarrierTemplate ClickSendAsLink(){
		robot.click("SendAsLink");
		return this;
	}
	public CreateNewCarrierTemplate ClickIncludeBreakoutSummary(){
		robot.click("IncludeBreakoutSummary");
		return this;
	}
	public CreateNewCarrierTemplate ClickShortDurationCallSurchargeDetail(){
		robot.click("ShortDurationCallSurchargeDetail");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowDetailByTrunk(){
		robot.click("ShowDetailByTrunk");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowCodeSummaryTopTen(){
		robot.click("ShowCodeSummaryTopTen");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowTrafficAnalysisByCountry(){
		robot.click("ShowTrafficAnalysisByCountry");
		return this;
	}
	public CreateNewCarrierTemplate ClickIncludeAvaliableCredit(){
		robot.click("IncludeAvaliableCredit");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowDailyUsage(){
		robot.click("ShowDailyUsage");
		return this;
	}
	public CreateNewCarrierTemplate ClickIncludeSummaryOfPayment(){
		robot.click("IncludeSummaryOfPayment");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowTotalByTrunk(){
		robot.click("ShowTotalByTrunk");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowTrafficAnalysisByCodeName(){
		robot.click("ShowTrafficAnalysisByCodeName");
		return this;
	}
	public CreateNewCarrierTemplate ClickShowCallsByDateSummary(){
		robot.click("ShowCallsByDateSummary");
		return this;
	}
	public CreateNewCarrierTemplate ClickBreakDownByRateTable(){
		robot.click("BreakDownByRateTable");
		return this;
	}
	public CreateNewCarrierTemplate SelectUsageDetailFields()
	{
		robot.select("UsageDetailFields", "label=Total Minutes");
		return this;
	}
	public CreateNewCarrierTemplate ClickLowBalanceNotification(){
		robot.click("LowBalanceNotification");
		return this;
	}
	public CreateNewCarrierTemplate EnterLowBalanceThreshold(String lowBalanceThreshold){
		robot.sendKeys("LowBalanceThreshold", lowBalanceThreshold);
		return this;
	}
	public CreateNewCarrierTemplate EnterTime(String time){
		robot.sendKeys("Time", time);
		return this;
	}
	public CreateNewCarrierTemplate SelectUSD()
	{
		robot.select("USD", "label=Actual Balance");
		return this;
	}
	public CreateNewCarrierTemplate SelectGMT()
	{
		robot.select("GMT", "label=GMT +00:00");
		return this;
	}	
	public CreateNewCarrierTemplate ClickSaveButton()
	{
		robot.click("SaveButton");
		return this;
	}
	public CreateNewCarrierTemplate ClickResetButton()
	{
		robot.click("ResetButton");
		return this;
	}
	public CreateNewCarrierTemplate CollapseBasicInfo()
	{
		robot.click("collapseBasicInfo");
		return this;
	}
	public CreateNewCarrierTemplate CollapseBasicSetting()
	{
		robot.click("collapseBasicSetting");
		return this;
	}
	public CreateNewCarrierTemplate CollapseFraudDetection()
	{
		robot.click("collapseFraudDetection");
		return this;
	}
	public CreateNewCarrierTemplate CollapseAutomaticReport()
	{
		robot.click("collapseAutomaticReport");
		return this;
	}
	public CreateNewCarrierTemplate CollapseShortCallCharge()
	{
		robot.click("collapseShortCallCharge");
		return this;
	}
	public CreateNewCarrierTemplate CollapseAutoInvoice()
	{
		robot.click("collapseLowBalanceNotification");
		return this;
	}
	public CreateNewCarrierTemplate CollapseLowBalanceNotification()
	{
		robot.click("collapseLowBalanceNotification");
		return this;
	}
	public CreateNewCarrierTemplate ClickEditButtonOfSavedData(){
		robot.click("CarrierTemplateTableEdit");
		return this;
	}
	public CreateNewCarrierTemplate ClickDeleteButtonOfSavedData(){
		robot.click("CarrierTemplateTableRemove");
		return this;
	}
	public CreateNewCarrierTemplate SearchForSavedCarrier(){
		robot.getValueFromProperties("SearchForSavedCarrier");
		return this;
	}
	 public CreateNewCarrierTemplate ClickEditPageBackButton(){
		  robot.click("EditBackButton");
		  return this;
		 }
	 
	 public String getResolvedObject(String locator)
	 {
		return robot.testmethod(locator);
	 }
	 
	 public CreateNewCarrierTemplate EditButtonClick(String locator)
	 {
		 robot.click(locator);
		 return this;
	 }
	 
	 public CreateNewCarrierTemplate DeleteButtonClick(String locator)
	 {
		 robot.click(locator);
		 return this;
	 }
}
