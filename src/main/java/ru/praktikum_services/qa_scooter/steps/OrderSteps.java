package ru.praktikum_services.qa_scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.testData.CancelOrderData;
import ru.praktikum_services.qa_scooter.testData.OrderData;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Получить список заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders")
                .then();
    }

    @Step("Создание нового заказа")
    public ValidatableResponse createOrder(OrderData orderData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderData)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Отмена заказа")
    public Response cancelOrder(CancelOrderData cancelOrderData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(cancelOrderData)
                .when()
                .put("/api/v1/orders/cancel");

    }
}
