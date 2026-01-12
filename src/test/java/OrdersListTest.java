import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest {

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void shouldGetOrderList() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        OrderSteps orderSteps = new OrderSteps();
        orderSteps.getOrderList()
                .assertThat()
                .body("orders", notNullValue());
    }
}
