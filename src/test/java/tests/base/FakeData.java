package tests.base;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class FakeData {
    public static String getFakeName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public static String getFakePassword() {
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public static String getFakeEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static List<String> getFakeListIngredients() {
        Faker faker = new Faker();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(faker.food().ingredient().hashCode() + "");
        }
        return list;
    }
}
