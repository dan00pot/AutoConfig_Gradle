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

public class AutoConfig_007 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-007 Group specific setting set parameters to get software update automatically.
	 *"Procedures:
		Group setting:
		1. Enable APPCAST_ENABLED, provide APPCAST_URL   and APPCAST_CHECK_INTERVAL (0-30day)to retrieve software update automatically.
		
		
		Expect result: Client retrieve update info automatically as previous setting."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_007";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_007");

	@Before
	public void beforetestAutoConfig_007() throws Exception {
		logger.info("beforetest AutoConfig_007 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_007 completed...\n");
	}

	@Test
	public void testAutoConfig_007() throws Exception {
		logger.info("AutoConfig_007 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SIP_CONTROLLER_LIST", true, aadsData.SIP_CONTROL_LIST);
				
				// Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_ENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_URL", true, "456.aam1.com:1010");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "APPCAST_CHECK_INTERVAL", true, "22");
				
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"iOS");
				
				AADSWebDriver.verify46xx(webDriver, "APPCAST_ENABLED 1",testCase);
				AADSWebDriver.verify46xx(webDriver, "APPCAST_URL 456.aam1.com:1010",testCase);
				AADSWebDriver.verify46xx(webDriver, "APPCAST_CHECK_INTERVAL 22",testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
			
		} catch (Exception exception) {
			logger.error("AutoConfig_007 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_007 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_007 - test completed\n");
	}

	
	@After
	public void tearDown() {
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
