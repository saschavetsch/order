package ch.fhnw.order.web;

import ch.fhnw.order.integration.CatalogClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
    private final CatalogClient catalogClient;

    private final ShoppingCart shoppingCart = new ShoppingCart();

    private String searchString = "";

    public OrderController(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }
    @GetMapping("")
    public String searchForm(Model model) {
        model.addAttribute("search", new BookSearch());
        model.addAttribute("cartCount", shoppingCart.getItemCount());
        return "index";
    }

    @Retry(name = "searchResult", fallbackMethod = "fallbackSearchResult")
    @PostMapping("/search")
    public String searchResult(@ModelAttribute BookSearch search, Model model) {
        model.addAttribute("search", search);
        if (search.getText() != null && !search.getText().isEmpty()) {
            searchString = search.getText();
        }
        model.addAttribute("books", catalogClient.findBooks(searchString));
        model.addAttribute("cartCount", shoppingCart.getItemCount());

        return "index";
    }

    public String fallbackSearchResult(BookSearch search, Model model, Exception ex) {
        Book[] emptyList = {};
        model.addAttribute("search", search);
        model.addAttribute("books", emptyList);
        model.addAttribute("cartCount", shoppingCart.getItemCount());
        model.addAttribute("error",
                "Catalog service is temporarily unavailable. Please try again later.");

        return "index";
    }

    @Retry(name = "addToCart", fallbackMethod = "fallbackAddToCart")
    @GetMapping("/cart/add/{isbn}")
    public String addToCart(Model model, @PathVariable String isbn) {
        Book book = catalogClient.getBook(isbn);
        if (book != null) {
            shoppingCart.addItem(book);
        }

        return searchResult(new BookSearch(), model);
    }

    public String fallbackAddToCart(Model model, String isbn, Exception ex) {
        model.addAttribute("error",
                "Failed to add book to cart. Catalog service is temporarily unavailable. Please try again later.");
        return searchForm(model);
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model) {
        List<Book> items = shoppingCart.getCart();
        model.addAttribute("items", items);
        return "shopping_cart";
    }
}
