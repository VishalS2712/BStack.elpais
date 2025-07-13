package com.bstack.elpais.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bstack.elpais.baseTest.Basetest;

public class CheckSpanishTest extends Basetest{
	 
	@Test(priority = 1)
    public void checkSpanishLanguage() {
		
		driver.get("https://elpais.com/?ed=es");
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
     
    	  WebDriverWait wait = new WebDriverWait(driver, 10);
          JavascriptExecutor js = (JavascriptExecutor) driver;

          try {
             
              WebElement acceptBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                  By.cssSelector("button#didomi-notice-agree-button")));

              js.executeScript("arguments[0].click();", acceptBtn);

              System.out.println("Removing popup...");
          } catch (Exception e) {
              System.out.println("Removing popup...");

              js.executeScript("let popup = document.querySelector('#didomi-notice'); if(popup) popup.remove();");
          }


     String lang = driver.findElement(By.xpath("/html")).getAttribute("lang");
     System.out.println("Page language: " + lang);

     Assert.assertTrue(lang != null && lang.toLowerCase().startsWith("es"), "Page is NOT in Spanish");
 }
	
}


