package ch.fhnw.order.web;

import ch.fhnw.order.integration.CatalogClient;
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

    @GetMapping("/cart/add/{isbn}")
    public String addToCart(Model model, @PathVariable String isbn) {
        Book book = catalogClient.getBook(isbn);
        if (book != null) {
            shoppingCart.addItem(book);
        }

        return searchResult(new BookSearch(), model);
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model) {
        List<Book> items = shoppingCart.getCart();
        model.addAttribute("items", items);
        return "shopping_cart";
    }
}
