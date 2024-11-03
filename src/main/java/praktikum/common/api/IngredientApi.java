package praktikum.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.common.RequestSpec;
import praktikum.common.wrapper.TypedResponse;
import praktikum.pojo.addClass.Data;
import praktikum.pojo.response.IngredientResponse;

import java.util.ArrayList;
import java.util.List;

import static praktikum.constants.UrlsRequest.INGREDIENTS_URL;

public class IngredientApi {

    RequestSpec spec = new RequestSpec();

    @Step("Получение списка ингредиентов.")
    public TypedResponse<IngredientResponse> getIngredients() {
        Response response = spec.baseRequestSpec()
                .basePath(INGREDIENTS_URL)
                .get();
        return new TypedResponse<>(response, IngredientResponse.class);
    }

    @Step("Получение списка ID ингредиентов.")
    public List<String> getIngredientsId() {
        List<Data> data = getIngredients().body().getData();
        List<String> idList = new ArrayList<>();
        for (Data i : data) {
            idList.add(data.get(data.indexOf(i)).get_id());
        }
        return idList;
    }
}
