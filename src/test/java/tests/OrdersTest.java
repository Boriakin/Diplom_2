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
import praktikum.pojo.response.OrdersResponse;

import static tests.base.ConstantsForTests.NO_INGREDIENTS;
import static tests.base.FakeData.*;

public class OrdersTest {

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
    @DisplayName("Создание заказа без авторизации.")
    @Description("Создание заказа без авторизации, проверка статус-кода и налличи номера заказа.")
    public void createOrderNonAuthTest() {
        TypedResponse<OrdersResponse> response =
                ordersApi.createOrderNonAuth(new OrdersRequest(ingredientApi.getIngredientsId()));
        Assert.assertEquals("Статус код не соответствует ожидаемому,",
                200,
                response.statusCode());
        Assert.assertTrue("Отсутствует номер заказа.", response.contains("number"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией.")
    @Description("Создание заказа с авторизацией, проверка статус-кода и наличия номера заказа.")
    public void createOrderWithAuthTest() {
        token = registerApi.registerUser(new RegisterAndEditRequest(getFakeEmail(),
                        getFakePassword(),
                        getFakeName()))
                .body()
                .getAccessToken();
        TypedResponse<OrdersResponse> response =
                ordersApi.createOrderWithAuth(new OrdersRequest(ingredientApi.getIngredientsId()),
                        token);
        Assert.assertEquals("Статус код не соответствует ожидаемому.",
                200,
                response.statusCode());
        Assert.assertTrue("Отсутствует номер заказа.", response.contains("number"));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов, без авторизации.")
    @Description("Создание заказа без ингредиентов, без авторизации, проверка статус кода и валидного сообщения об ошибке.")
    public void createOrderNonIngNonAuthTest() {
        TypedResponse<OrdersResponse> response = ordersApi.createOrderNonAuth(new OrdersRequest());
        Assert.assertEquals("Статус код не соответствует ожидаемому.",
                400,
                response.statusCode());
        Assert.assertEquals("Сообщение об ошибке не соответсвует ожидаемому.", NO_INGREDIENTS, response.message().getMessage());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов, с авторизацией.")
    @Description("Создание заказа без ингредиентов, с авторизацией, проверка статус кода и валидного сообщения об ошибке.")
    public void createOrderNonIngWithAuthTest() {
        token = registerApi.registerUser(new RegisterAndEditRequest(getFakeEmail(),
                        getFakePassword(),
                        getFakeName()))
                .body()
                .getAccessToken();
        TypedResponse<OrdersResponse> response = ordersApi.createOrderWithAuth(new OrdersRequest(), token);
        Assert.assertEquals("Статус код не соответствует ожидаемому.",
                400,
                response.statusCode());
        Assert.assertEquals("Сообщение об ошибке не соответсвует ожидаемому.", NO_INGREDIENTS, response.message().getMessage());
    }

    @Test
    @DisplayName("Создание заказа c неверным хешем ингредиентов, без авторизации.")
    @Description("Создание заказа c неверным хешем ингредиентов, без авторизации, проверка статус-кода ответа.")
    public void createOrderNonAuthIncorrectHashTest() {
        TypedResponse<OrdersResponse> response = ordersApi.createOrderNonAuth(new OrdersRequest(getFakeListIngredients()));
        Assert.assertEquals("Статус код не соответствует ожидаемому.",
                500,
                response.statusCode());
    }

    @Test
    @DisplayName("Создание заказа c неверным хешем ингредиентов, с авторизацией.")
    @Description("Создание заказа c неверным хешем ингредиентов, с авторизацией, проверка статус-кода ответа.")
    public void createOrderWithAuthIncorrectHashTest() {
        token = registerApi.registerUser(new RegisterAndEditRequest(getFakeEmail(),
                        getFakePassword(),
                        getFakeName()))
                .body()
                .getAccessToken();
        TypedResponse<OrdersResponse> response = ordersApi.createOrderWithAuth(new OrdersRequest(getFakeListIngredients()), token);
        Assert.assertEquals("Статус код не соответствует ожидаемому.",
                500,
                response.statusCode());
    }


    @After
    public void deleteUser() {
        if (token != null) {
            deleteApi.deleteUser(token);
        }
    }

}
