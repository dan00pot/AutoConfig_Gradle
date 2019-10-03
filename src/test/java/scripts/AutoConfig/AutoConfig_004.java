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

public class AutoConfig_004 {
	/*###############################--Test Steps--####################################
	 * AutoConfig-004 Group specific setting Config parameters to retrieved full infomartions from SMGR.
	 *"Procedures:
		Group setting :
		1.COMM_ADDR_HANDLE_TYPE select  Avaya SIP and COMM_ADDR_HANDLE_LENGTH = does not match with length of extension
		2.COMM_ADDR_HANDLE_LENGTH = Match with length of extension
		
		
		Expected result: 
		1.Table retrieved from SMGR  does not  included SIPDOMAIN, SIPUSERNAME
		2.Table retrieved from SMGR  included SIPDOMAIN, SIPUSERNAME"
	 *################################################################################# */
	
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_004";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_004");

	@Before
	public void beforetestAutoConfig_004() throws Exception {
		logger.info("beforetest AutoConfig_004 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_004 completed...\n");
	}

	@Test
	public void testAutoConfig_004() throws Exception {
		logger.info("AutoConfig_004 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");

					
				//Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "COMM_ADDR_HANDLE_LENGTH", true, "3");
				AADSWebDriver.configurationPageHandleType(webDriver, "Group", "COMM_ADDR_HANDLE_TYPE", "Avaya SIP");
				Thread.sleep(5000);
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				
				//Verify that Table retrieved from SMGR  does not  included SIPDOMAIN, SIPUSERNAME
				AADSWebDriver.verifySMGRVariableDisplay(webDriver, aadsData.AADS_USER_1_NAME, "SIPDOMAIN", false);
				AADSWebDriver.verifySMGRVariableDisplay(webDriver, aadsData.AADS_USER_1_NAME, "SIPUSERNAME", false);
				
				//Step 2
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, testCase);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "COMM_ADDR_HANDLE_LENGTH", true, "5");
				AADSWebDriver.configurationPageHandleType(webDriver, "Group", "COMM_ADDR_HANDLE_TYPE", "Avaya SIP");
				Thread.sleep(5000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				Thread.sleep(5000);
				
				//Verify that Table retrieved from SMGR included SIPDOMAIN, SIPUSERNAME
				AADSWebDriver.verifySMGRVariableDisplay(webDriver, aadsData.AADS_USER_1_NAME, "SIPDOMAIN", true);
				AADSWebDriver.verifySMGRVariableDisplay(webDriver, aadsData.AADS_USER_1_NAME, "SIPUSERNAME", true);
				
				assertTrue(true);
		} catch (Exception exception) {
			logger.error("AutoConfig_004 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_004 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_004 - test completed\n");
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
