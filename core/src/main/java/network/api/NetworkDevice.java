package network.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface NetworkDevice {
    Device getDevice();
    Optional<? extends NetworkDevice> findByMac(@NotNull Mac mac);
    List<NetworkDevice> getNetworkDevices();
    List<NetworkDevice> getDownLink();
}
