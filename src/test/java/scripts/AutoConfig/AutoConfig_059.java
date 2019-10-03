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

public class AutoConfig_059 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-059 Verify that AUTO_AMM_LOOKUP_ENABLED parameters could be configured on AADS
	 * "*Configuration:
		AADS cluster system or AADS standalone
		 
		*Steps to Reproduce:
		            1. Login to AADS Admin GUI
		            2. Navigate to Dynamic Configuration page. Click Configuration
		            3. Click seach TELEPHONY_PUSH_NOTIFICATION_ENABLED setting in GROUP, GLOBAL
		            4. Configuring values for them"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_059";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_059");

	@Before
	public void beforetestAutoConfig_059() throws Exception {
		logger.info("beforetest AutoConfig_059 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_059 completed...\n");
	}

	@Test
	public void testAutoConfig_059() throws Exception {
		logger.info("AutoConfig_059 - Starting\n");
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
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "1");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);

				
				webDriver.switchTo().defaultContent();
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Defaults");
				AADSWebDriver.selectLockedPreferencesCheckbox(webDriver, false);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				

				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				Thread.sleep(2000);
				androidClient.inputValueACSPORT(androidClientDriver, "8443");
				Thread.sleep(2000);
				androidClient.inputValueAMMServerPort(androidClientDriver, "443");
				
//				Thread.sleep(1000);
//				androidClient.signout(androidClientDriver);
//				Thread.sleep(2000);
//				androidClient.signin(androidClientDriver);
				
				String s1, s2 = new String();
				s1 = androidClient.getAADSServerPORT(androidClientDriver);
				s2 = androidClient.getAMMServerPORT(androidClientDriver);
				if (s1.contains("8443") && s2.contains("443"))
				{
					logger.info("Passed");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig059- Failed - Exception occurs: Incorrect....\n");
				
			


		} catch (Exception exception) {
			logger.error("AutoConfig_059 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_059 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		}
		logger.info("AutoConfig_059 - test completed\n");
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
