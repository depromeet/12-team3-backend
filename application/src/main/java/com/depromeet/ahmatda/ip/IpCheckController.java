package com.depromeet.ahmatda.ip;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class IpCheckController {

    @PostMapping("/api/ip")
    public IpResponse getIp() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ip = inetAddress.getHostAddress();
        String hostName = inetAddress.getHostName();
        String canonicalName = inetAddress.getCanonicalHostName();

        return IpResponse.builder()
                .ip(ip)
                .hostName(hostName)
                .canonicalName(canonicalName)
                .build();
    }
}
