package com.depromeet.ahmatda.ip;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IpResponse {
    private String ip;
    private String hostName;
    private String canonicalName;
}
