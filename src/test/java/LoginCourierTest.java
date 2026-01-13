import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.steps.CourierSteps;
import ru.praktikum_services.qa_scooter.testdata.CourierData;
import ru.praktikum_services.qa_scooter.testdata.CourierLoginData;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends BaseTest {

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
        courierData = new CourierData(login, password, firstName);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.createCourier(courierData);
    }

    @Test
    @DisplayName("Проверка успешного логина курьера в систему")
    public void shouldLoginCourier() {
        CourierSteps courierSteps = new CourierSteps();
        courierLogin = new CourierLoginData(login, password);
        courierSteps.loginCourier(courierLogin)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Невозможно залогиниться без логина")
    public void cantLoginWithoutLogin() {
        CourierSteps courierSteps = new CourierSteps();
        courierLogin = new CourierLoginData(login, password);
        String savedLogin = login;

        courierLogin.setLogin(null);
        courierSteps.loginCourier(courierLogin)
                .then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

        courierLogin.setLogin(savedLogin);// Возвращаем первоначальный логин для удаления курьера
    }

    @Test
    @DisplayName("Невозможно залогиниться без пароля")
    public void cantLoginWithoutPassword() {
        CourierSteps courierSteps = new CourierSteps();
        courierLogin = new CourierLoginData(login, password);
        String savedPassword = password;
        courierLogin.setPassword(null);

        courierSteps.loginCourier(courierLogin)
                .then().log().all()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
        courierLogin.setPassword(savedPassword); // Возвращаем первоначальный пароль дял удаления курьера
    }

    @Test
    @DisplayName("Невозможно залогиниться с неверным логином")
    public void cantLoginWithWrongLogin() {
        CourierSteps courierSteps = new CourierSteps();
        courierLogin = new CourierLoginData(login, password);
        String savedLogin = login;

        courierLogin.setLogin(randomAlphabetic(13));
        courierSteps.loginCourier(courierLogin)
                .then()
                .statusCode(404)
                .and()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

        courierLogin.setLogin(savedLogin);
    }

    @Test
    @DisplayName("Невозможно залогиниться с неверным паролем")
    public void cantLoginWithWrongPassword() {
        CourierSteps courierSteps = new CourierSteps();
        courierLogin = new CourierLoginData(login, password);
        String savedPassword = password;

        courierLogin.setPassword(randomAlphabetic(13));
        courierSteps.loginCourier(courierLogin)
                .then()
                .statusCode(404)
                .and()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

        courierLogin.setPassword(savedPassword);
    }

    @After
    public void tearDown() {
        courierLogin = new CourierLoginData(login, password);
        CourierSteps courierSteps = new CourierSteps();

        id = courierSteps.loginCourier(courierLogin)
                .then()
                .extract()
                .path("id");
        courierSteps.deleteCourier(id);
    }
}
