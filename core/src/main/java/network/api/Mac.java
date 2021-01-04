package network.api;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Bytes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mac {
    private static final String REGEX = "[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}";

    private final byte[] value;

    private Mac(byte[] value) {
        this.value = value;
    }

    public static Mac of(@NotNull byte[] value) {
        Preconditions.checkNotNull(value, "MAC empty");
        Preconditions.checkArgument(value.length == 6, "MAC length incorrect");

        return new Mac(value);
    }

    public static Mac of(@NotNull String value) {
        Preconditions.checkNotNull(value, "MAC empty");
        Preconditions.checkArgument(value.matches(REGEX), "MAC format incorrect, correct format " + REGEX);

        List<Byte> bytes = Arrays
                .stream(value.split("-"))
                .map(b -> (byte) Integer.parseInt(b, 16))
                .collect(Collectors.toList());

        return new Mac(Bytes.toArray(bytes));
    }

    @Override
    public String toString() {
        return Bytes
             .asList(value)
             .stream()
             .map(b -> String.format("%02x", b))
             .map(String::toUpperCase)
             .collect(Collectors.joining("-"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mac mac = (Mac) o;
        return Arrays.equals(value, mac.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
