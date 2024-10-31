package praktikum.common.api;

import io.qameta.allure.Step;
import praktikum.common.RequestSpec;

import static praktikum.constants.UrlsRequest.USER_URL;

public class DeleteApi {
    RequestSpec spec = new RequestSpec();

    @Step("Удаление пользователя.")
    public void deleteUser(String token) {
        spec.baseRequestSpec(token)
                .basePath(USER_URL)
                .delete();
    }
}
