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

public class AutoConfig_047 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-047 Verify that SCEPPASSWORD could be configured on AADS
	 * "Procedures:
		1. Logging into AADS web console
		2. Navigating Dynamic Configuration
		3. Setting value for SCEPPASSWORD 
		4. Checking configuration value for SCEPPASSWORD via Test Configuration as follows:
		Enter the above URL into an appropriate client to test the settings specified in the Test Configuration.
		Expected results:
		1. Successfully logging into AADS web console
		2. Successfully navigating Dynamic Configuration
		3. Successfully setting value for SCEPPASSWORD 
		4. Ensure that the configuration value was available for SCEPPASSWORD"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_047";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_047");

	@Before
	public void beforetestAutoConfig_047() throws Exception {
		logger.info("beforetest AutoConfig_047 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_047 completed...\n");
	}

	@Test
	public void AutoConfig_047() throws Exception {
		logger.info("AutoConfig_047 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				
				// Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SCEPPASSWORD", true, "tma_12Tma");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "SCEPPASSWORD", true, "tma_12Tma");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "SCEPPASSWORD", true, "tma_12Tma");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "SCEPPASSWORD", true, "tma_12Tma");
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"Android");
				
				AADSWebDriver.verify46xx(webDriver,	"SCEPPASSWORD tma_12Tma", testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);

		} catch (Exception exception) {
			logger.error("AutoConfig_047 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_047 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_047 - test completed\n");
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
