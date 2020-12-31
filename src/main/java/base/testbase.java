package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class testbase {


    public static String getGlobalvalue(String key) throws IOException {
        Properties prop = new Properties();

        String SRC_MAIN_RESOURCE_DIR = System.getProperty("user.dir")
                + "//src//test//resource//configureData.properties";

        FileInputStream fis = new FileInputStream(SRC_MAIN_RESOURCE_DIR);

        prop.load(fis);

        return prop.getProperty(key);

    }


    public String getOnlyNumbers(String text) {
        String tempnumberOnly = text.replaceAll("[^0-9]", "");
        return tempnumberOnly;
    }





}