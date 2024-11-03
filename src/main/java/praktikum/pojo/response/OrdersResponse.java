package praktikum.pojo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersResponse {

    private String name;
    private List<Object> order;
    private boolean success;
}
