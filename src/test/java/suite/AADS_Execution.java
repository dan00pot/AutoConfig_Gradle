package suite;

import libs.common.DriverManagement;

import scripts.AutoConfig.*;
import testData.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;



import io.appium.java_client.android.AndroidDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
//	AutoConfig_001.class,AutoConfig_002.class,AutoConfig_003.class,AutoConfig_004.class,AutoConfig_005.class,
//	AutoConfig_006.class,AutoConfig_007.class,AutoConfig_008.class,AutoConfig_009.class,AutoConfig_010.class,
//	AutoConfig_011.class,AutoConfig_012.class,AutoConfig_013.class,AutoConfig_014.class,AutoConfig_015.class,
//	AutoConfig_016.class,AutoConfig_017.class,AutoConfig_018.class,AutoConfig_019.class,AutoConfig_020.class,
//	AutoConfig_021.class,AutoConfig_022.class,AutoConfig_023.class,AutoConfig_024.class,
//	AutoConfig_025.class,AutoConfig_027.class,AutoConfig_028.class,AutoConfig_029.class,
//	AutoConfig_030.class,AutoConfig_031.class,AutoConfig_032.class,AutoConfig_033.class,AutoConfig_034.class,
//	AutoConfig_035.class,AutoConfig_036.class,AutoConfig_037.class,AutoConfig_038.class,
//	AutoConfig_043.class,AutoConfig_044.class,AutoConfig_045.class,AutoConfig_046.class,AutoConfig_047.class,
//	AutoConfig_048.class,AutoConfig_049.class,
	AutoConfig_050.class,AutoConfig_051.class,AutoConfig_052.class,
//	AutoConfig_053.class,AutoConfig_054.class,AutoConfig_055.class,AutoConfig_056.class,AutoConfig_057.class,
//	AutoConfig_059.class,AutoConfig_060.class,AutoConfig_061.class,
})

/**
 This is the test suite for All written scripts

 */
public class AADS_Execution {
	final static Logger logger = LogManager.getLogger("DriverManagement Keywords");
	public static AndroidDriver androidClientDriver;

	
	@BeforeClass
	public static void setUpSuite() throws Exception {
		logger.info("setUpSuite - startings");
		DriverManagement driverKws = new DriverManagement();

		logger.info("setUpSuite - completed");
		logger.info("********************************************************");
	}

	@AfterClass
	public static void tearDownSuite() {
		logger.info("********************************************************");
		logger.info("tearDownSuite - clearing cookies");
		//winClient2Driver.quit();
		//winClientDriver.quit();
		//androidClientDriver.quit();
		//iOSClientDriver.quit();
		logger.info("tearDownSuite - completed");
	}

}
