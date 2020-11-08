package com.pritamprasad.covid_data_provider.client;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DatewiseIndiaCovidApiResponse {

    @Getter
    private Map<String, State> stateMap = new HashMap<>();

    @Getter
    @Setter
    private LocalDate dateStamp;

    @Getter
    @ToString
    public static class State {
        @SerializedName("delta")
        private MetaData delta;

        @SerializedName("meta")
        private Meta meta;

        @SerializedName("districts")
        private Map<String, Districts> districts;

        @SerializedName("total")
        private MetaData total;
    }

    @Getter
    @ToString
    public static class Meta {
        @SerializedName("population")
        private Long population;

        @SerializedName("tested")
        private Tested tested;
    }

    @ToString
    private static class Tested {
        @SerializedName("last_updated")
        private String last_updated;

        @SerializedName("source")
        private String source;
    }

    @Getter
    @ToString
    public static class Districts {
        @SerializedName("delta")
        private MetaData delta;

        @SerializedName("meta")
        private Meta meta;

        @SerializedName("total")
        private MetaData total;
    }
}
