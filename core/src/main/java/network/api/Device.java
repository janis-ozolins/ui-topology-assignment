package network.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public class Device {
    private final Mac mac;
    private final DeviceType type;

    public Device(@NotNull Mac mac, @NotNull DeviceType type) {
        Preconditions.checkNotNull(mac, "MAC empty");
        Preconditions.checkNotNull(type, "Type empty");

        this.type = type;
        this.mac = mac;
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("mac", mac)
                .add("type", type)
                .toString();
    }

    public DeviceType getType() {
        return type;
    }

    public Mac getMac() {
        return mac;
    }
}
