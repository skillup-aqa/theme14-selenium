package ua.skillup;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.*;

public class SampleSeleniumTest {
    WebDriver driver;

    @BeforeMethod(enabled = false)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.setEnableDownloads(true);
        driver = new ChromeDriver(options);
       // driver.get("https://www.saucedemo.com/");
    }

    @Test(enabled = false)
    public void testLoginWithUI() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

    }

    @Test(enabled = false)
    public void testExecuteJS() {
        driver.get("https://example.cypress.io/todo");
        // Preparing test data
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem(arguments[0],arguments[1])",
                "todos-vanillajs",
                "[{\"title\":\"test1\",\"completed\":false}]");
        driver.navigate().refresh();
        List<WebElement> todos = driver.findElements(By.cssSelector(".todo-list li"));
        assertEquals(todos.size(), 1);
        assertEquals(todos.get(0).getText(), "test1");
    }

    @Test(enabled = false)
    public void testScreenshot()  {
        byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        File file = new File("screenshot01.png");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = false)
    public void testDragAndDrop() {
        driver.get("https://practice-automation.com/gestures/");
        By sourceLocator = By.id("dragMe");
        WebElement source = driver.findElement(sourceLocator);
        WebElement target1 = driver.findElement(By.id("div1"));
        WebElement target2 = driver.findElement(By.id("div2"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target2).perform();
        assertEquals(target2.findElements(sourceLocator).size(), 1,
                "The source element should be moved to target2");
        actions.dragAndDrop(source, target1).perform();
        assertEquals(target1.findElements(sourceLocator).size(), 1,
                "The source element should be moved to target1");
    }

    @Test(enabled = false)
    public void testAlert() {
        driver.get("https://practice-automation.com/popups/");
        driver.findElement(By.id("alert")).click();
        Alert alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "Hi there, pal!");
        alert.accept();
        driver.findElement(By.id("confirm")).click();
        alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "OK or Cancel, which will it be?");
        alert.dismiss();
        assertEquals(driver.findElement(By.id("confirmResult")).getText(), "Cancel it is!");
        driver.findElement(By.id("prompt")).click();
        alert = driver.switchTo().alert();
        alert.sendKeys("Test User");
        alert.accept();
        assertEquals(driver.findElement(By.id("promptResult")).getText(), "Nice to meet you, Test User!");
    }

    @Test(enabled = false)
    public void testIFrame() {
        driver.get("https://practice-automation.com/iframes/");
        driver.switchTo().frame("frame1");
        assertEquals(driver.findElement(By.cssSelector("a[href='/about/']")).getText(), "About Selenium");
    }

    @Test(enabled = false)
    public void testUploadFile() throws IOException {
        // Create a test file
        File file = Files.createTempFile("file", ".txt").toFile();
        Files.writeString(file.toPath(), "Hello, World!");
        driver.get("https://practice-automation.com/file-upload/");
        driver.findElement(By.id("file-upload")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.id("upload-btn")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(visibilityOfElementLocated(By.xpath(
                        "//div[text()='Thank you for your message. It has been sent.']")));
    }


    @Test(enabled = false)
    public void testWindowManagement() {
        driver.get("https://practice-automation.com/window-operations/");
        String originalWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//b[text()='New Tab']/..")).click();

        String newWindowHandle = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(originalWindow))
                .findFirst()
                .orElseThrow(() -> new AssertionError("New window was not opened"));
        driver.switchTo().window(newWindowHandle);
        assertEquals(driver.getCurrentUrl(), "https://automatenow.io/");
        driver.switchTo().window(originalWindow);
        assertEquals(driver.getCurrentUrl(), "https://practice-automation.com/window-operations/");
    }
    @Test(enabled = false)
    public void waitForElement() {
        driver.get("https://practice.expandtesting.com/slow");
        WebElement alert = new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until(webDriver -> driver.findElement(By.cssSelector("#result > .alert"))
                );
        // WebElement alert = new WebDriverWait(driver, Duration.ofSeconds(10))
        // .until(visibilityOfElementLocated(By.cssSelector("#result > .alert")));
        assertEquals(alert.getText(), "The slow task has finished. Thanks for waiting!");
    }


    @Test(enabled = false)
    public void testLoginWithApi() {
        driver.get("https://www.saucedemo.com/");
        driver.manage().addCookie(
                new Cookie.Builder("session-username", "standard_user").build());
        driver.get("https://www.saucedemo.com/inventory.html");
        //assertTrue(driver.findElement(By.id("shopping_cart_container")).isDisplayed());
        assertFalse(driver.findElements(By.id("inventory_container")).isEmpty());
    }

    @Test(enabled = false)
    public void manageWindow() {
       // driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().window().fullscreen();
        driver.manage().window().setSize(new Dimension(800, 600));
        driver.manage().window().setPosition(new Point(100, 100));
    }

    @Test(enabled = false)
    public void testNavigate() throws MalformedURLException {
        driver.navigate().to(new URL("https://automationexercise.com/"));
        String homePageTitle = driver.getTitle();
        driver.navigate().to("https://automationexercise.com/view_cart");
        assertNotEquals(driver.getTitle(), homePageTitle);
        driver.navigate().back();
        assertEquals(driver.getTitle(), homePageTitle);
        driver.navigate().refresh();
        assertEquals(driver.getTitle(), homePageTitle);
    }

    @AfterMethod(enabled = false)
    public void tearDown() {
        driver.quit();
    }
}

