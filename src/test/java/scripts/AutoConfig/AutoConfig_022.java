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

public class AutoConfig_022 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-022 Publish new user info
	 *"Procedures:
		1. Configure AD 2012/2016, LDS2008/2012 as a LDAP of AADS server 
		2. User setting: ACSENABLED=0,  Group setting: ACSENABLED=1, Global setting: ACSENABLED=1
		3. Does not select ACSENABLED on User setting, Group setting: ACSENABLED = 1, Global setting: ACSENABLED=0
		4. Does not select ACSENABLED on Group setting, Global setting: ACSENABLED=0
		5. Set all required settings on User, Global and Group as below then publisg the configuration for user on Domino LDAP
		ESMENABLED, ESMUSERNAME, ESMPASSWORD
		SIPENABLED, SIP_CONTROLLER_LIST, SIPDOMAIN, SIPUSERNAME, SIPPASSWORD
		ACSENABLED, ACSSRVR, ACSPORT, ACSUSERNAME, ACSPASSWORD
		 APPCAST_ENABLED, APPCAST_URL
		FIPS_ENABLED
		EWSENABLED, EWSSERVERADDRESS, EWSDOMAIN
		And so on.
		Expected results: 
		1. Successfully configure Domino server as a LDAP on AADS server
		2. Device Service on Client is disabled as User setting (High priority)
		3. Device Service on Client is enabled as Group setting (High priority)
		4. Device Service on Client is disabled as Global setting.
		5. Successfully publish the configuration for Domino user"

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_022";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_022");

	@Before
	public void beforetestAutoConfig_022() throws Exception {
		logger.info("beforetest AutoConfig_022 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_022 completed...\n");
	}

	@Test
	public void testAutoConfig_022() throws Exception {
		logger.info("AutoConfig_022 - Starting\n");
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
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_FOR_PUBLISH_NEW);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_FOR_PUBLISH_NEW, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
		
			


		} catch (Exception exception) {
			logger.error("AutoConfig_022 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_022 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_022 - test completed\n");
	}

	
	@After
	public void tearDown(){
		logger.info("tearDown starting...\n");
		try {
			webDriver.get(aadsData.AADS_SERVER_ADDRESS);
			AADSWebDriver.logoutAADSMainPage(webDriver);
			webDriver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("tearDown completed...\n");
	}
}
