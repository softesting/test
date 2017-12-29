/**
 * Created by Tomasz Diakowski on 2017-12-28.
 */

package pix;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;

public class PixersTest {
    private static WebDriver driver;

    Actions action = new Actions(driver);
    WebDriverWait wait = new WebDriverWait(driver, 10);

    String allPlacats = "//*[@id='main-menu']/nav/ul/li[3]/a[2]";
    String submenu = "id('main-menu')//li[3]/nav[1]";
    String picturesOnPlexa = "id('main-menu')/nav[1]/ul[1]/li[3]/nav[1]/ul[1]/li[5]/a[1]";
    String closeNewsLetter = "div.mfp-wrap.mfp-close-btn-in.mfp-auto-cursor.mfp-fade.mfp-ready:nth-child(2) div.mfp-container.mfp-s-ready.mfp-inline-holder div.mfp-content article.static-block-popup.desktop.container > i.mfp-close.btn-icon.btn-dark.btn-dark-hover-light.icon-close:nth-child(2)";
    String firstPicture = "(//a[@class='btn-icon icon-cart-empty'])[1]";
    String adjustAndBuy = "configButton";
    String size = "//span[text()='150 x 100']/parent::button";
    String selectSize = "//a[text()=' 60 x 60']";
    String buyNowButton = "buyProductButton";
    String goTocart = "//a[text()=' Przejdź do koszyka ']";
    String price = "(//span[@aria-label='price'])[1]";
    String qty = "//h1";

    @BeforeClass
    public static void setUp() throws Exception {
        String chromedriver = "D:/Selenium/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromedriver);
        driver = new ChromeDriver();
        driver.get("https://pixers.pl");
    }
    @Test
    public void test() throws Exception {
        assertEquals("Pixers® • Żyjemy by zmieniać", driver.getTitle());
        WebElement we = driver.findElement(By.xpath(allPlacats));
        action.moveToElement(we).moveToElement(driver.findElement(By.xpath(allPlacats))).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(submenu)));
        driver.findElement(By.xpath(picturesOnPlexa)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(closeNewsLetter)));
        driver.findElement(By.cssSelector(closeNewsLetter)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(firstPicture)));
        WebElement first = driver.findElement(By.xpath(firstPicture));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", first);
        first.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id(adjustAndBuy)));
        driver.findElement(By.id(adjustAndBuy)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(size)));
        driver.findElement(By.xpath(size)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectSize)));
        driver.findElement(By.xpath(selectSize)).click();
        Thread.sleep(1000);
        String itemPrice = driver.findElement(By.xpath(price)).getText();
        int pricesize = itemPrice.length() - 3;
        itemPrice = itemPrice.substring(0,pricesize);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(buyNowButton)));
        driver.findElement(By.id(buyNowButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(goTocart)));
        driver.findElement(By.xpath(goTocart)).click();
        String priceInCart = driver.findElement(By.xpath(price)).getText();
        priceInCart = priceInCart.substring(0,pricesize);
        String itemsInCart = driver.findElement(By.xpath(qty)).getText();
        Assert.assertTrue("nie zgadza się cena produktu z ceną w koszyku : " + itemPrice + " : " + priceInCart ,itemPrice.equals(priceInCart));
        itemsInCart.length();
        itemsInCart = itemsInCart.substring(14,15);
        Assert.assertTrue("nie zgadza się ilość elementów w koszyku. Powinno być 1 a jest : " + itemsInCart ,"1".equals(itemsInCart));
    }
    @AfterClass
    public static void tearDown() throws Exception {
       driver.close();
       driver.quit();
    }
}