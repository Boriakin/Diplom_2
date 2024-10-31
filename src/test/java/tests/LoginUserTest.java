package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.common.api.DeleteApi;
import praktikum.common.api.LoginApi;
import praktikum.common.api.RegisterApi;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.LoginRequest;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.RegisterAndLoginResponse;

import static tests.base.ConstantsForTests.INVALID_USER_DATA;
import static tests.base.FakeData.*;

public class LoginUserTest {

    private final String email = getFakeEmail();
    private final String password = getFakePassword();
    private final String name = getFakeName();
    RegisterApi registerApi = new RegisterApi();
    LoginApi loginApi = new LoginApi();
    DeleteApi deleteApi = new DeleteApi();
    private String token;

    @Before
    public void setUp() {
        token = null;
    }

    @Test
    @DisplayName("Логин пользователя с валидными данными")
    @Description("Логин пользователя с валидными данными, проверка статус-кода и наличия токена в ответе.")
    public void loginUserTest() {
        registerApi.registerUser(new RegisterAndEditRequest(email, password, name));
        TypedResponse<RegisterAndLoginResponse> response =
                loginApi.loginUser(new LoginRequest(email, password));
        Assert.assertEquals("Статус-код не соответсвует ожидаемому.",
                200,
                response.statusCode());
        Assert.assertNotNull("Отсуствует токен в ответе.",
                response.body().getAccessToken());
        token = response.body().getAccessToken();
    }

    @Test
    @DisplayName("Логин пользователя с невалидными данными.")
    @Description("Логин пользователя с невалидными данными, проверка статус-кода и валидного сообщения об ошибке.")
    public void loginUserInvalidDataTest() {
        TypedResponse<RegisterAndLoginResponse> response =
                loginApi.loginUser(new LoginRequest(email, password));
        Assert.assertEquals("Статус-код не соответсвует ожидаемому.",
                401,
                response.statusCode());
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому.",
                INVALID_USER_DATA,
                response.message().getMessage());
    }

    @After
    public void deleteUser() {
        if (token != null) {
            deleteApi.deleteUser(token);
        }
    }
}
