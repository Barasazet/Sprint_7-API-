import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.steps.OrderSteps;
import ru.praktikum_services.qa_scooter.testData.CancelOrderData;
import ru.praktikum_services.qa_scooter.testData.OrderData;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    String firstName;
    String lastName;
    String address;
    String metroStation;
    String phone;
    String rentTime;
    String deliveryDate;
    String comment;
    String[] color;
    OrderData orderData;
    CancelOrderData cancelOrderData;
    Integer track;

    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"Mihail", "Circle", "Vladimir railStation", "6", "8 800 555 35 35", "5", "2025-09-09", "Wind from the North", new String[]{"BLACK"}},
                {"Mihail", "Circle", "Vladimir railStation", "6", "8 800 555 35 35", "5", "2025-09-09", "Wind from the North", new String[]{"GREY"}},
                {"Mihail", "Circle", "Vladimir railStation", "6", "8 800 555 35 35", "5", "2025-09-09", "Wind from the North", new String[]{"BLACK", "GREY"}},
                {"Mihail", "Circle", "Vladimir railStation", "6", "8 800 555 35 35", "5", "2025-09-09", "Wind from the North", new String[]{null}},
        });
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Проверка выбора цвета самоката при оформлении заказа")
    public void chooseScooterColourInNewOrder() {
        orderData = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        OrderSteps orderSteps = new OrderSteps();

        ValidatableResponse response = orderSteps.createOrder(orderData);
        track = response
                .extract()
                .path("track");

        response
                .statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

    @After
    public void tearDown() {
        OrderSteps orderSteps = new OrderSteps();
        cancelOrderData = new CancelOrderData(track);

        orderSteps.cancelOrder(cancelOrderData);
    }
}
