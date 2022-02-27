package week3.weekend.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SnapDeal1 {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		
		//Disable Notifications
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(option);
		
		//Launch The Url
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		//Go to Mens Fashion
		WebElement mensFashion = driver.findElement(By.xpath("//span[@class='catText']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(mensFashion).perform();
		
		//Go to Sports Shoes & Verify The Title
		driver.findElement(By.xpath("//span[text()='Sports Shoes']")).click();
		String title = driver.getTitle();
		System.out.println(title);
		String title2 ="Sports Shoes For Men - Upto 70% OFF on Top Shoe Brands";
		if(title.equals(title2)) {
			System.out.println("Title Is Correct");
		}else {
			System.out.println("Title Is Incorrect");
		}
		
		//Get the count of the sports shoes
		Thread.sleep(1500);
		WebElement shoesCount = driver.findElement(By.xpath("//div[@class='child-cat-name selected']/following-sibling::div"));
		String text = shoesCount.getText();
		System.out.println("ShoesCount="+text);
		
		//Click Training shoes & Sort by Low to High
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();
		driver.findElement(By.xpath("//div[@class='sort-drop clearfix']")).click();
		driver.findElement(By.xpath("(//ul[@class='sort-value']//li)[2]")).click();
		Thread.sleep(2000);
		
		//Check if the items displayed are sorted correctly
		List<WebElement> product = driver.findElements(By.xpath("//div[@id='products']//span[@class='lfloat product-price']"));
		int size = product.size();
		System.out.println(size);
		for(int i =1;i<=size;i++) {
			WebElement sortPrice = driver.findElement(By.xpath("(//span[@class='lfloat product-price'])["+i+"]"));
			String text2 = sortPrice.getText();
			System.out.println("SortedPrice :"+text2);
		}
		
		//Select the price range (900-1200)
		WebElement from = driver.findElement(By.xpath("(//div[@class='price-text-box']//input)[1]"));
		from.clear();
		from.sendKeys("900");
		WebElement to = driver.findElement(By.xpath("(//div[@class='price-text-box']//input)[2]"));
		to.clear();
		to.sendKeys("1200");
		driver.findElement(By.xpath("//div[@class='price-go-arrow btn btn-line btn-theme-secondary']")).click();
		
		//Filter with color Navy & verify the all applied filters
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[@for='Color_s-Navy']//..")).click();
		WebElement verfyFilter = driver.findElement(By.xpath("(//div[@class='filters'])[1]"));
		String filter = verfyFilter.getText();
		System.out.println("Filters Applied : "+filter);
		
		//Mouse Hover on first resulting Training shoes
		Thread.sleep(2000);
		WebElement mouseOver = driver.findElement(By.xpath("(//div[@class='clearfix row-disc'])[1]"));
		Actions builder1 = new Actions(driver);
		builder1.moveToElement(mouseOver, 5, 5).build().perform();
		
		//click QuickView button
		driver.findElement(By.xpath("//div[contains(text(),'Quick View')]")).click();
		
		//Print the cost and the discount percentage
		WebElement costDis = driver.findElement(By.xpath("//span[@class='payBlkBig']//.."));
		String costDiscount = costDis.getText();
		System.out.println("CostDiscount : "+costDiscount);
		
		//Take the snapshot of the shoes.
		driver.findElement(By.xpath("//div[@class='sidebaroverlay']"));
		File snapOfShoe = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("/.SnapDeal.png");
		FileUtils.copyFile(snapOfShoe, destination);
		
		// Close the current window & Close the main window
		driver.findElement(By.xpath("((//i[@class='sd-icon sd-icon-delete-sign'])[3])//..")).click();
		Thread.sleep(500);
		driver.close();
		System.out.println("Sucess");
	}
}