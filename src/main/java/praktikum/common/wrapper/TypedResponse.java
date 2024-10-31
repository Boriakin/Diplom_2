package praktikum.common.wrapper;

import io.restassured.response.Response;
import praktikum.pojo.response.MessageResponse;

public class TypedResponse<T> {

    private final Response response;
    private final Class<T> cls;

    public TypedResponse(final Response response, final Class<T> cls) {
        this.response = response;
        this.cls = cls;
    }

    public T body() {
        return response.as(cls);
    }

    public MessageResponse message() {
        return response.as(MessageResponse.class);
    }

    public int statusCode() {
        return response.statusCode();
    }

    public boolean contains(String text) {
        return response.asString().contains(text);
    }
}
