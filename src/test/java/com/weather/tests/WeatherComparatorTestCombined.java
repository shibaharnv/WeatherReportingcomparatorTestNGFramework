package com.weather.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.NdtvHomePage;

import java.util.Map;

import static pages.NdtvHomePage.CITY_NAME;

@Listeners({listener.listenerClass.class})
public class WeatherComparatorTestCombined {


    private WebDriver driver;
    public int apiTemperature;
    int differenceTemp;
    String cityNameValue =NdtvHomePage.CITY_NAME;
    NdtvHomePage ndtvpage;

    @BeforeTest
    public void setUp(){

        System.setProperty("webdriver.chrome.driver","C:\\Users\\nshibaha\\OneDrive - Intel Corporation\\Desktop\\WORK\\TestVagrant\\chromedriver_win32\\chromedriver.exe");
        this.driver =new ChromeDriver();

    }


    @Test
    public void registrationPage() throws InterruptedException {
        ndtvpage = new NdtvHomePage(driver);
        ndtvpage.goTo();
        ndtvpage.navigatetoIndiapage();
        ndtvpage.navigatetoweatherpage();
        ndtvpage.storetemperature();
        ndtvpage.weatherPopUpValidation();

    }



    @Test(dependsOnMethods = "registrationPage")
    public void weatherApiTest(){

       // String cityNameValue =CITY_NAME;
        setProxymethod();
        String response = RestAssured.given().log().all()
                .queryParam("q", ""+cityNameValue+"")
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
            //Float tempvalue = main.get("temp");
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
        //int tempindegreesactualvalue =27;

        int tempindegreesactualvalueint=Integer.parseInt(ndtvpage.tempInDegreeOnlyNumbers);

        if(tempindegreesactualvalueint>apiTemperature)
        {
             differenceTemp=tempindegreesactualvalueint-apiTemperature;
        }
        else
        {
             differenceTemp=apiTemperature-tempindegreesactualvalueint;
        }

       // int differenceTemp=tempindegreesactualvalueint-apiTemperature;

        System.out.println("differenceTemp " +differenceTemp);


    }


    @Test(dependsOnMethods = "comparatorTest")
    public void  configureDataCheck()
    {
        if(differenceTemp>2)
        {
           // System.out.println("This difference in temperature is less so the test is passed ");
            org.testng.Assert.fail("The difference is more so testcase failed");
        }
        else
        {
            System.out.println("This difference in temperature is less so the test is passed ");
            //org.testng.Assert.fail("The difference is more so testcase failed");
        }
    }



    public void setProxymethod() {
        System.setProperty("https.proxyHost", "proxy-chain.intel.com");
        System.setProperty("https.proxyPort", "912");
        System.setProperty("http.proxyPort", "911");
        //RestAssured.useRelaxedHTTPSValidation();
    }


    @AfterTest
    public void quitDriver()
    {
        this.driver.quit();
    }
}