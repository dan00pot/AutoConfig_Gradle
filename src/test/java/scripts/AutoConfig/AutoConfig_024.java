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

public class AutoConfig_024 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-024 User AC settings view  - Can seach for a specific user in Configuration window (UID User email)
	 * "Procedures:
		1. SMGR Login ID has different user name and domain name as compared to the email address in LDAP.
		SMGR Login ID: 5203@gsc-corp.ca
		Email Address: mark.tremblay@greenshield.ca. MS-Exchange address (mark.tremblay@greenshield.ca) need to be added as one of the Communication Address in SMGR user account for this to work.
		2. On AADS GUI, retrieve user settings from SMGR
		
		
		Expected results:
		2. Make sure AADS retrieves user settings from SMGR successfully."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig024";
	
	final static Logger logger = LogManager.getLogger("AutoConfig024");

	@Before
	public void beforetestAutoConfig024() throws Exception {
		logger.info("beforetest AutoConfig024 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig024 completed...\n");
	}

	@Test
	public void testAutoConfig024() throws Exception {
		logger.info("AutoConfig024 - Starting\n");
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
				//if((s1[6].equals("1"))&&(s1[7].equals(aadsData.AADS_RETRIEVE_SIPUSER))&&((s1[8].equals(aadsData.AADS_RETRIEVE_SIP_DOMAIN))))
				if((s1[6].contains("1")))
				{
					logger.info("AutoConfig024 - Retrieved information of user is correct\n");
				}
				else throw new Exception("AutoConfig024- Failed - Exception occurs:  Retrieved information of user is not correct...\n");

		} catch (Exception exception) {
			logger.error("AutoConfig024 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig024 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig024 - test completed\n");
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
