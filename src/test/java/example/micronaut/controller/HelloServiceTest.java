package example.micronaut.controller;

import example.micronaut.service.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HelloServiceTest {

    @InjectMocks
    private HelloService helloService;

    @BeforeEach
    void setUp() {
        helloService = new HelloService();
    }

    @Test
    void testGetDefaultMessage() {
        String result = helloService.getMessage();
        assertNotNull(result);
        assertEquals("Hello World", result);
    }

    @Test
    void testGetMessageWithName() {
        String name = "John";
        String result = helloService.getMessage(name);
        assertNotNull(result);
        assertEquals("Hello John", result);
    }

    @Test
    void testGetMessageWithNullName() {
        String result = helloService.getMessage(null);
        assertNotNull(result);
        assertEquals("Hello World", result);
    }
}
