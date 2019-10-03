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

public class AutoConfig_025 {

	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_025";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_025");

	@Before
	public void beforetestAutoConfig_025() throws Exception {
		logger.info("beforetest AutoConfig_025 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_025 completed...\n");
	}

	@Test
	public void testAutoConfig_025() throws Exception {
		logger.info("AutoConfig_025 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");

					
				String s1[] = new String[9];
				s1 = AADSWebDriver.configurationPageRetriveUserSMGR(webDriver,aadsData.AADS_USER_1_NAME);
				if((s1[4].contains("5061"))&&(s1[3].contains(aadsData.SIP_CONTROL_LIST)))
		
				{
					logger.info("AutoConfig025 - SIP information of user is correct\n");
				}
				else throw new Exception("AutoConfig025- Failed - Exception occurs:  SIP information of user is not correct...\n");
				
		
			


		} catch (Exception exception) {
			logger.error("AutoConfig_025 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_025 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_025 - test completed\n");
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
