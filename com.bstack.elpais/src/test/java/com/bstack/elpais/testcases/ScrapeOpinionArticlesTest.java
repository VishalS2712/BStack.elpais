package com.bstack.elpais.testcases;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.bstack.elpais.baseTest.Basetest;

public class ScrapeOpinionArticlesTest extends Basetest{
	
	static String[] headers = new String[5];
	
	
	    @Test(priority = 2)
	    public void scrapeArticlesFromOpinion() {
	    	
	    	driver.get("https://elpais.com/opinion/");
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
	       //driver.findElement(By.xpath("//a[@href='https://elpais.com/opinion/']")).click();
	    	//driver.get("https://elpais.com/opinion/");
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        List<WebElement> articles = driver.findElements(By.xpath("//article"));
	        

	        for (int i=0;i<5;i++) {
	        	
	        	    WebElement article = articles.get(i);
	        	    System.out.println("-----------------");
	        	    System.out.println("Article "+(i+1));
	            
	                // Get the title
	                WebElement titleElement = article.findElement(By.cssSelector("h2.c_t a"));
	                String title = titleElement.getText();
	                System.out.println("Title: " + title);
	                
	                try {
	                // Get the paragraph
	                WebElement paraElement = article.findElement(By.cssSelector("p.c_d"));
	                String description = paraElement.getText();
	                System.out.println("Description: " + description);
	                }
	                catch(Exception e) {}
	                
	                try {
	                   
	                    // Get the <img> tag
	                    WebElement img = article.findElement(By.tagName("img"));
	                    String imageUrl = img.getAttribute("src");
	                    
	                    // Save to device
	                    String projectRoot = System.getProperty("user.dir");
	                    String imgsDir = projectRoot + File.separator + "target" + File.separator + "imgs";
	                    File dir = new File(imgsDir);
	                    if (!dir.exists()) {
	                        dir.mkdirs();
	                    }
	                    String fileName = "article_image_" + (i + 1) + ".jpg";
	                    String savePath = imgsDir + File.separator + fileName;

	                    //saveImage(imageUrl, savePath);
	                    System.out.println("Image found and saved to: " + savePath);
	                    
	                    
						//URL url = new URL(imageUrl);
						URI uri = new URI(imageUrl);
						URL url = uri.toURL();
	                    InputStream is = url.openStream();
	                    Files.copy(is, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
	                    is.close();

	                } catch (Exception e) {
	                    System.out.println("No image found in article " + (i + 1));
	                }
	        }//for loop closing
	        
	        


	    }// test closing
	    
	    @Test(priority = 3)
	    public void translateArticleHeaders() {
	    	
	    	System.out.println("-------------");
	    	
	    	driver.get("https://elpais.com/opinion/");
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
	        
	        //driver.findElement(By.xpath("//a[@href='https://elpais.com/opinion/']")).click();
	      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	//driver.get("https://elpais.com/opinion/");

	    	List<WebElement> articles = driver.findElements(By.xpath("//article"));
	        
	         for (int i=0;i<5;i++) {
	         	
	        	    WebElement article = articles.get(i);
	            
	                // Get the title (inside h2 > a)
	                WebElement titleElement = article.findElement(By.cssSelector("h2.c_t a"));
	                String title = titleElement.getText();
	                try {
	                	
	                	String textToTranslate = title;
	                    String fromLang = "es";
	                    String toLang = "en";

	                    // Prepare JSON payload
	                    JSONObject json = new JSONObject();
	                    json.put("text", textToTranslate);
	                    json.put("to", toLang);
	                    json.put("from_lang", fromLang);

	                    // Prepare the URL and open connection
	                    //URL url = new URL("https://google-api31.p.rapidapi.com/gtranslate");
	                    URI uri = new URI("https://google-api31.p.rapidapi.com/gtranslate");
	            		URL url = uri.toURL();
	                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	                    // Set method and headers
	                    conn.setRequestMethod("POST");
	                    conn.setRequestProperty("Content-Type", "application/json");
	                    conn.setRequestProperty("X-RapidAPI-Key", "78e4475852mshfe92f6a91371abep17f260jsnf379795961b0"); 
	                    conn.setRequestProperty("X-RapidAPI-Host", "google-api31.p.rapidapi.com");

	                    // Send JSON body
	                    conn.setDoOutput(true);
	                    try (OutputStream os = conn.getOutputStream()) {
	                        byte[] input = json.toString().getBytes("utf-8");
	                        os.write(input, 0, input.length);
	                    }

	                    // Read response
	                    int status = conn.getResponseCode();
	                    InputStream is = (status < 400) ? conn.getInputStream() : conn.getErrorStream();

	                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
	                    StringBuilder response = new StringBuilder();
	                    String line;

	                    while ((line = br.readLine()) != null) {
	                        response.append(line.trim());
	                    }

	                    br.close();
	                    conn.disconnect();

	                    // Parse and print translation
	                    JSONObject jsonResponse = new JSONObject(response.toString());
	                    //System.out.println("Raw API response: " + jsonResponse.toString(2));
	                    String translated = jsonResponse.getString("translated_text");
	                    System.out.println("Translated Titles: " + translated);
	                    
	                    headers[i]=translated;
	                }
	                
	                catch(Exception e){}
	    	    	System.out.println("-------------");

	         }
	    	
	    	
	    }

	    @Test(priority = 4, dependsOnMethods = "translateArticleHeaders")
	    public void analyzeTranslatedHeaders() {
	    	 Map<String, Integer> wordCount = new HashMap<>();

	         for (String header : headers) {
	             // Converting to lowercase, remove punctuation, split into words
	             String[] words = header.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
	             
	             for (String word : words) {
	                 if (!word.isEmpty()) {
	                     wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
	                 }
	             }
	         }

	         // Print words that occur more than twice
	         System.out.println("Repeated words ");
	         for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
	             if (entry.getValue() > 2) {
	                 System.out.println(entry.getKey() + " -> " + entry.getValue());
	             }
	         }
	    
	    }
}
	    

