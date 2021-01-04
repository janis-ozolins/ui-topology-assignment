package network.api;

import java.util.Random;

public class RandomDevice {
    public static final Mac MAC = Mac.of("AA-BB-CC-DD-EE-FF");
    public static final Mac MAC_OTHER = Mac.of("11-22-33-44-55-66");

    private final Random rand = new Random();

    public Device device() {
        return device(DeviceType.GATEWAY);
    }

    public Device device(DeviceType type) {
        byte[] mac = new byte[6];
        rand.nextBytes(mac);
        return new Device(Mac.of(mac), type);
    }
}
