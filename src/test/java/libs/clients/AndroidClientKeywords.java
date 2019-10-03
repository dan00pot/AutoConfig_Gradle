package libs.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import libs.common.Selenium;
import libs.clients.AndroidClientLocators;

public class AndroidClientKeywords {

	
	Logger logger = LogManager.getLogger("Android Client");
	Selenium selenium = new Selenium();
	AndroidClientLocators androidClient = new AndroidClientLocators();

	AndroidDriver androidClientDriver;
	int timeout = 50;

	public boolean autoConfigLogin(AndroidDriver androidClientDriver,
			String address, String usr, String pwd) throws Exception {
		boolean n = false;
		try {
			selenium.clickElement(androidClientDriver,androidClient.FIRST_SCREEN_SETTING_BTN);

			selenium.clickElement(androidClientDriver,androidClient.FIRST_SCREEN_SETTING_USE_WEB_BTN);
			selenium.clickElement(androidClientDriver,androidClient.FIRST_SCREEN_SETTING_WEB_ADDRESS_TXT);
			selenium.inputText(androidClientDriver,	androidClient.FIRST_SCREEN_SETTING_WEB_ADDRESS_TXT, address);
			selenium.clickElement(androidClientDriver,androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);

			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT);
			selenium.inputText(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT, usr);

			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT);
			selenium.inputText(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT, pwd);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);

			for (int i = 0; i < 2; i++) // 4
			{
				selenium.clickElement(androidClientDriver,
						androidClient.AADS_LOGIN_SCREEN_ALLOW_TXT);
				Thread.sleep(1000);
			}
			if(selenium.isElementExisted(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN);
			}
			if(selenium.isElementExisted(androidClientDriver, androidClient.ACCOUNTS_DETAIL_SCREEN_BACK_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.ACCOUNTS_DETAIL_SCREEN_BACK_BTN);
			}
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_OK_BTN);
			if(selenium.isElementExisted(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN);
			}
			if(selenium.isElementExisted(androidClientDriver,androidClient.AADS_LOGIN_SCREEN_ACCEPT_COMFIRM_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_COMFIRM_BTN);
			}
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_SKIP_TUTORIAL_BTN);
			Thread.sleep(1000);
			return n = selenium.isElementExisted(androidClientDriver, androidClient.PROFILE_AVATAR);
			
		} catch (Exception exception) {
			
			//throw new Exception("autoConfigLogin - Failed - Exception occurs: "	+ exception);
			return n;
		}
	
	}
	
	public boolean autoConfigLoginVerifyESM(AndroidDriver androidClientDriver, String address, String usr, String pwd) throws Exception{
		logger.info("autoConfigLoginVerifyESM - starting...\n");
		boolean n = false;
		try {
			Thread.sleep(5000);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_BTN);
			Thread.sleep(5000);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_BTN);
			Thread.sleep(3000);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_WEB_ADDRESS_TXT);
			selenium.inputText(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_WEB_ADDRESS_TXT, address );
			Thread.sleep(1000);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);
			Thread.sleep(8000);
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT);
			selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT, usr);
			Thread.sleep(3000);
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT);
			selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT, pwd);
			androidClientDriver.pressKeyCode(AndroidKeyCode.ENTER);
			
			Thread.sleep(3000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN))
			{
				selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);
			}
			Thread.sleep(5000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT))
			{
				logger.info("autoConfigLogin - Need to login Multimedia Messaging account starting...\n");
				selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT);
				selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT, usr);
				Thread.sleep(1000);
				selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT);
				selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT, pwd);
				androidClientDriver.pressKeyCode(AndroidKeyCode.ENTER);
				Thread.sleep(1000);
				if(selenium.isElementExisted(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN))
				{
					selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);
				}
				logger.info("autoConfigLogin - Need to login Multimedia Messaging account completed...\n");
				n=true;
			}
			Thread.sleep(5000);
			for(int i=0 ; i<2; i++) //2
			{
				selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ALLOW_TXT);
				Thread.sleep(1000);
			}
			Thread.sleep(1000);
			
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_OK_BTN);

			Thread.sleep(2000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN);
			}
			if(selenium.isElementExisted(androidClientDriver,androidClient.AADS_LOGIN_SCREEN_ACCEPT_COMFIRM_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_COMFIRM_BTN);
			}
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SKIP_TUTORIAL_BTN);	
		} catch (Exception exception) {
			logger.error("autoConfigLogin - Failed with Exception: " + exception + "...\n");
			throw new Exception("autoConfigLogin - Failed - Exception occurs: " + exception);
		}
		logger.info("autoConfigLoginVerifyESM - completed...\n");	
		return n;
    }
	
	public boolean verifyMultimediaMesaging(AndroidDriver androidClientDriver) throws Exception{
		logger.info("verifyMultimediaMesaging - starting...\n");
		boolean s ;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Multimedia Messaging");
			Thread.sleep(1000);
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_ON_OFF_TOGGLE, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				s = false;
			}else s= true;
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyMultimediaMesaging - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyMultimediaMesaging - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyMultimediaMesaging - completed...\n");
		return s;
    }
	
	public void navigateToSettingScreen(AndroidDriver androidClientDriver) throws Exception{
		logger.info("navigateToSettingScreen - starting...\n");
		try {
			if (selenium.isElementExisted(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_BTN)){
				selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_BTN);
				selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_MANUALLY_CONF_BTN);
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_DONE_BTN);		
			}
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_MENU_BTN);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_MENU_SETTING_BTN);
			Thread.sleep(2000);
		} catch (Exception exception) { 
			logger.error("navigateToSettingScreen - Failed with Exception: " + exception + "...\n");
			throw new Exception("navigateToSettingScreen - Failed - Exception occurs: " + exception);
		}
		logger.info("navigateToSettingScreen - completed...\n");			
    }
	
	public void settingScreenMainClickOnTab(AndroidDriver androidClientDriver, String tabName) throws Exception{
		logger.info("settingScreenMainClickOnTab - starting...\n");
		logger.info("tabName is: " + tabName + "\n");
		try {
			
			selenium.clickElement(androidClientDriver, androidClient.settingScreenTabLocators(tabName));
		} catch (Exception exception) { 
			logger.error("settingScreenMainClickOnTab - Failed with Exception: " + exception + "...\n");
			throw new Exception("settingScreenMainClickOnTab - Failed - Exception occurs: " + exception);
		}
		logger.info("settingScreenMainClickOnTab - completed...\n");		
    }
	
	public void settingScreenMainSelectOptionInsideTab(AndroidDriver androidClientDriver, String option) throws Exception{
		logger.info("settingScreenMainSelectOptionInsideTab - starting...\n");
		logger.info("option is: " + option + "\n");
		try {
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH);
			}
			Thread.sleep(2000);
			selenium.clickElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators(option));
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("settingScreenMainSelectOptionInsideTab - Failed with Exception: " + exception + "...\n");
			throw new Exception("settingScreenMainSelectOptionInsideTab - Failed - Exception occurs: " + exception);
		}
		logger.info("settingScreenMainSelectOptionInsideTab - completed...\n");		
    }
	
	public void settingScreenMainClickDoneButton(AndroidDriver androidClientDriver) throws Exception{
		logger.info("settingScreenMainClickDoneButton - starting...\n");
		try {
			selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_DONE_BTN);
			Thread.sleep(2000);
			if (selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_DONE_REQUIRE_SIGN_OUT_BTN)) {
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_DONE_REQUIRE_SIGN_OUT_BTN);
			}
	
		} catch (Exception exception) {
			logger.error("settingScreenMainClickDoneButton - Failed with Exception: " + exception + "...\n");
			throw new Exception("settingScreenMainClickDoneButton - Failed - Exception occurs: " + exception);
		}
		logger.info("settingScreenMainClickDoneButton - completed...\n");		
    }
	
	public String getPollingTimer(AndroidDriver androidClientDriver) throws Exception{
		logger.info("getPollingTimer - starting...\n");
		String currentStatus =new String();
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Multimedia Messaging");
			Thread.sleep(1000);
			currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_POLLING_INTERVAL_TXT, "text");
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("getPollingTimer - Failed with Exception: " + exception + "...\n");
			throw new Exception("getPollingTimer - Failed - Exception occurs: " + exception);
		}
		logger.info("getPollingTimer - completed...\n");
		return currentStatus;
    }
	
	public String getAMMServer(AndroidDriver androidClientDriver) throws Exception{
		logger.info("getAMMServer - starting...\n");
		String currentStatus =new String();
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Multimedia Messaging");
			Thread.sleep(1000);
			currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_SV_ADDRESS_TXT, "text");
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) {
			logger.error("getAMMServer - Failed with Exception: " + exception + "...\n");
			throw new Exception("getAMMServer - Failed - Exception occurs: " + exception);
		}
		logger.info("getAMMServer - completed...\n");
		return currentStatus;
    }

	
	public String[] getSIPServer(AndroidDriver androidClientDriver) throws Exception{
		logger.info("getSIPServer - starting...\n");
		String[] currentStatus =new String[2];
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Phone Service");
			Thread.sleep(1000);
			currentStatus[0] = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICES_SV_ADDRESS_TXT, "text");
			logger.info("getSIPServer - Server: "+currentStatus[0]);
			currentStatus[1] = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICES_SV_PORT_TX, "text");
			logger.info("getSIPServer - Port: "+currentStatus[1]);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(3000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) {
			logger.error("getSIPServer - Failed with Exception: " + exception + "...\n");
			throw new Exception("getSIPServer - Failed - Exception occurs: " + exception);
		}
		logger.info("getSIPServer - completed...\n");
		return currentStatus;
    }
	
	public boolean verifyPhoneService(AndroidDriver androidClientDriver) throws Exception{
		logger.info("verifyPhoneService - starting...\n");
		boolean s ;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Phone Service");
			Thread.sleep(1000);
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICE_ON_OFF_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				s = false;
			}else s= true;
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyPhoneService - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyPhoneService - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyPhoneService - completed...\n");
		return s;
    }
	
	public String getAADSServer(AndroidDriver androidClientDriver) throws Exception{
		logger.info("getAADSServer - starting...\n");
		String currentStatus =new String();
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Device Services");
			Thread.sleep(3000);
			currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_DEVICE_SERVICE_SV_ADDRESS_TXT, "text");
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) {
			logger.error("getAADSServer - Failed with Exception: " + exception + "...\n");
			throw new Exception("getAADSServer - Failed - Exception occurs: " + exception);
		}
		logger.info("getAADSServer - completed...\n");
		return currentStatus;
    }
	
	public boolean verifyDeviceServices(AndroidDriver androidClientDriver) throws Exception{
		logger.info("verifyDeviceServices - starting...\n");
		boolean s ;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Device Services");
			Thread.sleep(1000);
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				s = false;
			}else s= true;
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyDeviceServices - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyDeviceServices - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyDeviceServices - completed...\n");
		return s;
    }
	
	public void verifyEnterpriseContactSearchOnACA (AndroidDriver androidClientDriver, String name, String expected) throws Exception 
	{
		logger.info("verifyEnterpriseContactSearchOnACA - starting...\n");
		
		try {
			boolean flag = false;
			//input text into textfield
			selenium.clickElement(androidClientDriver, androidClient.CONTACT_SCREEN_SEARCH_TXT);
			Thread.sleep(1000);
			selenium.inputText(androidClientDriver, androidClient.CONTACT_SCREEN_SEARCH_TXT, name);
			Thread.sleep(10000);
			logger.info("**** verifyEnterprieseContactSearchOnACA - Get number of result ****");
			String s1 = selenium.getAttribute(androidClientDriver, androidClient.SEARCH_CONTACT_ENTERPRISE_CONTACT_TITLE_BAR, "text");
			String number = s1.replaceAll("[^0-9]", "");
			int num = Integer.parseInt(number);
			
			logger.info("verifyEnterprieseContactSearch - Number of result: " + num);
			logger.info("**** verifyEnterprieseContactSearch - Verify search ****");
			logger.info("verifyEnterprieseContactSearch - Expected result: " + expected);
			
			for (int i = 0; i < num; i++) {
				String order = String.valueOf(i + 1);
				// get value in Enterprise if found
				String s2 = selenium.getText(androidClientDriver, androidClient.enterprise_result_by_order(order));
				logger.info("verifyEnterprieseContactSearch - result "+(i+1)+": " + s2);
				if (s2.contains(expected)) {
					logger.info("verifyEnterprieseContactSearch - Existed the expected result - PASSED ");
					flag = true;
					break;
				}
			}
			if (flag == false) {
				logger.error("verifyEnterprieseContactSearch - FAILED - Expected result didn't exist");
				throw new Exception("verifyEnterprieseContactSearch - Expected result didn't exist");
			}
			
			logger.info("verifyEnterprieseContactSearch - PASSED");
			
		} catch (Exception e) {
			
			logger.error("ContactsSearch - Failed with Exception: " + e + "...\n");
			throw new Exception("ContactsUuSearch - Failed - Exception occurs: " + e);
		}
	}
	
	public boolean enableOrDisableMultimediaMesaging(AndroidDriver androidClientDriver) throws Exception{
		logger.info("EnableMultimediaMesaging - starting...\n");
		boolean s =false;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Multimedia Messaging");
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_ON_OFF_TOGGLE))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_ON_OFF_TOGGLE, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_ON_OFF_TOGGLE, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_ON_OFF_TOGGLE);
					}
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyMultimediaMesaging - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyMultimediaMesaging - Failed - Exception occurs: " + exception);
		}
		logger.info("EnableMultimediaMesaging - completed...\n");
		return s;
    }
	
	public boolean enableOrDisableDeviceServices(AndroidDriver androidClientDriver) throws Exception{
		logger.info("EnableMultimediaMesaging - starting...\n");
		boolean s = false;
		try {
			
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Device Services");
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH);
					}
	
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyMultimediaMesaging - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyMultimediaMesaging - Failed - Exception occurs: " + exception);
		}
		logger.info("EnableMultimediaMesaging - completed...\n");
		return s;
    }
	
	public boolean enableOrDisablePhoneService(AndroidDriver androidClientDriver) throws Exception{
		logger.info("EnablePhoneService - starting...\n");
		boolean s = false;
		try {
			
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Phone Service");
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICE_ON_OFF_SWITCH))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICE_ON_OFF_SWITCH, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICE_ON_OFF_SWITCH, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_PHONE_SERVICE_ON_OFF_SWITCH);
					}
	
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("EnablePhoneService - Failed with Exception: " + exception + "...\n");
			throw new Exception("EnablePhoneService - Failed - Exception occurs: " + exception);
		}
		logger.info("EnablePhoneService - completed...\n");
		return s;
    }
	
	public boolean enableOrDisableExchangeCalendar(AndroidDriver androidClientDriver) throws Exception{
		logger.info("EnableExchangeCalendar - starting...\n");
		boolean s = false;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH);
			}
			selenium.scrollFromElementToElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Multimedia Messaging"),
				androidClient.settingScreenOptionsInsideTabLocators("Device Services"));
			
			selenium.clickElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Exchange Calendar"));
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_EXCHANGE_CALENDAR_SWITCH))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_EXCHANGE_CALENDAR_SWITCH, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_EXCHANGE_CALENDAR_SWITCH, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_EXCHANGE_CALENDAR_SWITCH);
					}
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) {
			logger.error("verifyMultimediaMesaging - Failed with Exception: " + exception + "...\n");
			throw new Exception("EnableExchangeCalendar - Failed - Exception occurs: " + exception);
		}
		logger.info("EnableExchangeCalendar - completed...\n");
		return s;
    }	

	public boolean enableOrDisableClientEnablement(AndroidDriver androidClientDriver) throws Exception{
		logger.info("enableOrDisableClientEnablement - starting...\n");
		boolean s = false;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH);
			}
			selenium.scrollFromElementToElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Multimedia Messaging"),
				androidClient.settingScreenOptionsInsideTabLocators("Device Services"));
			selenium.waitUntilElementClickable(androidClientDriver,androidClient.settingScreenOptionsInsideTabLocators("Client Enablement (CES)") , timeout);
			selenium.clickElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Client Enablement (CES)"));
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_CES_SWITCH))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_CES_SWITCH, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_CES_SWITCH, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_CES_SWITCH);
					}
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("enableOrDisableClientEnablement - Failed with Exception: " + exception + "...\n");
			throw new Exception("enableOrDisableClientEnablement - Failed - Exception occurs: " + exception);
		}
		logger.info("enableOrDisableClientEnablement - completed...\n");
		return s;
    }	

	public boolean enableOrDisableAvayaCloudServices(AndroidDriver androidClientDriver) throws Exception{
		logger.info("enableAvayaCloudServices - starting...\n");
		boolean s = false;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Avaya Cloud Services");
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH);
					}
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("enableAvayaCloudServices - Failed with Exception: " + exception + "...\n");
			throw new Exception("enableAvayaCloudServices - Failed - Exception occurs: " + exception);
		}
		logger.info("enableAvayaCloudServices - completed...\n");
		return s;
    }	

	public boolean enableOrDisableMyMettingRoom(AndroidDriver androidClientDriver) throws Exception{
		logger.info("enableOrDisableMyMettingRoom - starting...\n");
		boolean s = false;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "My Metting Room");
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH))
			{
					Thread.sleep(2000);
					s = selenium.isElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH, timeout);
					if (s==true)
					{
						selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH, timeout);
						selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH);
					}
			}
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("enableOrDisableMyMettingRoom - Failed with Exception: " + exception + "...\n");
			throw new Exception("enableOrDisableMyMettingRoom - Failed - Exception occurs: " + exception);
		}
		logger.info("enableOrDisableMyMettingRoom - completed...\n");
		return s;
    }	
	
	
	public void signout (AndroidDriver androidClientDriver) throws Exception{
		logger.info("signout - starting...\n");
		try {
			selenium.clickElement(androidClientDriver, androidClient.PROFILE_AVATAR);
			boolean signoutButtonExist = selenium.isElementExisted(androidClientDriver, androidClient.SIGN_OUT_BTN);
			if(!signoutButtonExist){
				logger.info("Need to swipe screen");
				selenium.scrollFromElementToElement(androidClientDriver, androidClient.PREFERENCES, androidClient.LOCAL_USER_AVATAR);
			}
			selenium.clickElement(androidClientDriver, androidClient.SIGN_OUT_BTN);
			Thread.sleep(1000);
		} catch (Exception exception) {
			
			logger.error("signout - Failed with Exception: " + exception + "...\n");
			throw new Exception("signout - Failed - Exception occurs: " + exception);
		}
		logger.info("signout - completed...\n");		
    }
	
	
	public void signin (AndroidDriver androidClientDriver) throws Exception{
		logger.info("signin - starting...\n");
		try {
			selenium.waitUntilElementClickable(androidClientDriver, androidClient.SIGN_IN_BTN, timeout);
			selenium.clickElement(androidClientDriver, androidClient.SIGN_IN_BTN);
			Thread.sleep(5000);
		} catch (Exception exception) {
			
			logger.error("signin - Failed with Exception: " + exception + "...\n");
			throw new Exception("signin - Failed - Exception occurs: " + exception);
		}
		logger.info("signin - completed...\n");		
    }
	
	public boolean autoConfigLoginUsingPhoneDetails(AndroidDriver androidClientDriver, String address, String usr, String pwd, 
			String usr1, String pwd1) throws Exception{
		
		logger.info("autoConfigLoginUsingPhoneDetails - starting...\n");
		boolean n = false;
		try {
			Thread.sleep(3000);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_BTN);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_BTN);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_WEB_ADDRESS_TXT);
			selenium.inputText(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_WEB_ADDRESS_TXT, address );
			Thread.sleep(1000);
			selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);
			Thread.sleep(8000);
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT);
			selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT, usr);
			Thread.sleep(3000);
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT);
			selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT, pwd);
			androidClientDriver.pressKeyCode(AndroidKeyCode.ENTER);
			Thread.sleep(3000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN))
			{
				selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);
			}
			Thread.sleep(9000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT))
			{
				logger.info("autoConfigLoginUsingPhoneDetails - Need to login Phone Details starting...\n");
				
				selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT);
				selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_USER_TXT, usr1);
				Thread.sleep(1000);
				
				selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT);
				selenium.inputText(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SETTING_PWD_TXT, pwd1);
				androidClientDriver.pressKeyCode(AndroidKeyCode.ENTER);
				Thread.sleep(1000);
				if(selenium.isElementExisted(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN))
				{
					selenium.clickElement(androidClientDriver, androidClient.FIRST_SCREEN_SETTING_USE_WEB_NEXT_BTN);
				}
				logger.info("autoConfigLoginUsingPhoneDetails - Need to login Phone Details completed...\n");
				n=true;
			}
			Thread.sleep(5000);
			for(int i=0 ; i<2; i++) //4
			{
				
				selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ALLOW_TXT);
				Thread.sleep(1000);
			}
			Thread.sleep(1000);
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_OK_BTN);
			Thread.sleep(5000);
			
/*			for(int i=0 ; i<2; i++) //2
			{
				appium.waitUntilElementClickable(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ALLOW_TXT, timeout);
				appium.tapElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ALLOW_TXT);
				Thread.sleep(1000);
			}*/
			
			//appium.waitUntilElementClickable(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_OK_BTN, timeout);
			//appium.tapElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_OK_BTN);
			Thread.sleep(2000);
			if(selenium.isElementExisted(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_BTN);
			}
			if(selenium.isElementExisted(androidClientDriver,androidClient.AADS_LOGIN_SCREEN_ACCEPT_COMFIRM_BTN)) {
			selenium.clickElement(androidClientDriver,	androidClient.AADS_LOGIN_SCREEN_ACCEPT_COMFIRM_BTN);
			}
			selenium.clickElement(androidClientDriver, androidClient.AADS_LOGIN_SCREEN_SKIP_TUTORIAL_BTN);	
		} catch (Exception exception) { 
			logger.error("autoConfigLoginUsingPhoneDetails - Failed with Exception: " + exception + "...\n");
			throw new Exception("autoConfigLoginUsingPhoneDetails - Failed - Exception occurs: " + exception);
		}
		logger.info("autoConfigLoginUsingPhoneDetails - completed...\n");	
		return n;
    }
	
	public boolean verifyExchangeCalendar(AndroidDriver androidClientDriver) throws Exception{
		logger.info("verifyExchangeCalendar - starting...\n");
		boolean s ;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			selenium.scrollFromElementToElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Multimedia Messaging"),
				androidClient.settingScreenOptionsInsideTabLocators("Device Services"));
			
			selenium.clickElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Exchange Calendar"));
			Thread.sleep(1000);
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_EXCHANGE_CALENDAR_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				s = false;
			}else s= true;
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyExchangeCalendar - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyExchangeCalendar - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyExchangeCalendar - completed...\n");
		return s;
    }
	
	
	/**
	 * verify Client Enablement (CES) on/off
	 * @param androidClientDriver testing Client on Android
	 * 
	 * @author nttruc
	 * @throws Exception 
	 */	
	
	public boolean verifyClientEnablementCES(AndroidDriver androidClientDriver) throws Exception{
		logger.info("verifyClientEnablementCES - starting...\n");
		boolean s ;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			selenium.scrollFromElementToElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Multimedia Messaging"),
					androidClient.settingScreenOptionsInsideTabLocators("Device Services"));
			
			selenium.clickElement(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators("Client Enablement (CES)"));
			Thread.sleep(1000);
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_CES_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				s = false;
			}else s= true;
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyClientEnablementCES - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyClientEnablementCES - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyClientEnablementCES - completed...\n");
		return s;
    }
	
	/**
	 * verify Avaya Cloud Services on/off
	 * @param androidClientDriver testing Client on Android
	 * 
	 * @author nttruc
	 * @throws Exception 
	 */	
	
	public boolean verifyAvayaCloudServices(AndroidDriver androidClientDriver) throws Exception{
		logger.info("verifyAvayaCloudServices - starting...\n");
		boolean s ;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Avaya Cloud Services");
			Thread.sleep(1000);
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_AVAYA_CLOUD_SERVICES_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				s = false;
			}else s= true;
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) {
			logger.error("verifyAvayaCloudServices - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyAvayaCloudServices - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyAvayaCloudServices - completed...\n");
		return s;
    }
	
	public void inputValueACSPORT(AndroidDriver androidClientDriver, String value) throws Exception{
		logger.info("inputValueACSPORT - starting...\n");
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Device Services");
			Thread.sleep(3000);
			selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_DEVICE_SERVICE_SV_PORT_TXT, timeout);
			selenium.inputText(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_DEVICE_SERVICE_SV_PORT_TXT, value);
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("inputValueACSPORT - Failed with Exception: " + exception + "...\n");
			throw new Exception("inputValueACSPORT - Failed - Exception occurs: " + exception);
		}
		logger.info("inputValueACSPORT - completed...\n");
    }
	
	public void inputValueAMMServerPort(AndroidDriver androidClientDriver, String value) throws Exception{
		logger.info("inputValueAMMServerPort - starting...\n");
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Multimedia Messaging");
			Thread.sleep(3000);
			selenium.waitUntilElementClickable(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_SV_PORT_TXT, timeout);
			selenium.inputText(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_SV_PORT_TXT, value);
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("inputValueAMMServerPort - Failed with Exception: " + exception + "...\n");
			throw new Exception("inputValueAMMServerPort - Failed - Exception occurs: " + exception);
		}
		logger.info("inputValueAMMServerPort - completed...\n");
    }
	
	public String getAADSServerPORT(AndroidDriver androidClientDriver) throws Exception{
		logger.info("getAADSServer - starting...\n");
		String currentStatus =new String();
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Device Services");
			Thread.sleep(3000);
			currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_DEVICE_SERVICE_SV_PORT_TXT, "text");
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("getAADSServer - Failed with Exception: " + exception + "...\n");
			throw new Exception("getAADSServer - Failed - Exception occurs: " + exception);
		}
		logger.info("getAADSServer - completed...\n");
		return currentStatus;
    }

	
	public String getAMMServerPORT(AndroidDriver androidClientDriver) throws Exception{
		logger.info("getAMMServerPORT - starting...\n");
		String currentStatus = new String();
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			settingScreenMainSelectOptionInsideTab(androidClientDriver, "Multimedia Messaging");
			Thread.sleep(3000);
			currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_MM_SV_PORT_TXT, "text");
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("getAMMServerPORT - Failed with Exception: " + exception + "...\n");
			throw new Exception("getAMMServerPORT - Failed - Exception occurs: " + exception);
		}
		logger.info("getAMMServerPORT - completed...\n");
		return currentStatus;
    }
	
	public boolean verifyMultimediaMessagingExisted(AndroidDriver androidClientDriver, String options) throws Exception{
		logger.info("verifyMultimediaMessagingExisted - starting...\n");
		boolean s = false;
		try {
			navigateToSettingScreen(androidClientDriver);
			settingScreenMainClickOnTab(androidClientDriver, "Services");
			String currentStatus = selenium.getAttribute(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH, "checked");
			logger.info("current status is: " + currentStatus + "\n");
			if (currentStatus.toLowerCase().contains("false")) {
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_SERVICES_TAB_SERVICE_DETAILS_SWITCH);
			}
			if (selenium.checkElementExistedOrNotExisted(androidClientDriver, androidClient.settingScreenOptionsInsideTabLocators(options))) {
				s = true;
			}
			else {
				s= false;
			}
			Thread.sleep(1000);
			if(selenium.isElementExisted(androidClientDriver,androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN ))
			{
				selenium.clickElement(androidClientDriver, androidClient.SETTING_SCREEN_POP_UP_APPLY_CHANGES_BTN);
			}
			Thread.sleep(1000);
			settingScreenMainClickDoneButton(androidClientDriver);
			Thread.sleep(1000);
		} catch (Exception exception) { 
			logger.error("verifyMultimediaMessagingExisted - Failed with Exception: " + exception + "...\n");
			throw new Exception("verifyMultimediaMessagingExisted - Failed - Exception occurs: " + exception);
		}
		logger.info("verifyMultimediaMessagingExisted - completed...\n");
		return s;
    }
	
	public void clickBackButton(AndroidDriver androidClientDriver) throws Exception{
		try {
			Thread.sleep(3000);
			if (selenium.isElementExisted(androidClientDriver, androidClient.CONTACT_DETAIL_SCREEN_NAVIGATE_UP_BTN	)){
				selenium.clickElement(androidClientDriver, androidClient.CONTACT_DETAIL_SCREEN_NAVIGATE_UP_BTN	);
				}	
			Thread.sleep(3000);
		} catch (Exception exception) { 
			throw new Exception("clickBackButton - Failed - Exception occurs: " + exception);
		}
	
    }
	
}