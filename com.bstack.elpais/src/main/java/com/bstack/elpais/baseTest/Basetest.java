package com.bstack.elpais.baseTest;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Basetest {
	
	protected WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		String root = System.getProperty("user.dir");
		String driverPath = root
	            + File.separator + "src"
	            + File.separator + "main"
	            + File.separator + "java"
	            + File.separator + "com"
	            + File.separator + "bstack"
	            + File.separator + "elpais"
	            + File.separator + "baseTest"
	            + File.separator + "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@AfterClass
	public void tearDown() {
		
		 if (driver != null) {
	            driver.quit();
		 }
	}
}
