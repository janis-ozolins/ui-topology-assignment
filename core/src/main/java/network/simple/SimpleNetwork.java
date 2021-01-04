package network.simple;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import network.api.Device;
import network.api.Mac;
import network.api.Network;
import network.api.NetworkDevice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleNetwork implements Network {
    private List<SimpleNetworkDevice> roots;

    public SimpleNetwork() {
        this.roots = ImmutableList.of();
    }

    @Override
    public void add(@NotNull Device device) {
        roots = ImmutableList
                .<SimpleNetworkDevice>builder()
                .addAll(roots)
                .add(new SimpleNetworkDevice(device))
                .build();
    }

    @Override
    public void add(@NotNull Mac mac, @NotNull Device device) {
        Preconditions.checkNotNull(mac, "MAC empty");

        findByMac(mac).ifPresentOrElse(
                nd -> nd.attach(new SimpleNetworkDevice(device)),
                () -> { throw new IllegalStateException("Failed to find device[" + mac + "]"); });
    }

    @Override
    public Optional<SimpleNetworkDevice> findByMac(@NotNull Mac mac) {
        Preconditions.checkNotNull(mac, "MAC empty");

        return roots
                .stream()
                .map(nd -> nd.findByMac(mac))
                .flatMap(Optional::stream)
                .findFirst();
    }

    @Override
    public List<Device> getDevices() {
        return roots
                .stream()
                .flatMap(nd -> nd.getNetworkDevices().stream())
                .map(NetworkDevice::getDevice)
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends NetworkDevice> getRoots() {
        return roots;
    }
}
