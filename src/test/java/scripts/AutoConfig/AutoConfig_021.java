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

public class AutoConfig_021 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-021 Overriden specific setting verify ACSENABLED on User, Group and Global setting.
	 *"Procedures:
		1. User setting: ACSENABLED=0,  Group setting: ACSENABLED=1, Global setting: ACSENABLED=1
		2. Does not select ACSENABLED on User setting, Group setting: ACSENABLED = 1, Global setting: ACSENABLED=0
		3. Does not select ACSENABLED on Group setting, Global setting: ACSENABLED=0
		 
		Expected results: 
		1. Device Service on Client is disabled as User setting (High priority)
		2. Device Service on Client is enabled as Group setting (High priority)
		3. Device Service on Client is disabled as Global setting."

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_021";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_021");

	@Before
	public void beforetestAutoConfig_021() throws Exception {
		logger.info("beforetest AutoConfig_021 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_021 completed...\n");
	}

	@Test
	public void testAutoConfig_021() throws Exception {
		logger.info("AutoConfig_021 - Starting\n");
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
				//step1
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSENABLED", true, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ACSENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ACSENABLED", true, "1");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				boolean s;
				s= androidClient.verifyDeviceServices(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig021 - Step1_Multimedia messaging is disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig021- Failed - Exception occurs:  Step1_Multimedia messaging is enable . Incorrect....\n");
				
				//Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSENABLED", false, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ACSENABLED", true, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				s= androidClient.verifyDeviceServices(androidClientDriver);
				if(s == true)
				{
					logger.info("AutoConfig021 - Step2_Multimedia messaging is enable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig021- Failed - Exception occurs:  Step2_Multimedia messaging is disable . Incorrect....\n");
				
				//Step 3		
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ACSENABLED", false, "1");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				s= androidClient.verifyDeviceServices(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig021 - Step3_Multimedia messaging is disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig021- Failed - Exception occurs:  Step3_Multimedia messaging is enable . Incorrect....\n");
				

		} catch (Exception exception) {
			logger.error("AutoConfig_021 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_021 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_021 - test completed\n");
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
