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

public class AutoConfig_061 {
	/*###############################--Test Steps--##############################################
	 * DefaultConfig006_Verify that If select the check Obscure locked settings, the attributes of client will display if this not set from AADS WebUI
	 * "Verify that If select the check Obscure locked settings, the attributes of client will be displayed if these not set from AADS WebUI
		*Procedure:
		1. Login AADS WebUI
		2.Navigate to Dynamic Configuration -> click ""Configuration"" then uncheck for the following fields:
		  In User setting: ESMREFRESH, ACSPORT then publish the configuration setting
		3. Click ""Defaults"" in Dynamic Configuration tab then Select Obscure locked settings check box then click Save button
		4. Access to Equinox client by autoconfig login
		5. - Navigate to Multimedia Messaging tab and change Sever Port's value
		    - Navigate to Device Services tab and change Server Port's value
		6. Logout and relogin Equinox client
		7. - Navigate to Multimedia Messaging tab and verify Server Port's value
		    - Navigate to Device Services tab and verify Server Port's value
		*Expected result:
		1. Login to AADS WebUI successfully
		2. Uncheck for fields above and publish the configuration setting successfully
		3. Select Obscure locked settings then click Save button successffully
		4. Access to Equinox client successfully without error
		5. Verify that:
		    - Navigate to Multimedia Messaging tab and change Server Port's value successffully
		    - Navigate to Device Services tab and change Server Port's value successffully
		6. Re-login Equinox client without error
		7. Verify that:
		    - Value has been changed in Server Port of Multimedia Messaging tab
		    - Value has been changed in Server Port of Device Services tab"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_001";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_061");

	@Before
	public void beforetestAutoConfig_061() throws Exception {
		logger.info("beforetest AutoConfig_061 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_061 completed...\n");
	}

	@Test
	public void testAutoConfig_061() throws Exception {
		logger.info("AutoConfig_061 - Starting\n");
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
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
		
				webDriver.switchTo().defaultContent();
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Defaults");
				AADSWebDriver.selectObsecuredPreferenceCheckbox(webDriver, true, false);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				Thread.sleep(2000);
				androidClient.inputValueACSPORT(androidClientDriver, "8443");
				Thread.sleep(2000);
				androidClient.inputValueAMMServerPort(androidClientDriver, "443");
				
				Thread.sleep(1000);
				androidClient.signout(androidClientDriver);
				Thread.sleep(2000);
				androidClient.signin(androidClientDriver);
				
				String s1, s2 = new String();
				s1 = androidClient.getAADSServerPORT(androidClientDriver);
				s2 = androidClient.getAMMServerPORT(androidClientDriver);
				if (s1.contains("8443") && s2.contains("443"))
				{
					logger.info("Passed");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig061- Failed - Exception occurs: Incorrect....\n");


		} catch (Exception exception) {
			logger.error("AutoConfig_061 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_061 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} finally {
			webDriver.get(aadsData.AADS_SERVER_ADDRESS);
			AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
			Thread.sleep(1000);
			AADSWebDriver.configurationPageSelectConfigurationUser(webDriver,sheet);
			AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN ,aadsData.AADS_USER_1_NAME);
			AADSWebDriver.configurationPageSelectConfigurationUser(webDriver,testCase);
			Thread.sleep(1000);
			AADSWebDriver.configurationPageDeleteConfiguration(webDriver, testCase);
			AADSWebDriver.logoutAADSMainPage(webDriver);
		}
		logger.info("AutoConfig_061 - test completed\n");
	}

	
	@After
	public void tearDown() throws Exception {
		logger.info("tearDown starting...\n");
		webDriver.close();
		logger.info("tearDown completed...\n");
	}
}
