package network.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import network.rest.api.ApiDevice;
import network.rest.api.ApiNetworkDevice;
import network.rest.api.ApiDeviceReq;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface Specification {

    @Operation(
            summary = "Retrieving all registered devices, sorted by device type"
    )
    List<ApiDevice> getDevices();

    @Operation(
            summary = "Retrieving all registered network device topology"
    )
    List<ApiNetworkDevice> get();

    @Operation(
            summary = "Registering \"root\" device to a network deployment"
    )
    ApiNetworkDevice post(@RequestBody ApiDeviceReq req);

    @Operation(
            summary = "Registering a device to a network deployment"
    )
    ApiNetworkDevice postDownLinkDevice(
            @Parameter(description = "Device MAC address", example = "AA-BB-CC-DD-EE-FF", required = true) String mac,
            @RequestBody ApiDeviceReq req
    );

    @Operation(
            summary = "Retrieving network device topology starting from a specific device"
    )
    ApiNetworkDevice getByMac(
            @Parameter(description = "Device MAC address", example = "AA-BB-CC-DD-EE-FF", required = true) String mac
    );

    @Operation(
            summary = "Delete network, expected reset state to empty"
    )
    void delete();
}
