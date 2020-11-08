package com.pritamprasad.covid_data_provider.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public abstract class BaseResponse {

    private long id;
    private String name;
    private List<Counts> counts;
    @Setter
    @Getter
    public static class Counts {
        private Long confirmed;
        private Long deceased;
        private Long recovered;
        private Long tested;
        private LocalDate date;
    }
}
