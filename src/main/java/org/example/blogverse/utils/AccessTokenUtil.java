package org.example.blogverse.utils;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

public class AccessTokenUtil {

    private static int MAX_ACCESSIBLE_TIME = 30;

    public static String createAccessToken() {
        String token = UUID.randomUUID().toString();
        DateTime currentTime = DateTime.now();
        return token + "@" + currentTime.toString();
    }

    public static boolean isTokenValid(String token) {
        // this function checks whether the token that user has is valid or not?
        // well the token assigned expires in 30 min
        DateTime previousTime = DateTime.parse(token.split("@")[1]);
        Period period = new Period(previousTime, DateTime.now());
        return period.getMinutes() < MAX_ACCESSIBLE_TIME;
    }


}
