import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.ArrayList;
import java.util.List;

public class CheckSth {

    public static WebDriver driver = new ChromeDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 60);

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        List<String> products = new ArrayList<String>();

        goToPage("https://ezakupy.tesco.pl/groceries/pl-PL/");
        searchProduct("papryka");
        By productName = By.xpath("//a[contains(@class, 'product-tile--title product-tile--browsable')]");

        while (true) {
            products.addAll(getProductsName(productName));

            if(! changePage()) {
                break;
            }
        }
        System.out.println(products);
        driver.quit();
    }

    public static void goToPage(String url) {
        driver.get(url);
    }

    public static boolean changePage() {
        By nextPageButton = By.xpath("//a[contains(@class, 'pagination--button prev-next')]/span[contains(@class, 'icon-icon_whitechevronright')]");
        By nextPageButtonDisabled = By.xpath("//a[contains(@class, 'pagination--button prev-next disabled')]/span[contains(@class, 'icon-icon_whitechevronright')]");

        if (! driver.findElements(nextPageButtonDisabled).isEmpty()) {
            return false;
        }

        driver.findElement(nextPageButton).click();
        return true;
    }

    public static void waitForElement(By element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public static void searchProduct(String name) {
        By searchInput = By.id("search-input");
        driver.findElement(searchInput).click();
        driver.findElement(searchInput).sendKeys("papryka");

        By submitButton = By.xpath("//div[@class='search-bar']//button[@type='submit']");
        driver.findElement(submitButton).click();
    }

    public static List<String> getProductsName(By productName) {
        List<String> products = new ArrayList<String>();

        waitForElement(productName);

        for(WebElement product: driver.findElements(productName)) {
            if(product.isDisplayed()) {
                products.add(product.getAttribute("textContent"));
            }
        }

        return products;
    }
}
