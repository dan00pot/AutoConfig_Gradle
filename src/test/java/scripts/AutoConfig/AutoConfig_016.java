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

public class AutoConfig_016 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-016 Overriden specific setting verify SIP SERVER on User and SMGR setting.
	 *"Procedures:
		1. On SMGR SIP_CONTROLLER_LIST= server1, transport=tls
		     On User setting: SIP_CONTROLLER_LIST = server2, transport =tls
		2. On SMGR SIP_ENABLE = 1, User setting: SIP_ENABLE = 0.
		 
		Expected result:
		1-2. Client use setting on SMGR."

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_016";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_016");

	@Before
	public void beforetestAutoConfig_016() throws Exception {
		logger.info("beforetest AutoConfig_016 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_016 completed...\n");
	}

	@Test
	public void testAutoConfig_016() throws Exception {
		logger.info("AutoConfig_016 - Starting\n");
		String[] status = new String[2];
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
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SIPENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SIP_CONTROLLER_LIST", true, aadsData.SIP_SERVER2_NAME);
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				status= androidClient.getSIPServer(androidClientDriver);
				if(status[0].equals(aadsData.SIP_SERVER1_NAME)&&status[1].equals(aadsData.SIP_SERVER1_PORT_NAME))
				{
					logger.info("AutoConfig016 - Step1  SIP Sever are correct .....\n");
				}
				else throw new Exception("AutoConfig016- Failed - Exception occurs:  Step 1 SIP Sever are incorrect .....\n");
		
		//step2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SIPENABLED", true, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				boolean s;
				s= androidClient.verifyPhoneService(androidClientDriver);
				if(s == true)
				{
					logger.info("AutoConfig016 - Phone Service still enable cause retriving data from SMGR\n");
				}
				else throw new Exception("AutoConfig016 - Failed - Exception occurs:  Phone Service is disable . Incorrect....\n");
				assertTrue(true);
				
		
		} catch (Exception exception) {
			logger.error("AutoConfig_016 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_016 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_016 - test completed\n");
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
