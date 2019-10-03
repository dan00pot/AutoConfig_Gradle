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

public class AutoConfig_023 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-023 Retrive SMGR User Settings when SMGR Login ID mismatched with Email Address
	 *"Procedures:
		1. SMGR Login ID has different user name and domain name as compared to the email address in LDAP.
		SMGR Login ID: 5203@gsc-corp.ca
		Email Address: mark.tremblay@greenshield.ca. MS-Exchange address (mark.tremblay@greenshield.ca) need to be added as one of the Communication Address in SMGR user account for this to work.
		2. On AADS GUI, retrieve user settings from SMGR
		 
		Expected results:
		2. Make sure AADS retrieves user settings from SMGR successfully.
		
		
		***Note: Reference jira: ACS-3724"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_023";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_023");

	@Before
	public void beforetestAutoConfig_023() throws Exception {
		logger.info("beforetest AutoConfig_023 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_023 completed...\n");
	}

	@Test
	public void testAutoConfig_023() throws Exception {
		logger.info("AutoConfig_023 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");

				String s1[] = new String[9];
				s1 = AADSWebDriver.configurationPageRetriveUserSMGR(webDriver,
						aadsData.AADS_USER_1_NAME);
				if((s1[0].contains("1"))&&(s1[5].contains(aadsData.SM_IP_ADDRESS)))
				{
					logger.info("AutoConfig023 - Retrieved information of user is correct\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig023- Failed - Exception occurs:  Retrieved information of user is not correct...\n");

		} catch (Exception exception) {
			logger.error("AutoConfig_023 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_023 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_023 - test completed\n");
	}

	
	@After
	public void tearDown() throws Exception {
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
