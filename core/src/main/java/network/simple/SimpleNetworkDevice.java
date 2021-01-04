package network.simple;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import network.api.Device;
import network.api.Mac;
import network.api.NetworkDevice;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class SimpleNetworkDevice implements NetworkDevice {
    private final Device device;
    private List<SimpleNetworkDevice> downLink;

    public SimpleNetworkDevice(Device device) {
        this.device = device;
        this.downLink = ImmutableList.of();
    }

    public void attach(SimpleNetworkDevice device) {
        this.downLink = ImmutableList.<SimpleNetworkDevice>builder()
                .addAll(downLink)
                .add(device)
                .build();
    }

    public Optional<SimpleNetworkDevice> findByMac(@NotNull Mac mac) {
        if(Objects.equals(device.getMac(), mac)) {
            return Optional.of(this);
        } else if(downLink.isEmpty()) {
            return Optional.empty();
        }

        return downLink.stream()
                .map(nd -> nd.findByMac(mac))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public List<NetworkDevice> getNetworkDevices() {
        if(downLink.isEmpty()) {
            return Collections.singletonList(this);
        }

        List<NetworkDevice> downLinkDevices = downLink.stream()
                .flatMap(nd -> nd.getNetworkDevices().stream())
                .collect(Collectors.toList());

        return ImmutableList.<NetworkDevice>builder()
                .add(this)
                .addAll(downLinkDevices)
                .build();
    }

    @Override
    public List<NetworkDevice> getDownLink() {
        return ImmutableList.copyOf(downLink);
    }

    public Device getDevice() {
        return device;
    }
}
