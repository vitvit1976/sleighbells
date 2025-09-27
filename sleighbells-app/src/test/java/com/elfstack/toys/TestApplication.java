package com.elfstack.toys;

import org.junit.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Run this application class to start your application locally, using Testcontainers for all external services. You
 * have to configure the containers in {@link TestcontainersConfiguration}.
 */
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
    }

    @Test
    public void testEquality() {
        int expected = 2;
        int actual = 3;

        assertEquals(2, 3);
    }


    String s = "The secret code is prepared: ****.\n" +
            "\n" +
            "Turn 1. Answer:\n" +
            "1234\n" +
            "Grade: 1 cow.\n" +
            "\n" +
            "Turn 2. Answer:\n" +
            "5678\n" +
            "Grade: 1 cow.\n" +
            "\n" +
            "Turn 3. Answer:\n" +
            "9012\n" +
            "Grade: 1 bull and 1 cow.\n" +
            "\n" +
            "Turn 4. Answer:\n" +
            "9087\n" +
            "Grade: 1 bull and 1 cow.\n" +
            "\n" +
            "Turn 5. Answer:\n" +
            "1087\n" +
            "Grade: 1 cow.\n" +
            "\n" +
            "Turn 6. Answer:\n" +
            "9205\n" +
            "Grade: 3 bulls.\n" +
            "\n" +
            "Turn 7. Answer:\n" +
            "9305\n" +
            "Grade: 4 bulls.\n" +
            "Congrats! The secret code is 9305.";
}
