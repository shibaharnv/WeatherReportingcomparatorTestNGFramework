package pages;

import org.apache.http.util.Asserts;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class NdtvHomePage {

    public final static String CITY_NAME = "Lucknow";

    public String degreeCelsiueValueInMap;

    public String fahrenHeitValueInMap;


    @FindBy(xpath = "//div[@class='topnav_cont']//a[contains(text(),'INDIA')]")
    private WebElement Indialink;


    @FindBy(xpath = "//a[@class='notnow']")
    private WebElement Nothanklink;


    @FindBy(xpath = "//a[contains(text(),'Weather')]")
    private WebElement Weatherlink;

    @FindBy(id = "topnav_section")
    private WebElement section;

    @FindBy(xpath = "//input[@id='searchBox']")
    private WebElement mapsearchbox;


    @FindBy(xpath = "//input[@id='" + CITY_NAME + "']")
    private WebElement CITY_NAMEwebelement;

    @FindBy(xpath = "//div[contains(@class, 'cityText') and text()='" + CITY_NAME + "']")
    private WebElement CITY_NAMEinmap;

    @FindBy(xpath = "//div[@title='" + CITY_NAME + "']//span[@class='tempRedText']")
    private WebElement degreecelsius;

    @FindBy(xpath = "//div[@title='" + CITY_NAME + "']//span[@class='tempWhiteText']")
    private WebElement fahrenheit;

    @FindBy(id = "map_canvas")
    private WebElement maparea;


    @FindBy(xpath = "//div[@class='leaflet-popup-content-wrapper']")
    private WebElement weatherdetailpopup;

    @FindBy(xpath = "//div[@class='leaflet-popup-content-wrapper']//span[contains(text(),'" + CITY_NAME + "')]")
    private WebElement weatherdetailpopupCITY_NAME;

    @FindBy(xpath = "//div[@class='leaflet-popup-content-wrapper']//span//b[contains(text(),'Temp in Degrees')]")
    private WebElement weatherdetailpopuptempindegrees;

    @FindBy(xpath = "//div[@class='leaflet-popup-content-wrapper']//span//b[contains(text(),'Temp in Fahrenheit')]")
    private WebElement weatherdetailpopuptempinfarenheit;


    private WebDriver driver;
    private WebDriverWait wait;

    public NdtvHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
        PageFactory.initElements(driver, this);
    }

    public void goTo() {
        this.driver.get("https://www.ndtv.com/");
        this.wait.until(ExpectedConditions.visibilityOf(this.Nothanklink));
        this.jsclick(Nothanklink);

    }

    public void navigatetoIndiapage() {
        this.wait.until(ExpectedConditions.visibilityOf(this.Indialink));
        this.jsclick(Indialink);
    }


    public void navigatetoweatherpage() {

        this.wait.until(ExpectedConditions.visibilityOf(this.section));
        while (!Weatherlink.isDisplayed()) {
            this.jsclick(section);
        }
        this.wait.until(ExpectedConditions.visibilityOf(this.Weatherlink));
        this.jsclick(Weatherlink);


    }

    public void storetemperature() {
        this.wait.until(ExpectedConditions.visibilityOf(this.CITY_NAMEwebelement));
        if (!driver.findElement(By.id("" + CITY_NAME + "")).isSelected()) {
            this.jsclick(CITY_NAMEwebelement);
        }

        // movingMap();
        this.wait.until(ExpectedConditions.visibilityOf(this.CITY_NAMEinmap));
        Asserts.check(CITY_NAMEinmap.isDisplayed(), "CITY_NAME displayed in map");
        this.wait.until(ExpectedConditions.visibilityOf(this.degreecelsius));

        degreeCelsiueValueInMap = degreecelsius.getText().toString();
        fahrenHeitValueInMap = fahrenheit.getText().toString();
        System.out.println(degreeCelsiueValueInMap);
        System.out.println(fahrenHeitValueInMap);
        this.wait.until(ExpectedConditions.visibilityOf(this.CITY_NAMEinmap));
        jsclick(CITY_NAMEinmap);


    }


    public void movingMap() {
        Actions actions = new Actions(this.driver);
        WebElement clickmaparea = driver.findElement(By.xpath("//div[contains(@class, 'cityText') and text()='Bhopal']"));
        WebElement dropmaparea = driver.findElement(By.xpath("//div[contains(@class, 'cityText') and text()='Hyderabad']"));
        actions.clickAndHold(clickmaparea).moveToElement(dropmaparea)
                .release().build().perform();

    }


    public void weatherPopUpValidation() {
        this.wait.until(ExpectedConditions.visibilityOf(this.weatherdetailpopup));
        Asserts.check(weatherdetailpopup.isDisplayed(), "weatherdetailpopup displayed in map");
        this.wait.until(ExpectedConditions.visibilityOf(this.weatherdetailpopupCITY_NAME));
        String popupCITY_NAMEactualvalue = weatherdetailpopupCITY_NAME.getText();
        Assert.assertTrue(popupCITY_NAMEactualvalue.contains(CITY_NAME));
        String tempindegreesactualvalue = weatherdetailpopuptempindegrees.getText();
        System.out.println(tempindegreesactualvalue);
        Assert.assertEquals(getOnlyNumbers(tempindegreesactualvalue), getOnlyNumbers(degreeCelsiueValueInMap));
        String tempinfarenheitactualvalue = weatherdetailpopuptempinfarenheit.getText();
        System.out.println(tempinfarenheitactualvalue);
        Assert.assertEquals(getOnlyNumbers(tempinfarenheitactualvalue), getOnlyNumbers(fahrenHeitValueInMap));
    }

    public String getOnlyNumbers(String text) {
        String tempnumberOnly = text.replaceAll("[^0-9]", "");
        return tempnumberOnly;
    }

    public void jsclick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }


}