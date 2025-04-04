package example.micronaut.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import io.micronaut.http.server.netty.NettyHttpServer;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@MicronautTest
@Provider("MyCustomProvider")
@PactBroker(
        url = "http://localhost:9292"
)
public class ProviderContractTest {

    @Inject
    EmbeddedApplication<?> application;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        if (application.isRunning()) {
            context.setTarget(new HttpTestTarget("localhost", 9090));
        }
    }

    @State("Hello world application")
    public void stateHelloWorldApplication() {
    }
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void validatePactContract(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
