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

public class AutoConfig_028 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-028 Verify Auto Configuration if an user does not have User settings in DB
	 * "Objective: To verify that Auto Configuration shows correctly if an user does not have User settings in DB
 
		Configuration:
		AADS standalone or AADS cluster in latest build
		Configure some Auto Configs on AADS
		Ensure iView 9.0/8.5 connection was established
		 
		Procedures:
		Add the Auto Config A which does not have any settings on USER level, only add Virtual Room Number on iView (CONFERENCE_VIRTUAL_ROOM)
		Select the Auto Config added in step 1 and run TEST.
		Publish this Auto Config for a user and login this AADS user to check CONFERENCE_VIRTUAL_ROOM
		 
		Expected results:
		  
		The Auto Config is added successfully with Virtual Room Number on iView only.
		CONFERENCE_VIRTUAL_ROOM presented in 46xxsettings output
		CONFERENCE_VIRTUAL_ROOM presented in MEETINGS services setting of user
		 
		Notes: Reference jira /ACS-3728"
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_028";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_028");

	@Before
	public void beforetestAutoConfig_028() throws Exception {
		logger.info("beforetest AutoConfig_028 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_028 completed...\n");
	}

	@Test
	public void testAutoConfig_028() throws Exception {
		logger.info("AutoConfig_028 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "CONFERENCE_VIRTUAL_ROOM", true, "12");
				
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				AADSWebDriver.verify46xx(webDriver, "CONFERENCE_VIRTUAL_ROOM 12",testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(n) {
					System.out.println("testAutoconfigLogin - PASSED... \n");
				}
				else System.out.println("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				
		
			


		} catch (Exception exception) {
			logger.error("AutoConfig_028 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_028 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_028 - test completed\n");
	}

	
	@After
	public void tearDown() {
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
