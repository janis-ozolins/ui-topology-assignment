package network.rest;

import network.api.Device;
import network.api.Mac;
import network.api.Network;
import network.api.NetworkDevice;
import network.rest.api.ApiDevice;
import network.rest.api.ApiDeviceReq;
import network.rest.api.ApiNetworkDevice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(value = "/network")
public class NetworkController implements Specification {
    private Network network;

    public NetworkController() {
        this.network = Network.simple();
    }

    @GetMapping(value = "/devices")
    public List<ApiDevice> getDevices() {
        return network
                .getDevices()
                .stream()
                .sorted(Comparator.comparing(Device::getType))
                .map(this::toApi)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ApiNetworkDevice> get() {
        return network
                .getRoots()
                .stream()
                .map(this::toApi)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ApiNetworkDevice post(@RequestBody ApiDeviceReq req) {
        network.add(new Device(Mac.of(req.mac), req.type));

        return network
                .findByMac(Mac.of(req.mac))
                .map(this::toApi)
                .orElseThrow(() -> new IllegalStateException("Failed to retrieve just added device[" + req + "]"));
    }

    @PostMapping(value = "/{mac}/downLinks")
    public ApiNetworkDevice postDownLinkDevice(@PathVariable(value = "mac") String mac, @RequestBody ApiDeviceReq req) {
        network.add(Mac.of(mac), new Device(Mac.of(req.mac), req.type));

        return network
                .findByMac(Mac.of(req.mac))
                .map(this::toApi)
                .orElseThrow(() -> new IllegalStateException("Failed to retrieve just added device[" + req + "]"));
    }

    @GetMapping(value = "/{mac}")
    public ApiNetworkDevice getByMac(@PathVariable(value = "mac") String mac) {
        return network
                .findByMac(Mac.of(mac))
                .map(this::toApi)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find device with MAC[" + mac + "]"));
    }

    @DeleteMapping
    public void delete() {
        this.network = Network.simple();
    }

    private ApiDevice toApi(Device device) {
        return new ApiDevice(
                device.getMac().toString(),
                device.getType()
        );
    }

    private ApiNetworkDevice toApi(NetworkDevice device) {
        List<ApiNetworkDevice> downLink = device
                .getDownLink()
                .stream()
                .map(this::toApi)
                .collect(Collectors.toList());

        return new ApiNetworkDevice(
                device.getDevice().getMac().toString(),
                device.getDevice().getType(),
                downLink
        );
    }
}
