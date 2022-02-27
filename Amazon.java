package week3.weekend.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Amazon {

	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriverManager.chromedriver().setup();
		
		////Disable Notifications
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(option);
		
		//Launch The Url
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		//.search as oneplus 9 pro
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("oneplus 9 pro");
		driver.findElement(By.xpath("//input[@id='nav-search-submit-button']")).click();
		
		//Get the price of the first product
		String price = driver.findElement(By.xpath("//span[@class='a-price-whole']//..")).getText();
		System.out.println("FirstPhonePrice : "+price);
		
		//Print the number of customer ratings for the first displayed product
		String noOfRev = driver.findElement(By.xpath("//span[@class='a-size-base s-underline-text']//..")).getText();
		System.out.println("No Of Peoples Review :"+noOfRev);
		
		//click on the stars & Get the percentage of ratings for the 5 star
		driver.findElement(By.xpath("(//a[@role='button'])[2]")).click();
		String fiveStarPercentage = driver.findElement(By.xpath("(//span[@class='a-size-base'])[2]")).getText();
		System.out.println("FiveStarRatingPercentage : "+fiveStarPercentage);
		
		//Click the first text link of the first image
		driver.findElement(By.xpath("(//div[@class='a-section aok-relative s-image-fixed-height'])[1]")).click();
		
		//Window Handlings
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		ArrayList<String> value =new ArrayList<String>(windowHandles);
		driver.switchTo().window(value.get(1));
		String currentUrl = driver.getCurrentUrl();
		System.out.println("CurrentUrl : "+currentUrl);
		
		//Take a screen shot of the product displayed
		File snapOfShoe = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("/.Amazon.png");
		FileUtils.copyFile(snapOfShoe, destination);
		
		//Click 'Add to Cart' button
		driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
		Thread.sleep(2000);
		
		//Get the cart subtotal and verify if it is correct
		String subTotal = driver.findElement(By.xpath("(//div[@class='a-column a-span11 a-text-left a-spacing-top-large']//span)[3]")).getText();
		String replaceAll = subTotal.replaceAll(".00","");
		System.out.println("SUBTOTAL :"+replaceAll);
		
		//Verify with price and subTotal 
		if(price.equals(replaceAll)){
			System.out.println("Both Total Are Correct");
		}else {
			System.out.println("Both Total Are InCorrect");
		}
		Thread.sleep(1500);
	    driver.quit();
	}
}