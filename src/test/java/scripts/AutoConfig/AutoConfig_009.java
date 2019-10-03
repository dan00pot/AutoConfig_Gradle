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

public class AutoConfig_009 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-009 Group search Input characters to search Group, verify response time.
	 *"Procedures:
		Dynamic config ---> Search Cretira:
		1.Input less than 5 character to search groups, check response time.
		2.Input more than 5 character to search groups, check response time.
		
		
		Expected resutls:
		1.Group not showed unless press ENTER.
		2.Group included 5 appropriate character are showed fully."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_009";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_009");

	@Before
	public void beforetestAutoConfig_009() throws Exception {
		logger.info("beforetest AutoConfig_009 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_009 completed...\n");
	}

	@Test
	public void testAutoConfig_009() throws Exception {
		logger.info("AutoConfig_009 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");
			
				//Step 1	
				AADSWebDriver.configurationPageInputRetrivedGroup(webDriver, "Adm");
				Thread.sleep(3000);
				AADSWebDriver.configurationPageVerifyGroupDisplay(webDriver, false);
				Thread.sleep(3000);
			
				
			//Step 2
				AADSWebDriver.configurationPageInputRetrivedGroup(webDriver, "Admin");
				Thread.sleep(3000);
				AADSWebDriver.configurationPageVerifyGroupDisplay(webDriver, true);
				Thread.sleep(3000);
				assertTrue(true);


		} catch (Exception exception) {
			logger.error("AutoConfig_009 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_009 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		}
		logger.info("AutoConfig_009 - test completed\n");
	}

	
	@After
	public void tearDown() {
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
