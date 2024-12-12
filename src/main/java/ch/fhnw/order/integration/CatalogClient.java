package ch.fhnw.order.integration;

import ch.fhnw.order.web.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CatalogClient {
    private final RestClient restClient;

    public CatalogClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Book[] findBooks(String search) {
        return restClient
                .get()
                .uri("http://catalog:8080/books?search=%s".formatted(search))
                .retrieve()
                .body(Book[].class);
    }

}
