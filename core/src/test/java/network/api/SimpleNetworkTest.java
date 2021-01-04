package network.api;

import com.google.common.truth.Truth;
import com.google.common.truth.Truth8;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static network.api.RandomDevice.MAC;
import static network.api.RandomDevice.MAC_OTHER;

public class SimpleNetworkTest {
    private final RandomDevice rDevice = new RandomDevice();

    @Test
    public void getRoots_emptyNetwork_emptyRoots() {
        Network net = Network.simple();

        Truth.assertThat(net.getRoots()).isEmpty();
    }

    @Test
    public void getDevices_emptyNetwork_emptyDevices() {
        Network net = Network.simple();

        Truth.assertThat(net.getDevices()).isEmpty();
    }

    @Test
    public void findByMac_emptyNetwork_emptyOptional() {
        Network net = Network.simple();

        Truth8.assertThat(net.findByMac(MAC)).isEmpty();
    }

    @Test
    public void getDevices_singleDeviceNetworkSearch_foundDevice() {
        Network net = Network.simple();
        Device device = new Device(MAC, DeviceType.GATEWAY);
        net.add(device);

        List<Device> devices = net.getDevices();

        Truth.assertThat(devices).containsExactly(device);
    }

    @Test
    public void findByMac_2ConnectedDeviceFindEndDevice_successfullyFound() {
        Network net = Network.simple();
        net.add(new Device(MAC, DeviceType.GATEWAY));
        net.add(MAC, new Device(MAC_OTHER, DeviceType.ACCESS_POINT));

        Optional<Mac> oMac = net
                .findByMac(MAC_OTHER)
                .map(NetworkDevice::getDevice)
                .map(Device::getMac);

        Truth8.assertThat(oMac).hasValue(MAC_OTHER);
    }

    @Test
    public void findByMac_singleDeviceNetworkSearch_successfullyFound() {
        Network net = Network.simple();
        Device device = new Device(MAC, DeviceType.GATEWAY);
        net.add(device);

        Optional<Device> oDevice = net
                .findByMac(MAC)
                .map(NetworkDevice::getDevice);

        Truth8.assertThat(oDevice).hasValue(device);
    }

    @Test
    public void getDevices_3DeviceConnectedNetwork_all3DevicesReturned() {
        Network net = Network.simple();

        Device gateway = new Device(MAC, DeviceType.GATEWAY);
        Device accessPoint = rDevice.device(DeviceType.ACCESS_POINT);
        Device switchz = rDevice.device(DeviceType.SWITCH);
        net.add(gateway);
        net.add(MAC, accessPoint);
        net.add(MAC, switchz);

        List<Device> devices = net.getDevices();

        Truth.assertThat(devices).containsExactly(accessPoint, switchz, gateway);
    }

    @Test
    public void getRoots_2Roots_all2RootsReturned() {
        Network net = Network.simple();
        Device d1 = rDevice.device();
        Device d2 = rDevice.device();
        net.add(d1);
        net.add(d2);

        List<Device> devices = net
                .getRoots()
                .stream()
                .map(NetworkDevice::getDevice)
                .collect(Collectors.toList());

        Truth.assertThat(devices).containsExactly(d1, d2);
    }
}
