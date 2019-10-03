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

public class AutoConfig_055 {
	/*###############################--Test Steps--##############################################
	 * AutoConfig-055 Refresh autoconfig from ACW
	 * 
	 *###########################################################################################*/
	static WebDriver webDriver;
	static AndroidDriver<?> androidClientDriver;
	AndroidClientKeywords androidClient = new AndroidClientKeywords();
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	String sheet = "Default_Configuration";
	String testCase = "AutoConfig_055";
	
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
//				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				boolean status;
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Dynamic Configuration");
				Thread.sleep(2000);
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Defaults");
				AADSWebDriver.selectAllowPasswordsCheckbox(webDriver, true);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(5000);
				webDriver.switchTo().defaultContent();
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");
				AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
				AADSWebDriver.configurationPageSaveConfigurationNew(webDriver, testCase);
				Thread.sleep(3000);
				AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver,aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"Android");
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				status = androidClient.autoConfigLoginUsingPhoneDetails(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG, 
																							 aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD,
																							 aadsData.AADS_USER_1_NAME_SIP_PHONE, aadsData.AADS_USER_PWD);
				if(status == false)
				{
					logger.info("AutoConfig055 - Step1_User hasn't to input phone details\n");
					assertTrue(true);
				}
				else throw new Exception("AutoConfig055- Failed - Exception occurs:  Step1_User has to input phone details. Incorrect....\n");
				
			//Step2
				Thread.sleep(2000);
				webDriver.switchTo().defaultContent();
				AADSWebDriver.navigateToFeaturesPage(webDriver, "Defaults");
				AADSWebDriver.selectAllowPasswordsCheckbox(webDriver, false);
				AADSWebDriver.saveAndVerifyDefaultPage(webDriver, "Settings were published successfully");
				Thread.sleep(2000);
				androidClientDriver.resetApp();
				status = androidClient.autoConfigLoginUsingPhoneDetails(androidClientDriver, aadsData.AADS_SERVER_ADDRESS_AUTOCONFIG,  aadsData.AADS_USER_1_NAME, aadsData.AADS_USER_PWD, aadsData.AADS_USER_1_NAME_SIP_PHONE, aadsData.AADS_USER_PWD);
				if(status == true)
				{
						logger.info("AutoConfig055 - Step1_User has to input phone details\n");
				}
				else throw new Exception("AutoConfig055- Failed - Exception occurs:  Step1_User hasn't to input phone details. Incorrect....\n");
				
	} catch (Exception exception) {
			logger.error("AutoConfig_001 - Failed with Exception:"
					+ exception + "...\n");
			fail("AutoConfig_001 - Failed - Exception occurs: "
					+ exception.toString());
			assertTrue(false);
		} 
		logger.info("AutoConfig_001 - test completed\n");
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
			webDriver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("tearDown completed...\n");
	}
}
