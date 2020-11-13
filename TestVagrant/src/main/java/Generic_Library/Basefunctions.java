package Generic_Library;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Generic_Library.Utility;

public class Basefunctions {

	public static WebDriver w;
	public static ExtentReports es;
	public static ExtentTest et;
	public String browser_type;
	public static String tcid;
	public static String order;
	public static String scriptname;

	final static Logger log = Logger.getLogger(Basefunctions.class);
	
	@BeforeSuite (groups = {"SMK"})
	public void createreport(){		
		es = new ExtentReports(".\\Report\\testvagrant-DB_"+get_datetimestamp()+".html",false);
		
	}

	@Parameters({"browser"})
	@BeforeTest(groups = {"SMK"})
	public void launch(String btype) throws Exception{
		browser_type = btype;

		if(btype.equalsIgnoreCase("ff")){
			String geckoDriver=Utility.getpropertydetails("GeckoDriverPath");
			System.setProperty("webdriver.gecko.driver",geckoDriver);
			w = new FirefoxDriver();			
		}else if (btype.equalsIgnoreCase("ch")){
			String chromeDriver=Utility.getpropertydetails("ChromeDriverPath");
			System.setProperty("webdriver.chrome.driver",chromeDriver);
			w = new ChromeDriver();
			log.info("Open the Browser");
		}else if(btype.equalsIgnoreCase("ie")){
			String IEDriver=Utility.getpropertydetails("IEDriverPath");
			System.setProperty("webdriver.ie.driver",IEDriver);
			w = new InternetExplorerDriver();
		}

		w.get(Utility.getpropertydetails((Utility.getpropertydetails(("env")))));
		log.info("Enter the URL");
		w.manage().deleteAllCookies();
		w.manage().window().maximize();
		w.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}


	@AfterMethod(groups = {"SMK"})
	public void report(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.SUCCESS) {

			//System.out.println(result.getStatus());
			et.log(LogStatus.PASS, result.getName() + " passed");

		} else if (result.getStatus() == ITestResult.FAILURE) {
			et.log(LogStatus.FAIL, "Test failed name is " + result.getName()); // failed test case name
			et.log(LogStatus.FAIL, "Test failed name is " + result.getThrowable()); // fail test case error
		} else if (result.getStatus() == ITestResult.SKIP) {
			et.log(LogStatus.SKIP, "Test skipped name is " + result.getName()); // skipped test case name
		}

		es.endTest(et); // ending test and send all log messages
		
		
	}
	
	@AfterTest(groups={"SMK"})
	public void closeapp(){

		w.close();
		es.endTest(et);
		es.flush();
	}



	public static String get_datetimestamp(){
		Date date = new Date();
		//		format date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss");
		//		
		String format = dateFormat.format(date);
		return format;

	}

	//	capture snapshot
	public static String getScreenshot() throws Exception{

		TakesScreenshot sc=(TakesScreenshot)w;
		File screenshotAs = sc.getScreenshotAs(OutputType.FILE);

		String fpath = Utility.getpropertydetails("Screenshotpath") + "_"+ tcid + "_" + order + "_" + get_datetimestamp() +".png";
		
		FileUtils.copyFile(screenshotAs, new File(fpath));
		return fpath;

	}
	//	Common Validation
	//	equals
	public static void cv_equals(String actual,String expected,String stepname) throws Exception{

		if(actual.equals(expected)){			
			et.log(LogStatus.PASS, stepname , "Passed as the Step "  + stepname + " ." + et.addScreenCapture(getScreenshot()));

		}else{
			et.log(LogStatus.FAIL, stepname , "Failed the Step " +stepname+ " as the actual value is " + actual + " and the expected is " + expected  + et.addScreenCapture(getScreenshot()));
		}


	}

	//	contains
	public void cv_contains(String actual,String expected,String stepname) throws Exception{

		if(actual.contains(expected)){
			et.log(LogStatus.PASS, stepname , "Passed as the Step "  + stepname + " ." + et.addScreenCapture(getScreenshot()));

		}else{

			et.log(LogStatus.FAIL, stepname , "Failed the Step " +stepname+ " as the actual value is " + actual + " and the expected is " + expected  + et.addScreenCapture(getScreenshot()));
		}


	}
}

