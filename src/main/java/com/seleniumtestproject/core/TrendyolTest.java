package com.seleniumtestproject.core;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Selenium Trendyol Test Project
 **/
public class TrendyolTest {
	WebDriver driver;
	WebDriverWait webDriverWait;
	WebElement username, password, search;
	String productPrice,cartPriceString,cartPriceString2;
	
	@BeforeClass
	public void beforeClass(){
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\Documents\\Drivers\\chromedriver.exe");// chromedriver.exe key value
		System.out.println("Driver Check");// Log
		driver = new ChromeDriver();// Web driver create object
		driver.manage().window().maximize();// window full screen
		
		// Main page check
		driver.get("https://www.trendyol.com/");// Connection website
		System.out.println("Main Page Loaded...");// Log
		webDriverWait = new WebDriverWait(driver, 20);
	}
	
	@Test
	public void TesClasst() {

		loginCheck();
		searchCheck("Bilgisayar-Bileşenleri"+"\n");  //search click \n-> enter
		SelectProduct();
		BasketProductTest();
	}
	
	@AfterClass
	public void afterClass() {
		// driver.close();
	}

	public void loginCheck() {
		driver.get("https://www.trendyol.com/login");// login page

		// input definitions
		username = driver.findElement(By.name("LoginModel.Email"));
		username.sendKeys("yk@yopmail.com");
		password = driver.findElement(By.name("LoginModel.Password"));
		password.sendKeys("Qwer1234");

		driver.findElement(By.name("loginSubmitButton")).click();//login submit
		
		//equality check
		String expected_title = "giriş";
		String actual_title = driver.getTitle();
		Assert.assertEquals(expected_title, actual_title);
		if (expected_title.equals(actual_title)) 
		{
			System.out.println("Login Successfull");
		} 
		else 
		{
			System.out.println("Login Failed");
		}
		
	}

	public void searchCheck(String searchKey){
		//search word
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
		    search = driver.findElement(By.xpath("//input[@class='search-box' and @type='text']"));
		    search.click();
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			search = driver.findElement(By.xpath("//input[@class='search-box' and @type='text']"));
		    search.click();
		}
		search.sendKeys(searchKey);
		
	}
	
	public void SelectProduct(){
		driver.findElement(By.className("p-card-img")).click(); //click on the product.
		productPrice = driver.findElement(By.className("prc-slg")).getText();//Price before adding to cart.
		System.out.println(productPrice);
	}
	
	public void BasketProductTest(){
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Adding the product to the basket.
		try {
			driver.findElement(By.xpath("//button[@class='pr-in-btn add-to-bs']")).click();
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			System.out.println("Try errorrrrrr.");
		}
		System.out.println("The product was added to the basket.");
		
		driver.get("https://www.trendyol.com/sepetim#/basket");//Basket page
		
        List<WebElement> basketPrice = driver.findElements(By.className("pb-basket-item-price"));//Choosing the product.
        cartPriceString = basketPrice.get(0).getText();//Price in the basket
        cartPriceString2=cartPriceString.split("\n")[1];//I pulled the final price from the discounted and non-discounted price.
        System.out.println(cartPriceString);//log
        System.out.println(cartPriceString2);//log
        
        //equality check
        Assert.assertEquals(productPrice,cartPriceString2);
        if (productPrice.equals(cartPriceString2)) 
        {
			System.out.println("Product prices compatible");
		} else 
		{
			System.out.println("The price of the product is incorrect!");
		}
        
        //Increasing and decreasing the number of products.
		//driver.findElement(By.xpath("//button[@class='ty-numeric-counter-button']")).click();
		WebElement deneme=  driver.findElement(By.xpath("//input[@class='counter-content']"));
		deneme.click();
		deneme.sendKeys("2");
		System.out.println("product 2");
	
        //The process of deleting from the basket.
        driver.findElement(By.className("i-trash")).click(); 
		driver.findElement(By.xpath("//button[@class='btn-item btn-remove' and @type='button']")).click();
		System.out.println("The product has been deleted.");
	}
	
	

}
