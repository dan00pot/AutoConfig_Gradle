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

public class AutoConfig_020 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-020 Overriden specific setting verify parameters of software auto-update on Platform and Group setting.
	 *"Procedures:
		1. On Platform select APPCAST_ENABLED=1, APPCAST_URL= url1, APPCAST_CHECK_INTERVAL= aaa day.
		 On Group Setting:  APPCAST_ENABLED= 1, APPCAST_URL= url2, APPCAST_CHECK_INTERVAL= bbb day.
		2. Does not select these parameters above on Platform Setting, keep Group setting as step 1.
		
		
		
		
		Expected results:
		1. Enable automatic software update with URL= url1 and time to retrieve update automatically is aaa day.
		2. Enable automatic software update with URL= url2 and time to retrieve update automatically is bbb day."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_020";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_020");

	@Before
	public void beforetestAutoConfig_020() throws Exception {
		logger.info("beforetest AutoConfig_020 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_020 completed...\n");
	}

	@Test
	public void testAutoConfig_020() throws Exception {
		logger.info("AutoConfig_020 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "APPCAST_ENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "APPCAST_URL", true, "123.aam1.com:1010");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "APPCAST_CHECK_INTERVAL", true, "11");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_ENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_URL", true, "456.aam1.com:1010");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_CHECK_INTERVAL", true, "22");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"iOS");
				
				AADSWebDriver.verify46xx(webDriver, "APPCAST_ENABLED 1",testCase);
				AADSWebDriver.verify46xx(webDriver, "APPCAST_URL 123.aam1.com:1010",testCase);
				AADSWebDriver.verify46xx(webDriver, "APPCAST_CHECK_INTERVAL 11",testCase);
				
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "APPCAST_ENABLED", false, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "APPCAST_URL", false, "123.aam1.com:1010");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "APPCAST_CHECK_INTERVAL", false, "11");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_ENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_URL", true, "456.aam1.com:1010");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_CHECK_INTERVAL", true, "22");
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"iOS");
				
				AADSWebDriver.verify46xx(webDriver, "APPCAST_ENABLED 1",testCase);
				AADSWebDriver.verify46xx(webDriver, "APPCAST_URL 456.aam1.com:1010",testCase);
				AADSWebDriver.verify46xx(webDriver, "APPCAST_CHECK_INTERVAL 22",testCase);
				
				androidClientDriver.resetApp();
				n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
		
			


		} catch (Exception exception) {
			logger.error("AutoConfig_020 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_020 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_020 - test completed\n");
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
