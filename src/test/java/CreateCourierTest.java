import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.steps.CourierSteps;
import ru.praktikum_services.qa_scooter.testdata.CourierData;
import ru.praktikum_services.qa_scooter.testdata.CourierLoginData;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreateCourierTest extends BaseTest {

    CourierData courierData;
    CourierLoginData courierLogin;
    String login;
    String password;
    String firstName;
    int id;


    @Before
    public void setUp() {
        login = RandomStringUtils.randomAlphabetic(13);
        password = RandomStringUtils.randomAlphabetic(13);
        firstName = RandomStringUtils.randomAlphabetic(13);
    }

    @Test
    @DisplayName("Проверка создания курьера")
    public void shouldCreateCourier() {
        courierData = new CourierData(login, password, firstName);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.createCourier(courierData)
                .statusCode(201)
                .and()
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Невозможно создать курьера с занятым логином")
    public void cantCreateCourierWithTakenLogin() {
        courierData = new CourierData(login, password, firstName);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.createCourier(courierData);
        courierSteps.createCourier(courierData)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

    }

    @After
    public void tearDown() {
        courierLogin = new CourierLoginData(login, password);
        CourierSteps courierSteps = new CourierSteps();
        id = courierSteps.loginCourier(courierLogin).then().extract().path("id");
        courierSteps.deleteCourier(id);
    }
}
