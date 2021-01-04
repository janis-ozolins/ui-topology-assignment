package network.rest;

import com.google.common.truth.Truth;
import com.google.common.truth.Truth8;
import io.restassured.RestAssured;
import network.api.DeviceType;
import network.rest.api.ApiDevice;
import network.rest.api.ApiError;
import network.rest.api.ApiNetworkDevice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class NetworkControllerTest {
    private static final String MAC = "11-22-33-44-55-66";
    private static final String MAC_OTHER = "AA-BB-CC-DD-EE-FF";

    private final NetworkTestClient client;

    public NetworkControllerTest(@LocalServerPort int port) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        this.client = new NetworkTestClient();
    }

    @Test
    public void emptyNetworkReturnsEmptyDeviceList() {
        ApiNetworkDevice[] devices = client.getDevices()
                .statusCode(200)
                .extract()
                .as(ApiNetworkDevice[].class);

        Truth.assertThat(devices).hasLength(0);
    }

    @Test
    public void emptyNetworkReturn404SearchingForDevice() {
        client.findDeviceByMac(MAC)
              .statusCode(404);
    }

    @Test
    public void invalidMacAddressFindDevice() {
        String error = client.findDeviceByMac("11-22-33-44-55")
                .statusCode(400)
                .extract()
                .as(ApiError.class)
                .msg;

        Truth.assertThat(error).contains("MAC format incorrect," +
                " correct format [0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}");
    }

    @Test
    public void checkOrderOfDevices() {
        client.addDevice(MAC, DeviceType.SWITCH);
        client.addDevice(MAC_OTHER, DeviceType.GATEWAY);
        client.addDevice("FF-22-33-44-55-66", DeviceType.ACCESS_POINT);

        ApiDevice[] devices = client.getFlatView()
                .statusCode(200)
                .extract()
                .as(ApiDevice[].class);

        Truth8.assertThat(Arrays.stream(devices).map(d -> d.type))
                .containsExactly(DeviceType.ACCESS_POINT, DeviceType.SWITCH, DeviceType.GATEWAY)
                .inOrder();
    }

    @Test
    public void checkNestedDevices() {
        client.addDevice(MAC, DeviceType.GATEWAY);
        client.addDownLinkDevice(MAC, MAC_OTHER, DeviceType.ACCESS_POINT);

        ApiNetworkDevice device = client.findDeviceByMac(MAC)
                .statusCode(200)
                .extract()
                .as(ApiNetworkDevice.class);

        Truth.assertThat(device.downLink)
                .containsExactly(new ApiNetworkDevice(MAC_OTHER, DeviceType.ACCESS_POINT, Collections.emptyList()));
    }

    @Test
    public void postDeviceGetSame() {
        client.addDevice(MAC, DeviceType.GATEWAY);

        ApiNetworkDevice device = client.findDeviceByMac(MAC)
                .statusCode(200)
                .extract()
                .as(ApiNetworkDevice.class);

        Truth.assertThat(device).isEqualTo(new ApiNetworkDevice(MAC, DeviceType.GATEWAY, Collections.emptyList()));
    }

    @Test
    public void emptyTypeThrowError() {
        String error = client.addDevice(MAC, null)
                .statusCode(400)
                .extract()
                .as(ApiError.class)
                .msg;

        Truth.assertThat(error).isEqualTo("Type empty");
    }

    @AfterEach
    public void clean() {
        client.deleteDevices();
    }
}
