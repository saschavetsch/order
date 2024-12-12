package ch.fhnw.order.web;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Book> cart = new ArrayList<>();

    public List<Book> getCart() {
        return cart;
    }

    public void addItem(Book book) {
        cart.add(book);
    }
}
