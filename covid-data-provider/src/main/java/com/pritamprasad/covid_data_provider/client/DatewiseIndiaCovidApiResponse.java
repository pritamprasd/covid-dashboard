package com.pritamprasad.covid_data_provider.client;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DatewiseIndiaCovidApiResponse {

    @Getter
    private Map<String, State> stateMap = new HashMap<>();

    @Getter
    @Setter
    private LocalDate dateStamp;


//    private Map<String, State> stateMap;
//    public Map<String, State> getStateMap(){
//        return stateMap == null ? new HashMap<>() : stateMap;
//    }

    @Getter
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
    public static class Meta {
        @SerializedName("population")
        private Long population;

        @SerializedName("tested")
        private Tested tested;
    }

    private static class Tested {
        @SerializedName("last_updated")
        private String last_updated;

        @SerializedName("source")
        private String source;
    }

    @Getter
    public static class Districts {
        @SerializedName("delta")
        private MetaData delta;

        @SerializedName("meta")
        private Meta meta;

        @SerializedName("total")
        private MetaData total;
    }
}
