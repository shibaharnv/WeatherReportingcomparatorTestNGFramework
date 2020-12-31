package pages;

import base.testbase;
import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;

public class NdtvHomePage extends testbase {

    private WebDriver driver;
    private WebDriverWait wait;

    public String degreeCelsiueValueInMap;

    public String fahrenHeitValueInMap;

    public String tempindegreesactualvalue;

    public String tempInDegreeOnlyNumbers;


    public String CITY_NAMEnewfromfile = getGlobalvalue("cityName");


    public NdtvHomePage(WebDriver driver) throws IOException {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);

    }

    public WebElement city_NameWebelement(String cityNameData) {
        return driver.findElement(By.xpath("//input[@id='" + cityNameData + "']"));
    }


    public WebElement city_NameInMap(String cityNameData) {
        return driver.findElement(By.xpath("//div[contains(@class, 'cityText') and text()='" + cityNameData + "']"));
    }


    public WebElement degreeCelsius(String cityNameData) {
        return driver.findElement(By.xpath("//div[@title='" + cityNameData + "']//span[@class='tempRedText']"));
    }


    public WebElement fahrenHeit(String cityNameData) {
        return driver.findElement(By.xpath("//div[@title='" + cityNameData + "']//span[@class='tempWhiteText']"));
    }

    public WebElement weatherDetailPopupCityName(String cityNameData) {
        return driver.findElement(By.xpath("//div[@class='leaflet-popup-content-wrapper']//span[contains(text(),'" + cityNameData + "')]"));
    }


    public WebElement Indialink() {
        return driver.findElement(By.xpath("//div[@class='topnav_cont']//a[contains(text(),'INDIA')]"));
    }


    public WebElement Nothanklink() {
        return driver.findElement(By.xpath("//a[@class='notnow']"));
    }


    public WebElement Weatherlink() {
        return driver.findElement(By.xpath("//a[contains(text(),'Weather')]"));
    }

    public WebElement section() {
        return driver.findElement(By.xpath("//div[@id='topnav_section']"));
    }


    public WebElement mapsearchbox() {
        return driver.findElement(By.xpath("//input[@id='searchBox']"));
    }

    public WebElement maparea() {
        return driver.findElement(By.id("map_canvas"));
    }


    public WebElement weatherdetailpopup() {
        return driver.findElement(By.xpath("//div[@class='leaflet-popup-content-wrapper']"));
    }


    public WebElement weatherdetailpopuptempindegrees() {
        return driver.findElement(By.xpath("//div[@class='leaflet-popup-content-wrapper']//span//b[contains(text(),'Temp in Degrees')]"));
    }

    public WebElement weatherdetailpopuptempinfarenheit() {
        return driver.findElement(By.xpath("//div[@class='leaflet-popup-content-wrapper']//span//b[contains(text(),'Temp in Fahrenheit')]"));
    }


    public void storetemperatureNew() {
        this.wait.until(ExpectedConditions.visibilityOf(this.city_NameWebelement(CITY_NAMEnewfromfile)));
        if (!driver.findElement(By.id("" + CITY_NAMEnewfromfile + "")).isSelected()) {
            this.jsclick(city_NameWebelement(CITY_NAMEnewfromfile));
        }
        // movingMap();
        this.wait.until(ExpectedConditions.visibilityOf(this.city_NameInMap(CITY_NAMEnewfromfile)));
        Asserts.check(city_NameInMap(CITY_NAMEnewfromfile).isDisplayed(), "CITY_NAME displayed in map");
        this.wait.until(ExpectedConditions.visibilityOf(this.degreeCelsius(CITY_NAMEnewfromfile)));

        degreeCelsiueValueInMap = degreeCelsius(CITY_NAMEnewfromfile).getText().toString();
        fahrenHeitValueInMap = fahrenHeit(CITY_NAMEnewfromfile).getText().toString();
        System.out.println(degreeCelsiueValueInMap);
        System.out.println(fahrenHeitValueInMap);
        this.wait.until(ExpectedConditions.visibilityOf(this.city_NameInMap(CITY_NAMEnewfromfile)));
        jsclick(city_NameInMap(CITY_NAMEnewfromfile));


    }


    public void weatherPopUpValidationNew() {
        this.wait.until(ExpectedConditions.visibilityOf(this.weatherdetailpopup()));
        Asserts.check(weatherdetailpopup().isDisplayed(), "weatherdetailpopup displayed in map");
        this.wait.until(ExpectedConditions.visibilityOf(this.weatherDetailPopupCityName(CITY_NAMEnewfromfile)));
        String popupCITY_NAMEactualvalue = weatherDetailPopupCityName(CITY_NAMEnewfromfile).getText();
        Assert.assertTrue(popupCITY_NAMEactualvalue.contains(CITY_NAMEnewfromfile));
        tempindegreesactualvalue = weatherdetailpopuptempindegrees().getText();
        System.out.println("tempindegreesactualvalue from map" + tempindegreesactualvalue);
        tempInDegreeOnlyNumbers = getOnlyNumbers(tempindegreesactualvalue);
        Assert.assertEquals(getOnlyNumbers(tempindegreesactualvalue), getOnlyNumbers(degreeCelsiueValueInMap));
        String tempinfarenheitactualvalue = weatherdetailpopuptempinfarenheit().getText();
        System.out.println(tempinfarenheitactualvalue);
        Assert.assertEquals(getOnlyNumbers(tempinfarenheitactualvalue), getOnlyNumbers(fahrenHeitValueInMap));
    }


    public void goTo() throws IOException, InterruptedException {
        this.driver.get(getGlobalvalue("url"));
        Thread.sleep(3000);
        this.wait.until(ExpectedConditions.visibilityOf(this.Nothanklink()));
        this.jsclick(Nothanklink());

    }

    public void navigatetoIndiapage() throws InterruptedException {
        Thread.sleep(3000);
        this.wait.until(ExpectedConditions.visibilityOf(this.Indialink()));
        this.jsclick(Indialink());
    }


    public void navigatetoweatherpage() throws InterruptedException {

        this.wait.until(ExpectedConditions.visibilityOf(this.section()));
        this.jsclick(section());
        while (!Weatherlink().isDisplayed()) {
            this.jsclick(section());
        }
        this.wait.until(ExpectedConditions.visibilityOf(this.Weatherlink()));
        Thread.sleep(5000);

        this.jsclick(Weatherlink());


    }


    public void movingMap() {
        Actions actions = new Actions(this.driver);
        WebElement clickmaparea = driver.findElement(By.xpath("//div[contains(@class, 'cityText') and text()='Bhopal']"));
        WebElement dropmaparea = driver.findElement(By.xpath("//div[contains(@class, 'cityText') and text()='Hyderabad']"));
        actions.clickAndHold(clickmaparea).moveToElement(dropmaparea)
                .release().build().perform();

    }


    public void jsclick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }



}