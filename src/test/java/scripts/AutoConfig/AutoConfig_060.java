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

public class AutoConfig_060 {
	/*###############################--Test Steps--##############################################
	 * DefaultConfig001_Verify that user has to input phone details to login Equinox Client in case Allow passwords is unchecked
	 * "Verify that user has to input phone details to login Equinox Client in case Allow passwords is unchecked
		*Procedure:
		1. Login AADS WebUI
		2. Navigate to Dynamic Configuration tab then click Defaults
		3. Check Allow passwords  then click Save button
		4. Navigate to Dynamic Configuration tab then publish the configuration setting
		5. Access to Equinox client by autoconfig login
		6. In AADS WebUI, navigate to Dynamic Configuration tab then click Defaults
		7. Uncheck Allow passwords then click Save button
		8. Reset Equinox client then log into Equinox client by autoconfig login
		*Expected result:
		1. Login to AADS WebUI successfully
		2. Navigate to Dynamic Configuration tab then click Defaults successfully
		3. Check Allow passwords  then click Save button successffully
		4. Publish the configuration setting successffully
		5. Access to Equinox client successfully without error
		6. Navigate to Dynamic Configuration tab then click Defaults successfully
		7. Uncheck  Allow passwords then click Save button  successfully
		8. User has to input phone details to login"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_060";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_060");

	@Before
	public void beforetestAutoConfig_001() throws Exception {
		logger.info("beforetest AutoConfig_060 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_060 completed...\n");
	}

	@Test
	public void testAutoConfig_060() throws Exception {
		logger.info("AutoConfig_060 - Starting\n");
		try {
			boolean s;
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "1");
				// Step 2
				AADSWebDriver.configurationPageCommAddrHandle(webDriver, "Avaya SIP", "5");
				
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				AADSWebDriver.verify46xx(webDriver,	aadsData.SIP_CONTROL_LIST, testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
				webDriver.switchTo().defaultContent();
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Defaults");
				AADSWebDriver.selectLockedPreferencesCheckbox(webDriver, true);
				AADSWebDriver.selectObsecuredPreferenceCheckbox(webDriver, true, true);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
			
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				
/*				s = androidClient.verifyMultimediaMessagingExisted(androidClientDriver, "Multimedia Messaging");
				if(s == true)
				{
					logger.info("AutoConfig060 - Step1_Multimedia Messaging not existed expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step1_Multimedia Messaging is existed . Incorrect....\n");
				*/
				Thread.sleep(2000);
				s = androidClient.verifyMultimediaMessagingExisted(androidClientDriver, "Device Services");
				if(s == true)
				{
					logger.info("AutoConfig060 - Step1_Multimedia Messaging not existed expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step1_Multimedia Messaging is existed . Incorrect....\n");
				
				
			//Step2
				AADSWebDriver.selectObsecuredPreferenceCheckbox(webDriver, true, false);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				s = androidClient.verifyMultimediaMessagingExisted(androidClientDriver, "Multimedia Messaging");
				if(s == false)
				{
					logger.info("AutoConfig060 - Step1_Multimedia Messaging is existed expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step1_Multimedia Messaging not existed . Incorrect....\n");
				Thread.sleep(2000);
				s = androidClient.verifyMultimediaMessagingExisted(androidClientDriver, "Device Services");
				if(s == false)
				{
					logger.info("AutoConfig060 - Step1_Multimedia Messaging is existed expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig_060- Failed - Exception occurs:  Step1_Multimedia Messaging not existed . Incorrect....\n");
			


		} catch (Exception exception) {
			logger.error("AutoConfig_060 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_060 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_060 - test completed\n");
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
