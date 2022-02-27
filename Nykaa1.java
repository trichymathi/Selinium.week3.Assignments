package week3.weekend.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa1 {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		WebElement brand = driver.findElement(By.xpath("//a[text()='brands']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(brand).perform();
		
		WebElement search = driver.findElement(By.xpath("(//input[@id='brandSearchBox'])"));
		search.sendKeys("L'Oreal Paris");
		
		driver.findElement(By.linkText("L'Oreal Paris")).click();
		String title = driver.getTitle();
		System.out.println(title);
		String title1 ="Buy L'Oreal Paris products online at best price on Nykaa | Nykaa";
		
		if(title1.equals(title)) {
			System.out.println("The Page You Gone Is Correct");
		}else {
			System.out.println("The Page You Gone Is Wrong");
		}
		//Click sort By and select customer top rated
		driver.findElement(By.xpath("//div[@id='filter-sort']//button")).click();
		driver.findElement(By.xpath("(//div[@class='control-indicator radio '])[3]")).click();
		
		//Click Category and click Hair->Click haircare->Shampoo
		driver.findElement(By.xpath("//div[@id='first-filter']/div")).click();
		driver.findElement(By.xpath("//span[text()='Hair']/..")).click();
		driver.findElement(By.xpath("//span[text()='Hair Care']/..")).click();
		driver.findElement(By.xpath("//input[@id='checkbox_Shampoo_316']/..")).click();
		
		// Click->Concern->Color Protection
		driver.findElement(By.xpath("//span[text()='Concern']/..")).click();
		driver.findElement(By.xpath("//input[@id='checkbox_Color Protection_10764']//..")).click();
		
		//check whether the Filter is applied with Shampoo
		WebElement filterCheck = driver.findElement(By.xpath("(//div[@id='filters-listing']//div)[3]"));
		String filter = filterCheck.getText();
		System.out.println("FiltersApplied : "+filter);
		
		//Click on L'Oreal Paris Colour Protect Shampoo & GO to the new window and select size as 175ml
		driver.findElement(By.xpath("(//div[@class='css-1rd7vky'])[1]")).click();
		Thread.sleep(500);
		
		//Switch To Next Tab (To Access another Window)
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		ArrayList<String> value =new ArrayList<String>(windowHandles);
		driver.switchTo().window(value.get(1));
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		
	    //select size as 175ml
		WebElement dropdown = driver.findElement(By.xpath("(//select[@title='SIZE'])"));
		Select drop =new Select(dropdown);
		drop.selectByVisibleText("175ml");
		
		//Print MRP and Click On Add To Bags
		WebElement getMrp = driver.findElement(By.xpath("(//div[@class='css-k400rm']//span)[3]"));
		String Mrp = getMrp.getText();
		System.out.println("MRP :"+Mrp);
		
		//Click on ADD to BAG &  Go to Shopping Bag &  Print the Grand Total amount 
		WebElement addToBags = driver.findElement(By.xpath("//span[text()='ADD TO BAG']/.."));
		addToBags.click();
		driver.findElement(By.xpath("//span[@class='cart-count']//..")).click();
		Thread.sleep(2000);
		
		//Click Proceed
		driver.switchTo().frame(0);
		String grandTotal = driver.findElement(By.xpath("(//div[@class='first-col'])")).getText();
		System.out.println(grandTotal);
		driver.findElement(By.xpath("//span[text()='PROCEED']//..//..")).click();
		
		// Click Continue As Guest	& Check The grand Total
		driver.findElement(By.xpath("//button[text()='CONTINUE AS GUEST']")).click();
		if(Mrp.equals(grandTotal)) {
			System.out.println("The Grand Total Is Same");
		}else {
			System.out.println("The Grand Total Is Not Same");
		}
		driver.quit();
		//driver.close();
	}
}