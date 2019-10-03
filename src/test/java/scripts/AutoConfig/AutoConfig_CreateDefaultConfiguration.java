package scripts.AutoConfig;

import libs.clients.AADSWebKeywords;
import libs.common.DriverManagement;
import libs.common.Selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import excel.ExcelUtils;

import static org.junit.Assert.*;
import testData.aadsData;

public class AutoConfig_CreateDefaultConfiguration {

	static WebDriver webDriver;
	AADSWebKeywords AADSWebDriver = new AADSWebKeywords();
	Selenium selenium = new Selenium();
	DriverManagement driverMgnt = new DriverManagement();
	aadsData aadsData = new aadsData();
	ExcelUtils excel = new ExcelUtils();
	String sheet = "Default_Configuration";
	
	final static Logger logger = LogManager.getLogger("AutoConfig_001");

	@Before
	public void beforetestAutoConfig_001() throws Exception {
		logger.info("beforetest AutoConfig_001 starting...\n");
		webDriver = driverMgnt.createFFDriver();
		logger.info("beforetest AutoConfig_001 completed...\n");
	}

	@Test
	public void createDefaultConfiguration() throws Exception {
		logger.info("createDefaultConfiguration - Starting\n");
		try {
			/*================================== Common Steps ======================================*/
	
						webDriver.get(aadsData.AADS_SERVER_ADDRESS);
						AADSWebDriver.loginAADSMainPage(webDriver,	aadsData.AADS_ADMIN_ROLE_USER, aadsData.AADS_USER_PWD);
						// Navigate to Configuration Page
						AADSWebDriver.navigateToFeaturesPage(webDriver,	"Dynamic Configuration");
					//	AADSWebDriver.navigateToFeaturesPage(webDriver, "Configuration");
				Thread.sleep(1000);
						AADSWebDriver.configurationPageCommAddrHandle(webDriver, "Avaya SIP", "5");
						AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "ACSUSERNAME", true, aadsData.AADS_USER_1_NAME);
						AADSWebDriver.configurationPageSettingVariable(webDriver, "User", "SIPUSERNAME", true, aadsData.AADS_USER_1_NAME_SIP_PHONE);
						
						for (int i=1;i>=1;i++) {
							String tab= excel.configurationTab(i, sheet);
							System.out.println(tab);
							if(tab.isEmpty())break;
							String attribute= excel.configurationAttribute(i,sheet);
							String value= excel.configurationValue(i,sheet);
							
							AADSWebDriver.configurationPageSettingVariable(webDriver, tab, attribute, true, value);
							System.out.println("Set " +tab+ " - " + attribute + ": "+value);
						}
							
						AADSWebDriver.configurationPageAddNewConfig(webDriver, sheet);
						AADSWebDriver.configurationPageSelectConfigurationUser(webDriver, sheet);
						AADSWebDriver.configurationPagePublishUserIncludeFlatform(webDriver, aadsData.AADS_USER_1_GROUP_CN, aadsData.AADS_USER_1_NAME,"iOS");

		} catch (Exception exception) {
			logger.error("createDefaultConfiguration - Failed with Exception:"
					+ exception + "...\n");
			fail("createDefaultConfiguration - Failed - Exception occurs: "
					+ exception.toString());
		}
		logger.info("createDefaultConfiguration - test completed\n");
	}

	
	@After
	public void tearDown() throws Exception {
		logger.info("tearDown starting...\n");
		webDriver.get(aadsData.AADS_SERVER_ADDRESS);
		AADSWebDriver.logoutAADSMainPage(webDriver);
		webDriver.close();
		logger.info("tearDown completed...\n");
	}
}
