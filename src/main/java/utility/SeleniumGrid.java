package utility;

import base.testbase;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

public class SeleniumGrid extends testbase {

	public static void main(String[] args) throws IOException {

		// Desired capabilities
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.WINDOWS);

		// Chrome options:

		ChromeOptions options = new ChromeOptions();
		options.merge(cap);

		String huburl = "http://localhost:4444/wd/hub";

		WebDriver driver = new RemoteWebDriver(new URL(huburl), options);

		driver.get(getGlobalvalue("url"));

		driver.quit();
	}
}