package ch.fhnw.order.integration;

import ch.fhnw.order.web.Book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CatalogClient {
    private final RestClient restClient;

    @Value("${catalog.service.url}")
    private String catalogUrl; 

    public CatalogClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Book[] findBooks(String search) {
        return restClient
                .get()
                .uri(catalogUrl + "/books?search=%s".formatted(search))
                .retrieve()
                .body(Book[].class);
    }

    public Book getBook(String isbn) {
        return restClient
                .get()
                .uri(catalogUrl + "/books/%s".formatted(isbn))
                .retrieve()
                .body(Book.class);
    }
}
