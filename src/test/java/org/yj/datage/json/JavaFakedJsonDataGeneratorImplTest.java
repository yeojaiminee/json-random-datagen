package org.yj.datage.json;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

class JavaFakedJsonDataGeneratorImplTest {

    JavaFakedJsonDataGeneratorImpl javaFakedJsonDataGenerator;

    @BeforeEach
    void setup() {
        javaFakedJsonDataGenerator = new JavaFakedJsonDataGeneratorImpl();
    }

    @org.junit.jupiter.api.Test
    void generateJsonString() throws IOException {

        String inputString = "\n" +
                "{\n" +
                "  \"jobTitleName\":\"Job.title\",\n" +
                "  \"firstName\":\"Name.firstName\",\n" +
                "  \"lastName\":\"Name.lastName\",\n" +
                "  \"company\": \"Company.name\",\n" +
                "  \"dateOfBirth\": \"DateAndTime.birthday\",\n" +
                "  \"address\": {\n" +
                "    \"streetAddress\": \"Address.streetAddress\",\n" +
                "    \"city\": \"Address.city\",\n" +
                "    \"state\":\"Address.state\",\n" +
                "    \"country\": \"Address.country\",\n" +
                "    \"zipCode\": \"Address.zipCode\"\n" +
                "  },\n" +
                "  \"phoneNumber\":\"PhoneNumber.phoneNumber\"\n" +
                "}\n";
        System.out.println(javaFakedJsonDataGenerator.generateJsonString(inputString));
    }
}