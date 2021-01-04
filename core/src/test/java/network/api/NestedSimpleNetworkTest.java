package network.api;

import com.google.common.truth.Truth8;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static network.api.RandomDevice.MAC;
import static network.api.RandomDevice.MAC_OTHER;

public class NestedSimpleNetworkTest {
    private final RandomDevice rDevice = new RandomDevice();

    private Network net;
    private Device d1;
    private Device d2;

    @BeforeEach
    public void createNetwork() {
        this.net = Network.simple();
        this.d1 = rDevice.device();
        this.d2 = rDevice.device();
        net.add(d1);
        net.add(d2);
        net.add(d1.getMac(), new Device(MAC, DeviceType.GATEWAY));
        net.add(MAC, new Device(MAC_OTHER, DeviceType.ACCESS_POINT));
    }

    @Test
    public void fromSubTreeDeviceSearchForRoot_notRootFound() {
        Optional<? extends NetworkDevice> oRoot = net
                .findByMac(MAC)
                .flatMap(d -> d.findByMac(d1.getMac()));

        Truth8.assertThat(oRoot).isEmpty();
    }

    @Test
    public void subTreeOf2Devices_found2Devices() {
        Stream<Mac> deviceMacs = net
                .findByMac(MAC)
                .stream()
                .flatMap(d -> d.getNetworkDevices().stream())
                .map(d -> d.getDevice().getMac());

        Truth8.assertThat(deviceMacs).containsExactly(MAC, MAC_OTHER);
    }

    @Test
    public void leafDevice_noDownlinkDevices() {
        Stream<NetworkDevice> leafDownlinks = net
                .findByMac(MAC_OTHER)
                .stream()
                .flatMap(d -> d.getDownLink().stream());

        Truth8.assertThat(leafDownlinks).isEmpty();
    }
}
