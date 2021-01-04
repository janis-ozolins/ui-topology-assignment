package network.api;

import org.jetbrains.annotations.NotNull;
import network.simple.SimpleNetwork;

import java.util.List;
import java.util.Optional;

public interface Network {

    static Network simple() {
        return new SimpleNetwork();
    }

    void add(@NotNull Device device);
    void add(@NotNull Mac mac, @NotNull Device device);

    Optional<? extends NetworkDevice> findByMac(@NotNull Mac mac);
    List<? extends NetworkDevice> getRoots();
    List<Device> getDevices();
}
