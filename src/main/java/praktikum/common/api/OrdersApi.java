package praktikum.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.common.RequestSpec;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.OrdersRequest;
import praktikum.pojo.response.OrdersResponse;
import praktikum.pojo.response.UserOrdersResponse;

import static praktikum.constants.UrlsRequest.ORDERS_URL;

public class OrdersApi {

    RequestSpec spec = new RequestSpec();

    @Step("Создание заказа без авторизации.")
    public TypedResponse<OrdersResponse> createOrderNonAuth(OrdersRequest ordersRequest) {
        Response response = spec.baseRequestSpec()
                .basePath(ORDERS_URL)
                .body(ordersRequest)
                .post();
        return new TypedResponse<>(response, OrdersResponse.class);
    }

    @Step("Создание заказа c авторизацией.")
    public TypedResponse<OrdersResponse> createOrderWithAuth(OrdersRequest ordersRequest, String token) {
        Response response = spec.baseRequestSpec(token)
                .basePath(ORDERS_URL)
                .body(ordersRequest)
                .post();
        return new TypedResponse<>(response, OrdersResponse.class);
    }

    @Step("Пролучение списка заказов пользователя с авторизацией.")
    public TypedResponse<UserOrdersResponse> getUserOrdersWithAuth(String token) {
        Response response = spec.baseRequestSpec(token)
                .basePath(ORDERS_URL)
                .get();
        return new TypedResponse<>(response, UserOrdersResponse.class);
    }

    @Step("Пролучение списка заказов пользователя без авторизации.")
    public TypedResponse<UserOrdersResponse> getUserOrdersNonAuth() {
        Response response = spec.baseRequestSpec()
                .basePath(ORDERS_URL)
                .get();
        return new TypedResponse<>(response, UserOrdersResponse.class);
    }
}
