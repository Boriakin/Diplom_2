package praktikum.pojo.response;

import lombok.Getter;
import lombok.Setter;
import praktikum.pojo.addClass.Data;

import java.util.List;

@Getter
@Setter
public class IngredientResponse {

    private boolean success;
    private List<Data> data;
}
