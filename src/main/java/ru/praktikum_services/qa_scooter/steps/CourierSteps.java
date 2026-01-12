package ru.praktikum_services.qa_scooter.steps;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.testdata.CourierData;
import ru.praktikum_services.qa_scooter.testdata.CourierLoginData;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierData courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();

    }

    ;

    @Step("Логин курьера")
    public Response loginCourier(CourierLoginData courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

    }

    @Step("Удаление курьера")
    public Response deleteCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .pathParams("id", id)
                .when()
                .delete("/api/v1/courier/{id}");

    }


}

