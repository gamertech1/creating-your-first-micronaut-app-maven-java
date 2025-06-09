package example.micronaut.controller;

import example.micronaut.model.Product;
import example.micronaut.model.UserData;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class ProductControllerTest {
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void test_userController_firstName_blank() {
        var userData = new Product(1, "", 500.00);

        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(
                    HttpRequest.POST("/products", userData)
                            .contentType(MediaType.APPLICATION_JSON),
                    Argument.of(Product.class)
            );
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

    }  }