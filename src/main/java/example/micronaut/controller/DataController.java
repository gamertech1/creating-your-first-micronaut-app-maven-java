package example.micronaut.controller;

import example.micronaut.model.UserData;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/data")
public class DataController {

    @Get("/1")
    public UserData getUserData() {
        return new UserData("John Doe", 101, "New York");
    }
    @Get("/2")
    public UserData getUserData2() {
        return new UserData("James Right", 102, "New Wales");
    }
}
