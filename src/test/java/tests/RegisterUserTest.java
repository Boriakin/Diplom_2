package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.common.api.DeleteApi;
import praktikum.common.api.RegisterApi;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.RegisterAndLoginResponse;

import static tests.base.ConstantsForTests.USER_ALREADY_EXISTS;
import static tests.base.FakeData.*;

public class RegisterUserTest {

    private final String email = getFakeEmail();
    private final String password = getFakePassword();
    private final String name = getFakeName();
    RegisterApi registerApi = new RegisterApi();
    DeleteApi deleteApi = new DeleteApi();
    private String token;

    @Before
    public void setUp() {
        token = null;
    }

    @Test
    @DisplayName("Создание уникального пользователя.")
    @Description("Регистрация нового пользователя, проверка статус-кода ответа и наличия токена.")
    public void registerUserTest() {
        TypedResponse<RegisterAndLoginResponse> response =
                registerApi.registerUser(new RegisterAndEditRequest(email, password, name));
        Assert.assertEquals("Статус-код не соответсвует ожидаемому.",
                200,
                response.statusCode());
        Assert.assertNotNull("Отсуствует токен в ответе.",
                response.body().getAccessToken());
        token = response.body().getAccessToken();
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован.")
    @Description("Попытка создать дубль юзера, проверка статус-кода и валидного сообщения об ошибке.")
    public void registerTwoUsersTest() {
        TypedResponse<RegisterAndLoginResponse> response =
                registerApi.registerTwoUser(new RegisterAndEditRequest(email, password, name));
        Assert.assertEquals("Статус-код не соответсвует ожидаемому.",
                403,
                response.statusCode());
        Assert.assertEquals("Сообщение об ошибке не соответсвует ожидаемому.",
                USER_ALREADY_EXISTS,
                response.message().getMessage());
        token = response.body().getAccessToken();
    }


    @After
    public void deleteUser() {
        if (token != null) {
            deleteApi.deleteUser(token);
        }
    }
}

