package example.micronaut.pact.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "MyCustomProvider", pactVersion = PactSpecVersion.V3)
public class ConsumerContractTest {

    @Pact(consumer = "MyCustomConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Hello world application")
                .uponReceiving("Request to get data")
                .path("/data/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .stringType("name", "hello world")
                        .integerType("id", 123)
                        .stringType("location", "Delhi")
                )
                .toPact();
    }

    @Test
    void teststatuscodeforapplication(MockServer mockServer) throws IOException {
        URL url = new URL(mockServer.getUrl() + "/data/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertThat(connection.getResponseCode()).isEqualTo(200);
    }
}

