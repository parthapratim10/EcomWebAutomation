package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
	
	public WebDriver driver;
	public Properties p;
	
	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters ({"os", "browser"})
	public void setup(String os, String br) throws IOException
	{
		
		//for loading config.properties.file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		//chose execution env for remote 
//		if(p.getProperty("execution_env").equalsIgnoreCase("remote")){
//			
//			DesiredCapabilities capabilities = new DesiredCapabilities();
//			
//			//choose os
//			if(os.equalsIgnoreCase("windows"))
//			{
//				capabilities.setPlatform(Platform.WIN10);
//			}
//			else if (os.equalsIgnoreCase("mac"))
//			{
//				capabilities.setPlatform(Platform.MAC);
//			}
//			else
//			{
//				System.out.println("no matching os");
//				return;
//			}
			
//			//choose browser
//			switch(br.toLowerCase())
//			{
//			case "firefox": capabilities.setBrowserName("firefox"); break;
//			case "chrome": capabilities.setBrowserName("chrome"); break;
//			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
//			
//			default: System.out.println("no matching browser"); 
//			return;
//			}
//			
//			//driver = new RemoteWebDriver(new URL("http://192.168.29.37:4444/wd/hub"), capabilities);
//			driver = new RemoteWebDriver(new URL("http://192.168.29.37:4444/wd/hub"),capabilities);
//		}
		
		
		//for choosing browser type in local env
		
		//if(p.getProperty("exection_env").equalsIgnoreCase("local")) {
			
		switch(br.toLowerCase())
		{
		case "firefox":
			System.setProperty("webdriver.gecko.driver","C:\\browserdrivers\\geckodriver.exe");
			driver=new FirefoxDriver();
			break;
			
		case "chrome":
			System.setProperty("webdriver.chrome.driver","C:\\browserdrivers\\chromedriver.exe");
			driver=new ChromeDriver();
			break;
			
		case "edge":
			System.setProperty("webdriver.edge.driver","C:\\browserdrivers\\msedgedriver.exe");
			driver=new EdgeDriver();
			
		default :
			System.out.println("Invalid Browser name");
			return;
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));  // READING url FROM PROPERTIeS FILE
		driver.manage().window().maximize();
		
}
	

	@AfterClass(groups= {"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.close();
	}

	// to generate random stings, numbers etc
	public String randomString()
	{
	    String generatedString = RandomStringUtils.randomAlphabetic(5);
	    return generatedString;
	}

	public String randomNumber()
	{
	    String generatedNumber = RandomStringUtils.randomNumeric(10);
	    return generatedNumber;
	}

	public String randomAlphanumbeic()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
	    return (generatedString+"@"+generatedNumber);
	}
	
	
	// method to capture screenshot
	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		return targetFilePath;
	}
}
