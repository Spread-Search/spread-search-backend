package com.spreadsearch.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MainSpreadResponse {
    private List<SpreadResponse> makeMakeSpread;
    private List<SpreadResponse> takeMakeSpread;
    private List<SpreadResponse> makeTakeResponse;
    private List<SpreadResponse> takeTakeResponse;
}
