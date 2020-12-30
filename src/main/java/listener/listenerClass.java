package listener;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



/*
 * Listener class which is implementing ITestListener and hence we can use this to dynamically create reports, write logs.
 */
public class listenerClass implements ITestListener{

	private static String TestcaseName;



	public static String getTestcaseName() {
		return TestcaseName;
	}

	public static void setTestcaseName(String testcaseName) {
		TestcaseName = testcaseName;
	}

	public void onTestStart(ITestResult result) {
/*		TestcaseName =result.getMethod().getDescription();
		setTestcaseName(TestcaseName);*/
	/*	ExtentManager.setExtentTest(ExtentReport.report.startTest(TestcaseName));
		LogStatus.pass(TestcaseName+ " is started successfully");*/
		System.out.println("TEST CASE EXECUTION STARTED" +result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		/*if((ReadPropertyFile.get("RunMode").equalsIgnoreCase("Remote"))&&ReadPropertyFile.get("RemoteMode").equalsIgnoreCase("Zalenium")) {
			Cookie cookie = new Cookie("zaleniumTestPassed", "true");
		    DriverManager.getDriver().manage().addCookie(cookie);
		}*/
		System.out.println(result.getName()+ " test case is passed");
	//	LogStatus.pass(result.getMethod().getDescription()+ " test case is passed");
		//ExtentReport.report.endTest(ExtentManager.getExtTest());
	}

	public void onTestFailure(ITestResult result) {
		/*if((ReadPropertyFile.get("RunMode").equalsIgnoreCase("Remote"))&&ReadPropertyFile.get("RemoteMode").equalsIgnoreCase("Zalenium")) {
			Cookie cookie = new Cookie("zaleniumTestPassed", "false");
		    DriverManager.getDriver().manage().addCookie(cookie);
		}*/

		System.out.println(result.getName()+ " is failed");
/*		LogStatus.fail(result.getMethod().getDescription()+ " is failed");
		LogStatus.fail(result.getThrowable().toString());
		LogStatus.fail("Failed", TestUtils.pullScreenshotPath());
		ExtentReport.report.endTest(ExtentManager.getExtTest());*/

	}

	public void onTestSkipped(ITestResult result) {

		System.out.println(result.getName()+ " is skipped");
		/*LogStatus.skip(result.getMethod().getDescription()+ " is skipped");

		ExtentReport.report.endTest(ExtentManager.getExtTest());*/
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		//ExtentReport.report.endTest(ExtentManager.getExtTest());
	}

	public void onStart(ITestContext context) {

		System.out.println("TEST CASE EXECUTION STARTED " +context.getName());
	}

	public void onFinish(ITestContext context) {
		System.out.println("TEST CASE EXECUTION FINISHED "+context.getName());

	}



}