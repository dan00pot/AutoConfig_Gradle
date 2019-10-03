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

public class AutoConfig_018 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-018 Overriden specific setting verify Verbose Logging on Group and Global setting.
	 *"Procedures:
		1. On Group setting select LOG_VERBOSITY= 1, Global setting: LOG_VERBOSITY= 0
		2. Does not select LOG_VERBOSITY on Group Setting, Global setting: LOG_VERBOSITY= 0
		
		
		Expected results:
		1. Verbose Logging enabled.
		2.  Verbose Logging disabled."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_018";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_018");

	@Before
	public void beforetestAutoConfig_018() throws Exception {
		logger.info("beforetest AutoConfig_018 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_018 completed...\n");
	}

	@Test
	public void testAutoConfig_018() throws Exception {
		logger.info("AutoConfig_018 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "LOG_VERBOSITY", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "LOG_VERBOSITY", true, "0");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				AADSWebDriver.verify46xx(webDriver, "LOG_VERBOSITY 1",testCase);
				
				// Step 2 
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, testCase);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "LOG_VERBOSITY", false, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "LOG_VERBOSITY", true, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				AADSWebDriver.verify46xx(webDriver, "LOG_VERBOSITY 0",testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				

		} catch (Exception exception) {
			logger.error("AutoConfig_018 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_018 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		}
		logger.info("AutoConfig_018 - test completed\n");
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
