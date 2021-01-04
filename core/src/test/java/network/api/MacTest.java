package network.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MacTest {

    @Test
    public void checkStringFactory_noException() {
        Mac.of("11-22-33-44-55-66");
    }

    @Test
    public void checkByteFactory_noException() {
        Mac.of(new byte[6]);
    }

    @Test
    public void _5ByteAddressStringToMac_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Mac.of("11-22-33-44-55"));
    }

    @Test
    public void invalidHexSymbol_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Mac.of("11-22-33-44-EE"));
    }

    @Test
    public void _5ByteAddressToMac_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Mac.of(new byte[5]));
    }
}
