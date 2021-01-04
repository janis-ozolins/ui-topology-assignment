package network.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import network.api.DeviceType;

import java.util.StringJoiner;

public class ApiDeviceReq {
    public final String mac;
    public final DeviceType type;

    public ApiDeviceReq(
            @JsonProperty("mac") String mac,
            @JsonProperty("type") DeviceType type
    ) {
        this.mac = mac;
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiDeviceReq.class.getSimpleName() + "[", "]")
                .add("mac='" + mac + "'")
                .add("type=" + type)
                .toString();
    }
}
