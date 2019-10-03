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

public class AutoConfig_008 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-008 Platform specific setting The configurations of any platform are different.
	 *"Procedures:
		Platform setting: Set any parameter, select Android/ iOS/ Windows/Mac to publish.
		
		
		Expected result: Enter User and Platform, retrieve data. Make sure the setting 
						of any platform are different."
	 *###########################################################################################*/
	
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_008";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_008");

	@Before
	public void beforetestAutoConfig_008() throws Exception {
		logger.info("beforetest AutoConfig_008 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_008 completed...\n");
	}

	@Test
	public void testAutoConfig_008() throws Exception {
		logger.info("AutoConfig_008 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "DIRENABLED", true, "1");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver, aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME, "iOS");
				
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, testCase);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Platform", "DIRENABLED", true, "0");
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver, aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME, "Android");
				
				// Step 2 - Verify between iOS and Android platform
				AADSWebDriver.configurationPageRetriveUserDataIncludeFlatform(webDriver, aadsData.AADS_USER_1_NAME, "iOS");
				String verify1 = AADSWebDriver.configurationPageGetVariable(webDriver, "Platform", "DIRENABLED");
	
				
				AADSWebDriver.configurationPageRetriveUserDataIncludeFlatform(webDriver, aadsData.AADS_USER_1_NAME, "Android");
				Thread.sleep(3000);
				logger.info("AutoConfig_008 - Check");
				String verify2 = AADSWebDriver.configurationPageGetVariable(webDriver, "Platform", "DIRENABLED");
				
				if (verify1.equalsIgnoreCase(verify2)){
					assertTrue(false);
				}
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
			
		} catch (Exception exception) {
			logger.error("AutoConfig_008 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_008 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_008 - test completed\n");
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
