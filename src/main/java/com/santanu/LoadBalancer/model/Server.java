package com.santanu.LoadBalancer.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class Server {

    private int id;

    @NotEmpty(message = "Ip address can't be null or empty")
    @Size(min = 11, max = 27, message = "Ip should be atleast 11 character long upto 27 character ")
    @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$",
    message = "This violets the Ipv4 pattern")
    private String serverIp;

    @NotNull(message = "Port number can't be null or empty")
    @Positive(message = "Port number must be a positive integer")
    @Range(min = 2500, max = 60000, message = "Port number should be in between 2500-60000")
    private int port;

    @NotNull(message = "Max-weight can't be null or empty")
    @Positive(message = "Max-weight must be a positive integer")
    private int maxWeight;

    private Long currentLoad;

    @Value("0")
    private float loadFactor; // (activeConnection/ maxWeight)

    private Boolean status;

    @NotEmpty(message = "Server-url can't be empty or null")
    @Pattern(regexp = "^(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?$", message = "This is not a url")
    private String serverURL;

}
