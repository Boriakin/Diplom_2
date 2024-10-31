package praktikum.pojo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserOrdersResponse {

    boolean success;
    private List<String> orders;
    private int total;
    private int totalToday;

}
