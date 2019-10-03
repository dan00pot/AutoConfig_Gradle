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

public class AutoConfig_019 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-019 Overriden specific setting verify Email Support on Group and Global setting.
	 *"Procedures:
		1. On Group setting select SUPPORTEMAIL= 123@aam1.com, Global setting: SUPPORTEMAIL= 456@aam1.com
		2. Does not select SUPPORTEMAIL on Group Setting, Global setting: SUPPORTEMAIL= 456@aam1.com
		
		
		Expected results:
		1. Support Email Address: 123@aam1.com
		2. Support Email Address: 456@aam1.com"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_019";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_019");

	@Before
	public void beforetestAutoConfig_019() throws Exception {
		logger.info("beforetest AutoConfig_019 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_019 completed...\n");
	}

	@Test
	public void testAutoConfig_019() throws Exception {
		logger.info("AutoConfig_019 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "SUPPORTEMAIL", true, "123@aam1.com");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "SUPPORTEMAIL", true, "456@aam1.com");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);	
				AADSWebDriver.verify46xx(webDriver, "SUPPORTEMAIL 123@aam1.com",testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
				// Step 2
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, testCase);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "SUPPORTEMAIL", false, "123@aam1.com");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "SUPPORTEMAIL", true, "456@aam1.com");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				AADSWebDriver.verify46xx(webDriver, "SUPPORTEMAIL 456@aam1.com",testCase);
				
				androidClientDriver.resetApp();
				n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
		
			


		} catch (Exception exception) {
			logger.error("AutoConfig_019 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_019 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		}
		logger.info("AutoConfig_019 - test completed\n");
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
