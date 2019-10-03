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

public class AutoConfig_001 {
/*###############################--Test Steps--####################################
 * AutoConfig-001 User specific setting SIP_CONTROLLER_LIST with multiple phone_server, transport types
	"Procedures:
	User Setting : Set SIP_CONTROLLER_LIST: server1.com:5060; transport=tcp,server1.com:5061; transport=tls 
	Group setting: Set COMM_ADDR_HANDLE_TYPE: Avaya SIP and COMM_ADDR_HANDLE_LENGTH: 5
	Save this configuration
	Publish this configuration for a new added user
	 
	Expected results:
	3-4: The configuration is configured successfully and it publishes to new user successfully.
	46xxsetting.txt shows the first transport: server1.com:5060; transport=tcp"
 *################################################################################# */
	
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_001";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_001");

	@Before
	public void beforetestAutoConfig_001() throws Exception {
		logger.info("beforetest AutoConfig_001 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_001 completed...\n");
	}

	@Test
	public void testAutoConfig_001() throws Exception {
		logger.info("AutoConfig_001 - Starting\n");
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SIP_CONTROLLER_LIST", true, aadsData.SIP_CONTROL_LIST);
				
				// Step 2
				AADSWebDriver.configurationPageCommAddrHandle(webDriver, "Avaya SIP", "5");
				
				// Step 3-4
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				AADSWebDriver.verify46xx(webDriver,	aadsData.SIP_CONTROL_LIST, testCase);
				
				androidClientDriver.resetApp();
				boolean n = androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				
				if(n) {
					logger.info("testAutoconfigLogin - PASSED... \n");
				}
				else logger.info("testAutoconfigLogin - FAILED... \n");
				
				assertTrue(n);
				

		} catch (Exception exception) {
			logger.error("AutoConfig_001 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_001 - Failed - Exception occurs: "
					+ exception.toString());
		}
		logger.info("AutoConfig_001 - test completed\n");
	}

	
	@After
	public void tearDown(){
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
