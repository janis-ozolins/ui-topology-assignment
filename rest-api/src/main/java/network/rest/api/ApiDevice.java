package network.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import network.api.DeviceType;

import java.util.Objects;

public class ApiDevice {
    public final String mac;
    public final DeviceType type;

    public ApiDevice(
            @JsonProperty("mac") String mac,
            @JsonProperty("type") DeviceType type
    ) {
        this.mac = mac;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiDevice apiDevice = (ApiDevice) o;
        return Objects.equals(mac, apiDevice.mac) &&
                type == apiDevice.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mac, type);
    }
}
