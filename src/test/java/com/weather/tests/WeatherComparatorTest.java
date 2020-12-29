package com.weather.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.NdtvHomePage;

public class WeatherComparatorTest {

    private WebDriver driver;


    @BeforeTest

    public void setUp(){

        System.setProperty("webdriver.chrome.driver","C:\\Users\\nshibaha\\OneDrive - Intel Corporation\\Desktop\\WORK\\TestVagrant\\chromedriver_win32\\chromedriver.exe");
        this.driver =new ChromeDriver();

    }


    @Test
    public void registrationPage() throws InterruptedException {
        NdtvHomePage ndtvpage = new NdtvHomePage(driver);
        ndtvpage.goTo();
        ndtvpage.navigatetoIndiapage();
        ndtvpage.navigatetoweatherpage();
        ndtvpage.storetemperature();
        ndtvpage.weatherPopUpValidation();

    }


    @AfterTest
    public void quitDriver()
    {
        this.driver.quit();
    }
}