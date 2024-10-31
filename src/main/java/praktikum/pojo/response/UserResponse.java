package praktikum.pojo.response;

import lombok.Getter;
import lombok.Setter;
import praktikum.pojo.addClass.User;

@Getter
@Setter
public class UserResponse {

    private boolean success;
    private User user;
}
