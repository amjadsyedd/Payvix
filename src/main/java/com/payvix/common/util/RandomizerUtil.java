package com.payvix.common.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomizerUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();//for private static final nomenclature should be in cappitals

    public static String randomBase64(int length) {

        byte[] buf = new byte[length];
        SECURE_RANDOM.nextBytes(buf);
//        [4, 12, 100, -12] {-128, 127}
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
        //Base64 Class helps to do lot of operations on Base64 and one of which is to encode a stirng to make it safe URL, this string can put put inside URL and it gives the assurity that the O?P that gets generated from this string is going to be safe to put in URL it doestnt contain charsters like %2 or %04
        //Base64 String  will be like hgfdsrtuikjhvcvb== to remove this == we use withoutPadding())
        //Base64 is the combination of a-z,A-Z,0-9,-,_ = 26+26+10+2 = 64

    }
}
