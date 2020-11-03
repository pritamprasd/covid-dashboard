package com.pritamprasad.covid_data_provider.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "logs")
@Entity
@JsonIgnoreProperties
@Getter
@Setter
public class LatestLogEndpointResponse {
    @Id
    private UUID id = UUID.randomUUID();

    @JsonProperty("update")
    private String update;

    @JsonProperty("timestamp")
    private Long timestamp;

    @Override
    public String toString() {
        return String.format("Log timestamp: %d, message: %s",timestamp,update);
    }
}
