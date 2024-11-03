package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.common.api.DeleteApi;
import praktikum.common.api.RegisterApi;
import praktikum.common.api.UserApi;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.MessageResponse;
import praktikum.pojo.response.UserResponse;

import static tests.base.ConstantsForTests.NON_AUTH_USER;
import static tests.base.FakeData.*;

public class EditUserTest {

    private final String email = getFakeEmail();
    private final String password = getFakePassword();
    private final String name = getFakeName();
    RegisterApi registerApi = new RegisterApi();
    UserApi userApi = new UserApi();
    DeleteApi deleteApi = new DeleteApi();
    private String token;

    @Before
    public void setUp() {
        token = null;
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией.")
    @Description("Изменение данных пользователя с авторизацией, проверка статус-кода")
    public void editUserWithAuthTest() {
        token = registerApi.registerUser(new RegisterAndEditRequest(getFakeEmail(),
                        getFakePassword(),
                        getFakeName()))
                .body()
                .getAccessToken();
        TypedResponse<UserResponse> response =
                userApi.editUserWithAuth(new RegisterAndEditRequest(email, password, name), token);
        Assert.assertEquals("Статус-код не соответствует ожидаемому.",
                200,
                response.statusCode());
        Assert.assertTrue(response.body().isSuccess());
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации.")
    @Description("Изменение данных пользователя без авторизации, проверка стутус-кода и валидного сообщения об ошибке.")
    public void editUserNonAuthTest() {
        TypedResponse<MessageResponse> response =
                userApi.editUserNonAuth(new RegisterAndEditRequest(email, password, name));
        Assert.assertEquals("Статус-код не соответствует ожидаемому.", 401,
                response.statusCode());
        Assert.assertEquals("Текст сообщения не соответсвует ожидаемому.",
                NON_AUTH_USER,
                response.body().getMessage());
    }

    @After
    public void deleteUser() {
        if (token != null) {
            deleteApi.deleteUser(token);
        }
    }
}
