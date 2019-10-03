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

public class AutoConfig_056 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-056 Verify that TELEPHONY_PUSH_NOTIFICATION_ENABLED parameters could be configured on AADS
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
	String testCase = "AutoConfig_056";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_056");

	@Before
	public void beforetestAutoConfig_056() throws Exception {
		logger.info("beforetest AutoConfig_056 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_056 completed...\n");
	}

	@Test
	public void testAutoConfig_056() throws Exception {
		logger.info("AutoConfig_056 - Starting\n");
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
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				webDriver.switchTo().defaultContent();
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Defaults");
				AADSWebDriver.selectLockedPreferencesCheckbox(webDriver, true);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(2000);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
			
//				s = androidClient.enableOrDisableMultimediaMesaging(androidClientDriver);
//				if (s == false)
//				{
//					logger.info("AutoConfig056 - Step1_Can't disable Multimedia messaging \n");
//					assertTrue(true);
//				}
//				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step1_Multimedia messaging is disable . Incorrect....\n");
//				Thread.sleep(2000);
				s = androidClient.enableOrDisableDeviceServices(androidClientDriver);
				if (s == false)
				{
					logger.info("AutoConfig056 - Step1_Can't disable Device Services \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step1_Device Services is disable . Incorrect....\n");
				
				Thread.sleep(2000);
				s = androidClient.enableOrDisablePhoneService(androidClientDriver);
				if (s == false)
				{
					logger.info("AutoConfig056 - Step1_Can't disable Phone Services \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step1_Phone Service is disable . Incorrect....\n");
				
			//step2
				AADSWebDriver.selectLockedPreferencesCheckbox(webDriver, false);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				
/*				s = androidClient.enableOrDisableMultimediaMesaging(androidClientDriver);
				if (s == true)
				{
					logger.info("AutoConfig056 - Step2_Can disable Multimedia Messaging \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step2_Can't disable Miltimedia Messaging . Incorrect....\n");
				*/
				Thread.sleep(2000);
				s = androidClient.enableOrDisableDeviceServices(androidClientDriver);
				if (s == true)
				{
					logger.info("AutoConfig056 - Step2_Can disable Device Services \n");
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step2_Can't disable Device Services . Incorrect....\n");
				
				Thread.sleep(2000);
				s = androidClient.enableOrDisablePhoneService(androidClientDriver);
				if (s == true)
				{
					logger.info("AutoConfig056 - Step2_Can disable Phone Service \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step2_Can't disable Phone Service . Incorrect....\n");
				
				
//				Thread.sleep(2000);
//				androidClient.signout(androidClientDriver);
//				Thread.sleep(1000);
//				androidClient.signin(androidClientDriver);
				
				
			//Step3
/*				Thread.sleep(2000);
				s= androidClient.verifyMultimediaMesaging(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig056 - Step3_Multimedia messaging is disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step3_Multimedia messaging is enable . Incorrect....\n");
				*/
				Thread.sleep(2000);
				s= androidClient.verifyDeviceServices(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig056 - Step3_Device Services is disable as expected\n");
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step3_Device Services is enable . Incorrect....\n");
				
				Thread.sleep(2000);
				s= androidClient.verifyPhoneService(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig056 - Step3_Phone Service is disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step3_Phone Service is enable . Incorrect....\n");
	
			


		} catch (Exception exception) {
			logger.error("AutoConfig_056 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_056 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_056 - test completed\n");
	}

	
	@After
	public void tearDown()  {
		logger.info("tearDown starting...\n");
		try {
			webDriver.get(aadsData.AADS_SERVER_ADDRESS);
			AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
			Thread.sleep(1000);
			AADSWebDriver.configurationPageSelectConfigurationUser(webDriver,sheet);
			AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN ,aadsData.AADS_USER_1_NAME);
			Thread.sleep(1000);
			AADSWebDriver.logoutAADSMainPage(webDriver);
			webDriver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("tearDown completed...\n");
	}
}
