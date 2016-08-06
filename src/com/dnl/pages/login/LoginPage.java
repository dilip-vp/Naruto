package com.dnl.pages.login;

import com.dnl.pages.common.Page;

public class LoginPage extends Page{
	public void openGoogleSearchPage() {
		// TODO Auto-generated method stub
		
		robot.navigate("http://www.google.co.in/?gfe_rd=cr&ei=05u3VMnaLMmDoAPB_oDIAw&gws_rd=cr");
		robot.sendKeys("searchBoxId","selenium");
		System.out.println("reached");
		sleep(4000);
	}
	
	public LoginPage EnterUsername(String name){
		robot.sendKeys("Username", name);
		return this;
	}
	public LoginPage EnterPassword(String password){
		robot.sendKeys("Password", password);
		return this;
	}
	public LoginPage ClickRememberMe(){
		robot.click("rememberMe");
		return this;
	}
	public LoginPage ClickSignIn(){
		robot.click("SignMeIn");
		return this;
	}
	public LoginPage ClickForgotPassword(){
		robot.click("forgotPassword");
		return this;
	}
	public LoginPage EnterForgotPasswordUsername(String forgotname){
		robot.sendKeys("usernameinputbox", forgotname);
		return this;
	}
	public LoginPage ClickRetrievePasswordButton(){
		robot.click("retrievepasswordbutton");
		return this;
	}
	public LoginPage ClickBackToLoginPageButton(){
		robot.click("backtologinpage");
		return this;
	}
	public LoginPage OpenDenovolabMainPage(){
		navigateToLoginDenovoLabUrl();
		return this;
	}
	
	
	
	
	
	public void LoginToDenovoLab(){
		navigateToLoginDenovoLabUrl();
		EnterUsername("admin");
		EnterPassword("123456");
		ClickSignIn();
		robot.waitForElementVisible("management");
	}
	
	public void LogoutFromDenovoLab()
	{
		//robot.waitForElement("LogoutLock");
		robot.click("LogoutLock");
		robot.click("SignoutButton");
	}

}
