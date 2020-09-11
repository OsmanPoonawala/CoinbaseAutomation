import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CoinbaseTest {
	
	static WebDriver driver;
	static String targetUrl = "https://www.coinbase.com";
	static String email = "osmanfaisalp@hotmail.com";
	static String pass = "bapva7-qepgiM-citrih";
	static String[] requestedCoins = {"ethereum", "chainlink", "tether"};
	static ArrayList<Integer> coinPrices = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		
		init();
		
		login(email, pass);
		
		twoFactorVerification();
		
		for (int i = 0; i > -1; i++) {
			
			getPrices();
			
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void init() {
		
		String projectpath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectpath + "//drivers/chromedriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(targetUrl);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void login(String email, String pass) {
		
		WebElement LoginButton = driver.findElement(By.xpath("//a[@class='Link__A-eh4rrz-0 cBtbDY HeaderNavItem__LinkStyle-sc-34h4r7-4 HeaderNavItem__LoginLink-sc-34h4r7-6 ictdMy']"));
		LoginButton.click();
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		WebElement EmailBox = driver.findElement(By.xpath("//input[@id='email']"));
		WebElement PassBox = driver.findElement(By.xpath("//input[@id='password']"));
		EmailBox.sendKeys(email);
		PassBox.sendKeys(pass);
				
		WebElement SignInButton = driver.findElement(By.xpath("//input[@id='signin_button']"));
		SignInButton.click();
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void twoFactorVerification() {
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement verificationBox = driver.findElement(By.xpath("//input[@id='token']"));
		verificationBox.sendKeys(TOTPGenerator.getTwoFactorCode());
		
		WebElement verificationButton = driver.findElement(By.xpath("//input[@id='step_two_verify']"));
		verificationButton.click();
	}
	
	public static void getPrices() {
	
		for(int i = 0; i < requestedCoins.length; i++) {
			driver.get(targetUrl + "/price/" + requestedCoins[i]);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				String coinPrice = driver.findElement(By.xpath("//span[@class='AssetChartAmount__Number-sc-1b4douf-1 AWmny']")).getText();
				coinPrices.add(Integer.parseInt(coinPrice));
			} catch (Exception e) {
			}
						
		}
		
		for (int i = 0; i < coinPrices.size(); i++) {
			System.out.println(coinPrices.get(i));
		}
	}
	
}
