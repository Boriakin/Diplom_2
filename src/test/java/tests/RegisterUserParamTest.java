package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.common.api.DeleteApi;
import praktikum.common.api.RegisterApi;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.RegisterAndLoginResponse;

import static tests.base.ConstantsForTests.NONE_REQUIRED_FIELDS;
import static tests.base.FakeData.*;

@RunWith(Parameterized.class)
public class RegisterUserParamTest {

    private final String email;
    private final String password;
    private final String name;
    RegisterApi registerApi = new RegisterApi();
    DeleteApi deleteApi = new DeleteApi();
    private String token;

    public RegisterUserParamTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {getFakeEmail(), getFakePassword(), ""},
                {"", getFakePassword(), getFakeName()},
                {getFakeEmail(), "", getFakeName()}
        };
    }

    @Before
    public void setUp() {
        token = null;
    }

    @Test
    @DisplayName("Регистрация пользователя с одним пустым полем.")
    @Description("Регистрация пользователя с пустым полем, проверка статус-кода и сообщения об ошибке.")
    public void registerUserWithEmptyFieldTest() {
        TypedResponse<RegisterAndLoginResponse> response =
                registerApi.registerUser(new RegisterAndEditRequest(email, password, name));
        Assert.assertEquals("Статус-код не соответсвует ожидаемому.",
                403,
                response.statusCode());
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому.",
                NONE_REQUIRED_FIELDS,
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
