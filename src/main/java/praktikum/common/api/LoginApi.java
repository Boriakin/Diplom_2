package praktikum.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.common.RequestSpec;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.LoginRequest;
import praktikum.pojo.response.RegisterAndLoginResponse;

import static praktikum.constants.UrlsRequest.LOGIN_URL;

public class LoginApi {

    RequestSpec spec = new RequestSpec();

    @Step("Логин пользователя.")
    public TypedResponse<RegisterAndLoginResponse> loginUser(LoginRequest loginRequest) {
        Response response = spec.baseRequestSpec()
                .basePath(LOGIN_URL)
                .body(loginRequest)
                .post();
        return new TypedResponse<>(response, RegisterAndLoginResponse.class);
    }
}
