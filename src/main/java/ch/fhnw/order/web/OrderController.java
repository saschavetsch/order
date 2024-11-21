package ch.fhnw.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class OrderController {
    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("search", new BookSearch());
        return "book_search";
    }

    @PostMapping("/search")
    public String searchSubmit(@ModelAttribute BookSearch search, Model model) {
        model.addAttribute("search", search);
        return "book_result";
    }
}
