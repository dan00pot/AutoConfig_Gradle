package libs.listener;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

/**
 * This class contains keywords which used for screenshot. Author : Quan Nguyen
 */
public class Screenshot {
	final Logger logger = LogManager.getLogger("Screenshot Listener");

	/**
	 * To create screenshot for failed case.
	 * 
	 * @author Quan Nguyen
	 * @param driver
	 *            Selenium WebDriver
	 * @param screenshot
	 *            the location of to take screenshot to
	 */
	public static void writeScreenshotToFile(WebDriver driver, File screenshot) {
		try {
			FileOutputStream screenshotStream = new FileOutputStream(screenshot);
			screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			screenshotStream.close();
		} catch (IOException unableToWriteScreenshot) {
			System.err.println("Unable to write "
					+ screenshot.getAbsolutePath());
			unableToWriteScreenshot.printStackTrace();
		}
	}

		public void takeScreenshotOnFailedTest(WebDriver driver, Description description) throws Exception {
		Common commonListener = new Common();
		try {
			logger.info("takeScreenshotOnFailedTest - starting with driver" + driver);

			String testingDate = commonListener.getTestingDate();
			String testSuiteName = commonListener.getSuiteName(description);
			String testClassName = commonListener.getClassName(description);
			String testMethodName = description.getMethodName();
			
			String screenshotDirectory = "junit" + File.separator
					+ "screenshots-and-logs" + File.separator + testingDate;
			screenshotDirectory = screenshotDirectory + File.separator
					+ testSuiteName + File.separator + testClassName;
			String screenshotAbsolutePath = screenshotDirectory
					+ File.separator + testClassName + "_" + testMethodName
					+ "_" + ".png";
			
			logger.info("screenshotAbsolutePath is " + screenshotAbsolutePath);
			
			File screenShotdir = new File(screenshotDirectory);
			if (!screenShotdir.exists()) {
				screenShotdir.mkdirs();
				logger.info("Created screenshot folder at " + screenShotdir);
			}
			File screenshot = new File(screenshotAbsolutePath);
			if (commonListener.createFile(screenshot)) {
				try {
					writeScreenshotToFile(driver, screenshot);
				} catch (ClassCastException weNeedToAugmentOurDriverObject) {
					writeScreenshotToFile(
							new Augmenter().augment(driver), screenshot);
				}
				logger.info("Written screenshot to " + screenshotAbsolutePath);
			} else {
				logger.error("Unable to create " + screenshotAbsolutePath);
			}
		} catch (Exception ex) {
			logger.error("Unable to capture screenshot - Exception: " + ex + "...");
			ex.printStackTrace();
		}
		logger.info("takeScreenshotOnFailedTest - completed");
	}

}
