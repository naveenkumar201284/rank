package com.qst.ohrm.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewChrome
{
	WebDriver driver;
	
	@BeforeMethod
	public void a()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	
	@Test
	public void b() throws InterruptedException
	{
		driver.get("https://www.google.com");
		Thread.sleep(3000);
		System.out.println(driver.getTitle());
	}
	
	@AfterMethod
	public void c()
	{
		driver.quit();
	}
}
