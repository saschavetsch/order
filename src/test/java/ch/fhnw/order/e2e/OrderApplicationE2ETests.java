package ch.fhnw.order.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Response;


public class OrderApplicationE2ETests {
    static Playwright playwright;
    static Browser browser;
    static Page page;
    static final String BASE_URL = "http://localhost:8081";


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        page = browser.newPage();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @Test
    void testSuccessfulOrderExecution() {
        page.navigate(BASE_URL);
        Response navigationResponse = page.navigate(BASE_URL);
        assertTrue(navigationResponse.ok());
        assertEquals(200, navigationResponse.status());

        assertEquals(BASE_URL + "/", page.url());

        assertNotNull(page.title());

        assertTrue(page.isVisible("body"));
    }
}
