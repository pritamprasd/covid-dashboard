package com.pritamprasad.covid_data_provider.util;

import com.pritamprasad.covid_data_provider.models.BaseResponse;
import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.repository.MetaDataEntity;

public class Helper {
    private Helper(){}

    public static Counts getCountFromMeta(final MetaDataEntity m) {
        final Counts counts = new Counts();
        counts.setConfirmed(m.getConfirmed());
        counts.setDeceased(m.getDeceased());
        counts.setRecovered(m.getRecovered());
        counts.setTested(m.getTested());
        counts.setDate(m.getCreatedDate());
        return counts;
    }

    public static String normalizeDate(String date) {
        String[] split = date.split("-");
        final int dateIndex = split.length - 1;
        split[dateIndex] = split[dateIndex].length() == 1 ? "0" + split[dateIndex] : split[dateIndex];
        return String.join( "-",split);
    }
}
