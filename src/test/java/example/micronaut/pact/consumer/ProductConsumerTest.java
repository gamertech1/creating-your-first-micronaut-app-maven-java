package example.micronaut.pact.consumer;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.*;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProductProvider", pactVersion = PactSpecVersion.V3)
public class ProductConsumerTest {

    @Pact(consumer = "ProductConsumer")
    public RequestResponsePact createProductSuccessPact(PactDslWithProvider builder) {
        return builder
                .given("Product application")
                .uponReceiving("A request to create a product")
                .path("/products")
                .method("POST")
                .headers("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .integerType("id", 1)
                        .stringType("name", "Mobile")
                        .decimalType("price", 800.99))
                .willRespondWith()
                .status(201)
                .body(new PactDslJsonBody()
                        .like("id", 2)
                        .like("name", "PC")
                        .like("price", 550.99))
                .toPact();
    }

    @Pact(consumer = "ProductConsumer")
    public RequestResponsePact createProductNotFoundPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("A request with an invalid product")
                .path("/products/999")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "ProductProvider",pactMethod = "createProductSuccessPact")
    public void testCreateProductSuccess(MockServer mockServer) throws Exception {
        String baseUrl = mockServer.getUrl();
        URL url = new URL(baseUrl + "/products");
        System.out.println("Sending request to: " + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInput = "{\"id\":1,\"name\":\"Mobile\",\"price\":800.99}";
        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonInput.getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode);
    }

    @Test
    @PactTestFor(pactMethod = "createProductNotFoundPact")
    public void testCreateProductNotFound(MockServer mockServer) throws Exception {
        URL url = new URL(mockServer.getUrl() + "/products/999");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(404, responseCode);
    }
}


