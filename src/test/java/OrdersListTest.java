import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.BaseTest;
import ru.praktikum_services.qa_scooter.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest extends BaseTest {

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void shouldGetOrderList() {
        OrderSteps orderSteps = new OrderSteps();
        orderSteps.getOrderList()
                .assertThat()
                .body("orders", notNullValue());
    }
}
