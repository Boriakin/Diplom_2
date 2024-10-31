package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.common.api.DeleteApi;
import praktikum.common.api.IngredientApi;
import praktikum.common.api.OrdersApi;
import praktikum.common.api.RegisterApi;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.OrdersRequest;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.UserOrdersResponse;

import static tests.base.ConstantsForTests.NON_AUTH_USER;
import static tests.base.FakeData.*;

public class UserOrdersTest {

    RegisterApi registerApi = new RegisterApi();
    OrdersApi ordersApi = new OrdersApi();
    IngredientApi ingredientApi = new IngredientApi();
    DeleteApi deleteApi = new DeleteApi();
    String token;

    @Before
    public void setUp() {
        token = null;
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя.")
    @Description("Получение заказов авторизованного пользователя, проверка статус кода на наличия заказов в ответе.")
    public void getUserOrdersWithAuthTest() {
        token = registerApi.registerUser(new RegisterAndEditRequest(getFakeEmail(),
                        getFakePassword(),
                        getFakeName()))
                .body()
                .getAccessToken();
        ordersApi.createOrderWithAuth(new OrdersRequest(ingredientApi.getIngredientsId()),
                token);
        TypedResponse<UserOrdersResponse> response = ordersApi.getUserOrdersWithAuth(token);
        Assert.assertEquals("Статус-код не соответствует ожидаемому.", 200, response.statusCode());
        Assert.assertTrue("Отсутствуют заказы в ответе.", response.contains("orders"));
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя.")
    @Description("Получение заказов неавторизованного пользователя, проверка статус кода и валидного сообщения об ошибке.")
    public void getUserOrdersNonAuthTest() {
        TypedResponse<UserOrdersResponse> response = ordersApi.getUserOrdersNonAuth();
        Assert.assertEquals("Статус-код не соответствует ожидаемому.",
                401,
                response.statusCode());
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому.",
                NON_AUTH_USER,
                response.message().getMessage());
    }

    @After
    public void deleteUser() {
        if (token != null) {
            deleteApi.deleteUser(token);
        }
    }
}
