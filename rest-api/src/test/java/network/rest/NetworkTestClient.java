package network.rest;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import network.api.DeviceType;
import network.rest.api.ApiDeviceReq;

import static io.restassured.RestAssured.*;

public class NetworkTestClient {

    public ValidatableResponse getFlatView() {
        return get("/network/devices")
                .then();
    }

    public ValidatableResponse addDevice(String mac, DeviceType accessPoint) {
         return given()
                .body(new ApiDeviceReq(mac, accessPoint))
                .contentType(ContentType.JSON)
                .post("/network")
                .then();
    }

    public ValidatableResponse addDownLinkDevice(String uplinkMac, String mac, DeviceType accessPoint) {
        return given()
                .pathParam("mac", uplinkMac)
                .body(new ApiDeviceReq(mac, accessPoint))
                .contentType(ContentType.JSON)
                .post("/network/{mac}/downLinks")
                .then();
    }

    public ValidatableResponse findDeviceByMac(String mac) {
        return given()
                .pathParam("mac", mac)
                .get("/network/{mac}")
                .then();
    }

    public ValidatableResponse getDevices() {
        return get("/network")
                .then();
    }

    public ValidatableResponse deleteDevices() {
        return delete("/network")
                .then();
    }
}
