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

public class AutoConfig_057 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-057 Verify that ESM_PUSH_NOTIFICATION_ENABLED parameters could be configured on AADS
	 * "*Configuration:
		AADS cluster system or AADS standalone
		 
		*Steps to Reproduce:
		            1. Login to AADS Admin GUI
		            2. Navigate to Dynamic Configuration page. Click Configuration
		            3. Click seach ESM_PUSH_NOTIFICATION_ENABLED setting in GROUP, GLOBAL
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
	String testCase = "AutoConfig_057";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_057");

	@Before
	public void beforetestAutoConfig_057() throws Exception {
		logger.info("beforetest AutoConfig_057 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_057 completed...\n");
	}

	@Test
	public void testAutoConfig_057() throws Exception {
		logger.info("AutoConfig_057 - Starting\n");
		try {
			boolean s;
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");

				// Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
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
				
				s = androidClient.enableOrDisableExchangeCalendar(androidClientDriver);
				if (s == false)
				{
					logger.info("AutoConfig057 - Step1_Can't disable Exchange Calendar \n");
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step1_Exchange Calendar is disable . Incorrect....\n");
				
				Thread.sleep(2000);
				s = androidClient.enableOrDisableClientEnablement(androidClientDriver);
				if (s == false)
				{
					logger.info("AutoConfig057 - Step1_Can't disable CES \n");
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step1_CES is disable . Incorrect....\n");
				
				Thread.sleep(2000);
				s = androidClient.enableOrDisableAvayaCloudServices(androidClientDriver);
				if (s == false)
				{
					logger.info("AutoConfig057 - Step1_Can't disable Avaya Cloud Services \n");
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step1_Avaya Cloud Services is disable . Incorrect....\n");
				
			//step2
				AADSWebDriver.selectLockedPreferencesCheckbox(webDriver, false);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				
				s = androidClient.enableOrDisableExchangeCalendar(androidClientDriver);
				if (s == true)
				{
					logger.info("AutoConfig057 - Step2_Can disable Exchange Calendar \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step2_Can't disable Exchange Calendar . Incorrect....\n");
			
				Thread.sleep(2000);
				s = androidClient.enableOrDisableClientEnablement(androidClientDriver);
				if (s == true)
				{
					logger.info("AutoConfig057 - Step2_Can disable CES \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step2_Can't disable CES . Incorrect....\n");
				
				Thread.sleep(2000);
				s = androidClient.enableOrDisableAvayaCloudServices(androidClientDriver);
				if (s == true)
				{
					logger.info("AutoConfig057 - Step2_Can disable Avaya Cloud Services \n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step2_Can't Avaya Cloud Services. Incorrect....\n");
				
				Thread.sleep(2000);
				androidClient.signout(androidClientDriver);
				Thread.sleep(2000);
				androidClient.signin(androidClientDriver);
		
				
			//Step3
				Thread.sleep(2000);
				s= androidClient.verifyExchangeCalendar(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig057 - Step3_Exchange Calendar is disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig057- Failed - Exception occurs:  Step3_Exchange Calendar is enable . Incorrect....\n");
				
				Thread.sleep(2000);
				s= androidClient.verifyClientEnablementCES(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig057 - Step3_Client Enablement is disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step3_Client Enablement is enable . Incorrect....\n");
				
				Thread.sleep(2000);
				s= androidClient.verifyAvayaCloudServices(androidClientDriver);
				if(s == false)
				{
					logger.info("AutoConfig057 - Step3_Avaya Cloud Servicesis disable as expected\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig056- Failed - Exception occurs:  Step3_Avaya Cloud Services is enable . Incorrect....\n");
			
			


		} catch (Exception exception) {
			logger.error("AutoConfig_057 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_057 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_057 - test completed\n");
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
