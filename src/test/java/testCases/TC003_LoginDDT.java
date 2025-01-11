package testCases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven") //getting data providers from different class
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		try {
		//homepage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
				
			
		//loginpage
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
			
		//myaccount
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();
		
		//test conditions
		//data is valid - login is successful - test passed
		//data is valid - login is unsuccessful - test failed

		
		if(exp.equalsIgnoreCase("Valid")) {
			if(targetPage==true) {
				Assert.assertTrue(true);
				macc.clickLogout();
			}
			else {
				Assert.assertTrue(false);
			}
		}
		
		//test conditions
		//data is invalid - login is successful - test failed
		//data is invalid - login is unsuccessful - test passed
		
		if(exp.equalsIgnoreCase("Invalid")) {
			if(targetPage==true) {
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else {
				Assert.assertTrue(true);
			}
		}
		}catch(Exception e) {
			Assert.fail();
		}
	}
}
