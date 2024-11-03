package praktikum.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.common.RequestSpec;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.request.RegisterAndEditRequest;
import praktikum.pojo.response.RegisterAndLoginResponse;

import static praktikum.constants.UrlsRequest.REGISTER_URL;

public class RegisterApi {

    RequestSpec spec = new RequestSpec();

    @Step("Регистрация пользователя.")
    public TypedResponse<RegisterAndLoginResponse> registerUser(RegisterAndEditRequest registerAndEditRequest) {
        Response response =
                spec.baseRequestSpec()
                        .basePath(REGISTER_URL)
                        .body(registerAndEditRequest)
                        .post();
        return new TypedResponse<>(response, RegisterAndLoginResponse.class);
    }

    @Step("Регистрация пользователя с данными, которые уже использовались при регистрации.")
    public TypedResponse<RegisterAndLoginResponse> registerTwoUser(RegisterAndEditRequest registerAndEditRequest) {
        registerUser(registerAndEditRequest);
        return registerUser(registerAndEditRequest);
    }
}
