package ru.praktikum_services.qa_scooter;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseTest {
    final static String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @BeforeClass
    public static void UrlSetUp() {
        RestAssured.baseURI = BASE_URL;
    }

}
