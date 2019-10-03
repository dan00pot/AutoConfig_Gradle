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

public class AutoConfig_027 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-027 Verify Excluded Parameters in Configuration in User Settings
	 *"Objective:  To verify that excluded parameters in Auto Configuration are not displayed in User Settings
 
		Configuration:
		AADS standalone or AADS cluster in latest build
		Configure some Auto Configs on AADS
		 
		Procedures:
		Open Dynamic Configuration page
		Add a new Auto Config and leave some parameters unchecked but set value for it. (Set values for the parameters but do not enable them)
		Save the config and test the URL
		Publish the config for user
		
		
		Expected results:
		1-2. The Auto Config is added successfully
		3.     The excluded parameters (e.g. CES and etc) are not shown up in test URL
		4.     The excluded parameters (e.g. CES and etc) are not shown up in user setting
		
		
		***Note:
		There is an issue with client, it gets crashed when VideoMaxBandwidth is enabled in Auto Config file. It cannot parse the value and crashed. Please note 1280 is the correct value for this attribute.
		
		
		Reference jira: ACS-3718"

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_027";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_027");

	@Before
	public void beforetestAutoConfig_027() throws Exception {
		logger.info("beforetest AutoConfig_027 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_027 completed...\n");
	}

	@Test
	public void testAutoConfig_027() throws Exception {
		logger.info("AutoConfig_027 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");

				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "DIRSRVR", false, aadsData.ENTERPRISE_DIRECTORY);
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, "73913");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				AADSWebDriver.verifyUnselectedVariable(webDriver, "DIRSRVR", "73913");
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);

		} catch (Exception exception) {
			logger.error("AutoConfig_027 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_027 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_027 - test completed\n");
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
			Thread.sleep(1000);
			AADSWebDriver.configurationPageSelectConfigurationUser(webDriver,"73913");
			Thread.sleep(1000);
			AADSWebDriver.configurationPageDeleteConfiguration(webDriver, "73913");
			AADSWebDriver.logoutAADSMainPage(webDriver);
			webDriver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("tearDown completed...\n");
	}
}
