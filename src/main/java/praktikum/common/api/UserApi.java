package praktikum.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.common.RequestSpec;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.MessageResponse;
import praktikum.pojo.response.UserResponse;

import static praktikum.constants.UrlsRequest.USER_URL;

public class UserApi {
    RequestSpec spec = new RequestSpec();

    @Step("Изменение данных пользователя с авторизацией.")
    public TypedResponse<UserResponse> editUserWithAuth(RegisterAndEditRequest editRequest, String token) {
        Response response =
                spec.baseRequestSpec(token)
                        .basePath(USER_URL)
                        .body(editRequest)
                        .patch();
        return new TypedResponse<>(response, UserResponse.class);
    }

    @Step("Изменение данных пользователя без авторизации.")
    public TypedResponse<MessageResponse> editUserNonAuth(RegisterAndEditRequest editRequest) {
        Response response =
                spec.baseRequestSpec()
                        .basePath(USER_URL)
                        .body(editRequest)
                        .patch();
        return new TypedResponse<>(response, MessageResponse.class);
    }
}
