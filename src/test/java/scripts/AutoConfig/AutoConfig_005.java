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

public class AutoConfig_005 {
	/*###############################--Test Steps--####################################
	 * AutoConfig-005 User specific setting  retrieve SIP infomations from SMGR correctly
	 *"Procedures:
		1.SIP service has 2 listen port: 5060 and 5061. Both of them are Endpoint.
		2. User has extension belong to this SIP service.
		3. Dynamic configuration-->Retrieve info of user from SMGR.
		
		
		Expected result:
		3. SIP PORT=5061 and SIP SECURE=1 (TLS protocol is high priority)"
	 *################################################################################# */
	
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_005";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_005");

	@Before
	public void beforetestAutoConfig_005() throws Exception {
		logger.info("beforetest AutoConfig_005 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_005 completed...\n");
	}

	@Test
	public void testAutoConfig_005() throws Exception {
		logger.info("AutoConfig_005 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");

				String s1[] = new String[9];
				s1 = AADSWebDriver.configurationPageRetriveUserSMGR(webDriver,aadsData.AADS_USER_1_NAME);
				
			//Verify retrieve data	
				if((s1[4].contains("5061"))&&(s1[6].contains("1")))
				{
					logger.info("AutoConfig005 - SIP information of user is correct\n");
					assertTrue(true);
				}

		} catch (Exception exception) {
			logger.error("AutoConfig_005 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_005 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		}
		logger.info("AutoConfig_005 - test completed\n");
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
