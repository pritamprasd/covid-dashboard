package com.pritamprasad.covid_data_provider.util;

import io.jsonwebtoken.Jwt;

import java.util.Date;

import static com.pritamprasad.covid_data_provider.util.Messages.JWT_DECODE_TOKEN_SEPARATOR;
import static com.pritamprasad.covid_data_provider.util.Messages.JWT_WILL_EXPIRE_IN_MSG;
import static java.lang.Math.abs;

public class Helper {
    private Helper() {
    }

    public static String normalizeDate(String date) {
        String[] split = date.split("-");
        final int dateIndex = split.length - 1;
        split[dateIndex] = split[dateIndex].length() == 1 ? "0" + split[dateIndex] : split[dateIndex];
        return String.join("-", split);
    }

    public static String formJwtDecodeResponse(final Jwt jwt, final Date expire, final Long currentTimeStamp) {
        return new StringBuilder()
                .append(jwt.getHeader().toString())
                .append(JWT_DECODE_TOKEN_SEPARATOR)
                .append(jwt.getBody().toString())
                .append(JWT_DECODE_TOKEN_SEPARATOR)
                .append(JWT_WILL_EXPIRE_IN_MSG)
                .append(getRemainingTimeForJwtToken(currentTimeStamp, expire))
                .append(" seconds").toString();
    }

    private static double getRemainingTimeForJwtToken(final Long currentTimeStamp, final Date expire) {
        return abs(new Date(currentTimeStamp).getTime() - expire.getTime()) / 1000.0;
    }
}
