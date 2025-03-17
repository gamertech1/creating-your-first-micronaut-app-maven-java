package example.micronaut.controller;

import example.micronaut.model.UserData;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import io.micronaut.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class DataControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testGetUserData() {
        HttpRequest<String> request = HttpRequest.GET("/data/1");
        HttpResponse<UserData> response = client.toBlocking().exchange(request, Argument.of(UserData.class));

        assertNotNull(response);
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals("John Doe", response.body().getName());
        assertEquals(101, response.body().getId());
        assertEquals("New York", response.body().getLocation());
    }

    @Test
    void testGetUserData2() {
        HttpRequest<String> request = HttpRequest.GET("/data/2");
        HttpResponse<UserData> response = client.toBlocking().exchange(request, Argument.of(UserData.class));

        assertNotNull(response);
        assertEquals(200, response.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals("James Right", response.body().getName()); // Ensure the expected value is correct
        assertEquals(102, response.body().getId());
        assertEquals("New Wales", response.body().getLocation());
    }
}
