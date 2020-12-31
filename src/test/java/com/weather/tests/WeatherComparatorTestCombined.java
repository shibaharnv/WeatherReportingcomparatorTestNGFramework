package com.weather.tests;


import base.testbase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;
import pages.NdtvHomePage;

import java.io.IOException;
import java.util.Map;




public class WeatherComparatorTestCombined extends testbase {


    private WebDriver driver;
    public int apiTemperature;
    int differenceTemp;
    NdtvHomePage ndtvpage;

    ExtentHtmlReporter htmlReporter;

    ExtentReports extent;
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws Exception {


    String  extentreportpath = System.getProperty("user.dir")
                + "//src//main//executionreports.ExecutionStatusReport.html";
         htmlReporter = new ExtentHtmlReporter(extentreportpath);
         extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }

    @BeforeTest
    @Parameters("browserName")
    public void setUp(String browsername){

        System.out.println("browsername "+browsername);

        if(browsername.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//src//test//resource//drivers//chromedriver.exe");
            this.driver =new ChromeDriver();
        }
        else if(browsername.equalsIgnoreCase("firefox"))
        {
            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "//src//test//resource//drivers//geckodriver.exe");
            this.driver =new FirefoxDriver();
        }

        else if(browsername.equalsIgnoreCase("ie"))
        {
            System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "//src//test//resource//drivers//IEDriverServer.exe");
            this.driver =new InternetExplorerDriver();
        }

    }

    @Test
    public void registrationPage() throws InterruptedException, IOException {

        ndtvpage = new NdtvHomePage(driver);
        ndtvpage.goTo();
        ndtvpage.navigatetoIndiapage();
        ndtvpage.navigatetoweatherpage();
        ndtvpage.storetemperatureNew();
        ndtvpage.weatherPopUpValidationNew();
    }

    //@Test(dependsOnMethods = "registrationPage")
    @Test
    public void weatherApiTest() throws IOException {

        String response = RestAssured.given().log().all()
                .queryParam("q", ""+testbase.getGlobalvalue("cityName")+"")
                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
                .queryParam("units", "metric")
                .when().get("https://api.openweathermap.org/data/2.5/weather").then()
                .log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("response "+response);
        JsonPath js = new JsonPath(response);
        Map<Object, Object> main = js.getMap("main");

        String tempdatatype= main.get("temp").getClass().getSimpleName();

        System.out.println("tempdatatype " +tempdatatype);

        if(tempdatatype.equalsIgnoreCase("Integer"))
        {
            apiTemperature= (int) main.get("temp");
            System.out.println("apiTemperature integer value " +apiTemperature);
        }
        else
        {
            Float tempvalue = (Float) main.get("temp");
            apiTemperature=Math.round(tempvalue);
            System.out.println("apiTemperature integer value from float " +apiTemperature);
        }
    }

    @Test(dependsOnMethods = "weatherApiTest")
    public void comparatorTest()
    {
        int tempindegreesactualvalueint=Integer.parseInt(ndtvpage.tempInDegreeOnlyNumbers);
        if(tempindegreesactualvalueint>apiTemperature)
        {
             differenceTemp=tempindegreesactualvalueint-apiTemperature;
        }
        else
        {
             differenceTemp=apiTemperature-tempindegreesactualvalueint;
        }
        System.out.println("differenceTemp " +differenceTemp);
    }

    @Test(dependsOnMethods = "comparatorTest")
    public void  configureDataCheck() throws IOException {

        int inputTempearture=Integer.parseInt(getGlobalvalue("setTemperatureDifferenceLimit"));
        if(differenceTemp>inputTempearture)
        {
            org.testng.Assert.fail("The temperature difference is more so the testcase is failed");

        }
        else
        {
            System.out.println("This difference in temperature is in acceptable range so the testcase is passed ");

        }
    }


    @AfterTest
    public void quitDriver()
    {
        this.driver.quit();
    }
}