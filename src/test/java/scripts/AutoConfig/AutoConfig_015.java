package scripts.AutoConfig;

import libs.clients.AADSWebKeywords;
import libs.clients.AndroidClientKeywords;
import libs.clients.SMGRKeywords;
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

public class AutoConfig_015 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-015 Overriden specific setting verify ESMSRVR on SMGR, Group and Global setting.
	 *"Procedures:
		1. On SMGR User select AMMserver1, Group setting: AMMServer2, Global setting: AMMServer3.
		2. On SMGR User does not select AMMServer1, Group setting: AMMServer2, Global setting: AMMServer3.
		3. Only select Global setting: AMMServer3.
		
		
		Expected results:
		1. AMM server is AMMserver1 as SMGR setting.
		2. AMM server is AMMserver2 as Group setting.
		3. AMM server is AMMserver3 as Global setting."
	 *###########################################################################################*/
	static WebDriver webDriver;
	static WebDriver webDriver2;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	SMGRKeywords SMGRWebDriver = new SMGRKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_015";
	String SMGRversion = aadsData.SMGR_VERSION;
	
	final static Logger logger = LogManager.getLogger("AutoConfig_015");

	@Before
	public void beforetestAutoConfig_015() throws Exception {
		logger.info("beforetest AutoConfig_015 starting...\n");
		webDriver = driverMgnt.createChromeDriver();
		webDriver2 = driverMgnt.createChromeDriver();
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		logger.info("beforetest AutoConfig_015 completed...\n");
	}

	@Test
	public void testAutoConfig_015() throws Exception {
		logger.info("AutoConfig_015 - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
			//Login AADS
				webDriver.get(aadsData.AADS_SERVER_ADDRESS);
				AADSWebDriver.loginAADSMainPage(webDriver, aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
			//Navigate to Configuration Page
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				//AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");

				webDriver2.get(aadsData.SMGR_WEB_ADDR);
				SMGRWebDriver.loginSMGRMainPage(webDriver2, aadsData.SMGR_ADMIN_USR, aadsData.SMGR_ADMIN_PWD);
				SMGRWebDriver.navigatetoUserManagement(webDriver2);
				SMGRWebDriver.manageUserSearchUserBySipPhone(webDriver2, aadsData.AADS_USER_1_NAME_SIP_PHONE);
				SMGRWebDriver.manageUserPageActionUser(webDriver2, "Edit");
				SMGRWebDriver.manageUserEditPresenceProfile(webDriver2, true, aadsData.AMM_SERVER1_NAME_SMGR);
				
				

				
				// Step 1
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMSRVR", true, aadsData.AMM_SERVER2_NAME);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global", "ESMSRVR", true, aadsData.AMM_SERVER3_NAME);

				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if((androidClient.getAMMServer(androidClientDriver)).contains(aadsData.AMM_SERVER1_NAME_SMGR))
				{
					logger.info("AutoConfig015 - Step1  AMM Sever are correct .....\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig015- Failed - Exception occurs:  Step 1 AMM Sever is incorrect .....\n");
		
		//step2
				SMGRWebDriver.manageUserPageActionUser(webDriver2, "Edit");
				SMGRWebDriver.manageUserEditPresenceProfile(webDriver2, false, aadsData.AMM_SERVER1_NAME_SMGR);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if((androidClient.getAMMServer(androidClientDriver)).contains(aadsData.AMM_SERVER2_NAME_CLIENT))
				{
					logger.info("AutoConfig015 - Step2  AMM Sever are correct .....\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig015- Failed - Exception occurs:  Step 2 AMM Sever are incorrect .....\n");
		//step3
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "ESMSRVR", false, aadsData.AMM_SERVER2_NAME);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				androidClient.autoConfigLogin(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if((androidClient.getAMMServer(androidClientDriver)).contains(aadsData.AMM_SERVER3_NAME_CLIENT))
				{
					logger.info("AutoConfig015 - Step3  AMM Sever are correct .....\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig015- Failed - Exception occurs:  Step 3 AMM Sever are incorrect .....\n");

		} catch (Exception exception) {
			logger.error("AutoConfig_015 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_015 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		}
		logger.info("AutoConfig_015 - test completed\n");
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
			SMGRWebDriver.manageUserPageActionUser(webDriver2, "Edit");
			SMGRWebDriver.manageUserEditPresenceProfile(webDriver2, true, aadsData.AMM_SERVER1_NAME_SMGR);
			webDriver.close();
			webDriver2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("tearDown completed...\n");
	}
}
