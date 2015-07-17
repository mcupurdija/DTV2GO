package rs.multitelekom.dtv2go.util;

import java.util.Random;

public class DevIdGenerator {

    private static final String AB = "123456789abcdef";

    public static String getCode() {
        StringBuilder sb = new StringBuilder(17);
        for (int i = 0; i < 16; i++) {
            sb.append(AB.charAt(new Random().nextInt(AB.length())));
        }
        return sb.toString();
    }

}
