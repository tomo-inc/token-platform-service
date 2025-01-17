package com.tomo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SolKlineDTO implements Serializable {
    @JsonIgnore
    private String pair;
    @JsonIgnore
    private String base;
    @JsonProperty(value = "o")
    private Double open;
    @JsonProperty(value = "h")
    private Double high;
    @JsonProperty(value = "l")
    private Double low;
    @JsonProperty(value = "c")
    private Double close;
    @JsonProperty(value = "v")
    private Double volume;
    @JsonProperty(value = "t")
    private Long time;
}
