package libs.common;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import testData.aadsData;

//import io.appium.java_client.windows.WindowsDriver;

import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;





public class DriverManagement {

	public WebDriver webDriver;
	public AndroidDriver androidClientDriver;
	static WiniumDriver winClientDriver;
	static WindowsDriver windowsClientDriver; 
	static Logger logger = LogManager.getLogger("Driver Managerment");
	aadsData aadsData = new aadsData();

	/**
	 * @return
	 * @throws Exception
	 */

	public WebDriver createFFDriver() throws Exception {
		try {
			System.setProperty("wdm.proxy", "http://10.10.10.10:8080"); 
			WebDriverManager.firefoxdriver().setup();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			FirefoxProfile profile = new FirefoxProfile();
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			capabilities.setCapability("acceptInsecureCerts", true);
			profile.setPreference("browser.tabs.remote.autostart.2", false);
			FirefoxOptions ffoption = new FirefoxOptions(capabilities);
		//	ffoption.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			ffoption.setCapability("marionette", true);
			webDriver = new FirefoxDriver(ffoption);
		} catch (Exception e) {
			throw new Exception("createWebDriver - Failed - Exception occurs: "
					+ e);
		}
		return webDriver;
	}

	public WebDriver createChromeDriver() throws Exception {
		try {
			logger.info("createWebDriverChrome - starting...\n");
			System.out.println("launching Chorme browser");
			System.setProperty("wdm.proxy", "http://10.10.10.10:8080"); 
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.addArguments("start-maximized");
			chromeOptions.addArguments("--use-fake-ui-for-media-stream");
	//		chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability("chrome.switches",
					Arrays.asList("--ignore-certificate-errors"));
			capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,
					true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			webDriver = new ChromeDriver(chromeOptions);
			// webDriver = new ChromeDriver();
			logger.info("createWebDriverChrome - completed...\n");
		} catch (Exception exception) {
			logger.error("createWebDriverChrome - Failed with Exception: "
					+ exception + "...\n");
			throw new Exception(
					"createWebDriverChrome - Failed - Exception occurs: "
							+ exception);
		}
		return webDriver;
	}

	public WebDriver createIEDriver() throws Exception {
		try {
			logger.info("createWebDriverIE - starting...\n");
			System.out.println("launching IE browser");
			System.setProperty("wdm.proxy", "http://10.10.10.10:8080"); 
			WebDriverManager.iedriver().arch32().setup();
			InternetExplorerOptions ieOption = new InternetExplorerOptions();
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			InternetExplorerOptions ieOptions = new InternetExplorerOptions(
					capabilities);
			webDriver = new InternetExplorerDriver(ieOptions);
			logger.info("createWebDriverIE - completed...\n");
		} catch (Exception exception) {
			logger.error("createWebDriverIE - Failed with Exception: "
					+ exception + "...\n");
			throw new Exception(
					"createWebDriverIE - Failed - Exception occurs: "
							+ exception);
		}
		return webDriver;
	}

	public AndroidDriver createAndroidClientDriver() throws Exception {
		try {
			String appiumServer = aadsData.APPIUM_SERVER_ANDROID;
			String appiumPort = aadsData.APPIUM_PORT_ANDROID;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("automationName", "uiautomator2");
			capabilities.setCapability("deviceName", "A6");
			capabilities.setCapability("platformName", "ANDROID");
			capabilities.setCapability("platformVersion", "8.0.0");
			capabilities.setCapability("udid", aadsData.MOBILE_UDID_ANDROID);
			capabilities.setCapability("appPackage", "com.avaya.android.flare");
			capabilities.setCapability("appActivity",
					"com.avaya.android.flare.MainActivity");
			capabilities.setCapability("newCommandTimeout", 300000);
			// disable the soft keyboard
			// capabilities.setCapability("unicodeKeyboard", true);
			// capabilities.setCapability("resetKeyboard", true);
			String url = "http://" + appiumServer + ":" + appiumPort
					+ "/wd/hub";
			androidClientDriver = new AndroidDriver(new URL(url), capabilities);
		} catch (Exception exception) {
			throw new Exception(
					"createAndroidClientDriver - Failed - Exception occurs: "
							+ exception);
		}
		return androidClientDriver;
	}

	public WiniumDriver createWindowsClientDriver(String winiumURL) throws Exception{	
		WiniumDriver tempDriver = null;
		try {
			try {
				DesktopOptions options = new DesktopOptions();
				options.setApplicationPath("C:\\Program Files (x86)\\Avaya\\Avaya Equinox\\Avaya Equinox.exe");
				winClientDriver = new WiniumDriver(new URL(winiumURL), options);
				tempDriver = winClientDriver;
			} catch (Exception e) {
				DesktopOptions options = new DesktopOptions();
				options.setApplicationPath("C:\\Program Files (x86)\\Avaya\\Avaya Equinox\\Avaya Equinox.exe");
				winClientDriver = new WiniumDriver(new URL(winiumURL), options);
				tempDriver = winClientDriver;
				// TODO: handle exception
			}
			Thread.sleep(15000);
			logger.info("createWindowsClientDriver - completed...\n");
		} catch (Exception exception) {
			logger.error("createWindowsClientDriver - Failed with Exception: " + exception + "...\n");
			throw new Exception("createWindowsClientDriver - Failed - Exception occurs: " + exception);
		}
		return tempDriver;
    }
	
	public WindowsDriver createWinAppDriver(String winiumURL) throws Exception{	
		WindowsDriver tempDriver = null;
		try {
			try {
				
	            DesiredCapabilities capabilities = new DesiredCapabilities();
	            capabilities.setCapability("app", "Root");
	          //  capabilities.setCapability("appTopLevelWindow", "0x5e1472");
	          //  capabilities.setCapability("app", "C:\\Program Files (x86)\\Avaya\\Avaya Equinox\\Avaya Equinox.exe");
	           // capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
	            windowsClientDriver = new WindowsDriver(new URL(winiumURL), capabilities);
	            windowsClientDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			} catch (Exception e) {
				logger.info("createWindowsClientDriver - Failed...\n");
				// TODO: handle exception
			}
			Thread.sleep(15000);
			logger.info("createWindowsClientDriver - completed...\n");
		} catch (Exception exception) {
			logger.error("createWindowsClientDriver - Failed with Exception: " + exception + "...\n");
			throw new Exception("createWindowsClientDriver - Failed - Exception occurs: " + exception);
		}
		return windowsClientDriver;
    }
	
	public WindowsDriver createWinHandleDriver(String winiumURL, String equinox) throws Exception{	
		WindowsDriver tempDriver = null;
		try {
			try {
				
	            DesiredCapabilities capabilities = new DesiredCapabilities();
	          //  capabilities.setCapability("app", "Root");
	            capabilities.setCapability("appTopLevelWindow", equinox);
	          //  capabilities.setCapability("app", "C:\\Program Files (x86)\\Avaya\\Avaya Equinox\\Avaya Equinox.exe");
	           // capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
	            windowsClientDriver = new WindowsDriver(new URL(winiumURL), capabilities);
	            windowsClientDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			} catch (Exception e) {
				logger.info("createWindowsClientDriver - Failed...\n");
				// TODO: handle exception
			}
			Thread.sleep(15000);
			logger.info("createWindowsClientDriver - completed...\n");
		} catch (Exception exception) {
			logger.error("createWindowsClientDriver - Failed with Exception: " + exception + "...\n");
			throw new Exception("createWindowsClientDriver - Failed - Exception occurs: " + exception);
		}
		return windowsClientDriver;
    }
}
