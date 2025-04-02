package example.micronaut.pact.provider;


import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@MicronautTest
@Provider("ProductProvider")
@PactBroker(url = "http://localhost:9292")
public class ProductProviderTest {

    @Inject
    EmbeddedApplication<?> application;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        if (application.isRunning()) {
            context.setTarget(new HttpTestTarget("localhost", 9090));
        }
    }
    @State("Product application")
    public void stateProductApplication() {
    }
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void validatePact(PactVerificationContext context) {
        context.verifyInteraction();
    }
}

