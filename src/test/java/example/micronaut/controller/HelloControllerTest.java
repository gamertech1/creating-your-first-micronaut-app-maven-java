package example.micronaut.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import example.micronaut.service.HelloService;
import io.micronaut.http.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;
import org.mockito.Mockito;


@MicronautTest
class HelloControllerTest {

    @Inject
    @Client("/")  // Inject Micronaut HTTP Client
    HttpClient client;

    @Inject
    HelloService helloService;  // Inject Mocked Service

    @BeforeEach
    void setup() {
        // Mock behavior: When helloService.sayHello() is called, return "Mocked Hello"
        when(helloService.sayHello()).thenReturn("Mocked Hello");
    }

    @Test
    void testHelloEndpoint() {
        // Create a GET request to "/hello"
        HttpRequest<String> request = HttpRequest.GET("/hello");

        // Execute the request and retrieve the response
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());  // Assert HTTP 200 OK
        assertEquals("Mocked Hello", response.body());  // Assert response body
    }

    @Test
    void testHelloWithQueryParameter() {
        // Create a GET request with a query parameter
        HttpRequest<String> request = HttpRequest.GET("/hello/greet?name=John");

        // Execute the request and retrieve the response
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());  // Assert HTTP 200 OK
        assertEquals("Hello, John!", response.body());  // Assert response with query parameter
    }

    @Test
    void testNotFoundEndpoint() {
        // Request to an unknown endpoint
        HttpRequest<String> request = HttpRequest.GET("/unknown");

        // Try to send the request and catch the exception
        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(request, String.class));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    // Mock the HelloService to control responses during testing
    @MockBean(HelloService.class)
    HelloService mockedHelloService() {
        return Mockito.mock(HelloService.class);
    }
}
