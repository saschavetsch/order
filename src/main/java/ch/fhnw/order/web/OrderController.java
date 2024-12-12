package ch.fhnw.order.web;

import ch.fhnw.order.integration.CatalogClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class OrderController {
    private final CatalogClient catalogClient;

    private final ShoppingCart shoppingCart = new ShoppingCart();

    public OrderController(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }
    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("search", new BookSearch());
        return "book_search";
    }

    @PostMapping("/search")
    public String searchSubmit(@ModelAttribute BookSearch search, Model model) {
        model.addAttribute("search", search);

        model.addAttribute("books", catalogClient.findBooks(search.getText()));

        return "book_result";
    }

    @GetMapping("/cart/add/{isbn}")
    public String addToCart(Model model, @PathVariable String isbn) {
        Book book = catalogClient.getBook(isbn);
        if (book != null) {
            shoppingCart.addItem(book);
        }

        return shoppingCart(model);
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model) {
        List<Book> items = shoppingCart.getCart();
        model.addAttribute("items", items);
        return "shopping_cart";
    }
}
