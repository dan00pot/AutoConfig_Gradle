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

public class AutoConfig_017 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-017 Overriden specific setting verify ACSSRVR on User, Group setting.
	 *"Procedures:
		1. On User Setting select ACSSRVR= ACSServer1, Group setting: ACSSRVR= ACSServer2.
		2. On User Setting does not select ACSSRVR, Group setting:  ACSSRVR= ACSServer2.
		
		
		Expected results:
		1. ACS server is ACSServer1 as User setting.
		2. ACS server is ACSServer2 as Group setting."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_017";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_017");

	@Before
	public void beforetestAutoConfig_017() throws Exception {
		logger.info("beforetest AutoConfig_017 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_017 completed...\n");
	}

	@Test
	public void testAutoConfig_017() throws Exception {
		logger.info("AutoConfig_017 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");

						
				// Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSSRVR", true, aadsData.AADS_SERVER1_ADDRESS_CLIENT);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ACSSRVR", true, aadsData.AADS_SERVER2_ADDRESS_CLIENT);
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				
				String s= androidClient.getAADSServer(androidClientDriver);
				if(s.equals(aadsData.AADS_SERVER1_ADDRESS_CLIENT))
				{
					logger.info("AutoConfig017 - Step1_AADS server  is as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig017- Failed - Exception occurs:  Step1_AADS server "+ s +" is not as expected. Incorrect....\n");
				
				//Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSSRVR", false, aadsData.AADS_SERVER1_ADDRESS_CLIENT);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				s= androidClient.getAADSServer(androidClientDriver);
				if(s.equals(aadsData.AADS_SERVER2_ADDRESS_CLIENT))
				{
					logger.info("AutoConfig017 - Step2_AADS server  is as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig017- Failed - Exception occurs:  Step2_AADS server "+ s +" is not as expected. Incorrect....\n");


		} catch (Exception exception) {
			logger.error("AutoConfig_017 - Failed with Exception:"	+ exception + "...\n");
			fail("AutoConfig_017 - Failed - Exception occurs: "		+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_017 - test completed\n");
	}

	
	@After
	public void tearDown() throws Exception {
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
