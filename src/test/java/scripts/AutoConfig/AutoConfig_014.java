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

public class AutoConfig_014 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-014 Overriden specific setting verify ESMREFRESH on User, Group and Global setting.
	 *"Procedures:
		1. User setting: ESMREFRESH= 60minutes,  Group setting: ESMREFRESH= 0, Global setting: ESMREFRESH= 0.
		2. Does not select ESMREFRESH on User setting, Group setting: ESMREFRESH= 0 minutes, Global setting: ESMREFRESH =60 minutes
		3. Does not select ESMREFRESH on Group setting, Global setting: ESMREFRESH = 60 minutes
		 
		Expected results: 
		1. Polling interval = 60 minutes  as User setting (High priority)
		2. Polling interval = Continous  as as Group setting (High priority)
		3. Polling interval = 60 minutes  as  as Global setting."

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_014";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_014");

	@Before
	public void beforetestAutoConfig_014() throws Exception {
		logger.info("beforetest AutoConfig_014 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_014 completed...\n");
	}

	@Test
	public void testAutoConfig_014() throws Exception {
		logger.info("AutoConfig_014 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMREFRESH", true, "60");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMREFRESH", true, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ESMREFRESH", true, "0");
				
				
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if((androidClient.getPollingTimer(androidClientDriver)).contains("1 Hour"))
				{
					logger.info("AutoConfig014 - Step1_Pooling interval as expected  1 hour .....\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig014- Failed - Exception occurs:  Step1_Pooling interval is not as expected  1 hour ..Incorrect.....\n");
					assertTrue(false);
				}
				
						
			//Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMREFRESH", false, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ESMREFRESH", true, "60");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if((androidClient.getPollingTimer(androidClientDriver)).contains("Continuous"))
				{
					logger.info("AutoConfig014 - Step2_Pooling interval as expected  continuous .....\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig014- Failed - Exception occurs:  Step2_Pooling interval is not as expected  continuous ..Incorrect.....\n");
					assertTrue(false);
				}
				
			//Step 3	
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMREFRESH", false, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if((androidClient.getPollingTimer(androidClientDriver)).contains("1 Hour"))
				{
					logger.info("AutoConfig014 - Step3_Pooling interval as expected  1 hour .....\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig014- Failed - Exception occurs:  Step3_Pooling interval is not as expected  1 hour ..Incorrect.....\n");
					assertTrue(false);
				}


		} catch (Exception exception) {
			logger.error("AutoConfig_014 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_014 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_014 - test completed\n");
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
