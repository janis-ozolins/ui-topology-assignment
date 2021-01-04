package network.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import network.api.DeviceType;

import java.util.List;
import java.util.Objects;

public class ApiNetworkDevice {
    public final ApiDevice device;
    public final List<ApiNetworkDevice> downLink;

    public ApiNetworkDevice(
            @JsonProperty("device") ApiDevice device,
            @JsonProperty("downLink") List<ApiNetworkDevice> downLink
    ) {
        this.device = device;
        this.downLink = downLink;
    }

    public ApiNetworkDevice(
            String mac,
            DeviceType type,
            List<ApiNetworkDevice> downLink
    ) {
        this.device = new ApiDevice(mac, type);
        this.downLink = downLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiNetworkDevice that = (ApiNetworkDevice) o;
        return Objects.equals(device, that.device) &&
                Objects.equals(downLink, that.downLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(device, downLink);
    }
}
