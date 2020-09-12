import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CoinbaseTest {
	
	static WebDriver driver;
	static String targetUrl = "https://www.coinbase.com";
	static String email = "osmanfaisalp@hotmail.com";
	static String pass = "bapva7-qepgiM-citrih";
	static String[] requestedCoins = {"ethereum", "chainlink", "tether"};
	static ArrayList<Double> coinPrices = new ArrayList<Double>();
	
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
		
		ChromeOptions options = new ChromeOptions();
		String userAgent = RandomUserAgent.getRandomUserAgent();
		options.addArguments(userAgent);
		
		String projectpath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectpath + "//drivers/chromedriver/chromedriver");
		driver = new ChromeDriver(options);
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
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				String coinPriceInt = driver.findElement(By.xpath("//span[@class='AssetChartAmount__Number-sc-1b4douf-1 AWmny']")).getText();
				String coinPriceDecimal = driver.findElement(By.cssSelector("div.Transitioner__Container-sc-1nlar1d-0.fmqQye div.moduleFade__Frame-sc-53f9yi-0.eCGjpA div.Flex-l69ttv-0.kNzJnP div.Flex-l69ttv-0.Layout__Container-sc-140tb7h-0.iOtOlJ div.Flex-l69ttv-0.LayoutDesktop__AppWrapper-bh368c-1.jnVjyC div.Flex-l69ttv-0.LayoutDesktop__ContentContainer-bh368c-3.cVvWeI div.Flex-l69ttv-0.LayoutDesktop__Wrapper-bh368c-2.kknCrF:nth-child(3) div.Flex-l69ttv-0.LayoutDesktop__Content-bh368c-4.hSskCm div.styles__OuterContainer-ehviky-0.jaHWnl div.Transitioner__Container-sc-1nlar1d-0.fmqQye div.moduleFade__Frame-sc-53f9yi-0.eCGjpA div.styles__InnerContainer-ehviky-1.eUGEHB div.Transitioner__Container-sc-1nlar1d-0.fmqQye div.moduleFade__Frame-sc-53f9yi-0.eCGjpA div.Flex-l69ttv-0.kvilOX div.Flex-l69ttv-0.AssetOverview__OverviewBody-sc-1y6aknu-1.fvNaNq div.Flex-l69ttv-0.AssetOverview__ChartColumn-sc-1y6aknu-2.cEazxh:nth-child(1) div.Flex-l69ttv-0.AssetOverview__ChartContainer-sc-1y6aknu-4.kQNFbI div.Transitioner__Container-sc-1nlar1d-0.fmqQye:nth-child(1) div.moduleFade__Frame-sc-53f9yi-0.eCGjpA div.AssetChart__ChartContainer-xr1uk5-0.bCHGko div.Transitioner__Container-sc-1nlar1d-0.fmqQye div.moduleFade__Frame-sc-53f9yi-0.eCGjpA div.Chart__Wrapper-sc-11ivqj-0.hpfldP div.Flex-l69ttv-0.Chart__ControlBar-sc-11ivqj-2.bfPpxX:nth-child(1) div.AssetChartHeader__Container-sc-111iush-0.fBcijN div.Flex-l69ttv-0.AssetChartAmount__Wrapper-sc-1b4douf-0.bIaMsv:nth-child(1) > span.AssetChartAmount__AmountSuper-sc-1b4douf-2.hWKAUI:nth-child(3)")).getText();
				String coinPrice = coinPriceInt + coinPriceDecimal;
				coinPrices.add(Double.parseDouble(coinPrice));
			} catch (Exception e) {
			}
						
		}
		
		for (int i = 0; i < coinPrices.size(); i++) {
			System.out.println(coinPrices.get(i));
		}
		coinPrices.clear();
	}
	
}
