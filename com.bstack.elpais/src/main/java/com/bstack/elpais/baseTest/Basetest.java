package com.bstack.elpais.baseTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Basetest {
	
	protected WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		 	
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Vishal Sharma\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
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
