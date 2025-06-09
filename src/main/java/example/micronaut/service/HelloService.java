package example.micronaut.service;
import jakarta.inject.Singleton;

@Singleton  // Marks this class as a Micronaut-managed singleton service
public class HelloService {

    public String getMessage() {
        return "Hello World";
    }
    public String getMessage(String name) {
        return name;
    }

}
