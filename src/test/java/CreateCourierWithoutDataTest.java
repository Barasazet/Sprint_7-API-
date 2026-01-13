import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.steps.CourierSteps;
import ru.praktikum_services.qa_scooter.testdata.CourierData;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierWithoutDataTest extends BaseTest {
    CourierData courierData;
    String login;
    String password;
    String firstName;

    @Before
    public void setUp() {
        login = randomAlphabetic(13);
        password = randomAlphabetic(13);
        firstName = randomAlphabetic(13);
    }

    @Test
    @DisplayName("Невозможно создать курьера без ввода логина")
    public void cantCreateCourierWithoutLogin() {

        courierData = new CourierData(login, password, firstName);
        CourierSteps courierSteps = new CourierSteps();
        courierData.setLogin(null);
        courierSteps.createCourier(courierData)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Невозможно создать курьера без ввода пароля")
    public void cantCreateCourierWithoutPassword() {

        courierData = new CourierData(login, password, firstName);
        CourierSteps courierSteps = new CourierSteps();
        courierData.setPassword(null);
        courierSteps.createCourier(courierData)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

}
