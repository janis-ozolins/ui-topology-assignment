package network.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {
    @JsonProperty(value = "msg", access = JsonProperty.Access.READ_ONLY)
    public final String msg;

    public ApiError(@JsonProperty(value = "msg", access = JsonProperty.Access.WRITE_ONLY) String msg) {
        this.msg = msg;
    }
}
