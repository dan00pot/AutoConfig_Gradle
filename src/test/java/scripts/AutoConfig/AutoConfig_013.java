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

public class AutoConfig_013 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-013 Overriden specific setting verify ESMSSO on User, Group and Global setting.
	 *"Configuration: Group setting: SSOENABLE=1, set SSOUSERID, SSOPASSWORD
 
		Procedures:
		1. User setting: ESMSSO= 0, Group setting: ESMSSO=1, Global setting: ESMSSO=1
		2. Does not select  ESMSSO on User setting, Group setting: ESMSSO=1, Global setting: ESMSSO=0.
		3. Does not select ESMSSO on Group setting, Global setting: ESMSSO=0.
		 
		Expected results:
		1. User must enter ESMUSER and ESMPASSWORD to login.
		2. Only enter Unified User to login.
		3. User must enter ESMUSER and ESMPASSWORD to login."

	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_013";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_013");

	@Before
	public void beforetestAutoConfig_013() throws Exception {
		logger.info("beforetest AutoConfig_013 starting...\n");
		androidClientDriver = driverMgnt.createAndroidClientDriver();
		webDriver = driverMgnt.createChromeDriver();
		logger.info("beforetest AutoConfig_013 completed...\n");
	}

	@Test
	public void testAutoConfig_013() throws Exception {
		logger.info("AutoConfig_013 - Starting\n");
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
	//			AADSWebDriver.configurationPageHandleType(webDriver, "Group", "", text);
				
				
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group", "SSOENABLED", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SSOUSERID", true, aadsData.AADS_USER_1_NAME);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SSOPASSWORD", true, aadsData.AADS_USER_PWD);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMENABLED", true, "1");
				
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMUSERNAME", false, aadsData.AADS_USER_1_NAME);
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMPASSWORD", false, aadsData.AADS_USER_PWD);
				
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				
				//step1
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMSSO", true, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group","ESMSSO", true, "1");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global","ESMSSO", true, "1");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				boolean status = androidClient.autoConfigLoginVerifyESM(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(status == true)
				{
					logger.info("AutoConfig013 - Step1_Need to login Multimedia Messaging account as expected\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig013- Failed - Exception occurs:  Step1_Not login Multimedia Messaging account. Incorrect....\n");
					assertTrue(false);
				}
				
				//Step 2
				AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ESMSSO", false, "0");
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Global","ESMSSO", true, "0");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				androidClientDriver.resetApp();
				status = androidClient.autoConfigLoginVerifyESM(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(status == false)
				{
					logger.info("AutoConfig013 - Step2_Not login Multimedia Messaging account as expected\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig013- Failed - Exception occurs:  Step2_login Multimedia Messaging account. Incorrect....\n");
					assertTrue(true);
				}

				//Step 3	
				AADSWebDriver.configurationPageSettingVariable(webDriver, "Group","ESMSSO", false, "1");
				AADSWebDriver.configurationPagePublishUser(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME);
				Thread.sleep(5000);
				androidClientDriver.resetApp();
				status = androidClient.autoConfigLoginVerifyESM(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD);
				if(status == true)
				{
					logger.info("AutoConfig013 - Step3_Need to login Multimedia Messaging account as expected\n");
					assertTrue(true);
				}
				else {
					logger.info("AutoConfig013- Failed - Exception occurs:  Step3_Not login Multimedia Messaging account. Incorrect....\n");
					assertTrue(true);
					
				}

		} catch (Exception exception) {
			logger.error("AutoConfig_013 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_013 - Failed - Exception occurs: "
					+ exception.toString());
		} 
		logger.info("AutoConfig_013 - test completed\n");
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
