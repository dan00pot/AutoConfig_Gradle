package scripts.AutoConfig;

import libs.clients.AADSWebKeywords;
import libs.clients.AndroidClientKeywords;
import libs.common.DriverManagement;
import libs.common.Selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.*;
import testData.aadsData;

public class AutoConfig_003 {
	/*###############################--Test Steps--####################################
	 * AutoConfig-003 Group specific setting  Enable unified login with correct unified user and password
	 * "Procedures:
		Group setting: 
		1.Enable SSOENABLED 
		2.Configure SSOUERID and SSOPASSWORD correctly
		3.SET ESMSSO or ACSSSO = 0
		4. Reload auto_configure
		 
		Expected results:
		2.User could unified login by enter credential one time.
		4.User must enter Messaging credential and ACS credential."
		
		Khanh's note: set ACSSSO = 0 when aura not config with PS
	 *################################################################################# */
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_003";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_003");

	@Before
	public void beforetestAutoConfig_003() throws Exception {
		logger.info("beforetest AutoConfig_003 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_003 completed...\n");
	}

	@Test
	public void testAutoConfig_003() throws Exception {
		logger.info("AutoConfig_003 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");


				boolean status;
				// Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "SSOENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SSOUSERID", true, aadsData.AADS_USER_1_NAME);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SSOPASSWORD", true, aadsData.AADS_USER_PWD);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMUSERNAME", false, aadsData.AADS_USER_1_NAME);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMPASSWORD", false, aadsData.AADS_USER_PWD);
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(5000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, testCase);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "SSOENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SSOUSERID", true, aadsData.AADS_USER_1_NAME);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SSOPASSWORD", true, aadsData.AADS_USER_PWD);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				
				//Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ESMSSO", true, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMSSO", true, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMSSO", true, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				
				status = androidClient.autoConfigLoginVerifyESM(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(status == true)
				{
					logger.info("AutoConfig003 - Step2_Need to login Multimedia Messaging account as expected\n");
					assertTrue(status);
				} 
				
				assertTrue(status);
		} catch (Exception exception) {
			logger.error("AutoConfig_003 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_003 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_003 - test completed\n");
	}
	
	@After
	public void tearDown(){
		logger.info("tearDown starting...\n");
		try {
			webDriver.get(aadsData.AADS_SERVER_ADDRESS);
			AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
			Thread.sleep(1000);
			AADSWebDriver.configurationPageSelectConfigurationUser(webDriver,sheet);
			AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN ,aadsData.AADS_USER_1_NAME);
			AADSWebDriver.configurationPageSelectConfigurationUser(webDriver,testCase);
			Thread.sleep(1000);
			AADSWebDriver.configurationPageDeleteConfiguration(webDriver, testCase);
			AADSWebDriver.logoutAADSMainPage(webDriver);
			webDriver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("tearDown completed...\n");
	}
}
