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

public class AutoConfig_012 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-012 Overriden specific setting verify ESMENABLED on User, Group and Global setting.
	 *"Procedures:
		1. User setting: ESMENABLED=0,  Group setting: ESMENABLED=1, Global setting: ESMENABLED=1
		2. Does not select ESMENABLED on User setting, Group setting: ESMENABLED=1, Global setting: ESMENABLED=0
		3. Does not select ESMENABLED on Group setting, Global setting: ESMENABLED=0
		 
		Expected results: 
		1 Multimedia messaging on Client is disabled as User setting (High priority)
		2. Multimedia messaging on Client is enabled as Group setting (High priority)
		3. Multimedia messaging on Client is disabled as Global setting."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_012";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_012");

	@Before
	public void beforetestAutoConfig_012() throws Exception {
		logger.info("beforetest AutoConfig_012 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_012 completed...\n");
	}

	@Test
	public void testAutoConfig_012() throws Exception {
		logger.info("AutoConfig_012 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ESMENABLED", true, "1");
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
				boolean s;
				s= androidClient.verifyMultimediaMesaging(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig012 - Step1_Multimedia messaging is disable as expected\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig012- Failed - Exception occurs:  Step1_Multimedia messaging is enable . Incorrect....\n");
					assertTrue(false);
				}
				
				//Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", false, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ESMENABLED", true, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
			
				androidClientDriver.resetApp();
				n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
				s= androidClient.verifyMultimediaMesaging(androidClientDriver);
				if(s == true)
				{
					logger.info("AutoConfig012 - Step2_Multimedia messaging is enable as expected\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig012- Failed - Exception occurs:  Step2_Multimedia messaging is disable . Incorrect....\n");
					assertTrue(false);
				}
				
				//Step 3		
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMENABLED", false, "1");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				
				n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
				s= androidClient.verifyMultimediaMesaging(androidClientDriver);
				
				if(s == false)
				{
					logger.info("AutoConfig012 - Step3_Multimedia messaging is disable as expected\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig012- Failed - Exception occurs:  Step3_Multimedia messaging is enable . Incorrect....\n");
					assertTrue(false);
				}
		
		} catch (Exception exception) {
			logger.error("AutoConfig_012 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_012 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_012 - test completed\n");
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
			androidClientDriver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("tearDown completed...\n");
	}
}
