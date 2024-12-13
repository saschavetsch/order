package ch.fhnw.order.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;


public class OrderApplicationE2ETests {
    static Playwright playwright;
    static Browser browser;
    static BrowserContext context;
    static Page page;
    static final String BASE_URL = "http://localhost:8081";

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    // TBD - clear the cart after each test: no possiblity atm in frontend.
    @Test
    void testSuccessfulOrderExecution() {
        page.navigate(BASE_URL);
        assertEquals("Title", page.title());

        // Enter search term one by one - search for "Sapiens"
        page.locator("#search").pressSequentially("Sapiens");
        page.locator("#submit").click();

        // Test if book has been found
        assertTrue(page.isVisible("text=Sapiens"));

        // Add book to cart
        page.locator("button:has-text('Add to cart')").first().click();
        
        // Check if cart icon shows correct book count
        assertEquals("1", page.locator(".ml-2.text-sm.font-medium").innerText());

        // Press cart icon
        page.locator("a[href='/cart']").click();

        // Check if book has been added to the shopping cart
        assertTrue(page.isVisible("text=Sapiens"));
    }
}
