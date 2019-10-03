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

public class AutoConfig_037 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-037 Verify that ENABLE_OPUS parameters could be configured on AADS
	 * "Objective:
		Verify that ENABLE_OPUS parameters could be configured on AADS
		 
		Configuration:
		AADS cluster system or AADS standalone
		 
		Steps to Reproduce:
		            1. Login to AADS Admin GUI
		            2. Navigate to Dynamic Configuration page. Click Configuration
		            3. Click seach ENABLE_OPUS setting in USER, PLATFORM, GROUP, GLOBAL
		            4. Configuring values for them
		Expected Results:
		                1. Login successfully.
		                2. Configuration page is displayed.
		                3. ENABLE_OPUS setting is displayed. 
		                4. Values are successfuly configured"

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_037";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_037");

	@Before
	public void beforetestAutoConfig_037() throws Exception {
		logger.info("beforetest AutoConfig_037 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_037 completed...\n");
	}

	@Test
	public void AutoConfig_037() throws Exception {
		logger.info("AutoConfig_037 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				
				// Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ENABLE_OPUS", true, "3");
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"Android");
				
				AADSWebDriver.verify46xx(webDriver,	"ENABLE_OPUS 3", testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);

		} catch (Exception exception) {
			logger.error("AutoConfig_037 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_037 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_037 - test completed\n");
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
