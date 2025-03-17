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
                .given("A hotel exists")
                .uponReceiving("A request for hotel details")
                .path("/data/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .like("name", "hello world")
                        .like("id", 123)
                        .like("location", "Delhi")
                )
                .toPact();
    }

    @Test
    void testHotelBookingConsumer(MockServer mockServer) throws IOException {
        URL url = new URL(mockServer.getUrl() + "/data/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertThat(connection.getResponseCode()).isEqualTo(200);
    }
}

